package sunsetsatellite.fluidapi;

import net.minecraft.src.ItemStack;

public interface IItemFluidContainer {
    int getCapacity(ItemStack stack);
    int getRemainingCapacity(ItemStack stack);
    boolean canFill(ItemStack stack);
    boolean canDrain(ItemStack stack);
    ItemStack fill(SlotFluid slot, ItemStack stack);
    ItemStack fill(int slotIndex, TileEntityFluidContainer tile, ItemStack stack);
    void drain(ItemStack stack, SlotFluid slot, TileEntityFluidContainer tile);
    void drain(ItemStack stack, FluidStack fluid, TileEntityFluidContainer tile);
}
