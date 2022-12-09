package net.sunsetsatellite.fluidapi;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiFluid extends GuiContainer {
    public GuiFluid(InventoryPlayer inventoryPlayer, TileEntityFluidItemContainer tile) {
        super(new ContainerFluid(inventoryPlayer, tile));
    }

    public GuiFluid(ContainerFluid containerFluid) {
        super(containerFluid);
    }

    @Override
    public void drawScreen(int i1, int i2, float f3) {
        super.drawScreen(i1, i2, f3);
        int i4 = (this.width - this.xSize) / 2;
        int i5 = (this.height - this.ySize) / 2;
        //this.drawGuiContainerBackgroundLayer(f3);
        GL11.glPushMatrix();
        GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)i4, (float)i5, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        int i9;
        int i10;
        SlotFluid slot6 = null;
        ContainerFluid fluidContainer = ((ContainerFluid) inventorySlots);
        for(int i7 = 0; i7 < fluidContainer.fluidSlots.size(); i7++) {
            SlotFluid slot8 = fluidContainer.fluidSlots.get(i7);
            this.drawFluidSlotInventory(slot8);
            if(this.getIsMouseOverFluidSlot(slot8, i1, i2)) {
                slot6 = slot8;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                i9 = slot8.xPos;
                i10 = slot8.yPos;
                this.drawGradientRect(i9, i10, i9 + 16, i10 + 16, 0x40FFFFFF, 0x40FFFFFF);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }
        if(slot6 != null && slot6.getHasStack() && slot6.getFluidStack().getLiquid() != null) {
            String string13 = ("" + StringTranslate.getInstance().translateNamedKey(slot6.getFluidStack().getLiquid().getBlockName())).trim();
            if(string13.length() > 0) {
                i9 = i1 - i4 + 12;
                i10 = i2 - i5 - 12;
                int i11 = this.fontRenderer.getStringWidth(string13);
                int w = mc.fontRenderer.getStringWidth(slot6.getFluidStack().amount+"/"+fluidContainer.tile.getFluidCapacityForSlot(slot6.slotIndex)+" mB");
                if (i11 < w) {
                    i11 = w;
                }
                drawGradientRect(i9 - 3, i10 - 3, i9 + i11 + 3, i10 + 8 + 15, 0xc0000000, 0xc0000000);
                fontRenderer.drawStringWithShadow(string13, i9, i10, -1);
                fontRenderer.drawStringWithShadow(slot6.getFluidStack().amount+"/"+fluidContainer.tile.getFluidCapacityForSlot(slot6.slotIndex)+" mB", i9, i10 + 12, 0xFF808080);
            }
        }
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f1) {

    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        super.drawGuiContainerForegroundLayer();
    }

    private boolean getIsMouseOverFluidSlot(SlotFluid slot1, int i2, int i3) {
        int i4 = (this.width - this.xSize) / 2;
        int i5 = (this.height - this.ySize) / 2;
        i2 -= i4;
        i3 -= i5;
        return i2 >= slot1.xPos - 1 && i2 < slot1.xPos + 16 + 1 && i3 >= slot1.yPos - 1 && i3 < slot1.yPos + 16 + 1;
    }

    private void drawFluidSlotInventory(SlotFluid slot1) {
        int i2 = slot1.xPos;
        int i3 = slot1.yPos;
        if(slot1.getHasStack() && slot1.getFluidStack().liquid != null){
            ItemStack itemStack4 = new ItemStack(slot1.getFluidStack().getLiquid(),slot1.getFluidStack().amount,0);
            int i5 = slot1.getBackgroundIconIndex();
            if(i5 >= 0) {
                GL11.glDisable(GL11.GL_LIGHTING);
                this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
                this.drawTexturedModalRect(i2, i3, i5 % 16 * 16, i5 / 16 * 16, 16, 16);
                GL11.glEnable(GL11.GL_LIGHTING);
                return;
            }
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack4, i2, i3);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack4, i2, i3);
        }
    }

    private SlotFluid getFluidSlotAtPosition(int i1, int i2) {
        ContainerFluid fluidContainer = ((ContainerFluid) inventorySlots);
        for(int i3 = 0; i3 < fluidContainer.fluidSlots.size(); ++i3) {
            SlotFluid slot4 = fluidContainer.fluidSlots.get(i3);
            if(this.getIsMouseOverFluidSlot(slot4, i1, i2)) {
                return slot4;
            }
        }

        return null;
    }

    protected void mouseClicked(int i1, int i2, int i3) {
        super.mouseClicked(i1, i2, i3);
        ContainerFluid fluidContainer = ((ContainerFluid) inventorySlots);
        if (i3 == 0 || i3 == 1) {
            SlotFluid slot = this.getFluidSlotAtPosition(i1, i2);
            if(slot != null){
                InventoryPlayer inventoryPlayer = ModLoader.getMinecraftInstance().thePlayer.inventory;
                if(inventoryPlayer.getItemStack() != null && inventoryPlayer.getItemStack().getItem() instanceof ItemBucket){
                    ItemBucket bucket = (ItemBucket) inventoryPlayer.getItemStack().getItem();
                    if(mod_FluidAPI.fluids.get(bucket) != null || bucket == Item.bucketEmpty) {
                        if (bucket == Item.bucketEmpty) {
                            if (slot.getFluidStack() != null && slot.getFluidStack().amount >= 1000) {
                                inventoryPlayer.setItemStack(new ItemStack(mod_FluidAPI.fluidsInv.get(slot.getFluidStack().liquid), 1));
                                fluidContainer.tile.decrFluidAmount(slot.slotIndex, 1000);
                                slot.onPickupFromSlot(slot.getFluidStack());
                            }
                        } else {
                            if (slot.getFluidStack() == null) {
                                inventoryPlayer.setItemStack(new ItemStack(Item.bucketEmpty, 1));
                                slot.putStack(new FluidStack(mod_FluidAPI.fluids.get(bucket), 1000));
                            } else if (slot.getFluidStack() != null && slot.getFluidStack().getLiquid() == mod_FluidAPI.fluids.get(bucket)) {
                                if (slot.getFluidStack().amount + 1000 <= fluidContainer.tile.getFluidCapacityForSlot(slot.slotIndex)) {
                                    inventoryPlayer.setItemStack(new ItemStack(Item.bucketEmpty, 1));
                                    slot.getFluidStack().amount += 1000;
                                    slot.onSlotChanged();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void initGui()
    {
        super.initGui();
    }
}
