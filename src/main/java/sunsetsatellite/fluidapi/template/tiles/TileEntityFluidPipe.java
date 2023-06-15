package sunsetsatellite.fluidapi.template.tiles;

import net.minecraft.src.TileEntity;
import org.lwjgl.Sys;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.api.IFluidInventory;
import sunsetsatellite.fluidapi.api.IMassFluidInventory;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.HashMap;
import java.util.Objects;

public class TileEntityFluidPipe extends TileEntityFluidContainer{
    public boolean isPressurized = false;
    private TileEntityFluidPipe last = null;

    public float size = 0.5f;

    public int rememberTicks = 0;
    public int maxRememberTicks = 100;

    public TileEntityFluidPipe(){
        fluidCapacity[0] = 2000;
        transferSpeed = 20;
        for (Direction dir : Direction.values()) {
            connections.put(dir, Connection.BOTH);
            activeFluidSlots.put(dir,0);
        }
        acceptedFluids.get(0).addAll(FluidRegistry.getAllFluids());

    }

    /*public void insertIntoEmptyExternal(TileEntityFluidContainer inv, FluidStack intFluid, int amount, Direction dir){
        Integer activeSlot = inv.activeFluidSlots.get(dir);
        if(inv.acceptedFluids.get(activeSlot).contains(intFluid.liquid) || inv.acceptedFluids.get(activeSlot).isEmpty()){
            if(intFluid.amount >= amount){
                inv.setFluidInSlot(activeSlot,new FluidStack(intFluid.getLiquid(),amount));
                decrFluidAmount(0,amount);
            } else {
                int size = intFluid.amount;
                inv.setFluidInSlot(activeSlot,new FluidStack(intFluid.getLiquid(),size));
                decrFluidAmount(0,size);
            }
        }
    }

    public void extractFromExternalWhenEmpty(TileEntityFluidContainer inv, FluidStack extFluid, int amount, Direction dir){
        Integer activeSlot = inv.activeFluidSlots.get(dir);
        if(acceptedFluids.get(0).contains(extFluid.liquid) || acceptedFluids.get(0).isEmpty()) {
            if (extFluid.amount >= amount) {
                setFluidInSlot(0, new FluidStack(extFluid.getLiquid(), amount));
                inv.decrFluidAmount(activeSlot, amount);
            } else {
                int size = extFluid.amount;
                setFluidInSlot(0, new FluidStack(extFluid.getLiquid(), size));
                inv.decrFluidAmount(activeSlot, size);
            }
        }
    }

    public void AddToExternal(TileEntityFluidContainer inv, FluidStack intFluid, FluidStack extFluid, int amount, Direction dir){
        Integer activeSlot = inv.activeFluidSlots.get(dir);
        if (intFluid.isFluidEqual(extFluid) && (inv.acceptedFluids.get(activeSlot).contains(intFluid.liquid) || inv.acceptedFluids.get(activeSlot).isEmpty())) {
            if (extFluid.amount + amount <= inv.getFluidCapacityForSlot(activeSlot)) {
                if (intFluid.amount >= amount) {
                    inv.incrFluidAmount(activeSlot, amount);
                    decrFluidAmount(0, amount);
                } else {
                    int size = intFluid.amount;
                    inv.incrFluidAmount(activeSlot, size);
                    decrFluidAmount(0, size);
                }
            } else if(extFluid.amount + amount > inv.getFluidCapacityForSlot(activeSlot)){
                int size = inv.getFluidCapacityForSlot(activeSlot) - extFluid.amount;
                inv.incrFluidAmount(activeSlot, size);
                decrFluidAmount(0, size);
            }
        }
    }

    public void TakeFromExternal(TileEntityFluidContainer inv, FluidStack intFluid, FluidStack extFluid, int amount, Direction dir){
        Integer activeSlot = inv.activeFluidSlots.get(dir);
        if (intFluid.isFluidEqual(extFluid) && (acceptedFluids.get(0).contains(extFluid.liquid) || acceptedFluids.get(0).isEmpty())) {
            if (intFluid.amount + amount <= getFluidCapacityForSlot(0)) {
                if (extFluid.amount >= amount) {
                    inv.decrFluidAmount(activeSlot, amount);
                    incrFluidAmount(0, amount);
                } else {
                    int size = extFluid.amount;
                    inv.decrFluidAmount(activeSlot, size);
                    incrFluidAmount(0, size);
                }
            } else if(intFluid.amount + amount > inv.getFluidCapacityForSlot(activeSlot)){
                int size = inv.getFluidCapacityForSlot(activeSlot) - intFluid.amount;
                inv.decrFluidAmount(activeSlot, size);
                incrFluidAmount(0, size);
            }
        }
    }*/

    @Override
    public String getInvName() {
        return "Fluid Pipe";
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        rememberTicks++;
        if(rememberTicks >= maxRememberTicks){
            rememberTicks = 0;
            last = null;
        }
        HashMap<Direction, TileEntity> neighbors = new HashMap<>();
        for (Direction dir : Direction.values()) {
            neighbors.put(dir,dir.getTileEntity(worldObj,this));
        }
        neighbors.forEach((side, tile) -> {
            if (tile instanceof TileEntityFluidPipe && !tile.equals(last)) {
                TileEntityFluidPipe inv = (TileEntityFluidPipe) tile;
                Integer activeSlot = inv.activeFluidSlots.get(side.getOpposite());
                FluidStack intFluid = getFluidInSlot(0);
                FluidStack extFluid = inv.getFluidInSlot(activeSlot);
                //TODO: Readd "pressure" mechanic (fluids cant climb up if pipes are unpressurized)
                if (isPressurized) {
                    if (intFluid != null && extFluid == null) {
                        last = (TileEntityFluidPipe) tile;
                        ((TileEntityFluidPipe) tile).last = this;
                        give(side);
                    } else if (intFluid == null && extFluid != null) {
                        last = (TileEntityFluidPipe) tile;
                        ((TileEntityFluidPipe) tile).last = this;
                        take(extFluid,side);
                    } else if (intFluid != null) { //if both internal and external aren't null
                        last = (TileEntityFluidPipe) tile;
                        ((TileEntityFluidPipe) tile).last = this;
                        if (intFluid.amount < extFluid.amount) {
                            take(extFluid,side);
                        } else {
                            give(side);
                        }
                    }
                }
                /*TileEntityFluidContainer inv = (TileEntityFluidPipe) tile;
                Integer activeSlot = inv.activeFluidSlots.get(side.getOpposite());
                FluidStack intFluid = getFluidInSlot(activeSlot);
                FluidStack extFluid = inv.getFluidInSlot(activeSlot);
                //insert into external empty
                if (intFluid != null && extFluid == null) {
                    if (!Objects.equals(side, Direction.Y_NEG) || isPressurized) {
                        last = (TileEntityFluidPipe) tile;
                        ((TileEntityFluidPipe) tile).last = this;
                        insertIntoEmptyExternal(inv, intFluid, transferSpeed,side);
                    }
                    //extract from external not empty
                } else if (intFluid == null && extFluid != null) {
                    if (!Objects.equals(side, Direction.Y_POS) || isPressurized) {
                        last = (TileEntityFluidPipe) tile;
                        ((TileEntityFluidPipe) tile).last = this;
                        extractFromExternalWhenEmpty(inv, extFluid, transferSpeed,side);
                    }
                } else if (intFluid != null && extFluid != null) {
                    if (intFluid.isFluidEqual(extFluid)) {
                        if (Objects.equals(side, Direction.Y_NEG) && !isPressurized) {
                            last = (TileEntityFluidPipe) tile;
                            ((TileEntityFluidPipe) tile).last = this;
                            TakeFromExternal(inv, intFluid, extFluid, transferSpeed,side);
                        } else {
                            if (intFluid.amount < extFluid.amount) {
                                last = (TileEntityFluidPipe) tile;
                                ((TileEntityFluidPipe) tile).last = this;
                                TakeFromExternal(inv, intFluid, extFluid, transferSpeed,side);
                            } else if (intFluid.amount > extFluid.amount) {
                                last = (TileEntityFluidPipe) tile;
                                ((TileEntityFluidPipe) tile).last = this;
                                AddToExternal(inv, intFluid, extFluid, transferSpeed,side);
                            } else {
                                last = (TileEntityFluidPipe) tile;
                                ((TileEntityFluidPipe) tile).last = this;
                                AddToExternal(inv, intFluid, extFluid, transferSpeed,side);
                            }
                        }
                    }
                }*/
            }
        });
    }
}
