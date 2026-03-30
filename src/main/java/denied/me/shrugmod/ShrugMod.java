package denied.me.shrugmod;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShrugMod implements ClientModInitializer {
	public static final String MOD_ID = "shrug-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("[ShrugMod] Registering client-side commands...");

		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommands.literal("shrug")
                .executes(ShrugMod::shrugWithoutMessage)
                .then(ClientCommands.argument("message", StringArgumentType.greedyString())
                        .executes(ShrugMod::shrugWithMessage))));
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommands.literal("tableflip")
                .executes(ShrugMod::tfWithoutMessage)
                .then(ClientCommands.argument("message", StringArgumentType.greedyString())
                        .executes(ShrugMod::tfWithMessage))));
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommands.literal("unflip")
                .executes(ShrugMod::ufWithoutMessage)
                .then(ClientCommands.argument("message", StringArgumentType.greedyString())
                        .executes(ShrugMod::ufWithMessage))));

	}

	private static int shrugWithoutMessage(CommandContext<?> context) {return sendClientChatMessage("¯\\_(ツ)_/¯");}

	private static int shrugWithMessage(CommandContext<?> context) {
		String message = StringArgumentType.getString(context, "message");
		return sendClientChatMessage(message + " ¯\\_(ツ)_/¯");
	}
	private static int tfWithoutMessage(CommandContext<?> context) {return sendClientChatMessage("(╯°□°)╯︵ ┻━┻");}

	private static int tfWithMessage(CommandContext<?> context) {
		String message = StringArgumentType.getString(context, "message");
		return sendClientChatMessage(message + " (╯°□°)╯︵ ┻━┻");
	}
	private static int ufWithoutMessage(CommandContext<?> context) {return sendClientChatMessage("┬─┬ノ( º _ ºノ)");}

	private static int ufWithMessage(CommandContext<?> context) {
		String message = StringArgumentType.getString(context, "message");
		return sendClientChatMessage(message + " ┬─┬ノ( º _ ºノ)");
	}

	private static int sendClientChatMessage(String message) {
		Minecraft client = Minecraft.getInstance();

		if (client.player != null) {
			if (message.startsWith("/")) {
				String command = message.substring(1);
				client.player.connection.sendCommand(command);
				LOGGER.info("[ShrugMod] Executed command: /{}", command);
			} else {
				client.player.connection.sendChat(message);
				LOGGER.info("[ShrugMod] Sent chat message: {}", message);
			}
		} else {
			LOGGER.warn("[ShrugMod] Message not sent.");
		}

		return 1;
	}
}
