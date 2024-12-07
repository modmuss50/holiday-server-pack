package test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.spongepowered.asm.mixin.MixinEnvironment;

public class TestEntrypoint implements ModInitializer {
    private int ticks = 0;

    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ticks++;

            if (ticks == 50) {
                MixinEnvironment.getCurrentEnvironment().audit();
                server.stop(false);
            }
        });
    }
}
