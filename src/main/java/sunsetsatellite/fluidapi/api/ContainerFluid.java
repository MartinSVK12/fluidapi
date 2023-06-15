package sunsetsatellite.fluidapi.api;

import net.minecraft.src.*;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class ContainerFluid extends Container {

    public ArrayList<SlotFluid> fluidSlots = new ArrayList<>();
    public List<FluidStack> fluidItemStacks = new ArrayList<>();

    protected void addFluidSlot(SlotFluid slot){
        slot.slotNumber = this.fluidSlots.size();
        this.fluidSlots.add(slot);
        this.fluidItemStacks.add(null);
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

    public TileEntityFluidItemContainer tile;

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
            if(inventoryPlayer.getHeldItemStack() != null
                    && inventoryPlayer.getHeldItemStack().getItem() instanceof ItemBucketEmpty
                    && FluidRegistry.getEmptyContainersForFluid(slot.getFluidStack().liquid).contains(inventoryPlayer.getHeldItemStack().getItem())) {
                if (slot.getFluidStack() != null && slot.getFluidStack().amount >= 1000) {
                    Item item = FluidRegistry.findFilledContainer(inventoryPlayer.getHeldItemStack().getItem(),slot.getFluidStack().liquid);
                    if(item != null){
                        inventoryPlayer.setHeldItemStack(new ItemStack(item, 1));
                        tile.fluidContents[slot.slotIndex].amount -= 1000;
                        slot.onPickupFromSlot(slot.getFluidStack());
                        slot.onSlotChanged();
                        return fluidSlots.get(slotID).getFluidStack();
                    }
                }
            }
            if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof ItemBucket) {
                ItemBucket bucket = (ItemBucket) inventoryPlayer.getHeldItemStack().getItem();
                if (slot.getFluidStack() == null) {
                    if(tile.acceptedFluids.get(slotID).isEmpty() || tile.acceptedFluids.get(slotID).contains(FluidRegistry.getFluidForContainer(bucket))){
                        inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                        slot.putStack(new FluidStack(FluidRegistry.getFluidForContainer(bucket), 1000));
                        slot.onSlotChanged();
                    }
                } else if (slot.getFluidStack() != null && slot.getFluidStack().getLiquid() == FluidRegistry.getFluidForContainer(bucket)) {
                    if (slot.getFluidStack().amount + 1000 <= tile.getFluidCapacityForSlot(slot.slotIndex)) {
                        if(tile.acceptedFluids.get(slotID).isEmpty() || tile.acceptedFluids.get(slotID).contains(FluidRegistry.getFluidForContainer(bucket))){
                            inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                            slot.getFluidStack().amount += 1000;
                            slot.onSlotChanged();
                        }
                    }
                }
            }
            if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof IItemFluidContainer) {
                IItemFluidContainer item = (IItemFluidContainer) inventoryPlayer.getHeldItemStack().getItem();
                if(FluidRegistry.getFluidForContainer((Item) item) != null){
                    if(tile.acceptedFluids.get(slotID).isEmpty()
                            || tile.acceptedFluids.get(slotID).contains(FluidRegistry.getFluidForContainer((Item) item))
                            || (slot.getFluidStack() != null
                            && FluidRegistry.getContainersForFluid(slot.getFluidStack().liquid).contains(item))){
                        if(item.canDrain(inventoryPlayer.getHeldItemStack())){
                            if (tile.getFluidInSlot(slot.slotIndex) == null){
                                item.drain(inventoryPlayer.getHeldItemStack(), slot, tile);
                                slot.onSlotChanged();
                            }
                            else if (tile.getFluidInSlot(slot.slotIndex).amount < tile.getFluidCapacityForSlot(slot.slotIndex)) {
                                item.drain(inventoryPlayer.getHeldItemStack(), slot, tile);
                                slot.onSlotChanged();
                            }
                            else if(tile.getFluidInSlot(slot.slotIndex).amount >= tile.getFluidCapacityForSlot(slot.slotIndex)){
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
