package dzuchun.minecraft.testmod.world.gen.feachure.village;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.util.Pair;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.FeatureJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.AlwaysTrueRuleTest;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.BlockStateMatchRuleTest;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleEntry;
import net.minecraft.world.gen.feature.template.RuleStructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

@SuppressWarnings({"deprecation", "unused" })
public class TestVillagePools {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
	public static void init() {
	}

	static {
		LOGGER.warn("Initing custom village piece");
		
		JigsawManager.REGISTRY.register(new JigsawPattern(
			new ResourceLocation("village/plains/houses"),
			new ResourceLocation("village/plains/terminators"),
			ImmutableList.of(
				new Pair<>(new SingleJigsawPiece("testmod:test_structure"), 10)
				),
			PlacementBehaviour.RIGID
		));
		
		/*
		ImmutableList<StructureProcessor> lvt_0_1_ = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.8F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.MOSSY_COBBLESTONE.getDefaultState()),
				new RuleEntry(new TagMatchRuleTest(BlockTags.DOORS), AlwaysTrueRuleTest.INSTANCE,
						Blocks.AIR.getDefaultState()),
				new RuleEntry(new BlockMatchRuleTest(Blocks.TORCH), AlwaysTrueRuleTest.INSTANCE,
						Blocks.AIR.getDefaultState()),
				new RuleEntry(new BlockMatchRuleTest(Blocks.WALL_TORCH), AlwaysTrueRuleTest.INSTANCE,
						Blocks.AIR.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.07F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.MOSSY_COBBLESTONE, 0.07F),
						AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHITE_TERRACOTTA, 0.07F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.OAK_LOG, 0.05F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.OAK_PLANKS, 0.1F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.OAK_STAIRS, 0.1F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.STRIPPED_OAK_LOG, 0.02F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GLASS_PANE, 0.5F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.COBWEB.getDefaultState()),
				new RuleEntry[] {
						new RuleEntry(
								new BlockStateMatchRuleTest((BlockState) ((BlockState) Blocks.GLASS_PANE
										.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true)),
								AlwaysTrueRuleTest.INSTANCE,
								(BlockState) ((BlockState) Blocks.BROWN_STAINED_GLASS_PANE.getDefaultState()
										.with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true)),
						new RuleEntry(
								new BlockStateMatchRuleTest((BlockState) ((BlockState) Blocks.GLASS_PANE
										.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true)),
								AlwaysTrueRuleTest.INSTANCE,
								(BlockState) ((BlockState) Blocks.BROWN_STAINED_GLASS_PANE.getDefaultState()
										.with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true)),
						new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.3F), AlwaysTrueRuleTest.INSTANCE,
								Blocks.CARROTS.getDefaultState()),
						new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.2F), AlwaysTrueRuleTest.INSTANCE,
								Blocks.POTATOES.getDefaultState()),
						new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.1F), AlwaysTrueRuleTest.INSTANCE,
								Blocks.BEETROOTS.getDefaultState()) })));
		ImmutableList<StructureProcessor> lvt_1_1_ = ImmutableList.of(new RuleStructureProcessor(
				ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.1F),
						AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.getDefaultState()))));
		JigsawManager.REGISTRY
				.register(
						new JigsawPattern(new ResourceLocation("village/test/town_centers"),
								new ResourceLocation("empty"),
								ImmutableList.of(
										new Pair(
												new SingleJigsawPiece("village/test/town_centers/plains_fountain_01",
														ImmutableList.of(new RuleStructureProcessor(
																ImmutableList.of(new RuleEntry(
																		new RandomBlockMatchRuleTest(Blocks.COBBLESTONE,
																				0.2F),
																		AlwaysTrueRuleTest.INSTANCE,
																		Blocks.MOSSY_COBBLESTONE.getDefaultState()))))),
												50),
										new Pair(
												new SingleJigsawPiece(
														"village/test/town_centers/plains_meeting_point_1",
														ImmutableList.of(new RuleStructureProcessor(
																ImmutableList.of(new RuleEntry(
																		new RandomBlockMatchRuleTest(Blocks.COBBLESTONE,
																				0.2F),
																		AlwaysTrueRuleTest.INSTANCE,
																		Blocks.MOSSY_COBBLESTONE.getDefaultState()))))),
												50),
										new Pair(new SingleJigsawPiece(
												"village/test/town_centers/plains_meeting_point_2"), 50),
										new Pair(
												new SingleJigsawPiece(
														"village/test/town_centers/plains_meeting_point_3",
														ImmutableList.of(new RuleStructureProcessor(
																ImmutableList.of(new RuleEntry(
																		new RandomBlockMatchRuleTest(Blocks.COBBLESTONE,
																				0.7F),
																		AlwaysTrueRuleTest.INSTANCE,
																		Blocks.MOSSY_COBBLESTONE.getDefaultState()))))),
												50),
										new Pair(new SingleJigsawPiece(
												"village/test/zombie/town_centers/plains_fountain_01", lvt_0_1_), 1),
										new Pair(new SingleJigsawPiece(
												"village/test/zombie/town_centers/plains_meeting_point_1", lvt_0_1_),
												1),
										new Pair(new SingleJigsawPiece(
												"village/test/zombie/town_centers/plains_meeting_point_2", lvt_0_1_),
												1),
										new Pair(new SingleJigsawPiece(
												"village/test/zombie/town_centers/plains_meeting_point_3", lvt_0_1_),
												1)),
								JigsawPattern.PlacementBehaviour.RIGID));
		ImmutableList<StructureProcessor> lvt_2_1_ = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(
				new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_PATH), new BlockMatchRuleTest(Blocks.WATER),
						Blocks.OAK_PLANKS.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GRASS_PATH, 0.1F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.GRASS_BLOCK.getDefaultState()),
				new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_BLOCK), new BlockMatchRuleTest(Blocks.WATER),
						Blocks.WATER.getDefaultState()),
				new RuleEntry(new BlockMatchRuleTest(Blocks.DIRT), new BlockMatchRuleTest(Blocks.WATER),
						Blocks.WATER.getDefaultState()))));
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("village/test/streets"),
				new ResourceLocation("village/test/terminators"),
				ImmutableList.of(new Pair(new SingleJigsawPiece("village/test/streets/corner_01", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/streets/corner_02", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/streets/corner_03", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/streets/straight_01", lvt_2_1_), 4),
						new Pair(new SingleJigsawPiece("village/test/streets/straight_02", lvt_2_1_), 4),
						new Pair(new SingleJigsawPiece("village/test/streets/straight_03", lvt_2_1_), 7),
						new Pair(new SingleJigsawPiece("village/test/streets/straight_04", lvt_2_1_), 7),
						new Pair(new SingleJigsawPiece("village/test/streets/straight_05", lvt_2_1_), 3),
						new Pair(new SingleJigsawPiece("village/test/streets/straight_06", lvt_2_1_), 4),
						new Pair(new SingleJigsawPiece("village/test/streets/crossroad_01", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/streets/crossroad_02", lvt_2_1_), 1),
						new Pair(new SingleJigsawPiece("village/test/streets/crossroad_03", lvt_2_1_), 2),
						new Pair[] { new Pair(new SingleJigsawPiece("village/test/streets/crossroad_04", lvt_2_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/streets/crossroad_05", lvt_2_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/streets/crossroad_06", lvt_2_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/streets/turn_01", lvt_2_1_), 3) }),
				JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("village/test/zombie/streets"),
				new ResourceLocation("village/test/terminators"),
				ImmutableList.of(new Pair(new SingleJigsawPiece("village/test/zombie/streets/corner_01", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/corner_02", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/corner_03", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/straight_01", lvt_2_1_), 4),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/straight_02", lvt_2_1_), 4),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/straight_03", lvt_2_1_), 7),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/straight_04", lvt_2_1_), 7),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/straight_05", lvt_2_1_), 3),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/straight_06", lvt_2_1_), 4),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/crossroad_01", lvt_2_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/crossroad_02", lvt_2_1_), 1),
						new Pair(new SingleJigsawPiece("village/test/zombie/streets/crossroad_03", lvt_2_1_), 2),
						new Pair[] {
								new Pair(new SingleJigsawPiece("village/test/zombie/streets/crossroad_04", lvt_2_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/zombie/streets/crossroad_05", lvt_2_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/zombie/streets/crossroad_06", lvt_2_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/zombie/streets/turn_01", lvt_2_1_), 3) }),
				JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
		ImmutableList<StructureProcessor> lvt_3_1_ = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.3F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.CARROTS.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.2F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.POTATOES.getDefaultState()),
				new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.1F), AlwaysTrueRuleTest.INSTANCE,
						Blocks.BEETROOTS.getDefaultState()))));
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("village/test/houses"),
				new ResourceLocation("village/test/terminators"),
				ImmutableList.of(
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_1", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_2", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_3", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_4", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_5", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_6", lvt_1_1_), 1),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_7", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_small_house_8", lvt_1_1_), 3),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_medium_house_1", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_medium_house_2", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_big_house_1", lvt_1_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_butcher_shop_1", lvt_1_1_), 2),
						new Pair[] {
								new Pair(new SingleJigsawPiece("village/test/houses/plains_butcher_shop_2", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_tool_smith_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_fletcher_house_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_shepherds_house_1"), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_armorer_house_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_fisher_cottage_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_tannery_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_cartographer_1", lvt_1_1_), 1),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_library_1", lvt_1_1_), 5),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_library_2", lvt_1_1_), 1),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_masons_house_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_weaponsmith_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_temple_3", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_temple_4", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_stable_1", lvt_1_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_stable_2"), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_large_farm_1", lvt_3_1_), 4),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_small_farm_1", lvt_3_1_), 4),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_animal_pen_1"), 1),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_animal_pen_2"), 1),
								//
								new Pair(new SingleJigsawPiece("testmod:structures/test_structure"), 10),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_animal_pen_3"), 5),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_accessory_1"), 1),
								new Pair(
										new SingleJigsawPiece("village/test/houses/plains_meeting_point_4",
												ImmutableList
														.of(new RuleStructureProcessor(
																ImmutableList.of(new RuleEntry(
																		new RandomBlockMatchRuleTest(Blocks.COBBLESTONE,
																				0.7F),
																		AlwaysTrueRuleTest.INSTANCE,
																		Blocks.MOSSY_COBBLESTONE.getDefaultState()))))),
										3),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_meeting_point_5"), 1),
								Pair.of(EmptyJigsawPiece.INSTANCE, 10) }),
				JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("village/test/zombie/houses"),
				new ResourceLocation("village/test/terminators"),
				ImmutableList.of(
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_1", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_2", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_3", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_4", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_5", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_6", lvt_0_1_), 1),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_7", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_small_house_8", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_medium_house_1", lvt_0_1_),
								2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_medium_house_2", lvt_0_1_),
								2),
						new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_big_house_1", lvt_0_1_), 2),
						new Pair(new SingleJigsawPiece("village/test/houses/plains_butcher_shop_1", lvt_0_1_), 2),
						new Pair[] {
								new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_butcher_shop_2",
										lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_tool_smith_1", lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_fletcher_house_1",
										lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_shepherds_house_1",
										lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_armorer_house_1", lvt_0_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_fisher_cottage_1", lvt_0_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_tannery_1", lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_cartographer_1", lvt_0_1_),
										1),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_library_1", lvt_0_1_), 3),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_library_2", lvt_0_1_), 1),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_masons_house_1", lvt_0_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_weaponsmith_1", lvt_0_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_temple_3", lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_temple_4", lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_stable_1", lvt_0_1_),
										2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_stable_2", lvt_0_1_), 2),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_large_farm_1", lvt_0_1_), 4),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_small_farm_1", lvt_0_1_), 4),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_animal_pen_1", lvt_0_1_), 1),
								new Pair(new SingleJigsawPiece("village/test/houses/plains_animal_pen_2", lvt_0_1_), 1),
								new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_animal_pen_3",
										lvt_0_1_), 5),
								new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_meeting_point_4",
										lvt_0_1_), 3),
								new Pair(new SingleJigsawPiece("village/test/zombie/houses/plains_meeting_point_5",
										lvt_0_1_), 1),
								Pair.of(EmptyJigsawPiece.INSTANCE, 10) }),
				JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("village/test/terminators"),
				new ResourceLocation("empty"),
				ImmutableList.of(new Pair(new SingleJigsawPiece("village/test/terminators/terminator_01", lvt_2_1_), 1),
						new Pair(new SingleJigsawPiece("village/test/terminators/terminator_02", lvt_2_1_), 1),
						new Pair(new SingleJigsawPiece("village/test/terminators/terminator_03", lvt_2_1_), 1),
						new Pair(new SingleJigsawPiece("village/test/terminators/terminator_04", lvt_2_1_), 1)),
				JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
		JigsawManager.REGISTRY
				.register(
						new JigsawPattern(new ResourceLocation("village/test/trees"), new ResourceLocation("empty"),
								ImmutableList.of(new Pair(new FeatureJigsawPiece(
										Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG)),
										1)),
								JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("village/test/decor"),
				new ResourceLocation("empty"),
				ImmutableList.of(new Pair(new SingleJigsawPiece("village/test/plains_lamp_1"), 2),
						new Pair(new FeatureJigsawPiece(
								Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG)), 1),
						new Pair(
								new FeatureJigsawPiece(
										Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.PLAINS_FLOWER_CONFIG)),
								1),
						new Pair(new FeatureJigsawPiece(
								Feature.BLOCK_PILE.withConfiguration(DefaultBiomeFeatures.HAY_PILE_CONFIG)), 1),
						Pair.of(EmptyJigsawPiece.INSTANCE, 2)),
				JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation("village/test/zombie/decor"),
				new ResourceLocation("empty"),
				ImmutableList.of(new Pair(new SingleJigsawPiece("village/test/plains_lamp_1", lvt_0_1_), 1),
						new Pair(
								new FeatureJigsawPiece(
										Feature.NORMAL_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG)),
								1),
						new Pair(
								new FeatureJigsawPiece(
										Feature.FLOWER.withConfiguration(DefaultBiomeFeatures.PLAINS_FLOWER_CONFIG)),
								1),
						new Pair(new FeatureJigsawPiece(
								Feature.BLOCK_PILE.withConfiguration(DefaultBiomeFeatures.HAY_PILE_CONFIG)), 1),
						Pair.of(EmptyJigsawPiece.INSTANCE, 2)),
				JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(
				new JigsawPattern(new ResourceLocation("village/test/villagers"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/test/villagers/nitwit"), 1),
								new Pair(new SingleJigsawPiece("village/test/villagers/baby"), 1),
								new Pair(new SingleJigsawPiece("village/test/villagers/unemployed"), 10)),
						JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(
				new JigsawPattern(new ResourceLocation("village/test/zombie/villagers"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/test/zombie/villagers/nitwit"), 1),
								new Pair(new SingleJigsawPiece("village/test/zombie/villagers/unemployed"), 10)),
						JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(
				new JigsawPattern(new ResourceLocation("village/common/animals"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/common/animals/cows_1"), 7),
								new Pair(new SingleJigsawPiece("village/common/animals/pigs_1"), 7),
								new Pair(new SingleJigsawPiece("village/common/animals/horses_1"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/horses_2"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/horses_3"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/horses_4"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/horses_5"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/sheep_1"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/sheep_2"), 1),
								Pair.of(EmptyJigsawPiece.INSTANCE, 5)),
						JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY
				.register(new JigsawPattern(new ResourceLocation("village/common/sheep"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/common/animals/sheep_1"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/sheep_2"), 1)),
						JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY
				.register(new JigsawPattern(new ResourceLocation("village/common/cats"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/common/animals/cat_black"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_british"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_calico"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_persian"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_ragdoll"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_red"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_siamese"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_tabby"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_white"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/cat_jellie"), 1),
								Pair.of(EmptyJigsawPiece.INSTANCE, 3)),
						JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(
				new JigsawPattern(new ResourceLocation("village/common/butcher_animals"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/common/animals/cows_1"), 3),
								new Pair(new SingleJigsawPiece("village/common/animals/pigs_1"), 3),
								new Pair(new SingleJigsawPiece("village/common/animals/sheep_1"), 1),
								new Pair(new SingleJigsawPiece("village/common/animals/sheep_2"), 1)),
						JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(
				new JigsawPattern(new ResourceLocation("village/common/iron_golem"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/common/iron_golem"), 1)),
						JigsawPattern.PlacementBehaviour.RIGID));
		JigsawManager.REGISTRY.register(
				new JigsawPattern(new ResourceLocation("village/common/well_bottoms"), new ResourceLocation("empty"),
						ImmutableList.of(new Pair(new SingleJigsawPiece("village/common/well_bottom"), 1)),
						JigsawPattern.PlacementBehaviour.RIGID));
		*/
	}
}