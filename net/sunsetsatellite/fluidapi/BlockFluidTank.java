package net.sunsetsatellite.fluidapi;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Random;

public class BlockFluidTank extends BlockContainer {
    public BlockFluidTank(int i1, int i2, Material material3) {
        super(i1, i2, material3);
    }

    public int idDropped(int i, Random random)
    {
        return mod_FluidAPI.fluidTank.blockID;
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.multiplayerWorld)
        {
            return true;
        } else
        {
            TileEntityFluidItemContainer tile = (TileEntityFluidItemContainer) world.getBlockTileEntity(i, j, k);
            //System.out.println(TileEntityDigitalChest);
            if (tile != null) {
                ModLoader.OpenGUI(entityplayer, new GuiFluidTank(entityplayer.inventory, tile, "Fluid Tank"));
            }
            return true;
        }
    }

    @Override
    public void onBlockRemoval(World world, int i, int j, int k) {
        TileEntityFluidTank tile = (TileEntityFluidTank) world.getBlockTileEntity(i, j, k);
        //System.out.println(TileEntityDigitalChest);
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
        return new TileEntityFluidTank();
    }
}
