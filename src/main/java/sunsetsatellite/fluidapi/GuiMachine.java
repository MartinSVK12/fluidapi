package sunsetsatellite.fluidapi;

import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;
import org.lwjgl.opengl.GL11;

public class GuiMachine extends GuiFluid {

    String name = "Fluid Machine";
    public GuiMachine(InventoryPlayer inventoryPlayer, TileEntity tile) {
        super(new ContainerExtractor(inventoryPlayer, (TileEntityFluidItemContainer) tile),inventoryPlayer);
        machine = (TileEntityMachine)tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1) {
        int i2 = this.mc.renderEngine.getTexture("/gui/furnace.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(i2);
        int i3 = (this.width - this.xSize) / 2;
        int i4 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.ySize);
        int i5;
        if(this.machine.isBurning()) {
            i5 = this.machine.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(i3 + 56, i4 + 36 + 12 - i5, 176, 12 - i5, 14, i5 + 2);
        }

        i5 = this.machine.getProgressScaled(24);
        this.drawTexturedModalRect(i3 + 79, i4 + 34, 176, 14, i5 + 1, 16);
    }

    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(name, 54, 6, 0xFF404040);
    }

    TileEntityMachine machine;
    public void initGui()
    {
        super.initGui();
    }
}