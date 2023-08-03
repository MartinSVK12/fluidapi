package sunsetsatellite.fluidapi.template.gui;


import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;
import sunsetsatellite.fluidapi.api.GuiFluid;
import sunsetsatellite.fluidapi.template.containers.ContainerMachine;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMachine;

public class GuiMachine extends GuiFluid {

    public String name = "Fluid Machine";
    public GuiMachine(InventoryPlayer inventoryPlayer, TileEntity tile) {
        super(new ContainerMachine(inventoryPlayer, (TileEntityFluidItemContainer) tile),inventoryPlayer);
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
