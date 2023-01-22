package sunsetsatellite.fluidapi;

public interface IFluidInventory {
    FluidStack getFluidInSlot(int slot);
    int getFluidCapacityForSlot(int slot);
    void setFluidInSlot(int slot, FluidStack fluid);
    FluidStack decrFluidAmount(int slot, int amount);
    FluidStack incrFluidAmount(int slot, int amount);
    int getFluidInventorySize();
    void onFluidInventoryChanged();
}
