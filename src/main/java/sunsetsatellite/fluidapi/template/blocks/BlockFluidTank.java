package sunsetsatellite.fluidapi.template.blocks;

import net.minecraft.src.*;
import sunsetsatellite.fluidapi.*;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.api.IItemFluidContainer;
import sunsetsatellite.fluidapi.template.containers.ContainerFluidTank;
import sunsetsatellite.fluidapi.template.gui.GuiFluidTank;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidPipe;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidTank;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;
import java.util.Random;

public class BlockFluidTank extends BlockContainer {
    public BlockFluidTank(int i1, Material material3) {
        super(i1, material3);
    }

    public int idDropped(int i, Random random)
    {
        return FluidAPI.fluidTank.blockID;
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isMultiplayerAndNotHost)
        {
            return true;
        } else
        {
            TileEntityFluidItemContainer tile = (TileEntityFluidItemContainer) world.getBlockTileEntity(i, j, k);
            /*ItemStack item = entityplayer.inventory.getCurrentItem();
            if(item != null) {
                if (item.getItem() instanceof ItemBucketEmpty) {
                    if (entityplayer.inventory.getCurrentItem().getItem() == Item.bucket) {
                        if (tile.getFluidInSlot(0) != null && tile.getFluidInSlot(0).amount >= 1000) {
                            entityplayer.inventory.getCurrentItem().itemID = FluidAPI.fluidRegistry.fluidsInv.get(tile.getFluidInSlot(0).liquid).itemID;
                            tile.decrFluidAmount(0, 1000);
                            tile.onFluidInventoryChanged();
                            return true;
                        }
                    }
                }
            }
            if(item != null && FluidAPI.fluidRegistry.fluids.get(entityplayer.inventory.getCurrentItem().getItem()) != null){
                Item bucket = (Item) entityplayer.inventory.getCurrentItem().getItem();
                if(bucket == Item.bucketWater || bucket == Item.bucketLava || bucket instanceof ItemBucket){
                    if (tile.getFluidInSlot(0) == null) {
                        entityplayer.inventory.getCurrentItem().itemID = bucket.getContainerItem().itemID;
                        tile.setFluidInSlot(0,new FluidStack(FluidAPI.fluidRegistry.fluids.get(bucket), 1000));
                    } else if (tile.getFluidInSlot(0)  != null && tile.getFluidInSlot(0).getLiquid() == FluidAPI.fluidRegistry.fluids.get(bucket)) {
                        if (tile.getFluidInSlot(0) .amount + 1000 <= tile.getFluidCapacityForSlot(0)) {
                            entityplayer.inventory.getCurrentItem().itemID = bucket.getContainerItem().itemID;
                            tile.getFluidInSlot(0).amount += 1000;
                            tile.onFluidInventoryChanged();
                        }
                    }
                } else if(bucket instanceof IItemFluidContainer){
                    if(FluidAPI.fluidRegistry.fluids.get(bucket) != null || FluidAPI.fluidRegistry.fluidContainers.containsValue(bucket)) {
                        IItemFluidContainer container = (IItemFluidContainer) bucket;
                        if (container.canDrain(entityplayer.inventory.getCurrentItem())) {
                            if (tile.getFluidInSlot(0) == null) {
                                container.drain(entityplayer.inventory.getCurrentItem(), tile.getFluidInSlot(0), tile);
                            } else if (tile.getFluidInSlot(0).amount < tile.getFluidCapacityForSlot(0)) {
                                container.drain(entityplayer.inventory.getCurrentItem(), tile.getFluidInSlot(0), tile);
                            } else if (tile.getFluidInSlot(0).amount >= tile.getFluidCapacityForSlot(0)) {
                                if (container.canFill(entityplayer.inventory.getCurrentItem())) {
                                    ItemStack stack = container.fill(0, tile, entityplayer.inventory.getCurrentItem());
                                    if (stack != null) {
                                        entityplayer.inventory.setHeldItemStack(stack);
                                    }
                                }
                            }
                        } else if (container.canFill(entityplayer.inventory.getCurrentItem())) {
                            ItemStack stack = container.fill(0, tile, entityplayer.inventory.getCurrentItem());
                            if (stack != null) {
                                entityplayer.inventory.setHeldItemStack(stack);
                            }
                        }
                    }
                }
                return true;
            }*/
            if(tile != null) {
                FluidAPI.displayGui(entityplayer,new GuiFluidTank(entityplayer.inventory, tile),new ContainerFluidTank(entityplayer.inventory,tile),tile);
            }
            return true;
        }
    }

    @Override
    public void onBlockRemoval(World world, int i, int j, int k) {
        TileEntityFluidTank tile = (TileEntityFluidTank) world.getBlockTileEntity(i, j, k);
        if (tile != null) {
            for (Direction dir : Direction.values()) {
                TileEntity tile2 = dir.getTileEntity(world,tile);
                if (tile2 instanceof TileEntityFluidPipe) {
                    tile.unpressurizePipes((TileEntityFluidPipe) tile2,new ArrayList<>());
                }
            }
        }
        super.onBlockRemoval(world, i, j, k);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityFluidTank();
    }
}
