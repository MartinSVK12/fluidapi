package net.sunsetsatellite.fluidapi;

import net.minecraft.src.*;

import java.util.Random;

public class BlockFluidPipe extends BlockContainer {

	public BlockFluidPipe(int i, int j) {
		super(i, j, Material.glass);
	}

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        if(entityplayer.isSneaking()){
            TileEntityFluidPipe tile = (TileEntityFluidPipe) world.getBlockTileEntity(i,j,k);
            //tile.isPressurized = !tile.isPressurized;
            if(tile.getFluidInSlot(0) != null && tile.getFluidInSlot(0).getLiquid() != null){
                ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Liquid: "+tile.getFluidInSlot(0).toString());
            } else {
                ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Liquid: Empty");
            }
            ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Capacity: "+tile.fluidCapacity[0]);
            ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Is pressurized? "+tile.isPressurized);
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

    public int idDropped(int i, Random random)
    {
        return mod_FluidAPI.fluidPipe.blockID;
    }
    
    public int getRenderType()
    {
        return mod_FluidAPI.pipeRenderId;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityFluidPipe();
    }
}
