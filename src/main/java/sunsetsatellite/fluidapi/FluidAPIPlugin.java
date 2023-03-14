package sunsetsatellite.fluidapi;

import org.slf4j.Logger;

public interface FluidAPIPlugin {
    void initializePlugin(FluidRegistry registry, Logger logger);
}
