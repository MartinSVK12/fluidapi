package net.sunsetsatellite.fluidapi;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class GuiFluidTank extends GuiFluid {

    String name = "";
    public GuiFluidTank(InventoryPlayer inventoryPlayer, TileEntityFluidItemContainer tile, String guiName) {
        super(new ContainerFluidTank(inventoryPlayer, tile));
        name = guiName;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("/fluidapi/tank_gui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);

    }

    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(name, 64, 6, 0xFF404040);
    }


    public void initGui()
    {
        super.initGui();
    }
}
