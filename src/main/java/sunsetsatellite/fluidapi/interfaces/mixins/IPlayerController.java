package sunsetsatellite.fluidapi.interfaces.mixins;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import sunsetsatellite.fluidapi.FluidStack;

public interface IPlayerController {

    FluidStack fluidPickUpFromInventory(int i, int slotID, int button, boolean shift, boolean control, EntityPlayer entityplayer);
}
