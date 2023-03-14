package sunsetsatellite.fluidapi.template.gui;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.fluidapi.api.GuiFluid;
import sunsetsatellite.fluidapi.template.containers.ContainerFluidTank;

public class GuiFluidTank extends GuiFluid {

    String name = "Fluid Tank";
    public GuiFluidTank(InventoryPlayer inventoryPlayer, TileEntity tile) {
        super(new ContainerFluidTank(inventoryPlayer, (TileEntityFluidItemContainer) tile),inventoryPlayer);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("assets/fluidapi/gui/tank_gui.png");
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
