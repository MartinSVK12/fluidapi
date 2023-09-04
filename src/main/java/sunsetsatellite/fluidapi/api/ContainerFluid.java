package sunsetsatellite.fluidapi.api;


import net.minecraft.core.InventoryAction;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucket;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayerMP;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;

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
    public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
        return null;
    }

    public FluidStack clickFluidSlot(int slotID, int button, boolean shift, boolean control, EntityPlayer entityplayer) {
        if(slotID == -999){
            return null;
        }
        SlotFluid slot = fluidSlots.get(slotID);
        InventoryPlayer inventoryPlayer = entityplayer.inventory;
        if(slot != null){
            //extract fluid into bucket
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
            //insert fluid from bucket
            if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof ItemBucket) {
                ItemBucket bucket = (ItemBucket) inventoryPlayer.getHeldItemStack().getItem();
                BlockFluid fluid = FluidRegistry.getFluidForContainer(bucket);
                if (slot.getFluidStack() == null) {
                    if(tile.acceptedFluids.get(slotID).isEmpty() || tile.acceptedFluids.get(slotID).contains(FluidRegistry.getFluidForContainer(bucket))){
                        if(slot.isFluidValid(fluid)){
                            inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                            slot.putStack(new FluidStack(FluidRegistry.getFluidForContainer(bucket), 1000));
                            slot.onSlotChanged();
                        }
                    }
                } else if (slot.getFluidStack() != null && slot.getFluidStack().getLiquid() == FluidRegistry.getFluidForContainer(bucket)) {
                    if (slot.getFluidStack().amount + 1000 <= tile.getFluidCapacityForSlot(slot.slotIndex)) {
                        if(tile.acceptedFluids.get(slotID).isEmpty() || tile.acceptedFluids.get(slotID).contains(fluid)){
                            if(slot.isFluidValid(fluid)){
                                inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                                slot.getFluidStack().amount += 1000;
                                slot.onSlotChanged();
                            }
                        }
                    }
                }
            }
            //I/O from custom fluid container items
            if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof IItemFluidContainer) {
                IItemFluidContainer item = (IItemFluidContainer) inventoryPlayer.getHeldItemStack().getItem();
                BlockFluid fluid = FluidRegistry.getFluidForContainer((Item) item);
                if(FluidRegistry.getFluidForContainer((Item) item) != null){
                    if(tile.acceptedFluids.get(slotID).isEmpty()
                            || tile.acceptedFluids.get(slotID).contains(FluidRegistry.getFluidForContainer((Item) item))
                            || (slot.getFluidStack() != null
                            && FluidRegistry.getContainersForFluid(slot.getFluidStack().liquid).contains(item))
                            && slot.isFluidValid(fluid))
                    {
                        //drain
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
                        } else if(item.canFill(inventoryPlayer.getHeldItemStack())){ //fill
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
