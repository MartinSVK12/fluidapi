package sunsetsatellite.fluidapi.gbookpp;

import org.slf4j.Logger;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.guidebookpp.GuidebookCustomRecipePlugin;

public class FluidPlugin implements GuidebookCustomRecipePlugin {
    @Override
    public void initializePlugin(Logger logger) {
        if(FluidAPI.config.getFromConfig("enableMachine",1)==1){
            logger.info("Loading custom recipe plugin: "+this.getClass().getSimpleName()+" from "+FluidAPI.MOD_ID);
            new RecipeHandlerFluid().addRecipes();
            //customGuidebookRecipeRegistry.addHandler(new RecipeHandlerFluid(),RecipeFluid.class);
        }
    }
}
