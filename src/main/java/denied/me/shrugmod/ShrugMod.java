package denied.me.shrugmod;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShrugMod implements ModInitializer {
	public static final String MOD_ID = "shrug-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("shrug")
					.executes(ShrugMod::shrugWithoutMessage)
					.then(CommandManager.argument("message", StringArgumentType.greedyString())
							.executes(ShrugMod::shrugWithMessage)));
		});
	}

	private static int shrugWithoutMessage(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		source.sendMessage(Text.literal("¯\\_(ツ)_/¯"));
		return 1;
	}

	private static int shrugWithMessage(CommandContext<ServerCommandSource> context) {
		ServerCommandSource source = context.getSource();
		String message = StringArgumentType.getString(context, "message");
		source.sendMessage(Text.literal(message + " ¯\\_(ツ)_/¯"));
		return 1;
	}
}