package dzuchun.minecraft.testmod.world.dimension;

import java.util.Random;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

public class TestBiomeProvider extends BiomeProvider {
	
	private static final Set<Biome> biomeList = ImmutableSet.of(
			TestMod.TEST_BIOME_REGISTRY_OBJECT.get()
			);

	private final Random rand = new Random();
	
	public TestBiomeProvider() {
		super(biomeList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		return biomeList.iterator().next();
	}

}
