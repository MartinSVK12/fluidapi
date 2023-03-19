package sunsetsatellite.fluidapi.api;

import net.minecraft.src.BlockFluid;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidContainer;

import java.util.ArrayList;

public class SlotFluid {
    public final IFluidInventory fluidInventory;
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
        if(stack == null){
            this.fluidInventory.setFluidInSlot(this.slotIndex,null);
            this.onSlotChanged();
        }
        else if(fluidInventory.getAllowedFluidsForSLot(slotIndex).isEmpty() || fluidInventory.getAllowedFluidsForSLot(slotIndex).contains(stack.liquid)){
            this.fluidInventory.setFluidInSlot(this.slotIndex, stack);
            this.onSlotChanged();
        }
    }

    public int getBackgroundIconIndex() {
        return -1;
    }
}
