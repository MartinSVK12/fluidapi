package sunsetsatellite.fluidapi;

import net.minecraft.src.TileEntity;

import java.util.HashMap;
import java.util.Objects;

public class TileEntityFluidPipe extends TileEntityFluidContainer{
    public boolean isPressurized = false;
    private TileEntityFluidPipe last = null;

    public float size = 0.5f;

    public int rememberTicks = 0;

    public TileEntityFluidPipe(){
        fluidCapacity[0] = 1000;
        transferSpeed = 20;
    }

    public void insertIntoEmptyExternal(TileEntityFluidContainer inv, FluidStack intFluid, int amount){
        if(intFluid.amount >= amount){
            inv.setFluidInSlot(0,new FluidStack(intFluid.getLiquid(),amount));
            decrFluidAmount(0,amount);
        } else {
            int size = intFluid.amount;
            inv.setFluidInSlot(0,new FluidStack(intFluid.getLiquid(),size));
            decrFluidAmount(0,size);
        }
    }

    public void extractFromExternalWhenEmpty(TileEntityFluidContainer inv, FluidStack extFluid, int amount){
        if(extFluid.amount >= amount){
            setFluidInSlot(0, new FluidStack(extFluid.getLiquid(),amount));
            inv.decrFluidAmount(0,amount);
        } else {
            int size = extFluid.amount;
            setFluidInSlot(0, new FluidStack(extFluid.getLiquid(),size));
            inv.decrFluidAmount(0,size);
        }
    }

    public void AddToExternal(TileEntityFluidContainer inv, FluidStack intFluid, FluidStack extFluid, int amount){
        if (intFluid.isFluidEqual(extFluid)) {
            if (extFluid.amount + amount <= inv.getFluidCapacityForSlot(0)) {
                if (intFluid.amount >= amount) {
                    inv.incrFluidAmount(0, amount);
                    decrFluidAmount(0, amount);
                } else {
                    int size = intFluid.amount;
                    inv.incrFluidAmount(0, size);
                    decrFluidAmount(0, size);
                }
            } else if(extFluid.amount + amount > inv.getFluidCapacityForSlot(0)){
                int size = inv.getFluidCapacityForSlot(0) - extFluid.amount;
                inv.incrFluidAmount(0, size);
                decrFluidAmount(0, size);
            }
        }
    }

    public void TakeFromExternal(TileEntityFluidContainer inv, FluidStack intFluid, FluidStack extFluid, int amount){
        if (intFluid.isFluidEqual(extFluid)) {
            if (intFluid.amount + amount <= getFluidCapacityForSlot(0)) {
                if (extFluid.amount >= amount) {
                    inv.decrFluidAmount(0, amount);
                    incrFluidAmount(0, amount);
                } else {
                    int size = extFluid.amount;
                    inv.decrFluidAmount(0, size);
                    incrFluidAmount(0, size);
                }
            } else if(intFluid.amount + amount > inv.getFluidCapacityForSlot(0)){
                int size = inv.getFluidCapacityForSlot(0) - intFluid.amount;
                inv.decrFluidAmount(0, size);
                incrFluidAmount(0, size);
            }
        }
    }

    @Override
    public String getInvName() {
        return "Fluid Pipe";
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        rememberTicks++;
        if(rememberTicks >= 100){
            rememberTicks = 0;
            last = null;
        }
        HashMap<String, TileEntity> neighbors = new HashMap<>();
        neighbors.put("X+", worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord));
        neighbors.put("Y-", worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord));
        neighbors.put("Z+", worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1));
        neighbors.put("X-", worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord));
        neighbors.put("Y+", worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord));
        neighbors.put("Z-", worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1));
        neighbors.forEach((side, tile) -> {
            if (tile instanceof TileEntityFluidPipe && !tile.equals(last)) {
                TileEntityFluidContainer inv = (TileEntityFluidPipe) tile;
                FluidStack intFluid = getFluidInSlot(0);
                FluidStack extFluid = inv.getFluidInSlot(0);
                //insert into external empty
                if (intFluid != null && extFluid == null) {
                    if (!Objects.equals(side, "Y-") || isPressurized) {
                        last = (TileEntityFluidPipe) tile;
                        ((TileEntityFluidPipe) tile).last = this;
                        insertIntoEmptyExternal(inv, intFluid, transferSpeed);
                    }
                    //extract from external not empty
                } else if (intFluid == null && extFluid != null) {
                    if (!Objects.equals(side, "Y+") || isPressurized) {
                        last = (TileEntityFluidPipe) tile;
                        ((TileEntityFluidPipe) tile).last = this;
                        extractFromExternalWhenEmpty(inv, extFluid, transferSpeed);
                    }
                } else if (intFluid != null && extFluid != null) {
                    if (intFluid.isFluidEqual(extFluid)) {
                        if (Objects.equals(side, "Y-") && !isPressurized) {
                            last = (TileEntityFluidPipe) tile;
                            ((TileEntityFluidPipe) tile).last = this;
                            TakeFromExternal(inv, intFluid, extFluid, transferSpeed);
                        } else {
                            if (intFluid.amount < extFluid.amount) {
                                last = (TileEntityFluidPipe) tile;
                                ((TileEntityFluidPipe) tile).last = this;
                                TakeFromExternal(inv, intFluid, extFluid, transferSpeed);
                            } else if (intFluid.amount > extFluid.amount) {
                                last = (TileEntityFluidPipe) tile;
                                ((TileEntityFluidPipe) tile).last = this;
                                AddToExternal(inv, intFluid, extFluid, transferSpeed);
                            } else {
                                last = (TileEntityFluidPipe) tile;
                                ((TileEntityFluidPipe) tile).last = this;
                                AddToExternal(inv, intFluid, extFluid, transferSpeed);
                            }
                        }
                    }
                }
            }
        });
    }
}
