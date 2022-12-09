package net.sunsetsatellite.fluidapi;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileEntityFluidPump extends TileEntityFluidItemContainer
    implements IPipePressurizer
{
    public TileEntityFluidPump(){
        fluidCapacity[0] = 8000;
        transferSpeed = 50;
        isInput.replace("Y+",false);
        isInput.replace("Y-",true);
    }

    public int waitTicks = 0;
    public boolean lastSuccess = true;
    public int pumpY = -1;

    @Override
    public void updateEntity() {
        if(pumpY <= 0){
            pumpY = yCoord;
        }
        for (Map.Entry<String, Vec3D> entry : dir.entrySet()) {
            String K = entry.getKey();
            Vec3D V = entry.getValue();
            TileEntity tile = worldObj.getBlockTileEntity(xCoord + (int) V.xCoord, yCoord + (int) V.yCoord, zCoord + (int) V.zCoord);
            if (tile instanceof TileEntityFluidPipe) {
                pressurizePipes((TileEntityFluidPipe) tile, new ArrayList<>());
                moveFluids(K, (TileEntityFluidPipe) tile, transferSpeed);
            }
            if(waitTicks <= 0){
                if(isInput.get(K) != null && isInput.get(K)){
                    waitTicks = 120;
                    for(int i = pumpY;i>0;i--){
                        if(validFluidAtPos(xCoord + (int) V.xCoord, i + (int) V.yCoord, zCoord + (int) V.zCoord)){
                            lastSuccess = pumpFluid(xCoord + (int) V.xCoord, i + (int) V.yCoord, zCoord + (int) V.zCoord,0,8);
                            if(lastSuccess){
                                pumpY = i;
                            }
                            break;
                        }
                    }
                    //
                }
            } else {
                waitTicks--;
            }

        }
        super.updateEntity();
    }

    public boolean pumpFluid(int x, int y, int z, int range, int maxRange){
        if(range >= maxRange){
            return false;
        }
        int blockId = worldObj.getBlockId(x,y,z);
        boolean success = false;
        //System.out.printf("Trying to pump %d (%f) at %d, %d, %d\n",blockId,BlockFluid.getPercentAir(worldObj.getBlockMetadata(x, y, z)),x,y,z);
        for (Map.Entry<BlockFluid, ItemBucket> entry : mod_FluidAPI.fluidsInv.entrySet()) {
            BlockFluid K = entry.getKey();
            if(blockId == K.blockID || blockId == K.blockID+1){
                if(BlockFluid.getPercentAir(worldObj.getBlockMetadata(x, y, z)) <= 0.12){
                    if(getFluidAmountForSlot(0) + 1000 <= getFluidCapacityForSlot(0)){
                        setOrModifyFluidInSlot(0, new FluidStack(K, 1000),true);
                        worldObj.setBlockWithNotify(x,y,z,0);
                        success = true;
                    }
                }

            }
        }
        return success;
    }

    public boolean validFluidAtPos(int x, int y, int z) {

        int blockId = worldObj.getBlockId(x,y,z);
        //System.out.printf("Checking fluid %d (%f) at %d, %d, %d\n",blockId,BlockFluid.getPercentAir(worldObj.getBlockMetadata(x, y, z)),x,y,z);
        for (Map.Entry<BlockFluid, ItemBucket> entry : mod_FluidAPI.fluidsInv.entrySet()) {
            BlockFluid K = entry.getKey();
            if (blockId == K.blockID || blockId == K.blockID + 1) {
                if (BlockFluid.getPercentAir(worldObj.getBlockMetadata(x, y, z)) <= 0.12) {
                    return true;
                }
            }
        }
        return false;
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
