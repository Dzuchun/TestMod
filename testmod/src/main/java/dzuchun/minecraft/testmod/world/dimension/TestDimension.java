package dzuchun.minecraft.testmod.world.dimension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IRenderHandler;

public class TestDimension extends Dimension {

	public TestDimension(World world, DimensionType type) {
		super(world, type, 0.0f);
		this.setCloudRenderer((int ticks, float partialTicks, ClientWorld world_, Minecraft mc) -> {
		});
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator() {
		// TODO Handle different world types here
		return new TestChunkGenerator(world, new TestBiomeProvider(), new TestGenSettings());
	}

	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		return Vec3d.ZERO;
	}

	@Override
	public boolean canRespawnHere() {
		// TODO Handle can rewpawn here config
		return true;
	}

	@Override
	public boolean doesXZShowFog(int x, int z) {
		// TODO Handle storms here
		return false;
	}

	@Override
	public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
		// TODO handle creo chamber here
		player.sendStatusMessage(new TranslationTextComponent("block.minecraft.bed.can_always_sleep_at_ludoland"), true);
		return SleepResult.ALLOW;
	}

	@Override
	public int getActualHeight() {
		return 256;
	}

	@Override
	public IRenderHandler getCloudRenderer() {
		return (int ticks, float partialTicks, ClientWorld world_, Minecraft mc) -> {
		};
	}

	/**
	 * the y level at which clouds are rendered.
	 */
	@OnlyIn(Dist.CLIENT)
	public float getCloudHeight() {
		return 0;
	}

//	@Nullable
//	@OnlyIn(Dist.CLIENT)
//	@Override
//	public net.minecraftforge.client.IRenderHandler getCloudRenderer() {
//		return (int ticks, float partialTicks, ClientWorld world, Minecraft mc) -> {};
//		return null;
//	}
}
