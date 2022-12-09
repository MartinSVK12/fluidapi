package net.sunsetsatellite.fluidapi;

import net.minecraft.src.TileEntity;
import net.minecraft.src.Vec3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileEntityFluidTank extends TileEntityFluidItemContainer
    implements IPipePressurizer{
    public TileEntityFluidTank(){
        fluidCapacity[0] = 8000;
        transferSpeed = 50;
        isInput.replace("Y+",true);
        isInput.replace("Y-",false);
    }

    @Override
    public void updateEntity() {
        for (Map.Entry<String, Vec3D> entry : dir.entrySet()) {
            String K = entry.getKey();
            Vec3D V = entry.getValue();
            TileEntity tile = worldObj.getBlockTileEntity(xCoord + (int) V.xCoord, yCoord + (int) V.yCoord, zCoord + (int) V.zCoord);
            if (tile instanceof TileEntityFluidPipe) {
                pressurizePipes((TileEntityFluidPipe) tile, new ArrayList<>());
                moveFluids(K, (TileEntityFluidPipe) tile, transferSpeed);
            }
        }
        super.updateEntity();
    }

    public void pressurizePipes(TileEntityFluidPipe pipe, ArrayList<HashMap<String,Integer>> already){
        pipe.isPressurized = true;
        dir.forEach((K,V)-> {
            TileEntity tile = worldObj.getBlockTileEntity(pipe.xCoord + (int) V.xCoord, pipe.yCoord + (int) V.yCoord, pipe.zCoord + (int) V.zCoord);
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
        });
    }

    public void unpressurizePipes(TileEntityFluidPipe pipe,ArrayList<HashMap<String,Integer>> already){
        pipe.isPressurized = false;
        dir.forEach((K,V)-> {
            TileEntity tile = worldObj.getBlockTileEntity(pipe.xCoord + (int) V.xCoord, pipe.yCoord + (int) V.yCoord, pipe.zCoord + (int) V.zCoord);
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
        });
    }
}
