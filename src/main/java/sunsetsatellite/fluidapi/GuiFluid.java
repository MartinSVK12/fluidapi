package sunsetsatellite.fluidapi;

import net.minecraft.src.*;
import net.minecraft.src.helper.Color;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiFluid extends GuiContainer {
    public GuiFluid(InventoryPlayer invP, TileEntityFluidItemContainer tile) {
        super(new ContainerFluid(invP, tile));
        inventoryPlayer = invP;
    }

    public GuiFluid(ContainerFluid containerFluid, InventoryPlayer invP) {
        super(containerFluid);
        inventoryPlayer = invP;
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
            String string13 = ("" + StringTranslate.getInstance().translateNamedKey(slot6.getFluidStack().getLiquid().getBlockName(0))).trim();
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

            itemRender.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack4, i2, i3,1.0F);
            ContainerFluid container = ((ContainerFluid) inventorySlots);
            if(slot1.getFluidStack().getLiquid() == Block.fluidWaterFlowing){
                Color c = new Color().setARGB(Block.fluidWaterFlowing.colorMultiplier(this.mc.theWorld, this.mc.theWorld,container.tile.xCoord,container.tile.yCoord,container.tile.zCoord));
                c.setRGBA(c.getRed(),c.getGreen(),c.getBlue(),0x40);
                this.drawRect(slot1.xPos, slot1.yPos, slot1.xPos+16, slot1.yPos+16,c.value);
            }
            itemRender.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack4, i2, i3,1.0F);
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

    public void mouseClicked(int i1, int i2, int i3) {
        super.mouseClicked(i1, i2, i3);
        ContainerFluid fluidContainer = ((ContainerFluid) inventorySlots);
        if (i3 == 0 || i3 == 1) {
            SlotFluid slot = this.getFluidSlotAtPosition(i1, i2);
            if(slot != null){
                //InventoryPlayer inventoryPlayer = ModLoader.getMinecraftInstance().thePlayer.inventory;
                if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof ItemBucketEmpty) {
                    if (inventoryPlayer.getHeldItemStack().getItem() == Item.bucket) {
                        if (slot.getFluidStack() != null && slot.getFluidStack().amount >= 1000) {
                            inventoryPlayer.setHeldItemStack(new ItemStack(FluidAPI.fluidsInv.get(slot.getFluidStack().liquid), 1));
                            fluidContainer.tile.decrFluidAmount(slot.slotIndex, 1000);
                            slot.onPickupFromSlot(slot.getFluidStack());
                            return;
                        }
                    }
                }
                if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof ItemBucket) {
                    ItemBucket bucket = (ItemBucket) inventoryPlayer.getHeldItemStack().getItem();
                    if (slot.getFluidStack() == null) {
                        inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                        slot.putStack(new FluidStack(FluidAPI.fluids.get(bucket), 1000));
                    } else if (slot.getFluidStack() != null && slot.getFluidStack().getLiquid() == FluidAPI.fluids.get(bucket)) {
                        if (slot.getFluidStack().amount + 1000 <= fluidContainer.tile.getFluidCapacityForSlot(slot.slotIndex)) {
                            inventoryPlayer.setHeldItemStack(new ItemStack(bucket.getContainerItem(), 1));
                            slot.getFluidStack().amount += 1000;
                            slot.onSlotChanged();
                        }
                    }
                }
                if(inventoryPlayer.getHeldItemStack() != null && inventoryPlayer.getHeldItemStack().getItem() instanceof IFluidInventory) {
                    ItemBucket bucket = (ItemBucket) inventoryPlayer.getHeldItemStack().getItem();
                    if(FluidAPI.fluids.get(bucket) != null || FluidAPI.fluidContainers.containsValue(bucket)){
                        IItemFluidContainer container = (IItemFluidContainer) bucket;
                        if(container.canDrain(inventoryPlayer.getHeldItemStack())){
                            if (fluidContainer.tile.getFluidInSlot(slot.slotIndex) == null){
                                container.drain(inventoryPlayer.getHeldItemStack(), slot, fluidContainer.tile);
                            }
                            else if (fluidContainer.tile.getFluidInSlot(slot.slotIndex).amount < fluidContainer.tile.getFluidCapacityForSlot(slot.slotIndex)) {
                                container.drain(inventoryPlayer.getHeldItemStack(), slot, fluidContainer.tile);
                            }
                            else if(fluidContainer.tile.getFluidInSlot(slot.slotIndex).amount >= fluidContainer.tile.getFluidCapacityForSlot(slot.slotIndex)){
                                if(container.canFill(inventoryPlayer.getHeldItemStack())){
                                    ItemStack stack = container.fill(slot,inventoryPlayer.getHeldItemStack());
                                    if(stack != null){
                                        inventoryPlayer.setHeldItemStack(stack);
                                    }
                                }
                            }
                        } else if(container.canFill(inventoryPlayer.getHeldItemStack())){
                            ItemStack stack = container.fill(slot,inventoryPlayer.getHeldItemStack());
                            if(stack != null){
                                inventoryPlayer.setHeldItemStack(stack);
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
    private InventoryPlayer inventoryPlayer;
    public RenderItem itemRender;
    {
        try {
            itemRender = (RenderItem) FluidAPI.getPrivateValue(GuiContainer.class, this,"itemRenderer");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
