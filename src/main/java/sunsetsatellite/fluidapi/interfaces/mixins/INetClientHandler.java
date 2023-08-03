package sunsetsatellite.fluidapi.interfaces.mixins;


import sunsetsatellite.fluidapi.mp.packets.PacketSetFluidSlot;
import sunsetsatellite.fluidapi.mp.packets.PacketUpdateClientFluidRender;

public interface INetClientHandler {

    void handleSetFluidSlot(PacketSetFluidSlot packetSetFluidSlot);


    void handleUpdateClientFluidRender(PacketUpdateClientFluidRender packetUpdateClientFluidRender);
}
