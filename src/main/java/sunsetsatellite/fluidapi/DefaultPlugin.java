package sunsetsatellite.fluidapi;


import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;

public class DefaultPlugin implements FluidAPIPlugin {
    @Override
    public void initializePlugin(Logger logger) {
        logger.info("Loading vanilla minecraft fluids..");
        FluidRegistryEntry entry = new FluidRegistryEntry("minecraft", Item.bucketLava,Item.bucket, (BlockFluid)(Block.fluidLavaFlowing));
        FluidRegistry.addToRegistry("lava",entry);
        entry = new FluidRegistryEntry("minecraft",Item.bucketWater,Item.bucket, (BlockFluid)(Block.fluidWaterFlowing));
        FluidRegistry.addToRegistry("water",entry);
    }
}
