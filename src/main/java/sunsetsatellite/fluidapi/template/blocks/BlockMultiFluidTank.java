package sunsetsatellite.fluidapi.template.blocks;

import net.minecraft.src.*;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.template.containers.ContainerFluidTank;
import sunsetsatellite.fluidapi.template.containers.ContainerMultiFluidTank;
import sunsetsatellite.fluidapi.template.gui.GuiFluidTank;
import sunsetsatellite.fluidapi.template.gui.GuiMultiFluidTank;
import sunsetsatellite.fluidapi.template.tiles.*;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;
import java.util.Random;

public class BlockMultiFluidTank extends BlockContainer {
    public BlockMultiFluidTank(int i1, Material material3) {
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
            TileEntityMassFluidItemContainer tile = (TileEntityMassFluidItemContainer) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                FluidAPI.displayGui(entityplayer,new GuiMultiFluidTank(entityplayer.inventory, tile),new ContainerMultiFluidTank(entityplayer.inventory,tile),tile);
            }
            return true;
        }
    }

    @Override
    public void onBlockRemoval(World world, int i, int j, int k) {
        TileEntityMultiFluidTank tile = (TileEntityMultiFluidTank) world.getBlockTileEntity(i, j, k);
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
        return new TileEntityMultiFluidTank();
    }
}
