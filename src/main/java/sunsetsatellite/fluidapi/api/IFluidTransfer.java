package sunsetsatellite.fluidapi.api;


import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

public interface IFluidTransfer {
    void take(FluidStack fluidStack, Direction dir);

    void give(Direction dir);

    Connection getConnection(Direction dir);
}
