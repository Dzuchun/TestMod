package dzuchun.minecraft.testmod.block.tree;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

public class PineappleTreeFeature extends TreeFeature{
	
	public PineappleTreeFeature() {
		super(TreeFeatureConfig::func_227338_a_);
		this.withConfiguration((
				new TreeFeatureConfig.Builder(
    				new SimpleBlockStateProvider(TestMod.PINEAPPLE_LOG.getBlock().getDefaultState()), 
    				new SimpleBlockStateProvider(TestMod.PINEAPPLE_LEAVES.getBlock().getDefaultState()),
    				new BlobFoliagePlacer(2, 0)
    			)
    				.baseHeight(5)
    				.heightRandA(2)
    				.foliageHeight(3)
    				.ignoreVines()
        		)
				.build()
		)
		.withPlacement(
				Placement.COUNT_EXTRA_HEIGHTMAP
				.configure(new AtSurfaceWithExtraConfig(0, 0.04F, 1)));
	}

	public static ConfiguredFeature<TreeFeatureConfig, ?> getConfiguredFeature()
	{
		return Feature.NORMAL_TREE.withConfiguration((
						new TreeFeatureConfig.Builder(
		    				new SimpleBlockStateProvider(TestMod.PINEAPPLE_LOG.getBlock().getDefaultState()), 
		    				new SimpleBlockStateProvider(TestMod.PINEAPPLE_LEAVES.getBlock().getDefaultState()),
		    				new BlobFoliagePlacer(2, 0)
		    			)
		    				.baseHeight(5)
		    				.heightRandA(2)
		    				.foliageHeight(3)
		    				.ignoreVines()
		        		)
						.build()
				);
	}

}
