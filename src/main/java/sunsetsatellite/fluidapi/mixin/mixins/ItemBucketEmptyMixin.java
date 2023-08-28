package sunsetsatellite.fluidapi.mixin.mixins;


import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFluid;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.animal.EntityCow;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBucketEmpty;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import sunsetsatellite.fluidapi.FluidRegistry;

import java.util.Objects;

@Mixin(
        value = {ItemBucketEmpty.class},
        remap = false
)
public abstract class ItemBucketEmptyMixin {
    @Shadow
    public static boolean useBucket(EntityPlayer player, ItemStack itemToGive) {
        return false;
    }

    /**
     * @author MartinSVK12
     * @reason Custom bucket pickup support.
     */
    @Overwrite
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        float f = 1.0F;
        float f1 = entityplayer.xRotO + (entityplayer.xRot - entityplayer.xRotO) * f;
        float f2 = entityplayer.yRotO + (entityplayer.yRot - entityplayer.yRotO) * f;
        double d = entityplayer.xo + (entityplayer.x - entityplayer.xo) * (double)f;
        double d1 = entityplayer.yo + (entityplayer.y - entityplayer.yo) * (double)f + 1.62 - (double)entityplayer.heightOffset;
        double d2 = entityplayer.zo + (entityplayer.z - entityplayer.zo) * (double)f;
        Vec3d vec3d = Vec3d.createVector(d, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-f2 * 0.01745329F - 3.141593F);
        float f5 = -MathHelper.cos(-f1 * 0.01745329F);
        float f6 = MathHelper.sin(-f1 * 0.01745329F);
        float f7 = f4 * f5;
        float f9 = f3 * f5;
        double d3 = 5.0;
        Vec3d vec3d1 = vec3d.addVector((double)f7 * d3, (double)f6 * d3, (double)f9 * d3);
        HitResult movingobjectposition = world.checkBlockCollisionBetweenPoints(vec3d, vec3d1, true);
        if (movingobjectposition == null) {
            return itemstack;
        } else {
            if (movingobjectposition.hitType == HitResult.HitType.TILE) {
                int i = movingobjectposition.x;
                int j = movingobjectposition.y;
                int k = movingobjectposition.z;
                if (!world.canMineBlock(entityplayer, i, j, k)) {
                    return itemstack;
                }

                if (world.getBlockId(i, j, k) != 0){
                    Block block = Block.blocksList[world.getBlockId(i, j, k)-1];
                    if (block instanceof BlockFluid && world.getBlockMetadata(i, j, k) == 0) {
                        if (useBucket(entityplayer, new ItemStack(Objects.requireNonNull(FluidRegistry.findFilledContainer(entityplayer.inventory.getCurrentItem().getItem(), (BlockFluid) block))))) {
                            world.setBlockWithNotify(i, j, k, 0);
                            entityplayer.swingItem();
                        }
                    }
                }

                /*if (world.getBlockMaterial(i, j, k) == Material.water && world.getBlockMetadata(i, j, k) == 0) {
                    if (UseBucket(entityplayer, new ItemStack(Item.bucketWater))) {
                        world.setBlockWithNotify(i, j, k, 0);
                        entityplayer.swingItem();
                    }
                } else if (world.getBlockMaterial(i, j, k) == Material.lava
                        && world.getBlockMetadata(i, j, k) == 0
                        && UseBucket(entityplayer, new ItemStack(Item.bucketLava))) {
                    world.setBlockWithNotify(i, j, k, 0);
                    entityplayer.swingItem();
                }*/
            } else if (movingobjectposition.entity instanceof EntityCow) {
                useBucket(entityplayer, new ItemStack(Item.bucketMilk));
            }

            return itemstack;
        }
    }
    /*public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
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
                if (world.getid(i, j, k) != 0){
                    Block block = Block.blocksList[world.getid(i, j, k)-1];
                    if (block instanceof BlockFluid && world.getBlockMetadata(i, j, k) == 0) {
                        if (getFilledBucket(entityplayer, new ItemStack(Objects.requireNonNull(FluidRegistry.findFilledContainer(entityplayer.inventory.getCurrentItem().getItem(), (BlockFluid) block))))) {
                            world.setBlockWithNotify(i, j, k, 0);
                            entityplayer.swingItem();
                        }
                    }
                }
            } else if (movingobjectposition.entityHit instanceof EntityCow) {
                getFilledBucket(entityplayer, new ItemStack(Item.bucketMilk));
            }
        }
        return itemstack;
    }*/

}
