package sunsetsatellite.fluidapi;

import net.minecraft.src.*;

import java.util.Random;

public class BlockFluidPipe extends BlockContainer {

	public BlockFluidPipe(int i) {
		super(i, Material.glass);
	}

    @Override
    public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
        if(entityplayer.isSneaking() && !world.isMultiplayerAndNotHost){
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

    public int idDropped(int i, Random random)
    {
        return FluidAPI.fluidPipe.blockID;
    }

    @Override
    public int getRenderType() {
        return 30;
    }

    @Override
    protected TileEntity getBlockEntity() {
        return new TileEntityFluidPipe();
    }
}
