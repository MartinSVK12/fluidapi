package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.fluidapi.IOpenGUI;
import net.minecraft.client.Minecraft;

@Mixin(
        value={EntityPlayerSP.class},
        remap = false
)

public class EntityPlayerSPMixin implements IOpenGUI {
    @Shadow
    protected Minecraft mc;

    @Override
    public void displayGUI(GuiScreen gui) {
        this.mc.displayGuiScreen(gui);
    }

    @Override
    public void displayGUI(IInventory tile, Container container) {
    }
}
