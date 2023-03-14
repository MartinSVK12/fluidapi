package sunsetsatellite.fluidapi.gbookpp;

import net.minecraft.src.ItemStack;
import sunsetsatellite.fluidapi.api.FluidStack;

import java.util.ArrayList;

public class RecipeFluid {
    public ArrayList<ItemStack> itemInputs;
    public ArrayList<FluidStack> fluidInputs;
    public ArrayList<ItemStack> itemOutputs;
    public ArrayList<FluidStack> fluidOutputs;

    public RecipeFluid(ArrayList<ItemStack> itemInputs, ArrayList<FluidStack> fluidInputs, ArrayList<ItemStack> itemOutputs, ArrayList<FluidStack> fluidOutputs) {
        this.itemInputs = itemInputs;
        this.fluidInputs = fluidInputs;
        this.itemOutputs = itemOutputs;
        this.fluidOutputs = fluidOutputs;
    }

}
