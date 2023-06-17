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
        });
    }
}
