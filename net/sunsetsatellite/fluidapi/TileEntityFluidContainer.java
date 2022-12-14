package net.sunsetsatellite.fluidapi;

import net.minecraft.src.*;

import java.util.HashMap;

public class TileEntityFluidContainer extends TileEntity
    implements IFluidInventory {
    
    protected FluidStack[] fluidContents = new FluidStack[1];
    protected int[] fluidCapacity = new int[1];

    public int transferSpeed = 20;

    protected HashMap<String, Boolean> isInput = new HashMap<>();
    final HashMap<String, Vec3D> dir = new HashMap<>();

    public TileEntityFluidContainer(){
        isInput.put("Y+",null);
        isInput.put("Y-",null);
        isInput.put("X+",null);
        isInput.put("X-",null);
        isInput.put("Z+",null);
        isInput.put("Z-",null);

        dir.put("Y+",Vec3D.createVectorHelper(0,1,0));
        dir.put("Y-",Vec3D.createVectorHelper(0,-1,0));
        dir.put("X+",Vec3D.createVectorHelper(1,0,0));
        dir.put("X-",Vec3D.createVectorHelper(-1,0,0));
        dir.put("Z+",Vec3D.createVectorHelper(0,0,1));
        dir.put("Z-",Vec3D.createVectorHelper(0,0,-1));
    }

    public String getInvName() {
        return "Generic Fluid Container";
    }

    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readFromNBT(nBTTagCompound1);

        NBTTagList nbtTagList = nBTTagCompound1.getTagList("Fluids");
        this.fluidContents = new FluidStack[this.getFluidInventorySize()];

        for(int i3 = 0; i3 < nbtTagList.tagCount(); ++i3) {
            NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nbtTagList.tagAt(i3);
            int i5 = nBTTagCompound4.getByte("Slot") & 255;
            if(i5 >= 0 && i5 < this.fluidContents.length) {
                this.fluidContents[i5] = new FluidStack(nBTTagCompound4);
            }
        }

    }

    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        NBTTagList nBTTagList2 = new NBTTagList();
        NBTTagList nbtTagList = new NBTTagList();

        for(int i3 = 0; i3 < this.fluidContents.length; ++i3) {
            if(this.fluidContents[i3] != null && this.fluidContents[i3].getLiquid() != null) {
                NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
                nBTTagCompound4.setByte("Slot", (byte)i3);
                this.fluidContents[i3].writeToNBT(nBTTagCompound4);
                nbtTagList.setTag(nBTTagCompound4);
            }
        }
        nBTTagCompound1.setTag("Fluids", nbtTagList);
        nBTTagCompound1.setTag("Items", nBTTagList2);
    }

    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityPlayer1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public FluidStack getFluidInSlot(int slot) {
        if(this.fluidContents[slot] == null || this.fluidContents[slot].getLiquid() == null){
            this.fluidContents[slot] = null;
        }
        return fluidContents[slot];
    }

    @Override
    public int getFluidCapacityForSlot(int slot) {
        return fluidCapacity[slot];
    }

    public int getFluidAmountForSlot(int slot){
        if(fluidContents[0] == null){
            return 0;
        } else {
            return fluidContents[0].amount;
        }
    }

    @Override
    public void setFluidInSlot(int slot, FluidStack fluid) {
        if(fluid.getLiquid() == null){
            this.fluidContents[slot] = null;
            return;
        }
        this.fluidContents[slot] = fluid;

        this.onInventoryChanged();
    }

    public void setOrModifyFluidInSlot(int slot, FluidStack fluid, boolean add){
        if(getFluidInSlot(0) == null){
            setFluidInSlot(0, fluid);
        } else if(getFluidInSlot(0).isFluidEqual(fluid)){
            if(add){
                incrFluidAmount(0,fluid.amount);
            } else {
                decrFluidAmount(0,fluid.amount);
            }
        }
    }

    @Override
    public FluidStack decrFluidAmount(int slot, int amount) {
        if(this.fluidContents[slot] != null) {
            if(this.fluidContents[slot].getLiquid() == null){
                this.fluidContents[slot] = null;
                return null;
            }
            FluidStack fluidStack;
            if(this.fluidContents[slot].amount <= amount) {
                fluidStack = this.fluidContents[slot];
                this.fluidContents[slot] = null;
                this.onInventoryChanged();
                return fluidStack;
            } else {
                fluidStack = this.fluidContents[slot].splitStack(amount);
                if(this.fluidContents[slot].amount == 0) {
                    this.fluidContents[slot] = null;
                }

                this.onInventoryChanged();
                return fluidStack;
            }
        } else {
            return null;
        }
    }

    @Override
    public FluidStack incrFluidAmount(int slot, int amount) {
        if(this.fluidContents[slot] != null) {
            if(this.fluidContents[slot].getLiquid() == null){
                this.fluidContents[slot] = null;
                return null;
            }
            FluidStack fluidStack;
            if(this.fluidContents[slot].amount + amount > this.fluidCapacity[slot]) {
                fluidStack = this.fluidContents[slot];
                this.onInventoryChanged();
                return fluidStack;
            } else {
                fluidStack = this.fluidContents[slot];
                fluidStack.amount += amount;
                this.onInventoryChanged();
                return fluidStack;
            }
        } else {
            return null;
        }
    }

    @Override
    public int getFluidInventorySize() {
        return fluidContents.length;
    }

    @Override
    public void onFluidInventoryChanged() {

    }

    public void moveFluids(String K, TileEntityFluidPipe tile, int amount){
        if(isInput.get(K) != null && isInput.get(K)){
            if(tile.getFluidInSlot(0) != null){
                if(getFluidInSlot(0) != null){
                    tile.AddToExternal(this, tile.getFluidInSlot(0),this.getFluidInSlot(0), amount);
                } else {
                    tile.insertIntoEmptyExternal(this,tile.getFluidInSlot(0), amount);
                }
            }
        } else if(isInput.get(K) != null && !isInput.get(K)){
            if(getFluidInSlot(0) != null){
                if(tile.getFluidInSlot(0) != null){
                    tile.TakeFromExternal(this, tile.getFluidInSlot(0),this.getFluidInSlot(0), amount);
                } else {
                    tile.extractFromExternalWhenEmpty(this, getFluidInSlot(0), amount);
                }
            }
        }
    }
}
