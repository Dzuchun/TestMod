package dzuchun.minecraft.testmod.client.input;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.client.gui.overlay.ManaBarRenderer;
import dzuchun.minecraft.testmod.net.LeapMessage;
import dzuchun.minecraft.testmod.net.TestModPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TestMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyEvents {
	private static final Logger LOGGER = LogManager.getLogger();

	private static enum KeyName {
		SUMMON_WINGS, MODIFY_MAX_MANA, MODIFY_CURRENT_MANA, LEAP
	}

	private static final LinkedHashMap<KeyName, KeyBinding> keyBindings = new LinkedHashMap<KeyName, KeyBinding>() {
		private static final long serialVersionUID = 1L;
		{
			this.put(KeyName.SUMMON_WINGS, new KeyBinding("key.testmod.summon_wings", KeyConflictContext.IN_GAME,
					KeyModifier.NONE, InputMappings.Type.KEYSYM.getOrMakeInput(KeyEvent.VK_F), "Test Mod"));
			
			this.put(KeyName.MODIFY_MAX_MANA, new KeyBinding("key.testmod.modify_max_mana", KeyConflictContext.IN_GAME,
					KeyModifier.NONE, InputMappings.Type.KEYSYM.getOrMakeInput(KeyEvent.VK_PLUS), "Test Mod"));
			
			this.put(KeyName.MODIFY_CURRENT_MANA,
					new KeyBinding("key.testmod.modify_current_mana", KeyConflictContext.IN_GAME, KeyModifier.NONE,
							InputMappings.Type.KEYSYM.getOrMakeInput(KeyEvent.VK_SUBTRACT), "Test Mod"));
			
			this.put(KeyName.LEAP,
					new KeyBinding("key.testmod.leap", KeyConflictContext.IN_GAME, KeyModifier.NONE,
							InputMappings.Type.KEYSYM.getOrMakeInput(KeyEvent.VK_Z), "Test Mod"));
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
			Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.summoned")
					.setStyle(new Style().setColor(TextFormatting.DARK_PURPLE).setBold(true)), true);
		}
		
		if (keyBindings.get(KeyName.MODIFY_MAX_MANA).isPressed()) {
			if (!Screen.hasAltDown()) {
//				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.mana")
//						.setStyle(new Style().setColor(TextFormatting.DARK_PURPLE).setBold(true)), true);
				LOGGER.info("Adding max mana");
				ManaBarRenderer.addMaxMana(1);
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.mana").appendText(ManaBarRenderer.getCurrentMana() + "/" + ManaBarRenderer.getMaxMana())
						.setStyle(new Style().setColor(TextFormatting.DARK_AQUA).setBold(true)), true);
			} else {
				LOGGER.info("Substracting max mana");
				ManaBarRenderer.addMaxMana(-1);
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.mana").appendText(ManaBarRenderer.getCurrentMana() + "/" + ManaBarRenderer.getMaxMana())
						.setStyle(new Style().setColor(TextFormatting.DARK_AQUA).setBold(true)), true);
			}
		}

		if (keyBindings.get(KeyName.MODIFY_CURRENT_MANA).isPressed()) {
			if (!Screen.hasAltDown()) {
				LOGGER.info("Adding current mana");
				ManaBarRenderer.addCurrentMana(1);
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.mana").appendText(ManaBarRenderer.getCurrentMana() + "/" + ManaBarRenderer.getMaxMana())
						.setStyle(new Style().setColor(TextFormatting.DARK_AQUA).setBold(true)), true);
			} else {
				LOGGER.info("Substracting current mana");
				ManaBarRenderer.addCurrentMana(-1);
				Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.mana").appendText(ManaBarRenderer.getCurrentMana() + "/" + ManaBarRenderer.getMaxMana())
						.setStyle(new Style().setColor(TextFormatting.DARK_AQUA).setBold(true)), true);
			}
		}
		
		if (keyBindings.get(KeyName.LEAP).isPressed()) {
			Minecraft.getInstance().player.sendStatusMessage(new TranslationTextComponent("wings.leap")
					.setStyle(new Style().setColor(TextFormatting.GOLD).setBold(true)), true);
			Minecraft.getInstance().player.fallDistance = 0;
			TestModPacketHandler.INSTANCE.sendToServer(new LeapMessage());
		}
	}
}
