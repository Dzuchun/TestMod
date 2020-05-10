package dzuchun.minecraft.testmod.block;

import dzuchun.minecraft.testmod.tileentity.TestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.fml.network.NetworkHooks;

public class TestBlock extends Block implements IForgeBlock
{

	public TestBlock() 
	{
		super(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ActionResultType onBlockActivated (BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (!worldIn.isRemote)
		{
			if (!Screen.hasShiftDown())
			{
				final TileEntity tileEntity = worldIn.getTileEntity(pos);
				if (tileEntity instanceof TestTileEntity) {
					NetworkHooks.openGui((ServerPlayerEntity) player, (TestTileEntity) tileEntity, pos);
				}
			}
			if (player.getHeldItem(Hand.MAIN_HAND).equals(ItemStack.EMPTY, false))
			{
				player.sendMessage(new StringTextComponent("Is sky light substracted " + worldIn.getSkylightSubtracted()));
				player.sendStatusMessage(new StringTextComponent("This is overlay message"), true);
			}
			
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		//TODO
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TestTileEntity();
	}

   @SuppressWarnings("deprecation")
public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.getBlock() != newState.getBlock()) {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if (tileentity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
         }

         super.onReplaced(state, worldIn, pos, newState, isMoving);
      }
   }
}
