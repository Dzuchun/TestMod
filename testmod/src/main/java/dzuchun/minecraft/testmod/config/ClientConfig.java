package dzuchun.minecraft.testmod.config;

import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

import dzuchun.minecraft.testmod.TestMod;

/**
 * For configuration settings that change the behavior of code on the LOGICAL CLIENT.
 * This can be moved to an inner class of ExampleModConfig, but is separate because of personal preference and to keep the code organized
 *
 * @author Cadiboo
 */
final class ClientConfig {

	final ForgeConfigSpec.BooleanValue clientBoolean;
	final ForgeConfigSpec.ConfigValue<List<String>> clientStringList;
	final ForgeConfigSpec.EnumValue<DyeColor> clientDyeColorEnum;

	final ForgeConfigSpec.BooleanValue modelTranslucency;
	final ForgeConfigSpec.DoubleValue modelScale;

	ClientConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		clientBoolean = builder
				.comment("An example boolean in the client config")
				.translation(TestMod.MODID + ".config.clientBoolean")
				.define("clientBoolean", true);
		clientStringList = builder
				.comment("An example list of Strings in the client config")
				.translation(TestMod.MODID + ".config.clientStringList")
				.define("clientStringList", new ArrayList<>());
		clientDyeColorEnum = builder
				.comment("An example DyeColor enum in the client config")
				.translation(TestMod.MODID + ".config.clientDyeColorEnum")
				.defineEnum("clientDyeColorEnum", DyeColor.WHITE);

		modelTranslucency = builder
				.comment("If the model should be rendered translucent")
				.translation(TestMod.MODID + ".config.modelTranslucency")
				.define("modelTranslucency", true);
		modelScale = builder
				.comment("The scale to render the model at")
				.translation(TestMod.MODID + ".config.modelScale")
				.defineInRange("modelScale", 0.0625F, 0.0001F, 100F);
		builder.pop();
	}

}
