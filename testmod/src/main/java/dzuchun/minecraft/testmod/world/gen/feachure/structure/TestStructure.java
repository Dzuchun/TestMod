package dzuchun.minecraft.testmod.world.gen.feachure.structure;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class TestStructure extends ScatteredStructure<NoFeatureConfig>{

	private static final int seedModifier = 2521523;
	private static final Random random = new Random(seedModifier); 
	
	public TestStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	@Override
	protected int getSeedModifier() {
		return seedModifier;
	}

	@Override
	public IStartFactory getStartFactory() {
		// TODO Auto-generated method stub
		return TestStructure.Start::new;
	}

	@Override
	public String getStructureName() {
		// TODO Auto-generated method stub
		return TestMod.TEST_STRUCTURE_PIECE_LOCATION.toString();
	}

	@Override
	public int getSize() {
		return 1;
	}
	
	public class Start extends StructureStart {

		public Start(Structure<?> structIn, int int_1, int int_2, MutableBoundingBox mutableBB, int int_3, long long_1) {
			super(structIn, int_1, int_2, mutableBB, int_3, long_1);
		}

		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
			int worldX = chunkX * 16;
			int worldZ = chunkZ * 16;
			BlockPos blockpos = new BlockPos(worldX + random.nextInt(15), 0, worldZ + random.nextInt(15));
			this.components.add(new TestStructurePiece(templateManagerIn, TestMod.TEST_STRUCTURE_PIECE_LOCATION, blockpos, Rotation.randomRotation(random), 1));
			this.recalculateStructureSize();
		}
		
	}
}
