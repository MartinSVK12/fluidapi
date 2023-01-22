package sunsetsatellite.fluidapi;

import net.minecraft.src.Container;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.IInventory;
import net.minecraft.src.TileEntity;

public interface IOpenGUI {
    void displayGUI(GuiScreen gui);
    void displayGUI(IInventory tile, Container container);
}
