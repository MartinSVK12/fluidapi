package net.sunsetsatellite.fluidapi;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Item;
import net.minecraft.src.NBTTagCompound;

public final class FluidStack {
    public int amount;
    public BlockFluid liquid;

    public FluidStack(BlockFluid item, int size){
        amount = size;
        liquid = item;
    }

    public FluidStack(NBTTagCompound nbt){
        readFromNBT(nbt);
    }

    public BlockFluid getLiquid(){
        return liquid;
    }

    public FluidStack splitStack(int amount){
        this.amount -= amount;
        return new FluidStack(this.liquid, amount);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if(liquid != null){
            nbt.setShort("liquid", (short) liquid.blockID);
            nbt.setInteger("amount",amount);
        }
        return nbt;
    }

    public void readFromNBT(NBTTagCompound nbt){
        if(nbt.hasKey("liquid")){
            this.liquid = (BlockFluid) Block.blocksList[nbt.getShort("liquid")];
            this.amount = nbt.getInteger("amount");
        }
    }

    public FluidStack copy(){
        return new FluidStack(liquid, amount);
    }

    public String getFluidName(){
        return liquid.getBlockName();
    }

    public String toString(){
        return amount+"mB "+liquid.getBlockName();
    }

    public boolean isFluidEqual(FluidStack stack){
        return stack.liquid == liquid;
    }

    public boolean isStackEqual(FluidStack stack){
        return stack.liquid == liquid && stack.amount == amount;
    }
}
