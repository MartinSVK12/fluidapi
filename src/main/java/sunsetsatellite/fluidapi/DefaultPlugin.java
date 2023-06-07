package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;

public class DefaultPlugin implements FluidAPIPlugin {
    @Override
    public void initializePlugin(Logger logger) {
        logger.info("Loading vanilla minecraft fluids..");
        FluidRegistryEntry entry = new FluidRegistryEntry("minecraft",Item.bucketLava,Item.bucket, (BlockFluid)(Block.fluidLavaFlowing));
        FluidRegistry.addToRegistry("lava",entry);
        entry = new FluidRegistryEntry("minecraft",Item.bucketWater,Item.bucket, (BlockFluid)(Block.fluidWaterFlowing));
        FluidRegistry.addToRegistry("water",entry);
    }
}
