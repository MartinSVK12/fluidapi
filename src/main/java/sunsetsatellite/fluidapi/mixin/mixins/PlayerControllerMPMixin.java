package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.fluidapi.api.ContainerFluid;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.interfaces.mixins.IPlayerControllerMP;
import sunsetsatellite.fluidapi.mp.packets.PacketFluidWindowClick;

@Mixin(
        value = PlayerControllerMP.class,
        remap = false
)
public class PlayerControllerMPMixin extends PlayerController implements IPlayerControllerMP {
    @Shadow protected NetClientHandler netHandler;

    public PlayerControllerMPMixin(Minecraft minecraft) {
        super(minecraft);
    }

    @Override
    public FluidStack fluidPickUpFromInventory(int i, int slotID, int button, boolean shift, boolean control, EntityPlayer entityplayer) {
        short word0 = entityplayer.craftingInventory.func_20111_a(entityplayer.inventory);
        FluidStack fluidStack = ((ContainerFluid)entityplayer.craftingInventory).clickFluidSlot(slotID, button, shift, control, entityplayer);
        this.netHandler.addToSendQueue(new PacketFluidWindowClick(i, slotID, button, shift, control, fluidStack, word0));
        return fluidStack;
    }
}
