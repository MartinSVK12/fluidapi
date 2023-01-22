package sunsetsatellite.fluidapi;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RenderFluidInPipe extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity1, double d2, double d4, double d6, float f8) {

        int i = tileEntity1.xCoord;
        int j = tileEntity1.yCoord;
        int k = tileEntity1.zCoord;
        World blockAccess = this.tileEntityRenderer.renderEngine.minecraft.theWorld;
        Block block = FluidAPI.fluidPipe;

        GL11.glPushMatrix();

        float fluidAmount = 0;
        float fluidMaxAmount = 1;
        int fluidId = -1;

        if(((TileEntityFluidPipe) tileEntity1).fluidContents[0] != null && ((TileEntityFluidPipe) tileEntity1).getFluidInSlot(0).getLiquid() != null){
            fluidMaxAmount = ((TileEntityFluidPipe) tileEntity1).getFluidCapacityForSlot(0);
            fluidAmount = ((TileEntityFluidPipe) tileEntity1).fluidContents[0].amount;
            fluidId = ((TileEntityFluidPipe) tileEntity1).fluidContents[0].getLiquid().blockID;
        }

        boolean flag = blockAccess.getBlockId(i + 1, j, k) == block.blockID || (blockAccess.getBlockTileEntity(i + 1, j, k) instanceof IFluidInventory);
        boolean flag1 = blockAccess.getBlockId(i - 1, j, k) == block.blockID || (blockAccess.getBlockTileEntity(i - 1, j, k) instanceof IFluidInventory);
        boolean flag2 = blockAccess.getBlockId(i, j + 1, k) == block.blockID || (blockAccess.getBlockTileEntity(i, j + 1, k) instanceof IFluidInventory);
        boolean flag3 = blockAccess.getBlockId(i, j - 1, k) == block.blockID || (blockAccess.getBlockTileEntity(i, j - 1, k) instanceof IFluidInventory);
        boolean flag4 = blockAccess.getBlockId(i, j, k + 1) == block.blockID || (blockAccess.getBlockTileEntity(i, j, k + 1) instanceof IFluidInventory);
        boolean flag5 = blockAccess.getBlockId(i, j, k - 1) == block.blockID || (blockAccess.getBlockTileEntity(i, j, k - 1) instanceof IFluidInventory);


        float amount = (fluidAmount / fluidMaxAmount);
        float mapped = (float) FluidAPI.map(amount,0.0d,1.0d,0.0d,0.5d);
        
        GL11.glTranslatef((float)d2, (float)d4, (float)d6);
        GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.25F, 0.25f , 0.25f);
        if(!(flag2 && flag3)){
            GL11.glScalef(0.5f, mapped,0.5f);
        } else {
            GL11.glScalef(mapped,0.5f,mapped);
        }

        GL11.glDisable(GL11.GL_LIGHTING);

        if(fluidId != -1)
        drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine, fluidId, 0,0, 0, 0,tileEntity1);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();

        if(flag){
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d2, (float)d4, (float)d6);
            GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.5F, 0.25f , 0.25f);
            GL11.glScalef(0.5f, mapped,0.5f);
            GL11.glDisable(GL11.GL_LIGHTING);
            if(fluidId != -1)
                drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine, fluidId, 0,0, 0, 0,tileEntity1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        if(flag1){
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d2, (float)d4, (float)d6);
            GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.0f, 0.25f , 0.25f);
            GL11.glScalef(0.5f, mapped,0.5f);
            GL11.glDisable(GL11.GL_LIGHTING);
            if(fluidId != -1)
                drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine, fluidId, 0,0, 0, 0,tileEntity1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        if(flag2 && ((TileEntityFluidPipe) tileEntity1).isPressurized || (blockAccess.getBlockTileEntity(i,j+1,k) instanceof TileEntityFluidPipe && ((TileEntityFluidPipe) blockAccess.getBlockTileEntity(i,j+1,k)).getFluidInSlot(0) != null)){
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d2, (float)d4, (float)d6);
            GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.25F, 0.25f , 0.25f);
            GL11.glScalef(mapped, 0.5f,mapped);
            GL11.glDisable(GL11.GL_LIGHTING);
            if(fluidId != -1)
                drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine, fluidId, 0,0, 0, 0,tileEntity1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        if(flag3){
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d2, (float)d4, (float)d6);
            GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.25F, -0.25f , 0.25f);
            GL11.glScalef(mapped, 0.5f,mapped);
            GL11.glDisable(GL11.GL_LIGHTING);
            if(fluidId != -1)
                drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine, fluidId, 0,0, 0, 0, tileEntity1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        if(flag4){
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d2, (float)d4, (float)d6);
            GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.25F, 0.25f , 0.5f);
            GL11.glScalef(0.5f, mapped,0.5f);
            GL11.glDisable(GL11.GL_LIGHTING);
            if(fluidId != -1)
                drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine, fluidId, 0,0, 0, 0, tileEntity1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        if(flag5){
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d2, (float)d4, (float)d6);
            GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.25F, 0.25f , 0.0f);
            GL11.glScalef(0.5f, mapped,0.5f);
            GL11.glDisable(GL11.GL_LIGHTING);
            if(fluidId != -1)
                drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine, fluidId, 0,0, 0, 0, tileEntity1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
    }


    public void drawBlock(FontRenderer fontrenderer, RenderEngine renderengine, int i, int j, int k, int l, int i1, TileEntity tile) {
        renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
        //ForgeHooksClient.overrideTexture(Block.blocksList[i]);
        Block f1 = Block.blocksList[i];
        GL11.glPushMatrix();
        this.blockRenderer.renderBlock(f1, j, renderengine.minecraft.theWorld, tile.xCoord, tile.yCoord, tile.zCoord);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    private final RenderFluid blockRenderer = new RenderFluid();
}
