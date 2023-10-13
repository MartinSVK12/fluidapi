package sunsetsatellite.fluidapi.interfaces.mixins;


import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import sunsetsatellite.fluidapi.api.FluidStack;

public interface IEntityPlayer {
    void displayGuiScreen_fluidapi(IInventory inventory);

    void updateFluidSlot(Container container, int i, FluidStack fluidStack);
}
