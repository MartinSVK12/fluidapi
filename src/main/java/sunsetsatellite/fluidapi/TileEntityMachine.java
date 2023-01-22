package sunsetsatellite.fluidapi;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TileEntityMachine extends TileEntityFluidItemContainer
    implements IPipePressurizer {
    public TileEntityMachine(){
        isInput.replace("Y+",false);

        fluidCapacity[0] = 4000;
    }

    public int fuelBurnTicks = 0;
    public int fuelMaxBurnTicks = 0;
    public int progressTicks = 0;
    public int progressMaxTicks = 200;
    public int efficiency = 1;
    public int speedMultiplier = 1;

    public int getProgressScaled(int paramInt) {
        return this.progressTicks * paramInt / progressMaxTicks;
    }

    public int getBurnTimeRemainingScaled(int paramInt) {
        if(this.fuelMaxBurnTicks == 0) {
            this.fuelMaxBurnTicks = 200;
        }
        return this.fuelBurnTicks * paramInt / this.fuelMaxBurnTicks;
    }


    @Override
    public void updateEntity() {
        World world = worldObj;
        world.markBlocksDirty(xCoord,yCoord,zCoord,xCoord,yCoord,zCoord);
        extractFluids();
        for(int i = 0; i < itemContents.length; i++){
            if(itemContents[i] != null && itemContents[i].stackSize <= 0){
                itemContents[i] = null;
            }
        }
        boolean update = false;
        if(fuelBurnTicks > 0){
            fuelBurnTicks--;
        }
        if(itemContents[0] == null){
            progressTicks = 0;
        } else if(canProcess()) {
            progressMaxTicks = 200;
        }
        if(!worldObj.isMultiplayerAndNotHost){
            if (progressTicks == 0 && canProcess()){
                update = fuel();
            }
            if(isBurning() && canProcess()){
                progressTicks++;
                if(progressTicks >= progressMaxTicks){
                    progressTicks = 0;
                    processItem();
                    update = true;
                }
            } else if(canProcess()){
                fuel();
                if(fuelBurnTicks > 0){
                    fuelBurnTicks++;
                }
            }
        }

        if(update) {
            this.onInventoryChanged();
        }

    }


    @Override
    public void readFromNBT(NBTTagCompound nBTTagCompound1) {
        super.readFromNBT(nBTTagCompound1);
        fuelBurnTicks = nBTTagCompound1.getShort("BurnTime");
        progressTicks = nBTTagCompound1.getShort("ProcessTime");
        progressMaxTicks = nBTTagCompound1.getInteger("MaxProcessTime");
        fuelMaxBurnTicks = nBTTagCompound1.getShort("MaxBurnTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound nBTTagCompound1) {
        super.writeToNBT(nBTTagCompound1);
        nBTTagCompound1.setShort("BurnTime", (short)this.fuelBurnTicks);
        nBTTagCompound1.setShort("ProcessTime", (short)this.progressTicks);
        nBTTagCompound1.setShort("MaxBurnTime", (short)this.fuelMaxBurnTicks);
        nBTTagCompound1.setInteger("MaxProcessTime",this.progressMaxTicks);
    }

    public boolean fuel(){
        int burn = getItemBurnTime(itemContents[1]);
        if(burn > 0 && canProcess()){
            progressMaxTicks = 200;
            fuelMaxBurnTicks = fuelBurnTicks = burn;
            if(itemContents[1].getItem().hasContainerItem()) {
                itemContents[1] = new ItemStack(itemContents[1].getItem().getContainerItem());
            } else {
                itemContents[1].stackSize--;
                if(itemContents[1].stackSize == 0) {
                    itemContents[1] = null;
                }
            }
            return true;
        }
        return false;
    }

    public void processItem(){
        if(canProcess()){
            FluidStack stack = MachineRecipes.getInstance().getResult(this.itemContents[0].getItem().itemID);
            if(fluidContents[0] == null){
                setFluidInSlot(0, stack);
            } else if(getFluidInSlot(0).getLiquid() == stack.getLiquid()) {
                incrFluidAmount(0, stack.amount);
            }
            if(this.itemContents[0].getItem().hasContainerItem()) {
                this.itemContents[0] = new ItemStack(this.itemContents[0].getItem().getContainerItem());
            } else {
                --this.itemContents[0].stackSize;
            }
            if(this.itemContents[0].stackSize <= 0) {
                this.itemContents[0] = null;
            }
        }
    }

    private boolean canProcess() {
        if(itemContents[0] == null) {
            return false;
        } else {
            FluidStack stack = MachineRecipes.getInstance().getResult(itemContents[0].itemID);
            return stack != null && (fluidContents[0] == null || (fluidContents[0].isFluidEqual(stack) && (fluidContents[0].amount < fluidCapacity[0])));
            //return stack != null && (itemContents[2] == null || (itemContents[2].isItemEqual(stack) && (itemContents[2].stackSize < getInventoryStackLimit() && itemContents[2].stackSize < itemContents[2].getMaxStackSize() || itemContents[2].stackSize < stack.getMaxStackSize())));
        }
    }

    private int getItemBurnTime(ItemStack stack) {
        return stack == null ? 0 : LookupFuelFurnace.fuelFurnace().getFuelYield(stack.getItem().itemID);
    }

    public boolean isBurning(){
        return fuelBurnTicks > 0;
    }

    public void extractFluids(){
        for (Map.Entry<String, Vec3D> entry : dir.entrySet()) {
            String K = entry.getKey();
            Vec3D V = entry.getValue();
            TileEntity tile = worldObj.getBlockTileEntity(xCoord + (int) V.xCoord, yCoord + (int) V.yCoord, zCoord + (int) V.zCoord);
            if (tile instanceof TileEntityFluidPipe) {
                pressurizePipes((TileEntityFluidPipe) tile, new ArrayList<>());
                moveFluids(K, (TileEntityFluidPipe) tile, transferSpeed);
            }
        }
    }

    public void pressurizePipes(TileEntityFluidPipe pipe, ArrayList<HashMap<String,Integer>> already){
        pipe.isPressurized = true;
        dir.forEach((K,V)-> {
            TileEntity tile = worldObj.getBlockTileEntity(pipe.xCoord + (int) V.xCoord, pipe.yCoord + (int) V.yCoord, pipe.zCoord + (int) V.zCoord);
            if (tile instanceof TileEntityFluidPipe) {
                for (HashMap<String, Integer> V2 : already) {
                    if (V2.get("x") == tile.xCoord && V2.get("y") == tile.yCoord && V2.get("z") == tile.zCoord) {
                        return;
                    }
                }
                HashMap<String,Integer> list = new HashMap<>();
                list.put("x",tile.xCoord);
                list.put("y",tile.yCoord);
                list.put("z",tile.zCoord);
                already.add(list);
                ((TileEntityFluidPipe) tile).isPressurized = true;
                pressurizePipes((TileEntityFluidPipe) tile,already);
            }
        });
    }

    public void unpressurizePipes(TileEntityFluidPipe pipe,ArrayList<HashMap<String,Integer>> already){
        pipe.isPressurized = false;
        dir.forEach((K,V)-> {
            TileEntity tile = worldObj.getBlockTileEntity(pipe.xCoord + (int) V.xCoord, pipe.yCoord + (int) V.yCoord, pipe.zCoord + (int) V.zCoord);
            if (tile instanceof TileEntityFluidPipe) {
                for (HashMap<String, Integer> V2 : already) {
                    if (V2.get("x") == tile.xCoord && V2.get("y") == tile.yCoord && V2.get("z") == tile.zCoord) {
                        return;
                    }
                }
                HashMap<String,Integer> list = new HashMap<>();
                list.put("x",tile.xCoord);
                list.put("y",tile.yCoord);
                list.put("z",tile.zCoord);
                already.add(list);
                ((TileEntityFluidPipe) tile).isPressurized = false;
                unpressurizePipes((TileEntityFluidPipe) tile,already);
            }
        });
    }

    /*@Override
    public int getDynamicTexture(int side, BlockMultiID block) {
        if(getStackInSlot(0) != null && isBurning()){
            return BlockTextures.EXTRACTOR_PROTOTYPE_SIDE_ACTIVE.ordinal();
        }
        if (getStackInSlot(0) != null && !isBurning()) {
            return BlockTextures.EXTRACTOR_PROTOTYPE_SIDE_INACTIVE.ordinal();
        } else if(getStackInSlot(0) == null && !isBurning()) {
            return BlockTextures.EXTRACTOR_PROTOTYPE_SIDE_EMPTY.ordinal();
        } else {
            return BlockTextures.EXTRACTOR_PROTOTYPE_SIDE_EMPTY.ordinal();
        }
    }

    @Override
    public ArrayList<Integer> getDynamicSides(int front) {
        return new ArrayList<>(Arrays.asList(2, 3, 4, 5));
    }*/
}
