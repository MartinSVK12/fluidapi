package sunsetsatellite.fluidapi.mixin.mixins;


import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.crafting.ICrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.api.FluidStack;
import sunsetsatellite.fluidapi.interfaces.mixins.IEntityPlayer;
import sunsetsatellite.fluidapi.mp.packets.PacketSetFluidSlot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@Mixin(
        value = EntityPlayerMP.class,
        remap = false
)
public abstract class EntityPlayerMPMixin extends EntityPlayer implements IEntityPlayer, ICrafting {

    public EntityPlayerMPMixin(World world) {
        super(world);
    }

    @Shadow
    protected abstract void getNextWindowId();

    @Shadow public NetServerHandler playerNetServerHandler;

    @Shadow private int currentWindowId;

    @Unique
    private final EntityPlayerMP thisAs = (EntityPlayerMP)(Object)this;

    @Override
    public void displayGuiScreen_fluidapi(IInventory inventory) {
        this.getNextWindowId();
        ArrayList<Class<?>> list = FluidAPI.nameToGuiMap.get(inventory.getInvName());
        this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, FluidAPI.config.getFromConfig("GuiID",8), inventory.getInvName(), inventory.getSizeInventory()));
        try {
            this.craftingInventory = (Container)list.get(2).getDeclaredConstructors()[0].newInstance(thisAs.inventory, inventory);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        this.craftingInventory.windowId = this.currentWindowId;
        this.craftingInventory.onContainerInit(thisAs);
    }

    @Override
    public void updateFluidSlot(Container container, int i, FluidStack fluidStack) {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacket(new PacketSetFluidSlot(container.windowId, i, fluidStack));
        }
    }
}
