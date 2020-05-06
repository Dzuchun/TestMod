package dzuchun.minecraft.testmod.worldtype;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.OverworldChunkGenerator;
import net.minecraft.world.gen.OverworldGenSettings;

public class TestWorldType extends WorldType {
	  public TestWorldType() {
	    super("testworld");
	  }

	  @Override
	  public ChunkGenerator<?> createChunkGenerator(World world) {
	    if (world.getDimension().getType() == DimensionType.OVERWORLD) {
	      OverworldGenSettings overworldGenSettings = new OverworldGenSettings();
	      SingleBiomeProviderSettings biomeProviderSettings = new SingleBiomeProviderSettings(world.getWorldInfo());
	      biomeProviderSettings.setBiome(TestMod.TEST_BIOME_REGISTRY_OBJECT.get());
	      overworldGenSettings.setDefaultBlock(TestMod.TEST_BLOCK.getDefaultState());
	      return new OverworldChunkGenerator(world, new SingleBiomeProvider(biomeProviderSettings), overworldGenSettings);
	    } else
	      return super.createChunkGenerator(world);
	  }
	}
