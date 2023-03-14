package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import org.slf4j.Logger;
import sunsetsatellite.guidebookpp.CustomGuidebookRecipeRegistry;
import sunsetsatellite.guidebookpp.GuidebookCustomRecipePlugin;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerBlastFurnace;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerCrafting;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerFurnace;
import sunsetsatellite.guidebookpp.recipes.RecipeBlastFurnace;
import sunsetsatellite.guidebookpp.recipes.RecipeFurnace;

public class DefaultPlugin implements FluidAPIPlugin {
    @Override
    public void initializePlugin(FluidRegistry registry, Logger logger) {
        logger.info("Loading vanilla minecraft fluids..");
        registry.addFluid((BlockFluid) Block.fluidLavaFlowing, Item.bucketLava,Item.bucket);
        registry.addFluid((BlockFluid) Block.fluidWaterFlowing, Item.bucketWater,Item.bucket);
    }
}
