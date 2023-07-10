package sunsetsatellite.fluidapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
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
import sunsetsatellite.fluidapi.util.Config;
import turniplabs.halplibe.helper.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class FluidAPI implements ModInitializer {
    public static final String MOD_ID = "fluidapi";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static HashMap<String, ArrayList<Class<?>>> nameToGuiMap = new HashMap<>();

    public FluidAPI(){
        Config.init();
        PacketAccessor.callAddIdClassMapping(Config.getFromConfig("PacketSetFluidSlotID",110),true,false, PacketSetFluidSlot.class);
        PacketAccessor.callAddIdClassMapping(Config.getFromConfig("PacketFluidWindowClickID",111),false,true, PacketFluidWindowClick.class);
        PacketAccessor.callAddIdClassMapping(Config.getFromConfig("PacketUpdateClientFluidRenderID",112),true,false, PacketUpdateClientFluidRender.class);

        if(Config.getFromConfig("enableMultiTank",1) == 1){
            fluidTank = BlockHelper.createBlock(MOD_ID,new BlockMultiFluidTank(Config.getFromConfig("multiFluidTank",906),Material.glass),"multiFluidTank","tank.png",Block.soundGlassFootstep,1,1,0);
            EntityHelper.createSpecialTileEntity(TileEntityMultiFluidTank.class,new RenderMultiFluidInBlock(),"Multi Fluid Tank");
            addToNameGuiMap("Multi Fluid Tank", GuiMultiFluidTank.class, TileEntityMultiFluidTank.class);

        }
        if(Config.getFromConfig("enableTank",1) == 1){
            fluidTank = BlockHelper.createBlock(MOD_ID,new BlockFluidTank(Config.getFromConfig("fluidTank",900),Material.glass),"fluidTank","tank.png",Block.soundGlassFootstep,1,1,0);
            EntityHelper.createSpecialTileEntity(TileEntityFluidTank.class,new RenderFluidInBlock(),"Fluid Tank");
            addToNameGuiMap("Fluid Tank", GuiFluidTank.class, TileEntityFluidTank.class);

        }
        if(Config.getFromConfig("enablePipes",1) == 1){
            fluidPipe = BlockHelper.createBlock(MOD_ID,new BlockFluidPipe(Config.getFromConfig("fluidPipe",901)),"fluidPipe","pipe.png",Block.soundGlassFootstep,1,1,0);
            EntityHelper.createSpecialTileEntity(TileEntityFluidPipe.class,new RenderFluidInPipe(),"Fluid Pipe");

        }
        if(Config.getFromConfig("enableMachine",1) == 1){
            fluidMachine = BlockHelper.createBlock(MOD_ID,new BlockMachine(Config.getFromConfig("fluidMachine",902),Material.glass),"fluidMachine","tank.png","tank.png","machine.png","tank.png","tank.png","tank.png",Block.soundGlassFootstep,1,1,0);
            EntityHelper.createSpecialTileEntity(TileEntityMachine.class,new RenderFluidInBlock(),"Fluid Machine");
            addToNameGuiMap("Fluid Machine", GuiMachine.class, TileEntityMachine.class);
        }
    }

    @Override
    public void onInitialize() {
        Config.init();
        if(Config.getFromConfig("enableOil",1) == 1){
            oilTex = registerFluidTexture(MOD_ID,"oil.png");
            oilFlowing = BlockHelper.createBlock(MOD_ID,new BlockFluidFlowing(Config.getFromConfig("oil",903),Material.water),"oilFlowing","oil.png",Block.soundPowderFootstep,1.0f,1.0f,0).setNotInCreativeMenu().setPlaceOverwrites().setTexCoords(oilTex[0],oilTex[1],oilTex[2],oilTex[3],oilTex[4],oilTex[5],oilTex[6],oilTex[7],oilTex[8],oilTex[9],oilTex[10],oilTex[11]);
            oilStill = BlockHelper.createBlock(MOD_ID,new BlockFluidStill(Config.getFromConfig("oil",903)+1,Material.water),"oilStill","oil.png",Block.soundPowderFootstep,1.0f,1.0f,0).setNotInCreativeMenu().setPlaceOverwrites().setTexCoords(oilTex[0],oilTex[1],oilTex[2],oilTex[3],oilTex[4],oilTex[5],oilTex[6],oilTex[7],oilTex[8],oilTex[9],oilTex[10],oilTex[11]);
            bucketOil = ItemHelper.createItem(MOD_ID,new ItemBucket(Config.getFromConfig("bucketOil",500),oilFlowing.blockID),"bucketOil","bucketOil.png").setContainerItem(Item.bucket);
        }
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
            Minecraft.getMinecraft().displayGuiScreen(guiScreen);
        }
    }

    public static void addToNameGuiMap(String name, Class<? extends Gui> guiClass, Class<? extends TileEntity> tileEntityClass){
        ArrayList<Class<?>> list = new ArrayList<>();
        list.add(guiClass);
        list.add(tileEntityClass);
        nameToGuiMap.put(name,list);
    }


}
