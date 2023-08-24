package sunsetsatellite.fluidapi;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.client.sound.block.BlockSounds;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.BlockFluidFlowing;
import net.minecraft.core.block.BlockFluidStill;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayerMP;
import sunsetsatellite.fluidapi.mixin.accessors.PacketAccessor;
import sunsetsatellite.fluidapi.mp.packets.PacketFluidWindowClick;
import sunsetsatellite.fluidapi.mp.packets.PacketSetFluidSlot;
import sunsetsatellite.fluidapi.mp.packets.PacketUpdateClientFluidRender;
import sunsetsatellite.fluidapi.render.RenderFluidInBlock;
import sunsetsatellite.fluidapi.render.RenderFluidInPipe;
import sunsetsatellite.fluidapi.render.RenderMultiFluidInBlock;
import sunsetsatellite.fluidapi.template.blocks.BlockFluidPipe;
import sunsetsatellite.fluidapi.template.blocks.BlockFluidTank;
import sunsetsatellite.fluidapi.template.blocks.BlockMachine;
import sunsetsatellite.fluidapi.template.blocks.BlockMultiFluidTank;
import sunsetsatellite.fluidapi.template.gui.GuiFluidTank;
import sunsetsatellite.fluidapi.template.gui.GuiMachine;
import sunsetsatellite.fluidapi.template.gui.GuiMultiFluidTank;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidPipe;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidTank;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMachine;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMultiFluidTank;
import sunsetsatellite.sunsetutils.util.Config;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.helper.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FluidAPI implements ModInitializer {
    public static final String MOD_ID = "fluidapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static HashMap<String, ArrayList<Class<?>>> nameToGuiMap = new HashMap<>();

    public static final Config config = new Config(MOD_ID, mapOf(new String[]{"enableTank","enablePump","enablePipes","enableMachine","enableOil","GuiID","PacketSetFluidSlotID","PacketFluidWindowClickID","PacketUpdateClientFluidRender"},new String[]{"0","0","0","0","0","8","110","111","112"}), new Class[]{FluidAPI.class});

    public FluidAPI(){
        PacketAccessor.callAddIdClassMapping(config.getFromConfig("PacketSetFluidSlotID",110),true,false, PacketSetFluidSlot.class);
        PacketAccessor.callAddIdClassMapping(config.getFromConfig("PacketFluidWindowClickID",111),false,true, PacketFluidWindowClick.class);
        PacketAccessor.callAddIdClassMapping(config.getFromConfig("PacketUpdateClientFluidRenderID",112),true,false, PacketUpdateClientFluidRender.class);

        if(config.getFromConfig("enableMultiTank",1) == 1){
            multiFluidTank = new BlockBuilder(MOD_ID).setTextures("tank.png").setBlockSound(BlockSounds.GLASS).setHardness(1).setResistance(1).build(new BlockMultiFluidTank("multiFluidTank",config.getFromConfig("multiFluidTank",906),Material.glass));
            EntityHelper.createSpecialTileEntity(TileEntityMultiFluidTank.class,new RenderMultiFluidInBlock(),"Multi Fluid Tank");
            addToNameGuiMap("Multi Fluid Tank", GuiMultiFluidTank.class, TileEntityMultiFluidTank.class);

        }
        if(config.getFromConfig("enableTank",1) == 1){
            fluidTank = new BlockBuilder(MOD_ID).setTextures("tank.png").setBlockSound(BlockSounds.GLASS).setHardness(1).setResistance(1).build(new BlockFluidTank("fluidTank",config.getFromConfig("fluidTank",907),Material.glass));
            EntityHelper.createSpecialTileEntity(TileEntityFluidTank.class,new RenderFluidInBlock(),"Fluid Tank");
            addToNameGuiMap("Fluid Tank", GuiFluidTank.class, TileEntityFluidTank.class);

        }
        if(config.getFromConfig("enablePipes",1) == 1){
            fluidPipe = new BlockBuilder(MOD_ID).setTextures("pipe.png").setBlockSound(BlockSounds.GLASS).setHardness(1).setResistance(1).build(new BlockFluidPipe("fluidPipe",config.getFromConfig("fluidPipe",951)));
            EntityHelper.createSpecialTileEntity(TileEntityFluidPipe.class,new RenderFluidInPipe(),"Fluid Pipe");

        }
        if(config.getFromConfig("enableMachine",1) == 1){
            fluidMachine = new BlockBuilder(MOD_ID).setTextures("tank.png").setNorthTexture("machine.png").setBlockSound(BlockSounds.GLASS).setHardness(1).setResistance(1).build(new BlockMachine("fluidMachine",config.getFromConfig("fluidMachine",952),Material.glass));
            EntityHelper.createSpecialTileEntity(TileEntityMachine.class,new RenderFluidInBlock(),"Fluid Machine");
            addToNameGuiMap("Fluid Machine", GuiMachine.class, TileEntityMachine.class);
        }
    }

    @Override
    public void onInitialize() {
        if(config.getFromConfig("enableOil",1) == 1){
            oilTex = registerFluidTexture(MOD_ID,"oil.png");
            oilFlowing = new BlockBuilder(MOD_ID).setTextures("oil.png").setBlockSound(BlockSounds.DEFAULT).setHardness(1).setResistance(1).build(new BlockFluidFlowing("oilFlowing",config.getFromConfig("oilFlowing",955),Material.water).withTexCoords(oilTex[0],oilTex[1],oilTex[2],oilTex[3],oilTex[4],oilTex[5],oilTex[6],oilTex[7],oilTex[8],oilTex[9],oilTex[10],oilTex[11]).withTags(BlockTags.NOT_IN_CREATIVE_MENU,BlockTags.PLACE_OVERWRITES));
            oilStill = new BlockBuilder(MOD_ID).setTextures("oil.png").setBlockSound(BlockSounds.DEFAULT).setHardness(1).setResistance(1).build(new BlockFluidStill("oilStill",config.getFromConfig("oilStill",956),Material.water).withTexCoords(oilTex[0],oilTex[1],oilTex[2],oilTex[3],oilTex[4],oilTex[5],oilTex[6],oilTex[7],oilTex[8],oilTex[9],oilTex[10],oilTex[11]).withTags(BlockTags.NOT_IN_CREATIVE_MENU,BlockTags.PLACE_OVERWRITES));
            bucketOil = ItemHelper.createItem(MOD_ID,new ItemBucket(HalpLibe.addModId(MOD_ID,"bucketOil"),config.getFromConfig("bucketOil",17500),oilFlowing),"bucketOil","bucketOil.png").setContainerItem(Item.bucket);
        }
        BlockModelDispatcher.getInstance().addDispatch(oilFlowing,new BlockModelRenderBlocks(4));
        BlockModelDispatcher.getInstance().addDispatch(oilStill,new BlockModelRenderBlocks(4));

        LOGGER.info("Loading plugins..");
        FabricLoader.getInstance().getEntrypointContainers("fluidapi", FluidAPIPlugin.class).forEach(plugin -> {
            plugin.getEntrypoint().initializePlugin(LOGGER);
        });
        LOGGER.info("Fluid registry contains "+FluidRegistry.registry.size()+" entries.");
        LOGGER.info("FluidAPI initialized.");
    }
    public static int[] registerFluidTexture(String modId, String texture) {
        int[] origin = BlockCoords.nextCoords();
        TextureHelper.addTextureToTerrain(modId, texture, origin[0], origin[1]);
        /*TextureHelper.addTextureToTerrain(modId, texture, origin[0] + 1, origin[1]);
        TextureHelper.addTextureToTerrain(modId, texture, origin[0] + 2, origin[1]);
        TextureHelper.addTextureToTerrain(modId, texture, origin[0] + 1, origin[1] + 1);
        TextureHelper.addTextureToTerrain(modId, texture, origin[0] + 2, origin[1] + 1);*/
        return new int[]{origin[0], origin[1], origin[0], origin[1], origin[0], origin[1], origin[0], origin[1], origin[0], origin[1], origin[0], origin[1]};
        //return new int[]{origin[0], origin[1], origin[0] + 1, origin[1] + 1, origin[0] + 2, origin[1] + 1, origin[0] + 1, origin[1], origin[0] + 2, origin[1], origin[0], origin[1]};
    }

    public static Block fluidTank;
    public static Block multiFluidTank;
    public static Block fluidPipe;
    public static Block fluidMachine;
    public static Block oilFlowing;
    public static Block oilStill;
    public static Item bucketOil;
    public static int[] oilTex;

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

    public static <K,V> Map<K,V> mapOf(K[] keys, V[] values){
        if(keys.length != values.length){
            throw new IllegalArgumentException("Arrays differ in size!");
        }
        HashMap<K,V> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i],values[i]);
        }
        return map;
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

    public static void displayGui(EntityPlayer entityplayer, GuiScreen guiScreen, Container container, IInventory tile) {
        if(entityplayer instanceof EntityPlayerMP) {
            ((IEntityPlayerMP)entityplayer).displayGuiScreen_fluidapi(guiScreen,container,tile);
        } else {
            Minecraft.getMinecraft(Minecraft.class).displayGuiScreen(guiScreen);
        }
    }

    public static void addToNameGuiMap(String name, Class<? extends Gui> guiClass, Class<? extends TileEntity> tileEntityClass){
        ArrayList<Class<?>> list = new ArrayList<>();
        list.add(guiClass);
        list.add(tileEntityClass);
        nameToGuiMap.put(name,list);
    }


}
