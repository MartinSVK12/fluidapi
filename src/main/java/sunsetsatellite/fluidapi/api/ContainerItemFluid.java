package sunsetsatellite.fluidapi.api;

import net.minecraft.src.*;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class ContainerItemFluid extends Container {

    public ArrayList<SlotFluid> fluidSlots = new ArrayList<>();
    public List<FluidStack> fluidItemStacks = new ArrayList<>();

    protected void addFluidSlot(SlotFluid slot){
        slot.slotNumber = this.fluidSlots.size();
        this.fluidSlots.add(slot);
        this.fluidItemStacks.add(null);
    }

    public SlotFluid getFluidSlot(int idx) { return this.fluidSlots.get(idx); }
    public void putFluidInSlot(int idx, FluidStack fluid) { this.getFluidSlot(idx).putStack(fluid);}

    public ContainerItemFluid(IInventory iInventory, ItemInventoryFluid inv){
        this.inv = inv;
    }

    @Override
    public void quickMoveItems(int i, EntityPlayer entityPlayer, boolean bl, boolean bl2) {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }

    protected ItemInventoryFluid inv;

    @Override
    public void updateInventory() {
        super.updateInventory();
        for (int i = 0; i < this.fluidSlots.size(); i++) {
            FluidStack fluidStack = this.fluidSlots.get(i).getFluidStack();
            FluidStack fluidStack1 = this.fluidItemStacks.get(i);
            this.fluidItemStacks.set(i, fluidStack1);
            for (ICrafting crafter : this.crafters) {
                ((IEntityPlayerMP) crafter).updateFluidSlot(this, i, fluidStack);

            }
        }
        for(int i = 0; i < this.inventorySlots.size(); ++i) {
            ItemStack itemstack = this.inventorySlots.get(i).getStack();
            ItemStack itemstack1 = this.inventoryItemStacks.get(i);
            this.inventoryItemStacks.set(i, itemstack1);
            for (ICrafting crafter : this.crafters) {
                crafter.updateInventorySlot(this, i, itemstack);
            }
        }
    }

    @Override
    public ItemStack clickInventorySlot(int slotID, int button, boolean shift, boolean control, EntityPlayer player) {
        return super.clickInventorySlot(slotID, button, shift, control, player);
    }

    public FluidStack clickFluidSlot(int slotID, int button, boolean shift, boolean control, EntityPlayer entityplayer) {
        if(slotID == -999){
            return null;
        }
        SlotFluid slot = fluidSlots.get(slotID);
        InventoryPlayer inventoryPlayer = entityplayer.inventory;
        if(slot != null){
            if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof ItemBucketEmpty && FluidAPI.fluidRegistry.fluidContainers.get(FluidAPI.fluidRegistry.fluidsInv.get(slot.getFluidStack().liquid)) == inventoryPlayer.getHeldItemStack().getItem()) {
                if (slot.getFluidStack() != null && slot.getFluidStack().amount >= 1000) {
                    inventoryPlayer.setHeldItemStack(new ItemStack(FluidAPI.fluidRegistry.fluidsInv.get(slot.getFluidStack().liquid), 1));
                    inv.decrFluidAmount(slot.slotIndex, 1000);
                    slot.onPickupFromSlot(slot.getFluidStack());
                    slot.onSlotChanged();
                    return fluidSlots.get(slotID).getFluidStack();
                }
            }
            if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof ItemBucket) {
                ItemBucket bucket = (ItemBucket) inventoryPlayer.getHeldItemStack().getItem();
                if (slot.getFluidStack() == null) {
                    if(inv.acceptedFluids.get(slotID).isEmpty() || inv.acceptedFluids.get(slotID).contains(FluidAPI.fluidRegistry.fluids.get(bucket))){
                        inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                        slot.putStack(new FluidStack(FluidAPI.fluidRegistry.fluids.get(bucket), 1000));
                        slot.onSlotChanged();
                    }
                } else if (slot.getFluidStack() != null && slot.getFluidStack().getLiquid() == FluidAPI.fluidRegistry.fluids.get(bucket)) {
                    if (slot.getFluidStack().amount + 1000 <= inv.getFluidCapacityForSlot(slot.slotIndex)) {
                        if(inv.acceptedFluids.get(slotID).isEmpty() || inv.acceptedFluids.get(slotID).contains(FluidAPI.fluidRegistry.fluids.get(bucket))){
                            inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                            slot.getFluidStack().amount += 1000;
                            slot.onSlotChanged();
                        }
                    }
                }
            }
            if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof IItemFluidContainer) {
                IItemFluidContainer item = (IItemFluidContainer) inventoryPlayer.getHeldItemStack().getItem();
                if(FluidAPI.fluidRegistry.fluids.get(item) != null || FluidAPI.fluidRegistry.fluidContainers.containsValue(item)){
                    if(inv.acceptedFluids.get(slotID).isEmpty() || inv.acceptedFluids.get(slotID).contains(FluidAPI.fluidRegistry.fluids.get(item)) || (slot.getFluidStack() != null && (IItemFluidContainer) (FluidAPI.fluidRegistry.fluidContainers.get(FluidAPI.fluidRegistry.fluidsInv.get(slot.getFluidStack().liquid))) == item)){
                        if(item.canDrain(inventoryPlayer.getHeldItemStack())){
                            if (inv.getFluidInSlot(slot.slotIndex) == null){
                                item.drain(inventoryPlayer.getHeldItemStack(), slot, inv);
                                slot.onSlotChanged();
                            }
                            else if (inv.getFluidInSlot(slot.slotIndex).amount < inv.getFluidCapacityForSlot(slot.slotIndex)) {
                                item.drain(inventoryPlayer.getHeldItemStack(), slot, inv);
                                slot.onSlotChanged();
                            }
                            else if(inv.getFluidInSlot(slot.slotIndex).amount >= inv.getFluidCapacityForSlot(slot.slotIndex)){
                                if(item.canFill(inventoryPlayer.getHeldItemStack())){
                                    ItemStack stack = item.fill(slot,inventoryPlayer.getHeldItemStack());
                                    if(stack != null){
                                        inventoryPlayer.setHeldItemStack(stack);
                                        inventoryPlayer.onInventoryChanged();
                                    }
                                    slot.onSlotChanged();
                                }
                            }
                        } else if(item.canFill(inventoryPlayer.getHeldItemStack())){
                            ItemStack stack = item.fill(slot,inventoryPlayer.getHeldItemStack());
                            if(stack != null){
                                inventoryPlayer.setHeldItemStack(stack);
                            }
                            slot.onSlotChanged();
                        }
                    }
                }
            }
            slot.onSlotChanged();
            updateInventory();
        }
        return fluidSlots.get(slotID).getFluidStack();
    }
}
