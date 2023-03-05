package sunsetsatellite.fluidapi.gbookpp;

import org.slf4j.Logger;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.guidebookpp.CustomGuidebookRecipeRegistry;
import sunsetsatellite.guidebookpp.GuidebookCustomRecipePlugin;

public class FluidPlugin implements GuidebookCustomRecipePlugin {
    @Override
    public void initializePlugin(CustomGuidebookRecipeRegistry customGuidebookRecipeRegistry, Logger logger) {
        logger.info("Loading custom recipe plugin: "+this.getClass().getSimpleName()+" from "+FluidAPI.MOD_ID);
        customGuidebookRecipeRegistry.addHandler(new RecipeHandlerFluid(),RecipeFluid.class);
    }
}
