package sunsetsatellite.fluidapi;

import java.util.ArrayList;
import java.util.HashMap;

public interface IPipePressurizer {
    void pressurizePipes(TileEntityFluidPipe pipe, ArrayList<HashMap<String,Integer>> already);
    void unpressurizePipes(TileEntityFluidPipe pipe,ArrayList<HashMap<String,Integer>> already);
}
