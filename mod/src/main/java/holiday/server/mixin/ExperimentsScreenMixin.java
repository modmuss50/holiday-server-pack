package holiday.server.mixin;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.client.gui.screen.world.ExperimentsScreen;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(ExperimentsScreen.class)
public class ExperimentsScreenMixin {
    @Shadow
    @Final
    private Object2BooleanMap<ResourcePackProfile> experiments;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void hideForceEnabledExperiments(CallbackInfo ci) {
        Iterator<ResourcePackProfile> iterator = this.experiments.keySet().iterator();

        while (iterator.hasNext()) {
            ResourcePackProfile profile = iterator.next();

            if (profile.getRequestedFeatures().intersects(FeatureFlags.DEFAULT_ENABLED_FEATURES)) {
                iterator.remove();
            }
        }
    }
}
