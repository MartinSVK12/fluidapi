package sunsetsatellite.fluidapi.mixin.mixins;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.fluidapi.FluidAPI;
import sunsetsatellite.fluidapi.api.ContainerFluid;
import sunsetsatellite.fluidapi.interfaces.mixins.INetClientHandler;
import sunsetsatellite.fluidapi.mp.packets.PacketSetFluidSlot;
import sunsetsatellite.fluidapi.mp.packets.PacketUpdateClientFluidRender;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidContainer;

import java.lang.reflect.InvocationTargetException;

@Mixin(
        value= NetClientHandler.class,
        remap = false
)
public class NetClientHandlerMixin extends NetHandler implements INetClientHandler {

    @Shadow
    private Minecraft mc;

    @Inject(
            method = "handleOpenWindow",
            at = @At("TAIL")
    )
    public void handleOpenWindow(Packet100OpenWindow packet100openwindow, CallbackInfo ci) {
        if(packet100openwindow.inventoryType == FluidAPI.config.getFromConfig("GuiID",8)){
            TileEntity tile;
            try {
                tile = (TileEntity) FluidAPI.nameToGuiMap.get(packet100openwindow.windowTitle).get(1).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            try {
                this.mc.displayGuiScreen((GuiScreen) FluidAPI.nameToGuiMap.get(packet100openwindow.windowTitle).get(0).getDeclaredConstructors()[0].newInstance(this.mc.thePlayer.inventory,tile));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            this.mc.thePlayer.craftingInventory.windowId = packet100openwindow.windowId;
        }
    }

    @Override
    public boolean isServerHandler() {
        return false;
    }

    @Override
    public void handleSetFluidSlot(PacketSetFluidSlot packetSetFluidSlot) {
        if (packetSetFluidSlot.windowId == this.mc.thePlayer.craftingInventory.windowId && this.mc.thePlayer.craftingInventory instanceof ContainerFluid) {
            ((ContainerFluid) this.mc.thePlayer.craftingInventory).putFluidInSlot(packetSetFluidSlot.fluidSlot, packetSetFluidSlot.fluidStack);
        }
    }

    @Override
    public void handleUpdateClientFluidRender(PacketUpdateClientFluidRender packetUpdateClientFluidRender) {
        TileEntity tile = this.mc.theWorld.getBlockTileEntity(packetUpdateClientFluidRender.x, packetUpdateClientFluidRender.y, packetUpdateClientFluidRender.z);
        if(tile instanceof TileEntityFluidContainer){
            ((TileEntityFluidContainer) tile).shownFluid = packetUpdateClientFluidRender.fluidStack;
            ((TileEntityFluidContainer) tile).shownMaxAmount = packetUpdateClientFluidRender.fluidMaxAmount;
        }
    }
}
