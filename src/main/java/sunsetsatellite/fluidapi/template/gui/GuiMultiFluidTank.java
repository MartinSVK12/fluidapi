package sunsetsatellite.fluidapi.template.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.helper.Color;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.api.ContainerFluid;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.api.GuiFluid;
import sunsetsatellite.fluidapi.api.SlotFluid;
import sunsetsatellite.fluidapi.template.containers.ContainerFluidTank;
import sunsetsatellite.fluidapi.template.containers.ContainerMultiFluidTank;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMassFluidContainer;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMassFluidItemContainer;

import java.util.ArrayList;
import java.util.Objects;

//TODO: Make fluid layers hoverable and clickable
//TODO: Fluid rendering inside the actual block
public class GuiMultiFluidTank extends GuiContainer {

    public String name = "Multi Fluid Tank";
    public TileEntityMassFluidItemContainer tile;
    public RenderItem itemRender;
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
        for (FluidStack fluidStack : tile.fluidContents) {
            if(fluidStack != null) {
                BlockFluid fluid = fluidStack.liquid;
                if (fluidStack.liquid != null) {
                    int j = (width - xSize) / 2;
                    int k = (height - ySize) / 2;
                    int fluidBarSize = (int) FluidAPI.map(fluidStack.amount,0,tile.fluidCapacity,2,sizeY);
                    drawItemIntoGui(fontRenderer, this.mc.renderEngine, fluid.blockID, 0, fluid.getBlockTextureFromSide(0), x, y+i-fluidBarSize-2, sizeX-2, fluidBarSize,1.0f,1.0f);
                    if (fluidStack.getLiquid() == Block.fluidWaterFlowing && Minecraft.getMinecraft().gameSettings.biomeWater.value) {
                        Color c = new Color().setARGB(Block.fluidWaterFlowing.colorMultiplier(this.mc.theWorld, this.mc.theWorld, tile.xCoord, tile.yCoord, tile.zCoord));
                        //TODO: Water color is broken again :(
                        //c.setRGBA(c.getRed(), c.getGreen(), c.getBlue(), 0x40);
                        //c.setRGBA(0,0,255,255);
                        //this.drawRect(j, k+i, j+16, (k+i)+16, c.value);
                    }
                    i-=fluidBarSize-1;
                }
            }
        }
    }

    public void drawItemIntoGui(FontRenderer fontrenderer, RenderEngine renderengine, int id, int meta, int iconIndex, int x, int y, int sizeX, int sizeY, float brightness, float alpha) {
        GL11.glDisable(2896);
        int tileWidth;
        if (id < Block.blocksList.length) {
            renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
            tileWidth = TextureFX.tileWidthTerrain;
        } else {
            renderengine.bindTexture(renderengine.getTexture("/gui/items.png"));
            tileWidth = TextureFX.tileWidthItems;
        }
        GL11.glColor4f(brightness, brightness, brightness, alpha);
        renderTexturedQuad(x, y, sizeX, sizeY, iconIndex % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth, iconIndex / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth, tileWidth, tileWidth);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
    }

    public void renderTexturedQuad(int x, int y, int sizeX, int sizeY, int tileX, int tileY, int tileWidth, int tileHeight) {
        float f = 0.0F;
        float f1 = 1.0F / (float)(net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
        float f2 = 1.0F / (float)(net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * tileHeight);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + sizeY, 0.0, (float)(tileX) * f1, (float)(tileY + tileHeight) * f2);
        tessellator.addVertexWithUV(x + sizeX, y + sizeY, 0.0, (float)(tileX + tileWidth) * f1, (float)(tileY + tileHeight) * f2);
        tessellator.addVertexWithUV(x + sizeX, y, 0.0, (float)(tileX + tileWidth) * f1, (float)(tileY) * f2);
        tessellator.addVertexWithUV(x, y, 0.0, (float)(tileX) * f1, (float)(tileY) * f2);
        tessellator.draw();
    }

    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(name, 48, 6, 0xFF404040);
        int l = 16;
        for (FluidStack fluidStack : tile.fluidContents) {
            String name = StringTranslate.getInstance().translateNamedKey(fluidStack.getLiquid().getBlockName(0));
            fontRenderer.drawString(name.replace("Flowing ","").replace("Still ","")+" | "+fluidStack.amount,4,l,0xFF404040);
            l+=10;
        }

    }


    public void initGui()
    {
        super.initGui();
    }
}
