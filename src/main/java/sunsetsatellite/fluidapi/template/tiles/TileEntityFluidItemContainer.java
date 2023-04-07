package sunsetsatellite.fluidapi.template.tiles;

import net.minecraft.src.*;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileEntityFluidItemContainer extends TileEntityFluidContainer
    implements IInventory {

    protected ItemStack[] itemContents = new ItemStack[2];

    public HashMap<Direction, Connection> itemConnections = new HashMap<>();
    public HashMap<Direction, Integer> activeItemSlots = new HashMap<>();

    public TileEntityFluidItemContainer(){
        for (Direction dir : Direction.values()) {
            itemConnections.put(dir,Connection.NONE);
            activeItemSlots.put(dir,0);
        }
        for (FluidStack ignored : fluidContents) {
            acceptedFluids.add(new ArrayList<>());
        }
    }

    public int getSizeInventory() {
        return itemContents.length;
    }

    public ItemStack getStackInSlot(int i1) {
        return this.itemContents[i1];
    }

    public ItemStack decrStackSize(int i1, int i2) {
        if(this.itemContents[i1] != null) {
            ItemStack itemStack3;
            if(this.itemContents[i1].stackSize <= i2) {
                itemStack3 = this.itemContents[i1];
                this.itemContents[i1] = null;
                this.onInventoryChanged();
                return itemStack3;
            } else {
                itemStack3 = this.itemContents[i1].splitStack(i2);
                if(this.itemContents[i1].stackSize == 0) {
                    this.itemContents[i1] = null;
                }

                this.onInventoryChanged();
                return itemStack3;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i1, ItemStack itemStack2) {
        this.itemContents[i1] = itemStack2;
        if(itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
            itemStack2.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    public String getInvName() {
        return "Generic Fluid & Item Container";
    }

    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readFromNBT(nBTTagCompound1);
        NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Items");
        this.itemContents = new ItemStack[this.getSizeInventory()];

        for(int i3 = 0; i3 < nBTTagList2.tagCount(); ++i3) {
            NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nBTTagList2.tagAt(i3);
            int i5 = nBTTagCompound4.getByte("Slot") & 255;
            if(i5 >= 0 && i5 < this.itemContents.length) {
                this.itemContents[i5] = new ItemStack(nBTTagCompound4);
            }
        }

        NBTTagList nbtTagList = nBTTagCompound1.getTagList("Fluids");
        this.fluidContents = new FluidStack[this.getFluidInventorySize()];

        for(int i3 = 0; i3 < nbtTagList.tagCount(); ++i3) {
            NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nbtTagList.tagAt(i3);
            int i5 = nBTTagCompound4.getByte("Slot") & 255;
            if(i5 >= 0 && i5 < this.fluidContents.length) {
                this.fluidContents[i5] = new FluidStack(nBTTagCompound4);
            }
        }

        NBTTagCompound connectionsTag = nBTTagCompound1.getCompoundTag("itemConnections");
        for (Object con : connectionsTag.func_28110_c()) {
            itemConnections.replace(Direction.values()[Integer.parseInt(((NBTTagInt)con).getKey())],Connection.values()[((NBTTagInt)con).intValue]);
        }

        NBTTagCompound activeItemSlotsTag = nBTTagCompound1.getCompoundTag("itemActiveSlots");
        for (Object con : activeItemSlotsTag.func_28110_c()) {
            activeItemSlots.replace(Direction.values()[Integer.parseInt(((NBTTagInt)con).getKey())],((NBTTagInt) con).intValue);
        }

    }

    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        NBTTagList nBTTagList2 = new NBTTagList();
        NBTTagList nbtTagList = new NBTTagList();
        NBTTagCompound connectionsTag = new NBTTagCompound();
        NBTTagCompound activeItemSlotsTag = new NBTTagCompound();

        for(int i3 = 0; i3 < this.itemContents.length; ++i3) {
            if(this.itemContents[i3] != null) {
                NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
                nBTTagCompound4.setByte("Slot", (byte)i3);
                this.itemContents[i3].writeToNBT(nBTTagCompound4);
                nbtTagList.setTag(nBTTagCompound4);
            }
        }

        for(int i3 = 0; i3 < this.fluidContents.length; ++i3) {
            if(this.fluidContents[i3] != null && this.fluidContents[i3].amount != 0 && this.fluidContents[i3].liquid != null) {
                NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
                nBTTagCompound4.setByte("Slot", (byte)i3);
                this.fluidContents[i3].writeToNBT(nBTTagCompound4);
                nBTTagList2.setTag(nBTTagCompound4);
            }
        }
        for (Map.Entry<Direction, Integer> entry : activeItemSlots.entrySet()) {
            Direction dir = entry.getKey();
            activeItemSlotsTag.setInteger(String.valueOf(dir.ordinal()),entry.getValue());
        }
        for (Map.Entry<Direction, Connection> entry : itemConnections.entrySet()) {
            Direction dir = entry.getKey();
            Connection con = entry.getValue();
            connectionsTag.setInteger(String.valueOf(dir.ordinal()),con.ordinal());
        }
        nBTTagCompound1.setCompoundTag("itemConnections",connectionsTag);
        nBTTagCompound1.setCompoundTag("itemActiveSlots",activeItemSlotsTag);
        nBTTagCompound1.setTag("Items", nbtTagList);
        nBTTagCompound1.setTag("Fluids", nBTTagList2);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityPlayer1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }
}
