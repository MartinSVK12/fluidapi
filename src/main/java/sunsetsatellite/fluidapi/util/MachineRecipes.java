package sunsetsatellite.fluidapi.util;


import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import sunsetsatellite.fluidapi.api.FluidStack;

import java.util.HashMap;

public class MachineRecipes {
    private static final MachineRecipes instance = new MachineRecipes();
    private HashMap<Integer, FluidStack> recipeList = new HashMap();

    public static final MachineRecipes getInstance() {
        return instance;
    }

    private MachineRecipes() {
        this.addRecipe(Block.ice.id,new FluidStack((BlockFluid) Block.fluidWaterFlowing,1000));
        this.addRecipe(Block.obsidian.id,new FluidStack((BlockFluid) Block.fluidLavaFlowing,1000));
    }

    public void addRecipe(int id, FluidStack stack) {
        this.recipeList.put(id, stack);
    }

    public FluidStack getResult(int id) {
        FluidStack stack = ((FluidStack)this.recipeList.get(id));
        return stack == null ? null : stack.copy();
    }

    public HashMap<Integer,FluidStack> getRecipeList() {
        return this.recipeList;
    }
}