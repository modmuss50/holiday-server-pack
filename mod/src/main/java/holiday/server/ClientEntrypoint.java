package holiday.server;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;

public class ClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientConfigurationNetworking.registerGlobalReceiver(CommonEntrypoint.RequestVersionPayload.ID, (payload, context) -> {
            context.responseSender().sendPacket(new CommonEntrypoint.VersionResponsePayload(CommonEntrypoint.CURRENT_VERSION));
        });
    }
}
