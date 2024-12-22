package holiday.server.mixin.flightassistant;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.octol1ttle.flightassistant.computers.impl.AirDataComputer;

@Mixin(value = AirDataComputer.class, remap = false)
abstract class AirDataComputerMixin {
    @Inject(method = "validate(FFF)F", at = @At("HEAD"), cancellable = true)
    private static void cancelValidate(float f, float min, float max, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(f);
    }
}
