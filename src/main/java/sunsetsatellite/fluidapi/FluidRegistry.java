package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;

import java.util.HashMap;

public class FluidRegistry {

    public FluidRegistry(){};

    public static HashMap<Item, BlockFluid> fluids = new HashMap<>();
    public static HashMap<BlockFluid, Item> fluidsInv = new HashMap<>();
    public static HashMap<Item, Item> fluidContainers = new HashMap<>();

    public void addFluid(BlockFluid block, Item containerFilled, Item containerEmpty){
        FluidAPI.LOGGER.info("Adding fluid: "+block.getBlockName(0));
        FluidAPI.LOGGER.info("Adding filled container to fluid: "+containerFilled.getItemName());
        FluidAPI.LOGGER.info("Adding empty container to fluid: "+containerEmpty.getItemName());
        fluids.put(containerFilled,block);
        fluidsInv.put(block, containerFilled);
        fluidContainers.put(containerFilled, containerEmpty);
    }
}
