package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import org.slf4j.Logger;

public class DefaultPlugin implements FluidAPIPlugin {
    @Override
    public void initializePlugin(FluidRegistry registry, Logger logger) {
        logger.info("Loading vanilla minecraft fluids..");
        registry.addFluid((BlockFluid) Block.fluidLavaFlowing, Item.bucketLava,Item.bucket);
        registry.addFluid((BlockFluid) Block.fluidWaterFlowing, Item.bucketWater,Item.bucket);
    }
}
