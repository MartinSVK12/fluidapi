package sunsetsatellite.fluidapi;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Random;

public class BlockMachine extends BlockContainerRotatable {
    public BlockMachine(int i, Material material) {
        super(i, material);
    }

    public int idDropped(int i, Random random)
    {
        return FluidAPI.fluidMachine.blockID;
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
            TileEntityMachine tile = (TileEntityMachine) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                FluidAPI.displayGui(entityplayer,new GuiMachine(entityplayer.inventory, tile),new ContainerMachine(entityplayer.inventory,tile),tile);
            }
            return true;
        }
    }

    @Override
    public void onBlockRemoval(World world, int i, int j, int k) {
        TileEntityMachine tile = (TileEntityMachine) world.getBlockTileEntity(i, j, k);
        if (tile != null) {
            tile.dir.forEach((K,V)-> {
                TileEntity tile2 = world.getBlockTileEntity(i + (int) V.xCoord, j + (int) V.yCoord, k + (int) V.zCoord);
                if (tile2 instanceof TileEntityFluidPipe) {
                    tile.unpressurizePipes((TileEntityFluidPipe) tile2,new ArrayList<>());
                }
            });
        }
        super.onBlockRemoval(world, i, j, k);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityMachine();
    }
}
