package sunsetsatellite.fluidapi.template.tiles;

import net.minecraft.src.TileEntity;
import sunsetsatellite.fluidapi.FluidRegistry;
import sunsetsatellite.fluidapi.api.IPipePressurizer;
import sunsetsatellite.sunsetutils.util.Connection;
import sunsetsatellite.sunsetutils.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileEntityMultiFluidTank extends TileEntityMassFluidItemContainer
    implements IPipePressurizer {
    public TileEntityMultiFluidTank(){
        fluidCapacity = 16000;
        transferSpeed = 50;
        connections.replace(Direction.Y_POS, Connection.INPUT);
        connections.replace(Direction.Y_NEG, Connection.OUTPUT);
        acceptedFluids.addAll(FluidRegistry.getAllFluids());
    }

    @Override
    public void updateEntity() {
        extractFluids();
        super.updateEntity();
    }

    @Override
    public String getInvName() {
        return "Multi Fluid Tank";
    }

    public void extractFluids(){
        for (Map.Entry<Direction, Connection> e : connections.entrySet()) {
            Direction dir = e.getKey();
            Connection connection = e.getValue();
            TileEntity tile = dir.getTileEntity(worldObj,this);
            if (tile instanceof TileEntityFluidPipe) {
                pressurizePipes((TileEntityFluidPipe) tile, new ArrayList<>());
                moveFluids(dir, (TileEntityFluidPipe) tile);
                ((TileEntityFluidPipe) tile).rememberTicks = 100;
            }
        }
    }

    public void pressurizePipes(TileEntityFluidPipe pipe, ArrayList<HashMap<String,Integer>> already){
        pipe.isPressurized = true;
        for (Direction dir : Direction.values()) {
            TileEntity tile = dir.getTileEntity(worldObj,this);
            if (tile instanceof TileEntityFluidPipe) {
                for (HashMap<String, Integer> V2 : already) {
                    if (V2.get("x") == tile.xCoord && V2.get("y") == tile.yCoord && V2.get("z") == tile.zCoord) {
                        return;
                    }
                }
                HashMap<String,Integer> list = new HashMap<>();
                list.put("x",tile.xCoord);
                list.put("y",tile.yCoord);
                list.put("z",tile.zCoord);
                already.add(list);
                ((TileEntityFluidPipe) tile).isPressurized = true;
                pressurizePipes((TileEntityFluidPipe) tile,already);
            }
        }
    }

    public void unpressurizePipes(TileEntityFluidPipe pipe,ArrayList<HashMap<String,Integer>> already){
        pipe.isPressurized = false;
        for (Direction dir : Direction.values()) {
            TileEntity tile = dir.getTileEntity(worldObj,this);
            if (tile instanceof TileEntityFluidPipe) {
                for (HashMap<String, Integer> V2 : already) {
                    if (V2.get("x") == tile.xCoord && V2.get("y") == tile.yCoord && V2.get("z") == tile.zCoord) {
                        return;
                    }
                }
                HashMap<String,Integer> list = new HashMap<>();
                list.put("x",tile.xCoord);
                list.put("y",tile.yCoord);
                list.put("z",tile.zCoord);
                already.add(list);
                ((TileEntityFluidPipe) tile).isPressurized = false;
                unpressurizePipes((TileEntityFluidPipe) tile,already);
            }
        }
    }
}
