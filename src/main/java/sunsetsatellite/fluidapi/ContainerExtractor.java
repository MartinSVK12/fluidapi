package sunsetsatellite.fluidapi;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.Slot;

public class ContainerExtractor extends ContainerFluid {

    public ContainerExtractor(IInventory iInventory, TileEntityFluidItemContainer tileEntity){
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

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
        return true;
    }
}
