package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayerMP;
import sunsetsatellite.fluidapi.mp.packets.PacketSetFluidSlot;

@Mixin(
        value = EntityPlayerMP.class,
        remap = false
)
public abstract class EntityPlayerMPMixin extends EntityPlayer implements IEntityPlayerMP, ICrafting {

    public EntityPlayerMPMixin(World world) {
        super(world);
    }

    @Shadow protected abstract void getNextWindowId();

    @Shadow public NetServerHandler playerNetServerHandler;

    @Shadow private int currentWindowId;

    @Override
    public void displayGuiScreen_fluidapi(GuiScreen guiScreen, Container container, IInventory inventory) {
        this.getNextWindowId();
        this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, FluidAPI.config.getFromConfig("GuiID",8), inventory.getInvName(), inventory.getSizeInventory()));
        this.craftingInventory = container;
        this.craftingInventory.windowId = this.currentWindowId;
        this.craftingInventory.onContainerInit(((EntityPlayerMP)((Object)this)));
    }

    @Override
    public void updateFluidSlot(Container container, int i, FluidStack fluidStack) {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new PacketSetFluidSlot(container.windowId, i, fluidStack));
        }
    }
}
