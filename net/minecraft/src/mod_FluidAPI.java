package net.minecraft.src;

import net.sunsetsatellite.fluidapi.*;

import java.util.HashMap;

public class mod_FluidAPI extends BaseMod {

    public static Config config;

    public mod_FluidAPI(){
        Config.init();

        if(Config.getFromConfig("enableTank",1) == 1){
            tankTex = ModLoader.addOverride("/terrain.png", "/fluidapi/" + "tank.png");
            fluidTank = new BlockFluidTank(Config.getFromConfig("fluidTank",200),tankTex,Material.glass).setBlockName("fluidTank");
            ModLoader.RegisterBlock(fluidTank);
            ModLoader.RegisterTileEntity(TileEntityFluidTank.class, "Fluid Tank", new RenderFluidInBlock());
            ModLoader.AddName(fluidTank, "Fluid Tank");
        }
        if(Config.getFromConfig("enablePipes",1) == 1){
            pipeTex = ModLoader.addOverride("/terrain.png", "/fluidapi/" + "pipe.png");
            fluidPipe = new BlockFluidPipe(Config.getFromConfig("fluidPipe",201),pipeTex).setBlockName("fluidPipe");
            ModLoader.RegisterBlock(fluidPipe);
            ModLoader.RegisterTileEntity(TileEntityFluidPipe.class,"Fluid Pipe", new RenderFluidInPipe());
            ModLoader.AddName(fluidPipe, "Fluid Pipe");
        }
        if(Config.getFromConfig("enablePump",1) == 1){
            fluidPump = new BlockFluidPump(Config.getFromConfig("fluidPump",202),tankTex,Material.glass).setBlockName("fluidPump");
            pumpTex = ModLoader.addOverride("/terrain.png", "/fluidapi/" + "pump.png");
            ModLoader.RegisterBlock(fluidPump);
            ModLoader.AddName(fluidPump,"Fluid Pump");
            ModLoader.RegisterTileEntity(TileEntityFluidPump.class,"Fluid Pump", new RenderFluidInBlock());
        }

        ModLoader.RegisterTileEntity(TileEntityFluidItemContainer.class,"Generic Fluid & Item Container");
        ModLoader.RegisterTileEntity(TileEntityFluidContainer.class,"Generic Fluid Container");

        pipeRenderId = ModLoader.getUniqueBlockModelID(this, false);

    }

    @Override
    public void ModsLoaded() {
        super.ModsLoaded();
        registerFluids();
    }

    public boolean RenderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) {
        return block.getRenderType() == pipeRenderId && RenderPipeBlock.render(renderblocks, iblockaccess, i, j, k, block, l);
    }

    public static int tankTex = 0;//ModLoader.addOverride("/terrain.png", "/fluidapi/" + "tank.png");
    public static int pipeTex = 0;//ModLoader.addOverride("/terrain.png", "/fluidapi/" + "pipe.png");
    public static int pumpTex = 0;//ModLoader.addOverride("/terrain.png", "/fluidapi/" + "pump.png");


    public static Block fluidTank = null;//new BlockFluidTank(200,tankTex,Material.glass).setBlockName("fluidTank");
    public static Block fluidPipe = null;//new BlockFluidPipe(201,pipeTex).setBlockName("fluidPipe");
    public static Block fluidPump = null;//new BlockFluidPump(202,tankTex,Material.glass).setBlockName("fluidPump");

    public static HashMap<ItemBucket, BlockFluid> fluids = new HashMap<>();
    public static HashMap<BlockFluid, ItemBucket> fluidsInv = new HashMap<>();

    public static int pipeRenderId;

    public static void registerFluids(){
        for (Item b : Item.itemsList) {
            try {
                if(b instanceof ItemBucket){
                    int fluidId = (int) ModLoader.getPrivateValue(ItemBucket.class,b,0);
                    if(fluidId > 0){
                        fluids.put((ItemBucket) b,(BlockFluid) Block.blocksList[fluidId]);
                        fluidsInv.put((BlockFluid) Block.blocksList[fluidId],(ItemBucket) b);
                    }
                }
            } catch (NoSuchFieldException ignored) {}
        }

        System.out.println("[FluidAPI] Registered fluids:");
        fluids.forEach((K,V)->{
            System.out.println(K.getItemName() + " -> " + V.getBlockName());
        });
    }

    public static double map(double valueCoord1,
                             double startCoord1, double endCoord1,
                             double startCoord2, double endCoord2) {

        final double EPSILON = 1e-12;
        if (Math.abs(endCoord1 - startCoord1) < EPSILON) {
            throw new ArithmeticException("Division by 0");
        }

        double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return ratio * (valueCoord1 - startCoord1) + startCoord2;
    }

    @Override
    public String Version() {
        return "1.0";
    }

    public String Name() {
        return "FluidAPI";
    }

    public String Description() {
        return "Better fluid storage and transport.";
    }
}
