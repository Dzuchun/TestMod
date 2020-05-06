package dzuchun.minecraft.testmod.container;

import java.util.Objects;

import javax.annotation.Nonnull;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.tileentity.TestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.SlotItemHandler;

public class TestContainer extends Container {

	// private final IWorldPosCallable canInteractWithCallable;
	public final TestTileEntity tileEntity;

	public TestContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data));
		// this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(),
		// tileEntity.getPos());
	}

	private static TestTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
		Objects.requireNonNull(data, "data cannot be null!");
		final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
		if (tileAtPos instanceof TestTileEntity)
			return (TestTileEntity) tileAtPos;
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public TestContainer(int windowId, PlayerInventory inventory, TestTileEntity testTileEntity) {
		super(TestMod.TEST_CONTAINER_TYPE_REGISTRY_OBJECT.get(), windowId);


		this.tileEntity = testTileEntity;
		
		// Tile inventory slot(s)
		this.addSlot(new SlotItemHandler(tileEntity.inventory, TestTileEntity.FUNC_SLOT, 80, 35));

		final int playerInventoryStartX = 8;
		final int playerInventoryStartY = 84;
		final int slotSizePlus2 = 18; // slots are 16x16, plus 2 (for spacing/borders) is 18x18

		// Player Top Inventory slots
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(inventory, 9 + (row * 9) + column,
						playerInventoryStartX + (column * slotSizePlus2),
						playerInventoryStartY + (row * slotSizePlus2)));
			}
		}

		final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
		// Player Hotbar slots
		for (int column = 0; column < 9; ++column) {
			this.addSlot(
					new Slot(inventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
		}
	}

	/**
	 * Generic & dynamic version of
	 * {@link Container#transferStackInSlot(PlayerEntity, int)}. Handle when the
	 * stack in slot {@code index} is shift-clicked. Normally this moves the stack
	 * between the player inventory and the other inventory(s).
	 *
	 * @param player the player passed in
	 * @param index  the index passed in
	 * @return the {@link ItemStack}
	 */
	@Nonnull
	@Override
	public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			final ItemStack slotStack = slot.getStack();
			returnStack = slotStack.copy();

			final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
			if (index < containerSlots) {
				if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (slotStack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (slotStack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotStack);
		}
		return returnStack;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
		// return isWithinUsableDistance(canInteractWithCallable, playerIn,
		// TestMod.TEST_BLOCK);
	}

}
