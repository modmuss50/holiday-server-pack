package test;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.spongepowered.asm.mixin.MixinEnvironment;

public class TestEntrypoint implements DedicatedServerModInitializer {
    private int ticks = 0;

    @Override
    public void onInitializeServer() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ticks++;

            if (ticks == 50) {
                MixinEnvironment.getCurrentEnvironment().audit();
                server.stop(false);
            }
        });
    }
}
