package sunsetsatellite.fluidapi.mixin.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//quick way to fix a bug with guicontainers crashing the game pls fix kthxbye
@Mixin(
        value = Minecraft.class,
        remap = false
)
public class MinecraftMixin {

    @Shadow
    private static Minecraft theMinecraft;

    @Inject(
            method = "getMinecraft(Ljava/lang/Class;)Lnet/minecraft/client/Minecraft;",at = @At("HEAD"),cancellable = true
    )
    private static void getMinecraft(Class<?> caller, CallbackInfoReturnable<Minecraft> cir) {
        cir.setReturnValue(theMinecraft);
    }
}