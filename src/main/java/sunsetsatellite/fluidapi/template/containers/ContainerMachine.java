package sunsetsatellite.fluidapi.template.containers;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;
import sunsetsatellite.fluidapi.template.tiles.TileEntityFluidItemContainer;
import sunsetsatellite.fluidapi.template.tiles.TileEntityMachine;
import sunsetsatellite.fluidapi.api.ContainerFluid;
import sunsetsatellite.fluidapi.api.SlotFluid;

import java.util.Iterator;

public class ContainerMachine extends ContainerFluid {

    public int fuelBurnTicks = 0;
    public int fuelMaxBurnTicks = 0;
    public int progressTicks = 0;
    public int progressMaxTicks = 200;

    private final TileEntityMachine machine = ((TileEntityMachine) tile);

    public ContainerMachine(IInventory iInventory, TileEntityFluidItemContainer tileEntity){
        super(iInventory, tileEntity);
        tile = tileEntity;

        SlotFluid slot = new SlotFluid(tileEntity, 0, 116,35);
        addFluidSlot(slot);

        this.addSlot(new Slot(tileEntity, 0, 56, 17));
        this.addSlot(new Slot(tileEntity, 1, 56, 53));

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
                addSlot(new Slot(iInventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
            addSlot(new Slot(iInventory, k, 8 + k * 18, 142));
        }
    }

    public void updateInventory() {
        super.updateInventory();
        Iterator var1 = this.crafters.iterator();

        while(var1.hasNext()) {
            Object crafter = var1.next();
            ICrafting icrafting = (ICrafting)crafter;
            if (this.progressTicks != machine.progressTicks) {
                icrafting.updateCraftingInventoryInfo(this, 0, machine.progressTicks);
            }

            if (this.fuelBurnTicks != machine.fuelBurnTicks) {
                icrafting.updateCraftingInventoryInfo(this, 1, machine.fuelBurnTicks);
            }

            if (this.progressMaxTicks != machine.progressMaxTicks) {
                icrafting.updateCraftingInventoryInfo(this, 2, machine.progressMaxTicks);
            }

            if (this.fuelMaxBurnTicks != machine.fuelMaxBurnTicks) {
                icrafting.updateCraftingInventoryInfo(this, 3, machine.fuelMaxBurnTicks);
            }
        }

        this.progressTicks = machine.progressTicks;
        this.fuelBurnTicks = machine.fuelBurnTicks;
        this.progressMaxTicks = machine.progressMaxTicks;
        this.fuelMaxBurnTicks = machine.fuelMaxBurnTicks;
    }

    public void updateClientProgressBar(int id, int value) {
        if (id == 0) {
            machine.progressTicks = value;
        }

        if (id == 1) {
            machine.fuelBurnTicks = value;
        }

        if (id == 2) {
            machine.progressMaxTicks = value;
        }

        if (id == 3) {
            machine.fuelMaxBurnTicks = value;
        }

    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }
}
