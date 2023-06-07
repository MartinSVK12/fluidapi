package sunsetsatellite.fluidapi;

import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import org.jetbrains.annotations.NotNull;

public class FluidRegistryEntry {

    @NotNull public String modId;
    @NotNull public Item container;
    @NotNull public Item containerEmpty;
    @NotNull public BlockFluid fluid;

    public FluidRegistryEntry(@NotNull String modId, @NotNull Item container, @NotNull Item containerEmpty, @NotNull BlockFluid fluid) {
        this.modId = modId;
        this.container = container;
        this.containerEmpty = containerEmpty;
        this.fluid = fluid;
    }
}
