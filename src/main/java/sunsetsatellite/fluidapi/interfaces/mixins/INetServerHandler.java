package sunsetsatellite.fluidapi.interfaces.mixins;

import net.minecraft.src.Packet102WindowClick;
import sunsetsatellite.fluidapi.mp.packets.PacketFluidWindowClick;

public interface INetServerHandler {
    void handleFluidWindowClick(PacketFluidWindowClick p);
}
