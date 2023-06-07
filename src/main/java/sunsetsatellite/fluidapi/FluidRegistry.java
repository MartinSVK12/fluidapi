package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;

import java.util.ArrayList;
import java.util.HashMap;

public class FluidRegistry {

    public FluidRegistry(){};

    public static HashMap<String,FluidRegistryEntry> registry = new HashMap<>();

    public static void addToRegistry(String identifier, FluidRegistryEntry entry){
        registry.put(entry.modId+":"+identifier, entry);
    }

    public static BlockFluid getFluidForContainer(Item container){
        for (FluidRegistryEntry entry : registry.values()) {
            if(entry.container == container || entry.containerEmpty == container){
                return entry.fluid;
            }
        }
        return null;
    }

    public static ArrayList<Item> getContainersForFluid(BlockFluid fluid){
        ArrayList<Item> items = new ArrayList<>();
        for (FluidRegistryEntry entry : registry.values()) {
            if(entry.fluid == fluid){
                items.add(entry.container);
            }
        }
        return items;
    }

    public static ArrayList<Item> getEmptyContainersForFluid(BlockFluid fluid){
        ArrayList<Item> items = new ArrayList<>();
        for (FluidRegistryEntry entry : registry.values()) {
            if(entry.fluid == fluid){
                items.add(entry.containerEmpty);
            }
        }
        return items;
    }

    public static Item findFilledContainer(Item emptyContainer, BlockFluid fluid){
        for (FluidRegistryEntry entry : registry.values()) {
            if(entry.fluid == fluid && entry.containerEmpty == emptyContainer){
                return entry.container;
            }
        }
        return null;
    }

    public static ArrayList<BlockFluid> getAllFluids(){
        ArrayList<BlockFluid> fluids = new ArrayList<>();
        for (FluidRegistryEntry entry : registry.values()) {
            fluids.add(entry.fluid);
        }
        return fluids;
    }
    /*public HashMap<Item, BlockFluid> fluids = new HashMap<>();
    public HashMap<BlockFluid, Item> fluidsInv = new HashMap<>();
    public HashMap<Item, Item> fluidContainers = new HashMap<>();

    public void addFluid(BlockFluid block, Item containerFilled, Item containerEmpty){
        FluidAPI.LOGGER.info("Adding fluid: "+block.getBlockName(0));
        FluidAPI.LOGGER.info("Adding filled container to fluid: "+containerFilled.getItemName());
        FluidAPI.LOGGER.info("Adding empty container to fluid: "+containerEmpty.getItemName());
        fluids.put(containerFilled,block);
        fluidsInv.put(block, containerFilled);
        fluidContainers.put(containerFilled, containerEmpty);
    }*/
}
