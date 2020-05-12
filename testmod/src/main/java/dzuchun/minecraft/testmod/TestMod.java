package dzuchun.minecraft.testmod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dzuchun.minecraft.testmod.biome.TestBiome;
import dzuchun.minecraft.testmod.block.AnimationBlock;
import dzuchun.minecraft.testmod.block.TestBlock;
import dzuchun.minecraft.testmod.block.tree.PineappleTreeFeature;
import dzuchun.minecraft.testmod.blockitem.PineappleBlockItem;
import dzuchun.minecraft.testmod.blockitem.PineappleLeavesBlockItem;
import dzuchun.minecraft.testmod.blockitem.PineappleLogBlockItem;
import dzuchun.minecraft.testmod.blockitem.ShawurmaBlockItem;
import dzuchun.minecraft.testmod.client.gui.TestScreen;
import dzuchun.minecraft.testmod.client.input.KeyEvents;
import dzuchun.minecraft.testmod.client.render.entity.BarkingBuckerRenderer;
import dzuchun.minecraft.testmod.client.render.entity.HappyDolphinEntityRenderer;
import dzuchun.minecraft.testmod.client.render.entity.ProfessorRenderer;
import dzuchun.minecraft.testmod.client.render.tileentity.AnimationBlockEntityRenderer;
import dzuchun.minecraft.testmod.config.ConfigHolder;
import dzuchun.minecraft.testmod.container.TestContainer;
import dzuchun.minecraft.testmod.entity.passive.BarkingBucketEntity;
import dzuchun.minecraft.testmod.entity.passive.HappyDolphinEntity;
import dzuchun.minecraft.testmod.entity.passive.ProfessorEntity;
import dzuchun.minecraft.testmod.item.TestItem;
import dzuchun.minecraft.testmod.net.TestModPacketHandler;
import dzuchun.minecraft.testmod.server.SmashLogic;
import dzuchun.minecraft.testmod.tileentity.AnimationBlockEntity;
import dzuchun.minecraft.testmod.tileentity.TestTileEntity;
import dzuchun.minecraft.testmod.wings.capability.CapabilityWings;
import dzuchun.minecraft.testmod.wings.capability.WingsProvider;
import dzuchun.minecraft.testmod.world.dimension.TestModDimension;
import dzuchun.minecraft.testmod.world.gen.feachure.structure.TestStructure;
import dzuchun.minecraft.testmod.world.gen.feachure.structure.TestStructurePiece;
import dzuchun.minecraft.testmod.world.gen.feachure.village.TestVillagePools;
import dzuchun.minecraft.testmod.worldtype.TestWorldType;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings("deprecation")
@Mod(TestMod.MODID)
@Mod.EventBusSubscriber(modid = TestMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestMod {
	public static final String MODID = "testmod";
	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public static ItemGroup TEST_ITEM_GROUP = new ItemGroup(MODID) {

		@Override
		public ItemStack createIcon() {
			return new ItemStack(TEST_BLOCK_BLOCK_ITEM);
		}

	};

	public static final ResourceLocation TEST_DIMENSION_TYPE_LOCATION = new ResourceLocation(MODID, "test_dim");

	public static ResourceLocation ANIMATION_SPRITE = new ResourceLocation(MODID, "block/animation_block_render");

	public static ResourceLocation BUCKET_FRONT = new ResourceLocation(MODID, "block/barking_bucket_front");
	public static ResourceLocation BUCKET_SIDE = new ResourceLocation(MODID, "block/barking_bucket_side");
	public static ResourceLocation BUCKET_BOTTOM = new ResourceLocation(MODID, "block/barking_bucket_bottom");
	public static ResourceLocation BUCKET_TEARS = new ResourceLocation(MODID, "block/barking_bucket_tears");

	public static ResourceLocation MANA_BAR = new ResourceLocation(MODID, "textures/gui/ingame/energy_bar.png");

	public static ResourceLocation PROFESSOR_TEXTURE = new ResourceLocation(MODID,
			"textures/entity/professor_entity.png");

	public static ResourceLocation HAPPY_DOLPHIN_TEXTURE = new ResourceLocation(MODID,
			"textures/entity/happy_dolphin.png");

	public static Block TEST_BLOCK;

	public static BlockItem PINEAPPLE_LOG = new PineappleLogBlockItem();
	public static BlockItem PINEAPPLE_LEAVES = new PineappleLeavesBlockItem();
	public static BlockItem PINEAPPLE_SAPLING = new PineappleBlockItem();

	public static TreeFeature PINEAPPLE_TREE_FEATURE = new PineappleTreeFeature();

	public static RegistryObject<EntityType<ProfessorEntity>> PROFESSOR_ENTITY_TYPE_REGISTRY_OBJECT;
	public static RegistryObject<EntityType<BarkingBucketEntity>> BARKING_BUCKET_ENTITY_TYPE_REGISTRY_OBJECT;
	public static RegistryObject<EntityType<HappyDolphinEntity>> HAPPY_DOLPHIN_ENTITY_TYPE_REGISTRY_OBJECT;

	public static RegistryObject<ModDimension> TEST_DIMENSION_TYPE_REGISTRY_OBJECT;
	public static BlockItem TEST_BLOCK_BLOCK_ITEM;
	public static WorldType TEST_WORLD_TYPE = new TestWorldType();
	public static ArmorItem TEST_CHESTPLATE = new TestItem();
	public static AnimationBlock ANIMATION_BLOCK = new AnimationBlock();
	public static BlockItem SHAWURMA;

	public static EntityType<BarkingBucketEntity> BARKING_BUCKET_ENTITY_TYPE = EntityType.Builder
			.<BarkingBucketEntity>create(BarkingBucketEntity::new, EntityClassification.CREATURE)
			.size(EntityType.WOLF.getWidth(), EntityType.WOLF.getHeight())
			.build(new ResourceLocation(MODID, "barking_bucket_entity").toString());

	public static EntityType<HappyDolphinEntity> HAPPY_DOLPHIN_ENTITY_TYPE = EntityType.Builder
			.<HappyDolphinEntity>create(HappyDolphinEntity::new, EntityClassification.WATER_CREATURE)
			.size(EntityType.DOLPHIN.getWidth(), EntityType.DOLPHIN.getHeight())
			.build(new ResourceLocation(MODID, "happy_dolphin_entity").toString());

	public static Item.Properties SHAWURMA_PROPERTIES = new Item.Properties()
			.food(new Food.Builder().hunger(10).saturation(5).fastToEat().build()).group(TEST_ITEM_GROUP);

	public static RegistryObject<TileEntityType<AnimationBlockEntity>> ANIMATION_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT;
	public static RegistryObject<TileEntityType<TestTileEntity>> TEST_TILE_ENTITY_REGISTRY_OBJECT;
	public static RegistryObject<ContainerType<TestContainer>> TEST_CONTAINER_TYPE_REGISTRY_OBJECT;

	public static RegistryObject<Structure<NoFeatureConfig>> TEST_STRUCTURE_REGISTRY_OBJECT;
	public static RegistryObject<TreeFeature> PINEAPPLE_TREE_REGISTRY_OBJECT;

	public static ResourceLocation TEST_STRUCTURE_PIECE_LOCATION = new ResourceLocation(MODID, "test_structure");
	public static IStructurePieceType TEST_STRUCTURE_PIECE;

	public static RegistryObject<Biome> TEST_BIOME_REGISTRY_OBJECT;

	public TestMod() {

		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		// Registering config
		final ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC, "testmod-client.toml");
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC, "testmod-server.toml");

		// Creating BlockItem
		TEST_BLOCK = new TestBlock();
		TEST_BLOCK_BLOCK_ITEM = new BlockItem(TEST_BLOCK, new Item.Properties().group(TEST_ITEM_GROUP));

		// Creating shawurma block item
		SHAWURMA = new ShawurmaBlockItem();

		// Creating block register
		final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MODID);
		// Adding block to register
		@SuppressWarnings("unused")
		final RegistryObject<Block> TEST_BLOCK_REGISTRY_OBJECT = BLOCKS.register("test_block",
				() -> TEST_BLOCK_BLOCK_ITEM.getBlock());
		@SuppressWarnings("unused")
		final RegistryObject<Block> SHAWURMA_BLOCK = BLOCKS.register("shawurma", () -> SHAWURMA.getBlock());
		@SuppressWarnings("unused")
		final RegistryObject<Block> ANIMATION_BLOCK = BLOCKS.register("animation_block", () -> TestMod.ANIMATION_BLOCK);
		BLOCKS.register("pineapple_sapling", () -> PINEAPPLE_SAPLING.getBlock());
		BLOCKS.register("pineapple_log", () -> PINEAPPLE_LOG.getBlock());
		BLOCKS.register("pineapple_leaves", () -> PINEAPPLE_LEAVES.getBlock());

		// Creating item register
		final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MODID);
		// Adding item to register
		@SuppressWarnings("unused")
		final RegistryObject<Item> TEST_BLOCK_ITEM_REGISTRY_OBJECT = ITEMS.register("test_block",
				() -> TEST_BLOCK_BLOCK_ITEM.asItem());
		@SuppressWarnings("unused")
		final RegistryObject<Item> TEST_CHESTPLATE_REGISTRY_OBJECT = ITEMS.register("test_chestplate",
				() -> TEST_CHESTPLATE);
		@SuppressWarnings("unused")
		final RegistryObject<Item> TEST_SHAWURMA_REGISTRY_OBJECT = ITEMS.register("shawurma", () -> SHAWURMA);
		ITEMS.register("pineapple_sapling", () -> PINEAPPLE_SAPLING);
		ITEMS.register("pineapple_leaves", () -> PINEAPPLE_LEAVES);
		ITEMS.register("pineapple_log", () -> PINEAPPLE_LOG);

		// Creating TE register
		final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES,
				MODID);
		// Adding TE to register
		LOGGER.info("Adding TE to register");
		ANIMATION_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT = TILE_ENTITIES.register("animation_block",
				() -> TileEntityType.Builder.create(AnimationBlockEntity::new, new Block[] { TestMod.ANIMATION_BLOCK })
						.build(null));
		TEST_TILE_ENTITY_REGISTRY_OBJECT = TILE_ENTITIES.register("test_block", () -> TileEntityType.Builder
				.create(TestTileEntity::new, new Block[] { TestMod.TEST_BLOCK }).build(null));

		// Creating container type registry
		final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS,
				TestMod.MODID);
		// Adding contaners to registry
		TEST_CONTAINER_TYPE_REGISTRY_OBJECT = CONTAINER_TYPES.register("test_container",
				() -> IForgeContainerType.create(TestContainer::new));

		// Creating entity type registry
		final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<EntityType<?>>(
				ForgeRegistries.ENTITIES, MODID);
		// Adding entities to registry
		PROFESSOR_ENTITY_TYPE_REGISTRY_OBJECT = ENTITY_TYPES.register("professor_entity",
				() -> EntityType.Builder.<ProfessorEntity>create(ProfessorEntity::new, EntityClassification.CREATURE)
						.size(EntityType.PIG.getWidth(), EntityType.PIG.getHeight())
						.build(new ResourceLocation(MODID, "professor_entity").toString()));

		BARKING_BUCKET_ENTITY_TYPE_REGISTRY_OBJECT = ENTITY_TYPES.register("barking_bucket_entity",
				() -> BARKING_BUCKET_ENTITY_TYPE);

		HAPPY_DOLPHIN_ENTITY_TYPE_REGISTRY_OBJECT = ENTITY_TYPES.register("happy_dolphin_entity",
				() -> HAPPY_DOLPHIN_ENTITY_TYPE);
//    	ForgeRegistries.ENTITIES.register(BARKING_BUCKET_ENTITY_TYPE);

		// Creating feature register
		final DeferredRegister<Feature<? extends IFeatureConfig>> FEATURES = new DeferredRegister<Feature<? extends IFeatureConfig>>(
				ForgeRegistries.FEATURES, MODID);
		// Adding features to register
		TEST_STRUCTURE_REGISTRY_OBJECT = FEATURES.register("test_structure",
				() -> new TestStructure(NoFeatureConfig::deserialize));
		PINEAPPLE_TREE_REGISTRY_OBJECT = FEATURES.register("pineapple_tree", () -> new PineappleTreeFeature());
//    	ForgeRegistries.FEATURES.register(PINEAPPLE_TREE_FEATURE.setRegistryName(MODID, "pineapple_tree"));

		// Adding structure piece
		TEST_STRUCTURE_PIECE = Registry.register(Registry.STRUCTURE_PIECE, TEST_STRUCTURE_PIECE_LOCATION,
				TestStructurePiece::new);

		// Creating Biome register
		final DeferredRegister<Biome> BIOMES = new DeferredRegister<Biome>(ForgeRegistries.BIOMES, MODID);
		// Adding biomes to registry
		TEST_BIOME_REGISTRY_OBJECT = BIOMES.register("test_biome", () -> new TestBiome());

		// Creating dimension register
		final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<ModDimension>(
				ForgeRegistries.MOD_DIMENSIONS, MODID);
		// Adding dimensions to register
		TEST_DIMENSION_TYPE_REGISTRY_OBJECT = DIMENSIONS.register("test_dim", () -> new TestModDimension());

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		LOGGER.info("Trying to register structures");
		FEATURES.register(modEventBus);
		LOGGER.info("Trying to register blocks");
		BLOCKS.register(modEventBus);
		LOGGER.info("Trying to register items");
		ITEMS.register(modEventBus);
		LOGGER.info("Trying to register tile entities");
		TILE_ENTITIES.register(modEventBus);
		LOGGER.info("Trying to register entity types");
		ENTITY_TYPES.register(modEventBus);
		LOGGER.info("Trying to register container types");
		CONTAINER_TYPES.register(modEventBus);
		LOGGER.info("Trying to register biomes");
		BIOMES.register(modEventBus);
		LOGGER.info("Trying to register dimensions");
		DIMENSIONS.register(modEventBus);
		

//		Minecraft.getInstance().player.world.getPlayerByUuid(Minecraft.getInstance().player.getGameProfile().getId()).getCapability(null);
	}

	@SubscribeEvent
	public static void clientSetup(final FMLClientSetupEvent event) {
		// Binding special renderer
		LOGGER.info("Binding special renderer");
		AnimationBlockEntityRenderer.register();

		LOGGER.debug("Registering Entity Renderers");
		RenderingRegistry.registerEntityRenderingHandler(PROFESSOR_ENTITY_TYPE_REGISTRY_OBJECT.get(),
				ProfessorRenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(BARKING_BUCKET_ENTITY_TYPE_REGISTRY_OBJECT.get(),
				BarkingBuckerRenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(HAPPY_DOLPHIN_ENTITY_TYPE_REGISTRY_OBJECT.get(),
				HappyDolphinEntityRenderer::new);

		LOGGER.info("Registering screen");
		ScreenManager.registerFactory(TEST_CONTAINER_TYPE_REGISTRY_OBJECT.get(), TestScreen::new);

		KeyEvents.init();
	}

	@SubscribeEvent
	public static void commonSetup(final FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(() -> setupWorldGen());

		TestModPacketHandler.init();
		
		//Registering capability
		CapabilityWings.register();
		WingsProvider.init();
	}

	@SubscribeEvent
	public static void serverStartup(FMLServerStartingEvent event) {
		SmashLogic.init();
	}

	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		LOGGER.info("Adding sprite to atlas");
		if (event.getMap().getTextureLocation().equals(PlayerContainer.LOCATION_BLOCKS_TEXTURE)) {

			event.addSprite(ANIMATION_SPRITE);

			event.addSprite(BUCKET_BOTTOM);
			event.addSprite(BUCKET_FRONT);
			event.addSprite(BUCKET_SIDE);
			event.addSprite(BUCKET_TEARS);
			
			
			LOGGER.info("Adding manabar sprite");
			event.addSprite(MANA_BAR);

		}

		if (event.getMap().getTextureLocation().equals(AbstractGui.GUI_ICONS_LOCATION)) {
		}
	}

//    public void registerBlocks(final RegistryEvent.Register<Block> blockRegistryEvent) {
//        // register a new block here
//        LOGGER.info("HELLO from Register Event");
//        LOGGER.info("Current registry - " + blockRegistryEvent.getRegistry().getRegistryName());
//        if (blockRegistryEvent.getRegistry().getRegistryName().toString().equals("minecraft:block"))
//        {
//	        Block.Properties testBlockProperties = Block.Properties.create(Material.BAMBOO);
//	        testBlockProperties.hardnessAndResistance(10.0F, 1.0F);
//	        testBlockProperties.speedFactor(2.0F);
//	        Block testBlock = new Block(testBlockProperties);
//	        testBlock.setRegistryName(new ResourceLocation("testmod", "test_block"));
//	        LOGGER.info("Trying to register a block");
//	        blockRegistryEvent.getRegistry().register(testBlock);
//	        LOGGER.info("Registered block");
//        }
//        else
//        {
//        	LOGGER.warn("FORGE, PLEASE, GO FUCK YOURSELF");
//        }
//    }

	public static void setupWorldGen() {

		TestVillagePools.init();

		// Add Structures
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (biome == TEST_BIOME_REGISTRY_OBJECT.get()) {
				addSurfaceStructure(biome, TEST_STRUCTURE_REGISTRY_OBJECT.get());
			}

//            // Use categories to allow compatibility with biome mods such as Biomes O' Plenty.
//            if (biome.getCategory() == Biome.Category.PLAINS || biome.getCategory() == Biome.Category.FOREST) {
//                if (biome.getTempCategory() == Biome.TempCategory.MEDIUM && biome.getPrecipitation() == Biome.RainType.RAIN) {
//                	
//                    addSurfaceStructure(biome, TEST_STRUCTURE_REGISTRY_OBJECT.get());
//                    
//                    biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, PineappleTreeFeature.getConfiguredFeature()
//            				.withPlacement(
//    						Placement.COUNT_EXTRA_HEIGHTMAP
//    						.configure(new AtSurfaceWithExtraConfig(0, 0.04F, 5))));
//                }
//            }
//            if (biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
//                //addUndergroundStructure(biome, ModStructures.SMALL_DUNGEON.get());
//            }
		}
	}

	/**
	 * Add a structure to the given biome.
	 * 
	 * @param biome     The biome to add a structure to.
	 * @param structure The structure to add.
	 * @author Valhensia
	 */
	private static void addSurfaceStructure(Biome biome, Structure<NoFeatureConfig> structure) {
		biome.addStructure(structure.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
				structure.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
						.withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	}

	/**
	 * 
	 * @author TehNut
	 *
	 */
//    private static class TestModInfo extends ModInfo {
//        public TestModInfo(ModInfo modInfo) {
//        	
//            super(modInfo.getOwningFile(), modInfo.getModConfig());
//        }
//
//        @Override
//        public boolean hasConfigUI() {
//            return true;
//        }
//    }
}
