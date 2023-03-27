package sunsetsatellite.fluidapi.api;

import net.minecraft.src.BlockFluid;

import java.util.ArrayList;

public interface IFluidInventory {
    FluidStack getFluidInSlot(int slot);
    int getFluidCapacityForSlot(int slot);
    ArrayList<BlockFluid> getAllowedFluidsForSlot(int slot);
    void setFluidInSlot(int slot, FluidStack fluid);
    FluidStack decrFluidAmount(int slot, int amount);
    FluidStack incrFluidAmount(int slot, int amount);
    int getFluidInventorySize();
    void onFluidInventoryChanged();
}
