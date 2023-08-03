package sunsetsatellite.fluidapi.interfaces.mixins;


import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import sunsetsatellite.fluidapi.api.FluidStack;

public interface IEntityPlayerMP {
    void displayGuiScreen_fluidapi(GuiScreen guiScreen, Container container, IInventory inventory);

    void updateFluidSlot(Container container, int i, FluidStack fluidStack);
}
