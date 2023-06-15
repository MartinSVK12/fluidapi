package sunsetsatellite.fluidapi.api;

import net.minecraft.src.BlockFluid;
import sunsetsatellite.sunsetutils.util.Direction;

public interface IMassFluidInventory extends IFluidInventory {
    int getAllFluidAmount();

    int getRemainingCapacity();

    FluidStack findStack(BlockFluid fluid);
    FluidStack insertFluid(FluidStack fluidStack);

    BlockFluid getFilter(Direction dir);

    boolean canInsertFluid(FluidStack fluidStack);
}
