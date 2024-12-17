package holiday.server.mixin;

import holiday.server.ClientEntrypoint;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerList.class)
public class ServerListMixin {
	@Shadow
	@Final
	private List<ServerInfo> servers;

	@Inject(method = "loadFile", at = @At("TAIL"))
	private void onFileLoaded(CallbackInfo ci) {
		if (this.servers.isEmpty()) {
			this.servers.add(ClientEntrypoint.SERVER);
		}
	}
}
