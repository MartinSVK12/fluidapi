package sunsetsatellite.fluidapi;

import net.fabricmc.api.ModInitializer;
import net.minecraft.src.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.EntityHelper;

import java.lang.reflect.Field;
import java.util.HashMap;

public class FluidAPI implements ModInitializer {
    public static final String MOD_ID = "fluidapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public FluidAPI(){
        Config.init();

        if(Config.getFromConfig("enableTank",1) == 1){
            fluidTank = BlockHelper.createBlock(MOD_ID,new BlockFluidTank(Config.getFromConfig("fluidTank",900),Material.glass),"fluidTank","tank.png",Block.soundGlassFootstep,1,1,0);
            EntityHelper.createSpecialTileEntity(TileEntityFluidTank.class,new RenderFluidInBlock(),"Fluid Tank");

        }
        if(Config.getFromConfig("enablePipes",1) == 1){
            fluidPipe = BlockHelper.createBlock(MOD_ID,new BlockFluidPipe(Config.getFromConfig("fluidPipe",901)),"fluidPipe","pipe.png",Block.soundGlassFootstep,1,1,0);
            EntityHelper.createSpecialTileEntity(TileEntityFluidPipe.class,new RenderFluidInPipe(),"Fluid Pipe");

        }
        if(Config.getFromConfig("enableMachine",1) == 1){
            fluidMachine = BlockHelper.createBlock(MOD_ID,new BlockMachine(Config.getFromConfig("fluidMachine",902),Material.glass),"fluidMachine","tank.png","tank.png","machine.png","tank.png","tank.png","tank.png",Block.soundGlassFootstep,1,1,0);
            EntityHelper.createSpecialTileEntity(TileEntityMachine.class,new RenderFluidInBlock(),"Fluid Machine");
        }
    }

    @Override
    public void onInitialize() {
        Config.init();
        registerFluids();
        LOGGER.info("FluidAPI initialized.");
    }

    public static Block fluidTank;
    public static Block fluidPipe;
    //public static Block fluidPump;
    public static Block fluidMachine;

    public static HashMap<ItemBucket, BlockFluid> fluids = new HashMap<>();
    public static HashMap<BlockFluid, ItemBucket> fluidsInv = new HashMap<>();
    public static HashMap<ItemBucket, ItemBucketEmpty> fluidContainers = new HashMap<>();

    public static void registerFluids(){
        for (Item b : Item.itemsList) {
            try {
                if(b instanceof ItemBucket){
                    int fluidId = (int) FluidAPI.getPrivateValue(ItemBucket.class,b,0);
                    if(fluidId > 0){
                        fluids.put((ItemBucket) b,(BlockFluid) Block.blocksList[fluidId]);
                        fluidsInv.put((BlockFluid) Block.blocksList[fluidId],(ItemBucket) b);
                        fluidContainers.put((ItemBucket) b, (ItemBucketEmpty) b.getContainerItem());
                    }
                }
            } catch (NoSuchFieldException ignored) {}
        }

        LOGGER.info("Registered fluids:");
        fluids.forEach((K,V)->{
            LOGGER.info(K.getItemName() + " -> " + V.getBlockName(0));
        });
        LOGGER.info("Inverse:");
        fluidsInv.forEach((K,V)->{
            LOGGER.info(K.getBlockName(0) + " -> " + V.getItemName());
        });
        LOGGER.info("Containers:");
        fluidContainers.forEach((K,V)->{
            LOGGER.info(K.getItemName() + " -> " + V.getItemName());
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

    public static Object getPrivateValue(Class instanceclass, Object instance, int fieldindex) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            Field e = instanceclass.getDeclaredFields()[fieldindex];
            e.setAccessible(true);
            return e.get(instance);
        } catch (IllegalAccessException illegalAccessException4) {
            illegalAccessException4.printStackTrace();
            return null;
        }
    }

    public static Object getPrivateValue(Class instanceclass, Object instance, String field) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
        try {
            Field e = instanceclass.getDeclaredField(field);
            e.setAccessible(true);
            return e.get(instance);
        } catch (IllegalAccessException illegalAccessException4) {
            illegalAccessException4.printStackTrace();
            return null;
        }
    }


}
