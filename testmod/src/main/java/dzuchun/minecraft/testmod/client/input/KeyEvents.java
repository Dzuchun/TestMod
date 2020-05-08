package dzuchun.minecraft.testmod.client.input;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyEvents {
	private static final Logger LOGGER = LogManager.getLogger();

	private static enum KeyName {
		SUMMON_WINGS
	}

	private static final LinkedHashMap<KeyName, KeyBinding> keyBindings = new LinkedHashMap<KeyName, KeyBinding>() {
		private static final long serialVersionUID = 1L;
		{
			this.put(KeyName.SUMMON_WINGS, new KeyBinding("key.testmod.summon_wings", KeyConflictContext.IN_GAME,
					KeyModifier.NONE, InputMappings.Type.KEYSYM.getOrMakeInput(0), "Test Mod"));
		}
	};

	public static void init() {
		for (KeyBinding key : keyBindings.values()) {
			ClientRegistry.registerKeyBinding(key);
		}
		LOGGER.info("Keybindings registered");
	}

	@SubscribeEvent
	public static void onKeyPressed(InputEvent.KeyInputEvent event) {
		if (keyBindings.get(KeyName.SUMMON_WINGS).isPressed()) {
			Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.summoned").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE).setBold(true)), true);
		}
	}
}
