package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayer;

@Mixin(
        value = EntityPlayer.class,
        remap = false
)
public class EntityPlayerMixin implements IEntityPlayer {
    @Override
    public void displayGuiScreen_fluidapi(IInventory inventory) {

    }

    @Override
    public void updateFluidSlot(Container container, int i, FluidStack fluidStack) {

    }
}
