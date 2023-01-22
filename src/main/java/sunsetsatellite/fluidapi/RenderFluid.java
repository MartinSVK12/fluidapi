//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TextureFX;

public class RenderFluid {
    public IBlockAccess blockAccess;
    public int overrideBlockTexture;
    public boolean flipTexture;
    public boolean renderAllFaces;
    public boolean field_31088_b;
    private int uvRotateEast = 0;
    private int uvRotateWest = 0;
    private int uvRotateSouth = 0;
    private int uvRotateNorth = 0;
    private int uvRotateTop = 0;
    private int uvRotateBottom = 0;
    public boolean enableAO;
    public float lightValueOwn;
    public float aoLightValueXNeg;
    public float aoLightValueYNeg;
    public float aoLightValueZNeg;
    public float aoLightValueXPos;
    public float aoLightValueYPos;
    public float aoLightValueZPos;
    public float field_22377_m;
    public float field_22376_n;
    public float field_22375_o;
    public float field_22374_p;
    public float field_22373_q;
    public float field_22372_r;
    public float field_22371_s;
    public float field_22370_t;
    public float field_22369_u;
    public float field_22368_v;
    public float field_22367_w;
    public float field_22366_x;
    public float field_22365_y;
    public float field_22364_z;
    public float field_22362_A;
    public float field_22360_B;
    public float field_22358_C;
    public float field_22356_D;
    public float field_22354_E;
    public float field_22353_F;
    public int field_22352_G;
    public float colorRedTopLeft;
    public float colorRedBottomLeft;
    public float colorRedBottomRight;
    public float colorRedTopRight;
    public float colorGreenTopLeft;
    public float colorGreenBottomLeft;
    public float colorGreenBottomRight;
    public float colorGreenTopRight;
    public float colorBlueTopLeft;
    public float colorBlueBottomLeft;
    public float colorBlueBottomRight;
    public float colorBlueTopRight;
    public boolean field_22339_T;
    public boolean field_22338_U;
    public boolean field_22337_V;
    public boolean field_22336_W;
    public boolean field_22335_X;
    public boolean field_22334_Y;
    public boolean field_22333_Z;
    public boolean field_22363_aa;
    public boolean field_22361_ab;
    public boolean field_22359_ac;
    public boolean field_22357_ad;
    public boolean field_22355_ae;

    public RenderFluid() {
        this.blockAccess = null;
        this.enableAO = false;
        this.lightValueOwn = 0.0F;
        this.aoLightValueXNeg = 0.0F;
        this.aoLightValueYNeg = 0.0F;
        this.aoLightValueZNeg = 0.0F;
        this.aoLightValueXPos = 0.0F;
        this.aoLightValueYPos = 0.0F;
        this.aoLightValueZPos = 0.0F;
        this.field_22377_m = 0.0F;
        this.field_22376_n = 0.0F;
        this.field_22375_o = 0.0F;
        this.field_22374_p = 0.0F;
        this.field_22373_q = 0.0F;
        this.field_22372_r = 0.0F;
        this.field_22371_s = 0.0F;
        this.field_22370_t = 0.0F;
        this.field_22369_u = 0.0F;
        this.field_22368_v = 0.0F;
        this.field_22367_w = 0.0F;
        this.field_22366_x = 0.0F;
        this.field_22365_y = 0.0F;
        this.field_22364_z = 0.0F;
        this.field_22362_A = 0.0F;
        this.field_22360_B = 0.0F;
        this.field_22358_C = 0.0F;
        this.field_22356_D = 0.0F;
        this.field_22354_E = 0.0F;
        this.field_22353_F = 0.0F;
        this.colorRedTopLeft = 0.0F;
        this.colorRedBottomLeft = 0.0F;
        this.colorRedBottomRight = 0.0F;
        this.colorRedTopRight = 0.0F;
        this.colorGreenTopLeft = 0.0F;
        this.colorGreenBottomLeft = 0.0F;
        this.colorGreenBottomRight = 0.0F;
        this.colorGreenTopRight = 0.0F;
        this.colorBlueTopLeft = 0.0F;
        this.colorBlueBottomLeft = 0.0F;
        this.colorBlueBottomRight = 0.0F;
        this.colorBlueTopRight = 0.0F;
        this.field_22339_T = false;
        this.field_22338_U = false;
        this.field_22337_V = false;
        this.field_22336_W = false;
        this.field_22335_X = false;
        this.field_22334_Y = false;
        this.field_22333_Z = false;
        this.field_22363_aa = false;
        this.field_22361_ab = false;
        this.field_22359_ac = false;
        this.field_22357_ad = false;
        this.field_22355_ae = false;
        this.overrideBlockTexture = -1;
        this.flipTexture = false;
        this.renderAllFaces = false;
        this.field_31088_b = true;
        this.uvRotateEast = 0;
        this.uvRotateWest = 0;
        this.uvRotateSouth = 0;
        this.uvRotateNorth = 0;
        this.uvRotateTop = 0;
        this.uvRotateBottom = 0;
        this.field_22352_G = 1;
    }

    public void renderBottomFace(Block block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i = this.overrideBlockTexture;
        }

        int j = i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int k = i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d3 = ((double)j + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d4 = ((double)j + block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d5 = ((double)k + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d6 = ((double)k + block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = (double)(((float)j + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d4 = (double)(((float)j + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d5 = (double)(((float)k + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d6 = (double)(((float)k + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if (this.uvRotateBottom == 2) {
            d3 = ((double)j + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)j + block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else if (this.uvRotateBottom == 1) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else if (this.uvRotateBottom == 3) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;
        if (this.enableAO) {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        } else {
            
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        }

    }

    public void renderTopFace(Block block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i = this.overrideBlockTexture;
        }

        int j = i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int k = i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d3 = ((double)j + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d4 = ((double)j + block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d5 = ((double)k + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d6 = ((double)k + block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = (double)(((float)j + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d4 = (double)(((float)j + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d5 = (double)(((float)k + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d6 = (double)(((float)k + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;
        if (this.uvRotateTop == 1) {
            d3 = ((double)j + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)j + block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        } else if (this.uvRotateTop == 2) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        } else if (this.uvRotateTop == 3) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = d + block.minX;
        double d12 = d + block.maxX;
        double d13 = d1 + block.maxY;
        double d14 = d2 + block.minZ;
        double d15 = d2 + block.maxZ;
        if (this.enableAO) {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        } else {
            
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }

    }

    public void renderEastFace(Block block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i = this.overrideBlockTexture;
        }

        int j = i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int k = i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d3 = ((double)j + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d4 = ((double)j + block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d8;
        if (this.flipTexture) {
            d8 = d3;
            d3 = d4;
            d4 = d8;
        }

        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = (double)(((float)j + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d4 = (double)(((float)j + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = (double)(((float)k + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d6 = (double)(((float)k + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.uvRotateEast == 2) {
            d3 = ((double)j + block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)j + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.uvRotateEast == 1) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d3 = d4;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.uvRotateEast == 3) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }

        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.minZ;
        if (this.enableAO) {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.addVertexWithUV(d12, d15, d16, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.addVertexWithUV(d13, d15, d16, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.addVertexWithUV(d13, d14, d16, d9, d11);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.addVertexWithUV(d12, d14, d16, d4, d6);
        } else {
            
            tessellator.addVertexWithUV(d12, d15, d16, d8, d10);
            tessellator.addVertexWithUV(d13, d15, d16, d3, d5);
            tessellator.addVertexWithUV(d13, d14, d16, d9, d11);
            tessellator.addVertexWithUV(d12, d14, d16, d4, d6);
        }

    }

    public void renderWestFace(Block block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i = this.overrideBlockTexture;
        }

        int j = i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int k = i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d3 = ((double)j + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d4 = ((double)j + block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d8;
        if (this.flipTexture) {
            d8 = d3;
            d3 = d4;
            d4 = d8;
        }

        if (block.minX < 0.0 || block.maxX > 1.0) {
            d3 = (double)(((float)j + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d4 = (double)(((float)j + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = (double)(((float)k + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d6 = (double)(((float)k + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.uvRotateWest == 1) {
            d3 = ((double)j + block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)j + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.uvRotateWest == 2) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.maxX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d3 = d4;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.uvRotateWest == 3) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.minX * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxX * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }

        double d12 = d + block.minX;
        double d13 = d + block.maxX;
        double d14 = d1 + block.minY;
        double d15 = d1 + block.maxY;
        double d16 = d2 + block.maxZ;
        if (this.enableAO) {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.addVertexWithUV(d12, d15, d16, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.addVertexWithUV(d12, d14, d16, d9, d11);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.addVertexWithUV(d13, d14, d16, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.addVertexWithUV(d13, d15, d16, d8, d10);
        } else {
            
            tessellator.addVertexWithUV(d12, d15, d16, d3, d5);
            tessellator.addVertexWithUV(d12, d14, d16, d9, d11);
            tessellator.addVertexWithUV(d13, d14, d16, d4, d6);
            tessellator.addVertexWithUV(d13, d15, d16, d8, d10);
        }

    }

    public void renderNorthFace(Block block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i = this.overrideBlockTexture;
        }

        int j = i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int k = i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d3 = ((double)j + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d4 = ((double)j + block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d8;
        if (this.flipTexture) {
            d8 = d3;
            d3 = d4;
            d4 = d8;
        }

        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d3 = (double)(((float)j + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d4 = (double)(((float)j + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = (double)(((float)k + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d6 = (double)(((float)k + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.uvRotateNorth == 1) {
            d3 = ((double)j + block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)j + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.uvRotateNorth == 2) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d3 = d4;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.uvRotateNorth == 3) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }

        double d12 = d + block.minX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;
        if (this.enableAO) {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.addVertexWithUV(d12, d14, d16, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.addVertexWithUV(d12, d13, d15, d9, d11);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.addVertexWithUV(d12, d13, d16, d4, d6);
        } else {
            
            tessellator.addVertexWithUV(d12, d14, d16, d8, d10);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d15, d9, d11);
            tessellator.addVertexWithUV(d12, d13, d16, d4, d6);
        }

    }

    public void renderSouthFace(Block block, double d, double d1, double d2, int i) {
        Tessellator tessellator = Tessellator.instance;
        if (this.overrideBlockTexture >= 0) {
            i = this.overrideBlockTexture;
        }

        int j = i % net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        int k = i / net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES * TextureFX.tileWidthTerrain;
        double d3 = ((double)j + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d4 = ((double)j + block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
        double d8;
        if (this.flipTexture) {
            d8 = d3;
            d3 = d4;
            d4 = d8;
        }

        if (block.minZ < 0.0 || block.maxZ > 1.0) {
            d3 = (double)(((float)j + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d4 = (double)(((float)j + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        if (block.minY < 0.0 || block.maxY > 1.0) {
            d5 = (double)(((float)k + 0.0F) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
            d6 = (double)(((float)k + ((float)TextureFX.tileWidthTerrain - 0.01F)) / (float)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES));
        }

        d8 = d4;
        double d9 = d3;
        double d10 = d5;
        double d11 = d6;
        if (this.uvRotateSouth == 2) {
            d3 = ((double)j + block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)(k + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)j + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)(k + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d10 = d5;
            d11 = d6;
            d8 = d3;
            d9 = d4;
            d5 = d6;
            d6 = d10;
        } else if (this.uvRotateSouth == 1) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.maxZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.minY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d3 = d4;
            d4 = d9;
            d10 = d6;
            d11 = d5;
        } else if (this.uvRotateSouth == 3) {
            d3 = ((double)(j + TextureFX.tileWidthTerrain) - block.minZ * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d4 = ((double)(j + TextureFX.tileWidthTerrain) - block.maxZ * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d5 = ((double)k + block.maxY * (double)TextureFX.tileWidthTerrain) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d6 = ((double)k + block.minY * (double)TextureFX.tileWidthTerrain - 0.01) / (double)(TextureFX.tileWidthTerrain * net.minecraft.shared.Minecraft.TEXTURE_ATLAS_WIDTH_TILES);
            d8 = d4;
            d9 = d3;
            d10 = d5;
            d11 = d6;
        }

        double d12 = d + block.maxX;
        double d13 = d1 + block.minY;
        double d14 = d1 + block.maxY;
        double d15 = d2 + block.minZ;
        double d16 = d2 + block.maxZ;
        if (this.enableAO) {
            tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
            tessellator.addVertexWithUV(d12, d13, d16, d9, d11);
            tessellator.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
            tessellator.addVertexWithUV(d12, d14, d15, d8, d10);
            tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
            tessellator.addVertexWithUV(d12, d14, d16, d3, d5);
        } else {
            
            tessellator.addVertexWithUV(d12, d13, d16, d9, d11);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d14, d15, d8, d10);
            tessellator.addVertexWithUV(d12, d14, d16, d3, d5);
        }

    }

    public void renderBlock(Block block, int i, float f){
        Tessellator tessellator = Tessellator.instance;
        //block.setBlockBoundsForItemRender();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        this.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, i));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        this.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, i));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        this.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, i));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        this.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, i));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        this.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, i));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        this.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, i));
        tessellator.draw();
    }

}
