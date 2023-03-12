package sunsetsatellite.fluidapi.interfaces.mixins;

import net.minecraft.src.Container;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import sunsetsatellite.fluidapi.FluidStack;

public interface IEntityPlayerMP {
    void displayGuiScreen(GuiScreen guiScreen, Container container, IInventory inventory);

    void updateFluidSlot(Container container, int i, FluidStack fluidStack);
}
