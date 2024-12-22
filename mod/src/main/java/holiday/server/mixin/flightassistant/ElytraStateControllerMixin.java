package holiday.server.mixin.flightassistant;

import net.fabricmc.fabric.api.util.TriState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.octol1ttle.flightassistant.computers.impl.safety.ElytraStateController;

@Mixin(value = ElytraStateController.class, remap = false)
abstract class ElytraStateControllerMixin {
    @Shadow
    private TriState syncedState;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void setInitialSyncedState(CallbackInfo ci) {
        this.syncedState = TriState.DEFAULT;
    }
}
