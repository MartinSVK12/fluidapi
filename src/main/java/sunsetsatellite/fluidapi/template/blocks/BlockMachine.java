package sunsetsatellite.fluidapi.template.blocks;


import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayer;
import sunsetsatellite.fluidapi.template.containers.ContainerMachine;
import sunsetsatellite.fluidapi.template.gui.GuiMachine;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidPipe;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMachine;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;
import java.util.Random;

public class BlockMachine extends BlockTileEntityRotatable {
    public BlockMachine(String key, int i, Material material) {
        super(key, i, material);
    }

    public void onBlockAdded(World world, int i, int j, int k) {
        super.onBlockAdded(world, i, j, k);
    }

    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if (!world.isClientSide) {
            TileEntityMachine tile = (TileEntityMachine) world.getBlockTileEntity(i, j, k);
            if(tile != null) {
                ((IEntityPlayer)entityplayer).displayGuiScreen_fluidapi(tile);
            }
        }
        return true;
    }

    @Override
    public void onBlockRemoval(World world, int i, int j, int k) {
        TileEntityMachine tile = (TileEntityMachine) world.getBlockTileEntity(i, j, k);
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
    protected TileEntity getNewBlockEntity() {
        return new TileEntityMachine();
    }
}
