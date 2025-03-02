package denied.me.shrugmod;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShrugMod implements ClientModInitializer {
	public static final String MOD_ID = "shrug-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("[ShrugMod] Registering client-side command...");

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("shrug")
					.executes(ShrugMod::shrugWithoutMessage)
					.then(ClientCommandManager.argument("message", StringArgumentType.greedyString())
							.executes(ShrugMod::shrugWithMessage)));
		});
	}

	private static int shrugWithoutMessage(CommandContext<?> context) {
		return sendClientChatMessage("¯\\_(ツ)_/¯");
	}

	private static int shrugWithMessage(CommandContext<?> context) {
		String message = StringArgumentType.getString(context, "message");
		return sendClientChatMessage(message + " ¯\\_(ツ)_/¯");
	}

	private static int sendClientChatMessage(String message) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player != null) {
			// Send the message as if the player typed it
			client.player.networkHandler.sendChatMessage(message);
			LOGGER.info("[ShrugMod] Sent chat message: {}", message);
		} else {
			LOGGER.warn("[ShrugMod] Player instance is null! Message not sent.");
		}
		return 1;
	}
}
