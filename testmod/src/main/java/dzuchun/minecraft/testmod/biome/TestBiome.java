package dzuchun.minecraft.testmod.biome;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.carver.CanyonWorldCarver;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakesFeature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TestBiome extends Biome {
	private static final BlockState WATER = Blocks.WATER.getDefaultState();
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();

	private static final int[] skyZvet = new int[] { 20, 20, 150 }; // RGB
	private static final int[] fogZvet = new int[] { 200, 20, 50 }; // RGB
	private static final int[] waterZvet = new int[] { 50, 50, 50 }; // RGB
	private static final int[] waterFogZvet = new int[] { 100, 100, 100 }; // RGB
	private static final int[] grassZvet = new int[] { 70, 70, 70 }; // RGB
	private static final int[] foliageZvet = new int[] { 100, 100, 100 }; // RGB

	public static final BlockStateProvidingFeatureConfig HAY_PILE_CONFIG = new BlockStateProvidingFeatureConfig(
			new SimpleBlockStateProvider(TestMod.TEST_BLOCK.getDefaultState()));

	public TestBiome() {
		super((new Builder())
				.surfaceBuilder(SurfaceBuilder.SWAMP,
						new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.STONE.getDefaultState(),
								Blocks.RED_SAND.getDefaultState()))
				.precipitation(RainType.RAIN).category(Category.PLAINS).depth(0.125F).scale(0.05F).temperature(0.8F)
				.downfall(0.4F).waterColor(MathHelper.rgb(waterZvet[0], waterZvet[1], waterZvet[2]))
				.waterFogColor(MathHelper.rgb(waterFogZvet[0], waterFogZvet[1], waterFogZvet[2])));
//	    addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, PineappleTreeFeature.getConfiguredFeature()
//        );

//	    TestVillagePools.init();
//	    this.addStructure(Feature.VILLAGE.withConfiguration(new VillageConfig("village/test/town_centers", 6)));
//	    
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(
				new CaveWorldCarver(ProbabilityConfig::deserialize, 256), new ProbabilityConfig(0.14285715F)));
		this.addCarver(GenerationStage.Carving.AIR, Biome
				.createCarver(new CanyonWorldCarver(ProbabilityConfig::deserialize), new ProbabilityConfig(0.02F)));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
				new LakesFeature(BlockStateFeatureConfig::func_227271_a_)
						.withConfiguration(new BlockStateFeatureConfig(WATER))
						.withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
				new LakesFeature(BlockStateFeatureConfig::func_227271_a_)
						.withConfiguration(new BlockStateFeatureConfig(LAVA))
						.withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80))));
		// this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
		// Feature.NORMAL_TREE.withConfiguration(TEST_TREE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new
		// AtSurfaceWithExtraConfig(0, 0.05F, 1))));
//		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
//				Feature.VILLAGE.withConfiguration(new VillageConfig("village/plains/town_centers", 6))
//						.withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//
//	    LOGGER.info("Trying into villages...");
//		TestVillagePools.init();
//		this.addStructure(Feature.VILLAGE.withConfiguration(new VillageConfig("village/test/town_centers", 6)));

		this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(TestMod.BARKING_BUCKET_ENTITY_TYPE, 20, 0, 5));

//	    this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
//	    this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 10, 4, 4));
//	    this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
//	    this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.COW, 8, 4, 4));
//	    this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.HORSE, 5, 2, 6));
//	    this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.DONKEY, 1, 1, 3));
//	    this.addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
//	    this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
	}

	@Override
	public int getSkyColor() {
		return MathHelper.rgb(skyZvet[0], skyZvet[1], skyZvet[2]);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getGrassColor(double posX, double posZ) {
		return MathHelper.rgb(grassZvet[0], grassZvet[1], grassZvet[2]);
	}
	@Override
	public int getFoliageColor()
	{
		return MathHelper.rgb(foliageZvet[0], foliageZvet[1], foliageZvet[2]);
	}
}