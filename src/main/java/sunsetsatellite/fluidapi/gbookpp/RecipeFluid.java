package sunsetsatellite.fluidapi.gbookpp;

import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

import java.util.ArrayList;

public class RecipeFluid extends RecipeBase {

    public ArrayList<ItemStack> itemInputs;
    public ArrayList<FluidStack> fluidInputs;
    public ArrayList<ItemStack> itemOutputs;
    public ArrayList<FluidStack> fluidOutputs;
    public int cost;
    public float duration;

    public RecipeFluid(ArrayList<ItemStack> itemInputs, ArrayList<FluidStack> fluidInputs, ArrayList<ItemStack> itemOutputs, ArrayList<FluidStack> fluidOutputs, int cost, float duration) {
        this.itemInputs = itemInputs;
        this.fluidInputs = fluidInputs;
        this.itemOutputs = itemOutputs;
        this.fluidOutputs = fluidOutputs;
        this.cost = cost;
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "RecipeFluid{" +
                "itemInputs=" + itemInputs +
                ", fluidInputs=" + fluidInputs +
                ", itemOutputs=" + itemOutputs +
                ", fluidOutputs=" + fluidOutputs +
                ", cost=" + cost +
                ", duration=" + duration +
                ", group=" + group +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof RecipeFluid)){
            return false;
        }
        for (ItemStack itemInput : ((RecipeFluid) o).itemInputs) {
            boolean found = false;
            for (ItemStack input : itemInputs) {
                if (itemInput.isItemEqual(input)) {
                    found = true;
                    break;
                }

            }
            if(!found){
                return false;
            }
        }
        for (ItemStack itemOutput : ((RecipeFluid) o).itemOutputs) {
            boolean found = false;
            for (ItemStack output : itemOutputs) {
                if (itemOutput.isItemEqual(output)) {
                    found = true;
                    break;
                }

            }
            if(!found){
                return false;
            }
        }
        for (FluidStack fluidInput : ((RecipeFluid) o).fluidInputs) {
            boolean found = false;
            for (FluidStack input : fluidInputs) {
                if (fluidInput.isFluidEqual(input)) {
                    found = true;
                    break;
                }

            }
            if(!found){
                return false;
            }
        }
        for (FluidStack fluidOutput : ((RecipeFluid) o).fluidOutputs) {
            boolean found = false;
            for (FluidStack output : fluidOutputs) {
                if (fluidOutput.isFluidEqual(output)) {
                    found = true;
                    break;
                }

            }
            if(!found){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        if(!(o instanceof ItemStack)){
            return false;
        }
        return containsInput(o) || containsOutput(o);
    }

    @Override
    public boolean containsInput(Object o) {
        if(!(o instanceof ItemStack)){
            return false;
        }
        for (ItemStack input : itemInputs) {
            if (((ItemStack) o).isItemEqual(input)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOutput(Object o) {
        if(!(o instanceof ItemStack)){
            return false;
        }
        for (ItemStack output : itemOutputs) {
            if (((ItemStack) o).isItemEqual(output)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(String s) {
        StringTranslate translator = StringTranslate.getInstance();
        return containsInput(s) || containsOutput(s);
    }

    @Override
    public boolean containsInput(String s) {
        StringTranslate translator = StringTranslate.getInstance();
        for (ItemStack input : itemInputs) {
            if(translator.translateKey(input.getItemName()+".name").toLowerCase().contains(s.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOutput(String s) {
        StringTranslate translator = StringTranslate.getInstance();
        for (ItemStack output : itemOutputs) {
            if(translator.translateKey(output.getItemName()+".name").toLowerCase().contains(s.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}

