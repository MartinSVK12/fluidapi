package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import sunsetsatellite.fluidapi.FluidAPI;

import static net.minecraft.src.ItemBucketEmpty.UseBucket;

@Mixin(
        value = {ItemBucketEmpty.class},
        remap = false
)
public class ItemBucketEmptyMixin {
    /**
     * @author MartinSVK12
     * @reason Custom bucket pickup support.
     */
    @Overwrite
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        float f = 1.0F;
        float f1 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * f;
        float f2 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * f;
        double d = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)f;
        double d1 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)f + 1.62 - (double)entityplayer.yOffset;
        double d2 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)f;
        Vec3D vec3d = Vec3D.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 5.0;
        Vec3D vec3d1 = vec3d.addVector((double)f7 * d3, (double)f6 * d3, (double)f9 * d3);
        MovingObjectPosition movingobjectposition = world.rayTraceBlocks_do(vec3d, vec3d1, true);
        if (movingobjectposition != null) {
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;
                if (world.getBlockId(i, j, k) != 0){
                    Block block = Block.blocksList[world.getBlockId(i, j, k)-1];
                    if (FluidAPI.fluids.containsValue(block) && world.getBlockMetadata(i, j, k) == 0) {
                        if (UseBucket(entityplayer, new ItemStack(FluidAPI.fluidsInv.get(block)))) {
                            world.setBlockWithNotify(i, j, k, 0);
                            entityplayer.swingItem();
                        }
                    }
                }
            } else if (movingobjectposition.entityHit instanceof EntityCow) {
                UseBucket(entityplayer, new ItemStack(Item.bucketMilk));
            }
        }
        return itemstack;
    }
}
