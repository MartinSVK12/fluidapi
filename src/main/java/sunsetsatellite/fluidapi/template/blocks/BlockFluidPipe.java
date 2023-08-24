package sunsetsatellite.fluidapi.template.blocks;


import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidPipe;

import java.util.Random;

public class BlockFluidPipe extends BlockTileEntity {

	public BlockFluidPipe(String key, int i) {
		super(key, i, Material.glass);
	}

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        if(entityplayer.isSneaking() && !world.isClientSide){
            TileEntityFluidPipe tile = (TileEntityFluidPipe) world.getBlockTileEntity(i,j,k);
            if(tile.getFluidInSlot(0) != null && tile.getFluidInSlot(0).getLiquid() != null){
                entityplayer.addChatMessage("Liquid: "+tile.getFluidInSlot(0).toString());
            } else {
                entityplayer.addChatMessage("Liquid: Empty");
            }
            entityplayer.addChatMessage("Capacity: "+tile.fluidCapacity[0]);
            entityplayer.addChatMessage("Is pressurized? "+tile.isPressurized);
            return false;
        }
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    protected TileEntity getNewBlockEntity() {
        return new TileEntityFluidPipe();
    }
}
