package sunsetsatellite.fluidapi.mp.packets;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet;
import sunsetsatellite.fluidapi.FluidStack;
import sunsetsatellite.fluidapi.interfaces.mixins.INetClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketUpdateClientFluidRender extends Packet {
    public int x;
    public int y;
    public int z;
    public FluidStack fluidStack;
    public int fluidMaxAmount;

    public PacketUpdateClientFluidRender() {
    }

    public void processPacket(NetHandler nethandler) {
        ((INetClientHandler)nethandler).handleUpdateClientFluidRender(this);
    }

    public PacketUpdateClientFluidRender(int x, int y, int z, FluidStack fluidStack, int fluidMaxAmount) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.fluidMaxAmount = fluidMaxAmount;
        this.fluidStack = fluidStack != null ? fluidStack.copy() : null;
    }

    public void readPacketData(DataInputStream datainputstream) throws IOException {
        this.x = datainputstream.readInt();
        this.y = datainputstream.readInt();
        this.z = datainputstream.readInt();
        this.fluidMaxAmount = datainputstream.readInt();
        short liquid = datainputstream.readShort();
        if (liquid >= 0) {
            int amount = datainputstream.readInt();
            this.fluidStack = new FluidStack((BlockFluid) Block.blocksList[liquid],amount);
        } else {
            this.fluidStack = null;
        }

    }

    public void writePacketData(DataOutputStream dataoutputstream) throws IOException {
        dataoutputstream.writeInt(this.x);
        dataoutputstream.writeInt(this.y);
        dataoutputstream.writeInt(this.z);
        dataoutputstream.writeInt(this.fluidMaxAmount);
        if (this.fluidStack == null) {
            dataoutputstream.writeShort(-1);
        } else {
            dataoutputstream.writeShort(this.fluidStack.liquid.blockID);
            dataoutputstream.writeInt(this.fluidStack.amount);
        }

    }

    public int getPacketSize() {
        return 20;
    }
}
