package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MachineRecipes {
    private static final MachineRecipes instance = new MachineRecipes();
    private Map recipeList = new HashMap();

    public static final MachineRecipes getInstance() {
        return instance;
    }

    private MachineRecipes() {
        this.addRecipe(Block.ice.blockID,new FluidStack((BlockFluid) Block.fluidWaterFlowing,1000));
        this.addRecipe(Block.obsidian.blockID,new FluidStack((BlockFluid) Block.fluidLavaFlowing,1000));
    }

    public void addRecipe(int id, FluidStack stack) {
        this.recipeList.put(id, stack);
    }

    public FluidStack getResult(int id) {
        FluidStack stack = ((FluidStack)this.recipeList.get(id));
        return stack == null ? null : stack.copy();
    }

    public Map getRecipeList() {
        return this.recipeList;
    }
}