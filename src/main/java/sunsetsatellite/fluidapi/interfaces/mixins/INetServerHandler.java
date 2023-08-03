package sunsetsatellite.fluidapi.interfaces.mixins;


import sunsetsatellite.fluidapi.mp.packets.PacketFluidWindowClick;

public interface INetServerHandler {
    void handleFluidWindowClick(PacketFluidWindowClick p);
}
