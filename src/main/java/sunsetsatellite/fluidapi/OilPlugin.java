package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import org.slf4j.Logger;
import sunsetsatellite.fluidapi.util.Config;

public class OilPlugin implements FluidAPIPlugin {
    @Override
    public void initializePlugin(FluidRegistry registry, Logger logger) {
        if(Config.getFromConfig("enableOil",1)==1){
            logger.info("Loading fluid template fluid (Oil)..");
            registry.addFluid((BlockFluid) FluidAPI.oilFlowing, FluidAPI.bucketOil,Item.bucket);
        }
    }
}
