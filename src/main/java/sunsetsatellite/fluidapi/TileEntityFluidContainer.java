package sunsetsatellite.fluidapi;

import net.minecraft.src.*;
import sunsetsatellite.fluidapi.mp.packets.PacketUpdateClientFluidRender;

import java.util.HashMap;

public class TileEntityFluidContainer extends TileEntity
    implements IFluidInventory {
    
    public FluidStack[] fluidContents = new FluidStack[1];
    public int[] fluidCapacity = new int[1];

    public FluidStack shownFluid = fluidContents[0];
    public int shownMaxAmount = 0;

    public int transferSpeed = 20;

    public HashMap<String, Boolean> isInput = new HashMap<>();
    public final HashMap<String, Vec3D> dir = new HashMap<>();

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

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isMultiplayerAndNotHost){
            for (EntityPlayer player : worldObj.players) {
                if(player instanceof EntityPlayerMP){
                    ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new PacketUpdateClientFluidRender(xCoord,yCoord,zCoord,fluidContents[0],fluidCapacity[0]));
                }
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
        if(fluid == null){
            this.fluidContents[slot] = null;
            return;
        }
        this.fluidContents[slot] = fluid;
        this.onFluidInventoryChanged();
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
        this.onFluidInventoryChanged();
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
                this.onFluidInventoryChanged();
                return fluidStack;
            } else {
                fluidStack = this.fluidContents[slot].splitStack(amount);
                if(this.fluidContents[slot].amount == 0) {
                    this.fluidContents[slot] = null;
                }

                this.onFluidInventoryChanged();
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
                this.onFluidInventoryChanged();
                return fluidStack;
            } else {
                fluidStack = this.fluidContents[slot];
                fluidStack.amount += amount;
                this.onFluidInventoryChanged();
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
        if (this.worldObj != null) {
            this.worldObj.updateTileEntityChunkAndSendToPlayer(this.xCoord, this.yCoord, this.zCoord, this);
        }
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
