package sunsetsatellite.fluidapi.template.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import net.minecraft.src.helper.Color;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.api.*;
import sunsetsatellite.fluidapi.render.RenderFluid;
import sunsetsatellite.fluidapi.template.containers.ContainerMultiFluidTank;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMassFluidItemContainer;

import java.util.ArrayList;
import java.util.Objects;

public class GuiMultiFluidTank extends GuiContainer {

    public String name = "Multi Fluid Tank";
    public TileEntityMassFluidItemContainer tile;
    public RenderItem itemRender;
    public ArrayList<FluidLayer> fluidLayers = new ArrayList<>();
    {
        try {
            itemRender = (RenderItem) FluidAPI.getPrivateValue(GuiContainer.class, this,"itemRenderer");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public GuiMultiFluidTank(InventoryPlayer inventoryPlayer, TileEntity tile) {
        super(new ContainerMultiFluidTank(inventoryPlayer, (TileEntityMassFluidItemContainer) tile));
        this.tile = (TileEntityMassFluidItemContainer) tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("assets/fluidapi/gui/multitank_gui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        drawFluidTank(j+62,k+18,54,54);
    }

    protected void drawFluidTank(int x, int y, int sizeX, int sizeY) {
        tile.fluidContents.removeIf(Objects::isNull);
        int i = sizeY;
        int id = 0;
        fluidLayers.clear();
        for (FluidStack fluidStack : tile.fluidContents) {
            if(fluidStack != null) {
                BlockFluid fluid = fluidStack.liquid;
                if (fluidStack.liquid != null) {
                    int j = (width - xSize) / 2;
                    int k = (height - ySize) / 2;
                    int fluidBarSize = (int) FluidAPI.map(fluidStack.amount,0,tile.fluidCapacity,2,sizeY-3);
                    RenderFluid.drawFluidIntoGui(fontRenderer, this.mc.renderEngine, fluid.blockID, 0, fluid.getBlockTextureFromSide(0), x, y+i-fluidBarSize-2, sizeX-2, fluidBarSize);
                    if (fluidStack.getLiquid() == Block.fluidWaterFlowing && Minecraft.getMinecraft().gameSettings.biomeWater.value) {
                        Color c = new Color().setARGB(Block.fluidWaterFlowing.colorMultiplier(this.mc.theWorld, this.mc.theWorld, tile.xCoord, tile.yCoord, tile.zCoord));
                        c.setRGBA(c.getRed(), c.getGreen(), c.getBlue(), 0x40);
                        RenderFluid.drawFluidIntoGui(fontRenderer, this.mc.renderEngine, fluid.blockID, 0, fluid.getBlockTextureFromSide(0), x, y+i-fluidBarSize-2, sizeX-2, fluidBarSize, c.value);
                    }
                    fluidLayers.add(new FluidLayer(id,x,y+i-fluidBarSize-2,sizeX-2,fluidBarSize,fluidStack));
                    id++;

                    i-=fluidBarSize-1;
                }
            }
        }
    }

    public FluidLayer getFluidLayerAtPosition(int x, int y){
        if(!fluidLayers.isEmpty()){
            for (FluidLayer fluidLayer : fluidLayers) {
                if(this.getIsMouseOverFluidLayer(fluidLayer, x, y)) {
                    return fluidLayer;
                }
            }
        }
        return null;
    }

    public boolean getIsMouseOverFluidLayer(FluidLayer fluidLayer, int x, int y) {
        return x >= fluidLayer.x - 1 && x < fluidLayer.x + fluidLayer.sizeX + 1 && y >= fluidLayer.y - 1 && y < fluidLayer.y + fluidLayer.sizeY + 1;
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        super.drawScreen(x, y, renderPartialTicks);
        FluidLayer layer = getFluidLayerAtPosition(x,y);
        if(layer != null){
            String name = StringTranslate.getInstance().translateNamedKey(layer.getFluidStack().getLiquid().getBlockName(0)).replace("Flowing ","").replace("Still ","");
            String amount = layer.getFluidStack().amount+"/"+tile.getFluidCapacityForSlot(layer.id)+" mB";
            String percent = " ("+Math.round(((float)layer.getFluidStack().amount/(float)tile.getFluidCapacityForSlot(layer.id))*100)+"%)";
            drawTooltip(name+"\n"+ChatColor.lightGray+amount+percent,x,y,8,-8,true);
        }
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        if(button == 0){
            FluidLayer layer = getFluidLayerAtPosition(x,y);
            if(layer != null){
                tile.fluidContents.sort((F1,F2)->{
                    if(F1 == layer.fluidStack) return -1;
                    if(F2 == layer.fluidStack) return 1;
                    return 0;
                });
            }
        }
    }

    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(name, 48, 6, 0xFF404040);
        int l = 16;
    }


    public void initGui()
    {
        super.initGui();
    }
}
