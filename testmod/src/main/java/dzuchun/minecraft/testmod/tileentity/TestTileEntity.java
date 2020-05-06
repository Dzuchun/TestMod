package dzuchun.minecraft.testmod.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.container.TestContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TestTileEntity extends TileEntity implements INamedContainerProvider{
	
	private static final String INVENTORY_TAG = "inventory";

	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<IItemHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
	public final ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		public void onContentsChanged (final int slot) {
			super.onContentsChanged(slot);
			
			TestTileEntity.this.markDirty();
		}
	};
	
	public TestTileEntity() {
		super(TestMod.TEST_TILE_ENTITY_REGISTRY_OBJECT.get());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
		return new TestContainer(windowId, inventory, this);
	}
	
	/**
	 * Read saved data from disk into the tile.
	 */
	@Override
	public void read(final CompoundNBT compound) {
		super.read(compound);
		this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put(INVENTORY_TAG,  (this.inventory).serializeNBT());
		return compound;
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return new TranslationTextComponent(TestMod.TEST_BLOCK.getTranslationKey());
	}
	
	/**
	 * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the
	 * chunk or when many blocks change at once.
	 * This compound comes back to you client-side in {@link #handleUpdateTag}
	 * The default implementation ({@link TileEntity#handleUpdateTag}) calls {@link #writeInternal)}
	 * which doesn't save any of our extra data so we override it to call {@link #write} instead
	 */
	@Nonnull
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return inventoryCapabilityExternal.cast();
		return super.getCapability(cap, side);
	}

}
