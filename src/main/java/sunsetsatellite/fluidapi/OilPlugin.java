package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import org.slf4j.Logger;
import sunsetsatellite.fluidapi.util.Config;

import java.util.ArrayList;
import java.util.Collections;

public class OilPlugin implements FluidAPIPlugin {
    @Override
    public void initializePlugin(Logger logger) {
        if(Config.getFromConfig("enableOil",1)==1){
            logger.info("Loading fluid template fluid (Oil)..");
            FluidRegistryEntry entry = new FluidRegistryEntry(FluidAPI.MOD_ID,FluidAPI.bucketOil,Item.bucket, (BlockFluid) FluidAPI.oilFlowing);
            FluidRegistry.addToRegistry("oil",entry);
        }
    }
}
