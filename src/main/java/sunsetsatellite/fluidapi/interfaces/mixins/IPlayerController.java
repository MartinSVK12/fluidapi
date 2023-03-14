package sunsetsatellite.fluidapi.interfaces.mixins;

import net.minecraft.src.EntityPlayer;
import sunsetsatellite.fluidapi.api.FluidStack;

public interface IPlayerController {

    FluidStack fluidPickUpFromInventory(int i, int slotID, int button, boolean shift, boolean control, EntityPlayer entityplayer);
}
