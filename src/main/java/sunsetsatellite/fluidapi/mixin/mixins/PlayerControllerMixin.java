package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerController;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.fluidapi.ContainerFluid;
import sunsetsatellite.fluidapi.FluidStack;
import sunsetsatellite.fluidapi.interfaces.mixins.IPlayerController;

@Mixin(
      value = PlayerController.class,
      remap = false
)
public class PlayerControllerMixin implements IPlayerController {

    @Override
    public FluidStack fluidPickUpFromInventory(int i, int j, int k, boolean flag, boolean control, EntityPlayer entityplayer) {
        if(entityplayer.craftingInventory instanceof ContainerFluid){
            return ((ContainerFluid) entityplayer.craftingInventory).clickFluidSlot(j, k, flag, control, entityplayer);
        }
        return null;
    }
}
