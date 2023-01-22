package sunsetsatellite.fluidapi;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RenderFluidInBlock extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity1, double d2, double d4, double d6, float f8) {

        GL11.glPushMatrix();

        float fluidAmount = 0;
        float fluidMaxAmount = 1;
        int fluidId = FluidAPI.fluidTank.blockID;

        if(((TileEntityFluidContainer) tileEntity1).fluidContents[0] != null){
            if(((TileEntityFluidContainer) tileEntity1).fluidContents[0].getLiquid() != null){
                fluidMaxAmount = ((TileEntityFluidContainer) tileEntity1).getFluidCapacityForSlot(0);
                fluidAmount = ((TileEntityFluidContainer) tileEntity1).fluidContents[0].amount;
                fluidId = ((TileEntityFluidContainer) tileEntity1).fluidContents[0].getLiquid().blockID;
            }

        }


        float amount = Math.abs((fluidAmount / fluidMaxAmount) - 0.01f);

        GL11.glTranslatef((float)d2, (float)d4, (float)d6);
        GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F);
        GL11.glScalef(0.99f,amount,0.99f);
        GL11.glTranslatef(0.01F, 0.0f, 0.01f);

        GL11.glDisable(GL11.GL_LIGHTING);
        drawBlock(this.getFontRenderer(), this.tileEntityRenderer.renderEngine.minecraft.renderEngine, fluidId, 0,0, 0, 0);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }


    public void drawBlock(FontRenderer fontrenderer, RenderEngine renderengine, int i, int j, int k, int l, int i1) {
        renderengine.bindTexture(renderengine.getTexture("/terrain.png"));
        //ForgeHooksClient.overrideTexture(Block.blocksList[i]);
        Block f1 = Block.blocksList[i];
        GL11.glPushMatrix();
        this.blockRenderer.renderBlock(f1, j, 0.0F);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    private final RenderFluid blockRenderer = new RenderFluid();
}
