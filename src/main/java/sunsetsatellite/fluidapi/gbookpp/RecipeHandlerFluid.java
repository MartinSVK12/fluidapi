package sunsetsatellite.fluidapi.gbookpp;


import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.util.MachineRecipes;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.RecipeGroup;
import sunsetsatellite.guidebookpp.RecipeRegistry;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeHandlerFluid
    implements IRecipeHandlerBase {
    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeFluid recipe = (RecipeFluid) o;
        return new ContainerGuidebookRecipeFluid(new ItemStack(FluidAPI.fluidMachine),recipe);
    }

    @Override
    public void addRecipes() {
        GuidebookPlusPlus.LOGGER.info("Adding recipes for: " + this.getClass().getSimpleName());
        ArrayList<RecipeFluid> recipes = new ArrayList<>();
        MachineRecipes.getInstance().getRecipeList().forEach((I, O)->{
            recipes.add(new RecipeFluid(
                    new ArrayList<>(Arrays.asList(new ItemStack(I,1,0))),
                    new ArrayList<>(),
                    new ArrayList<>(),
                    new ArrayList<>(Arrays.asList(O)),0,0)
            );
        });
        RecipeGroup group = new RecipeGroup("fluidapi",FluidAPI.fluidMachine,this,recipes);
        RecipeRegistry.groups.add(group);
    }
}
