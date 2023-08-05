package sunsetsatellite.fluidapi.mixin.mixins;


import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryPlayer;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.util.helper.Color;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GuiContainer.class)
public class GuiContainerMixin extends GuiScreen {
    @Inject(
            method = "drawScreen",
            remap = false,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiRenderItem;render(Lnet/minecraft/core/item/ItemStack;IIZLnet/minecraft/core/player/inventory/slot/Slot;)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void colorWater(int x, int y, float renderPartialTicks, CallbackInfo ci, int centerX, int centerY, Slot slot, int i, Slot slot1, boolean mouseOver){
        if(slot != null && slot.getStack() != null) {
            if (slot.getStack().itemID == Block.fluidWaterFlowing.id) {
                int waterColor = BlockColorDispatcher.getInstance().getDispatch(Block.fluidWaterFlowing).getWorldColor(this.mc.theWorld,(int)this.mc.thePlayer.x,(int)this.mc.thePlayer.y,(int)this.mc.thePlayer.z);
                Color c = new Color().setARGB(waterColor);
                c.setRGBA(c.getRed(), c.getGreen(), c.getBlue(), 0x40);
                this.drawRect(slot.xDisplayPosition, slot.yDisplayPosition, slot.xDisplayPosition + 16, slot.yDisplayPosition + 16, c.value);
            }
        }
    }
    // TODO: readd if it is broken without it

}
