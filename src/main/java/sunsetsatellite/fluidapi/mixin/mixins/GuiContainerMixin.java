package sunsetsatellite.fluidapi.mixin.mixins;


import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiContainer.class)
public class GuiContainerMixin extends GuiScreen {
    /*@Inject(
            method = "drawScreen",
            remap = false,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderItem;renderItemIntoGUI(Lnet/minecraft/src/FontRenderer;Lnet/minecraft/src/RenderEngine;Lnet/minecraft/src/ItemStack;IIF)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void colorWater(int x, int y, float renderPartialTicks, CallbackInfo ci, int centerX, int centerY, Slot slot, InventoryPlayer inventoryplayer){
        if(slot != null && slot.getStack() != null) {
            if (slot.getStack().itemID == Block.fluidWaterFlowing.id) {
                Color c = new Color().setARGB(Block.fluidWaterFlowing.colorMultiplier(this.mc.theWorld, this.mc.theWorld, (int) inventoryplayer.player.posX, (int) inventoryplayer.player.posY, (int) inventoryplayer.player.posZ));
                c.setRGBA(c.getRed(), c.getGreen(), c.getBlue(), 0x40);
                this.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, c.value);
            }
        }
    }

    @Inject(
            method = "drawSlotInventory",
            remap = false,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/src/RenderItem;renderItemIntoGUI(Lnet/minecraft/src/FontRenderer;Lnet/minecraft/src/RenderEngine;Lnet/minecraft/src/ItemStack;IIFF)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void colorWater2(Slot slot, CallbackInfo ci, int i, int j, ItemStack itemstack){
        if(slot != null && slot.getStack() != null){
            if(slot.getStack().itemID == Block.fluidWaterFlowing.id){
                Color c = new Color().setARGB(Block.fluidWaterFlowing.colorMultiplier(this.mc.theWorld, this.mc.theWorld,(int)this.mc.thePlayer.posX,(int)this.mc.thePlayer.posY,(int)this.mc.thePlayer.posZ));
                c.setRGBA(c.getRed(),c.getGreen(),c.getBlue(),0x40);
                this.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition+16, slot.yDisplayPosition+16,c.value);
            }
        }
    }*/
    // TODO: readd if it is broken without it

}
