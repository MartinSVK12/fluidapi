package sunsetsatellite.fluidapi.template.tiles;

import net.minecraft.src.*;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.api.IFluidInventory;
import sunsetsatellite.fluidapi.api.IFluidTransfer;
import sunsetsatellite.fluidapi.api.IMassFluidInventory;
import sunsetsatellite.fluidapi.mp.packets.PacketUpdateClientFluidRender;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileEntityFluidContainer extends TileEntity
    implements IFluidInventory, IFluidTransfer {
    
    public FluidStack[] fluidContents = new FluidStack[1];
    public int[] fluidCapacity = new int[1];
    public ArrayList<ArrayList<BlockFluid>> acceptedFluids = new ArrayList<>(fluidContents.length);

    public FluidStack shownFluid = fluidContents[0];
    public int shownMaxAmount = 0;

    public int transferSpeed = 20;

    public HashMap<Direction, Connection> connections = new HashMap<>();
    public HashMap<Direction, Integer> activeFluidSlots = new HashMap<>();

    public TileEntityFluidContainer(){
        for (Direction dir : Direction.values()) {
            connections.put(dir,Connection.NONE);
            activeFluidSlots.put(dir,0);
        }
        for (FluidStack ignored : fluidContents) {
            acceptedFluids.add(new ArrayList<>());
        }
    }

    public void take(@NotNull FluidStack fluidStack, Direction dir){
        if(connections.get(dir) == Connection.INPUT || connections.get(dir) == Connection.BOTH){
            TileEntity tile = dir.getTileEntity(worldObj,this);
            if(tile instanceof IFluidInventory && tile instanceof IFluidTransfer){
                IFluidInventory fluidInv = ((IFluidInventory) tile);
                IFluidTransfer fluidTransfer = ((IFluidTransfer) tile);
                if(fluidTransfer.getConnection(dir.getOpposite()) == Connection.OUTPUT || fluidTransfer.getConnection(dir.getOpposite()) == Connection.BOTH){
                    int maxFlow = Math.min(transferSpeed,fluidInv.getTransferSpeed());
                    if(acceptedFluids.get(activeFluidSlots.get(dir)).contains(fluidStack.getLiquid())) {
                        if (canInsertFluid(activeFluidSlots.get(dir), new FluidStack(fluidStack.liquid, maxFlow))) {
                            FluidStack transferablePortion = fluidStack.splitStack(maxFlow);
                            if (fluidContents[activeFluidSlots.get(dir)] == null) {
                                fluidContents[activeFluidSlots.get(dir)] = transferablePortion;
                            } else {
                                fluidContents[activeFluidSlots.get(dir)].amount += transferablePortion.amount;
                            }
                        }
                    }
                }
            }
        }
    }

    public void give(Direction dir){
        int slot = activeFluidSlots.get(dir);
        FluidStack fluidStack = fluidContents[slot];
        if(connections.get(dir) == Connection.OUTPUT || connections.get(dir) == Connection.BOTH){
            TileEntity tile = dir.getTileEntity(worldObj,this);
            if(tile instanceof IFluidInventory && tile instanceof IFluidTransfer) {
                IFluidInventory fluidInv = ((IFluidInventory) tile);
                IFluidTransfer fluidTransfer = ((IFluidTransfer) tile);
                if(fluidTransfer.getConnection(dir.getOpposite()) == Connection.INPUT || fluidTransfer.getConnection(dir.getOpposite()) == Connection.BOTH) {
                    int maxFlow = Math.min(transferSpeed, fluidInv.getTransferSpeed());
                    if(tile instanceof IMassFluidInventory){
                        IMassFluidInventory massFluidInv = (IMassFluidInventory) tile;
                        if(fluidStack.isFluidEqual(massFluidInv.getFilter(dir.getOpposite())) || massFluidInv.getFilter(dir.getOpposite()) == null){
                            if(massFluidInv.canInsertFluid(new FluidStack(fluidStack.liquid,maxFlow))){
                                FluidStack transferablePortion = fluidStack.splitStack(maxFlow);
                                massFluidInv.insertFluid(transferablePortion);
                            }
                        }
                    } else {
                        int otherSlot = fluidInv.getActiveFluidSlot(dir.getOpposite());
                        if(fluidInv.getAllowedFluidsForSlot(otherSlot).contains(fluidStack.getLiquid())){
                            if(fluidInv.canInsertFluid(otherSlot,new FluidStack(fluidStack.liquid,maxFlow))){
                                FluidStack transferablePortion = fluidStack.splitStack(maxFlow);
                                fluidInv.insertFluid(otherSlot,transferablePortion);
                            }
                            /*if(fluidInv.getFluidInSlot(otherSlot) == null){
                                fluidInv.setFluidInSlot(otherSlot,transferablePortion);
                            } else {
                                fluidInv.getFluidInSlot(otherSlot).amount += transferablePortion.amount;
                            }*/
                        }
                    }
                }
            }
        }
    }

    @Override
    public FluidStack insertFluid(int slot, FluidStack fluidStack) {
        FluidStack stack = fluidContents[slot];
        FluidStack split = fluidStack.splitStack(Math.min(fluidStack.amount,getRemainingCapacity(slot)));
        if(stack != null){
            fluidContents[slot].amount += split.amount;
        } else {
            fluidContents[slot] = split;
        }
        return fluidStack;
    }

    @Override
    public int getRemainingCapacity(int slot) {
        if(fluidContents[slot] == null){
            return fluidCapacity[slot];
        }
        return fluidCapacity[slot]-fluidContents[slot].amount;
    }

    @Override
    public boolean canInsertFluid(int slot,FluidStack fluidStack){
        if(getFluidInSlot(slot) != null) if(!getFluidInSlot(slot).isFluidEqual(fluidStack)) return false;
        return Math.min(fluidStack.amount,getRemainingCapacity(slot)) > 0;
    }

    @Override
    public Connection getConnection(Direction dir) {
        return connections.get(dir);
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

        NBTTagCompound connectionsTag = nBTTagCompound1.getCompoundTag("fluidConnections");
        for (Object con : connectionsTag.func_28110_c()) {
            connections.replace(Direction.values()[Integer.parseInt(((NBTTagInt)con).getKey())],Connection.values()[((NBTTagInt)con).intValue]);
        }

        NBTTagCompound activeFluidSlotsTag = nBTTagCompound1.getCompoundTag("fluidActiveSlots");
        for (Object con : activeFluidSlotsTag.func_28110_c()) {
            activeFluidSlots.replace(Direction.values()[Integer.parseInt(((NBTTagInt)con).getKey())],((NBTTagInt) con).intValue);
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
        NBTTagCompound connectionsTag = new NBTTagCompound();
        NBTTagCompound activeFluidSlotsTag = new NBTTagCompound();
        for(int i3 = 0; i3 < this.fluidContents.length; ++i3) {
            if(this.fluidContents[i3] != null && this.fluidContents[i3].getLiquid() != null) {
                NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
                nBTTagCompound4.setByte("Slot", (byte)i3);
                this.fluidContents[i3].writeToNBT(nBTTagCompound4);
                nbtTagList.setTag(nBTTagCompound4);
            }
        }
        for (Map.Entry<Direction, Integer> entry : activeFluidSlots.entrySet()) {
            Direction dir = entry.getKey();
            activeFluidSlotsTag.setInteger(String.valueOf(dir.ordinal()),entry.getValue());
        }
        for (Map.Entry<Direction, Connection> entry : connections.entrySet()) {
            Direction dir = entry.getKey();
            Connection con = entry.getValue();
            connectionsTag.setInteger(String.valueOf(dir.ordinal()),con.ordinal());
        }
        nBTTagCompound1.setCompoundTag("fluidConnections",connectionsTag);
        nBTTagCompound1.setCompoundTag("fluidActiveSlots",activeFluidSlotsTag);
        nBTTagCompound1.setTag("Fluids", nbtTagList);
        nBTTagCompound1.setTag("Items", nBTTagList2);
    }

    public boolean canInteractWith(EntityPlayer entityPlayer1) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityPlayer1.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public FluidStack getFluidInSlot(int slot) {
        if(this.fluidContents[slot] == null || this.fluidContents[slot].getLiquid() == null || this.fluidContents[slot].amount == 0){
            this.fluidContents[slot] = null;
        }
        return fluidContents[slot];
    }

    @Override
    public int getFluidCapacityForSlot(int slot) {
        return fluidCapacity[slot];
    }

    @Override
    public ArrayList<BlockFluid> getAllowedFluidsForSlot(int slot) {
        return acceptedFluids.get(slot);
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
        if(fluid == null || fluid.amount == 0 || fluid.liquid == null){
            this.fluidContents[slot] = null;
            this.onFluidInventoryChanged();
            return;
        }
        if(acceptedFluids.get(slot).contains(fluid.liquid) || acceptedFluids.get(slot).isEmpty()){
            this.fluidContents[slot] = fluid;
            this.onFluidInventoryChanged();
        }

    }

    /*public void setOrModifyFluidInSlot(int slot, FluidStack fluid, boolean add){
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
    }*/

    /*@Override
    public FluidStack decrFluidAmount(int slot, int amount) {
        if(this.fluidContents[slot] != null) {
            if(this.fluidContents[slot].getLiquid() == null || this.fluidContents[slot].amount == 0){
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
            if(this.fluidContents[slot].getLiquid() == null || this.fluidContents[slot].amount == 0){
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
    }*/

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

    @Override
    public int getTransferSpeed() {
        return transferSpeed;
    }

    @Override
    public int getActiveFluidSlot(Direction dir) {
        return activeFluidSlots.get(dir);
    }


    public void moveFluids(Direction dir, TileEntityFluidPipe tile) {
        Integer activeSlot = activeFluidSlots.get(dir);
        if(connections.get(dir) == Connection.BOTH || connections.get(dir) == Connection.OUTPUT){
            if(getFluidInSlot(activeSlot) != null){
                give(dir);
            }
            /*if(getFluidInSlot(activeSlot) != null && (tile.getFluidInSlot(tile.getActiveFluidSlot(dir.getOpposite())) == null || (tile.getFluidInSlot(tile.getActiveFluidSlot(dir.getOpposite())) != null && tile.acceptedFluids.get(activeSlot).contains(getFluidInSlot(activeSlot).getLiquid())))){
                give(dir);
            }*/
        } else if(connections.get(dir) == Connection.BOTH || connections.get(dir) == Connection.INPUT){
            if(tile.getFluidInSlot(0) != null){
                take(tile.getFluidInSlot(0),dir);
            }
        }
    }
}
