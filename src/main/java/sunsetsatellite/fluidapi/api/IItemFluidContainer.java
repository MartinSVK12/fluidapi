package sunsetsatellite.fluidapi.api;


import net.minecraft.core.item.ItemStack;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidContainer;

public interface IItemFluidContainer {
    int getCapacity(ItemStack stack);
    int getRemainingCapacity(ItemStack stack);
    boolean canFill(ItemStack stack);
    boolean canDrain(ItemStack stack);
    ItemStack fill(SlotFluid slot, ItemStack stack);
    ItemStack fill(int slotIndex, TileEntityFluidContainer tile, ItemStack stack);
    ItemStack fill(int slotIndex, ItemInventoryFluid inv, ItemStack stack);
    void drain(ItemStack stack, SlotFluid slot, TileEntityFluidContainer tile);
    void drain(ItemStack stack, FluidStack fluid, TileEntityFluidContainer tile);
    void drain(ItemStack stack, SlotFluid slot, ItemInventoryFluid inv);
    void drain(ItemStack stack, FluidStack fluid, ItemInventoryFluid inv);
}
