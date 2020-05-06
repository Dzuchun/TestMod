package dzuchun.minecraft.testmod.container;

import java.util.Objects;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.tileentity.TestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class TestContainer extends Container {

	//private final IWorldPosCallable canInteractWithCallable;
	private TestTileEntity tileEntity;
	public TestContainer(int windowId, PlayerInventory inventory, TestTileEntity testTileEntity) {
		super(TestMod.TEST_CONTAINER_TYPE_REGISTRY_OBJECT.get(), windowId);
		this.tileEntity = testTileEntity;
		//this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
	}

	private static TestTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof TestTileEntity)
			return (TestTileEntity) tileAtPos;
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}
	
	public TestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;//isWithinUsableDistance(canInteractWithCallable, playerIn, TestMod.TEST_BLOCK);
	}

}
