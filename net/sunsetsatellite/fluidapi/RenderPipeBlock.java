package net.sunsetsatellite.fluidapi;

import net.minecraft.src.*;
import net.sunsetsatellite.retrostorage.TileEntityInNetwork;

public class RenderPipeBlock {
    public static boolean render(RenderBlocks renderblocks, IBlockAccess blockAccess, int i, int j, int k, Block block, int l) {
        float width = 0.6f;

        float halfWidth = (1.0F - width) / 2.0F;
        block.setBlockBounds(halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width);
        renderblocks.renderStandardBlock(block, i, j, k);
        boolean flag = blockAccess.getBlockId(i + 1, j, k) == block.blockID || (blockAccess.getBlockTileEntity(i + 1, j, k) instanceof IFluidInventory);
        boolean flag1 = blockAccess.getBlockId(i - 1, j, k) == block.blockID || (blockAccess.getBlockTileEntity(i - 1, j, k) instanceof IFluidInventory);
        boolean flag2 = blockAccess.getBlockId(i, j + 1, k) == block.blockID || (blockAccess.getBlockTileEntity(i, j + 1, k) instanceof IFluidInventory);
        boolean flag3 = blockAccess.getBlockId(i, j - 1, k) == block.blockID || (blockAccess.getBlockTileEntity(i, j - 1, k) instanceof IFluidInventory);
        boolean flag4 = blockAccess.getBlockId(i, j, k + 1) == block.blockID || (blockAccess.getBlockTileEntity(i, j, k + 1) instanceof IFluidInventory);
        boolean flag5 = blockAccess.getBlockId(i, j, k - 1) == block.blockID || (blockAccess.getBlockTileEntity(i, j, k - 1) instanceof IFluidInventory);
        if(flag)
        {
            block.setBlockBounds(halfWidth + width, halfWidth, halfWidth, 1.0F, halfWidth + width, halfWidth + width);
            renderblocks.renderStandardBlock(block, i, j, k);

        }
        if(flag2)
        {
            block.setBlockBounds(halfWidth, halfWidth + width, halfWidth, halfWidth + width, 1.0F, halfWidth + width);
            renderblocks.renderStandardBlock(block, i, j, k);

        }
        if(flag4)
        {
            block.setBlockBounds(halfWidth, halfWidth, halfWidth + width, halfWidth + width, halfWidth + width, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

        }
        if(flag1)
        {
            block.setBlockBounds(0.0F, halfWidth, halfWidth, halfWidth, halfWidth + width, halfWidth + width);
            renderblocks.renderStandardBlock(block, i, j, k);

        }
        if(flag3)
        {
            block.setBlockBounds(halfWidth, 0.0F, halfWidth, halfWidth + width, halfWidth, halfWidth + width);
            renderblocks.renderStandardBlock(block, i, j, k);

        }
        if(flag5)
        {
            block.setBlockBounds(halfWidth, halfWidth, 0.0F, halfWidth + width, halfWidth + width, halfWidth);
            renderblocks.renderStandardBlock(block, i, j, k);

        }
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }
}
