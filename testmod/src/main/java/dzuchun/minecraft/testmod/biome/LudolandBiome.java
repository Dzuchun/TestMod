package dzuchun.minecraft.testmod.biome;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class LudolandBiome extends Biome{

	protected LudolandBiome() {
		super(new Biome.Builder()
				.category(Category.JUNGLE)
				.scale(5.0f)
				.precipitation(RainType.NONE)
				.surfaceBuilder(
						SurfaceBuilder.DEFAULT,
						new SurfaceBuilderConfig(TestMod.TEST_BLOCK.getDefaultState(), TestMod.TEST_BLOCK.getDefaultState(), TestMod.TEST_BLOCK.getDefaultState())
				)
		);
		// TODO Auto-generated constructor stub
	}

}
