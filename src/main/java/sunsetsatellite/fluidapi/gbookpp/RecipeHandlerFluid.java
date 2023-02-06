package sunsetsatellite.fluidapi.gbookpp;

import net.minecraft.src.*;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.FluidStack;
import sunsetsatellite.fluidapi.MachineRecipes;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.recipes.RecipeFurnace;

import java.util.*;

public class RecipeHandlerFluid
    implements IRecipeHandlerBase {
    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeFluid recipe = (RecipeFluid) o;
        return new ContainerGuidebookRecipeFluid(new ItemStack(FluidAPI.fluidMachine),recipe.itemInputs,recipe.fluidInputs,recipe.itemOutputs,recipe.fluidOutputs);
    }


    public int getRecipeAmount() {
        return getRecipes().size();
    }

    public ArrayList<?> getRecipes() {
        HashMap<Integer, FluidStack> rawRecipes = new HashMap<>(MachineRecipes.getInstance().getRecipeList());
        ArrayList<RecipeFluid> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{
            ArrayList<ItemStack> singletonList = new ArrayList<>(Collections.singleton(new ItemStack(I, 1, 0)));
            ArrayList<FluidStack> singletonList2 = new ArrayList<>(Collections.singleton(O));
            recipes.add(new RecipeFluid(singletonList,null,null, singletonList2));
        });
        return recipes;
    }

    public ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage) {
        HashMap<Integer,FluidStack> rawRecipes = new HashMap<>(MachineRecipes.getInstance().getRecipeList());
        ArrayList<RecipeFluid> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{

            /*if(usage){
                if(O.isItemEqual(filter)){
                    recipes.add(new RecipeFurnace(new ItemStack(I,1,0),O));
                }
            } else {*/
            if(usage){
                if(new ItemStack(I,1,0).isItemEqual(filter)){
                    ArrayList<ItemStack> singletonList = new ArrayList<>(Collections.singleton(new ItemStack(I, 1, 0)));
                    ArrayList<FluidStack> singletonList2 = new ArrayList<>(Collections.singleton(O));
                    recipes.add(new RecipeFluid(singletonList,null,null, singletonList2));
                }
            }
        });
        return recipes;
    }
}
