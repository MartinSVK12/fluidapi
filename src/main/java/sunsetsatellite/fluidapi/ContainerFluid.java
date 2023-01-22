package sunsetsatellite.fluidapi;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;

import java.util.ArrayList;

public class ContainerFluid extends Container {

    public ArrayList<SlotFluid> fluidSlots = new ArrayList<>();

    protected void addFluidSlot(SlotFluid slot){
        slot.slotNumber = this.fluidSlots.size();
        this.fluidSlots.add(slot);
    }

    public SlotFluid getFluidSlot(int idx) { return this.fluidSlots.get(idx); }
    public void putFluidInSlot(int idx, FluidStack fluid) { this.getFluidSlot(idx).putStack(fluid);}

    public ContainerFluid(IInventory iInventory, TileEntityFluidItemContainer tileEntityFluidItemContainer){
        tile = tileEntityFluidItemContainer;
    }

    @Override
    public void quickMoveItems(int i, EntityPlayer entityPlayer, boolean bl, boolean bl2) {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }

    protected TileEntityFluidItemContainer tile;
}
