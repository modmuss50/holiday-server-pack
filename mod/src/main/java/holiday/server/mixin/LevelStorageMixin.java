package holiday.server.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import holiday.server.CommonEntrypoint;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelStorage.class)
public class LevelStorageMixin {
    @ModifyReturnValue(method = "parseDataPackSettings", at = @At("RETURN"))
    private static DataConfiguration forceEnableFeatures(DataConfiguration configuration) {
        return configuration.withFeaturesAdded(CommonEntrypoint.FORCE_ENABLED_FEATURES);
    }
}
