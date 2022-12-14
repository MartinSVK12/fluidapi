package net.sunsetsatellite.fluidapi;

import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class SlotFluid {
    protected final IFluidInventory fluidInventory;
    protected final int slotIndex;
    public int slotNumber;
    public int xPos;
    public int yPos;

    public SlotFluid(IFluidInventory iFluidInventory, int idx, int x, int y) {
        fluidInventory = iFluidInventory;
        slotIndex = idx;
        xPos = x;
        yPos = y;
    }

    public void onSlotChanged(){
        this.fluidInventory.onFluidInventoryChanged();
    }

    public void onPickupFromSlot(FluidStack stack) {
        this.onSlotChanged();
    }

    public boolean isFluidValid(FluidStack stack) {
        return true;
    }

    public FluidStack getFluidStack() {
        return fluidInventory.getFluidInSlot(this.slotIndex);
    }

    public boolean getHasStack() {
        return this.getFluidStack() != null;
    }

    public void putStack(FluidStack stack) {
        this.fluidInventory.setFluidInSlot(this.slotIndex, stack);
        this.onSlotChanged();
    }

    public int getBackgroundIconIndex() {
        return -1;
    }
}
