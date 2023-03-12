package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.fluidapi.ContainerFluid;
import sunsetsatellite.fluidapi.FluidStack;
import sunsetsatellite.fluidapi.interfaces.mixins.INetServerHandler;
import sunsetsatellite.fluidapi.mp.packets.PacketFluidWindowClick;

import java.util.ArrayList;

@Mixin(
        value = NetServerHandler.class,
        remap = false
)
public class NetServerHandlerMixin implements INetServerHandler {

    @Shadow private EntityPlayerMP playerEntity;

    @Override
    public void handleFluidWindowClick(PacketFluidWindowClick p) {
        if (this.playerEntity.craftingInventory.windowId == p.window_Id && this.playerEntity.craftingInventory instanceof ContainerFluid) {
            FluidStack fluidStack = ((ContainerFluid)this.playerEntity.craftingInventory).clickFluidSlot(p.inventorySlot, p.mouseClick, p.shift, p.control, this.playerEntity);
        }
    }
}
