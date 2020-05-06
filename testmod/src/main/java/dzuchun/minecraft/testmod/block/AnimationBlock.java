package dzuchun.minecraft.testmod.block;

import dzuchun.minecraft.testmod.tileentity.AnimationBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class AnimationBlock extends Block{

	private static final VoxelShape RENDER_SHAPE = VoxelShapes.empty();
	
	public AnimationBlock() {
		super(Block.Properties.create(Material.ANVIL));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public AnimationBlockEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new AnimationBlockEntity();
	}
	
	@Override
	public VoxelShape getRenderShape (BlockState state, IBlockReader wolrdIn, BlockPos pos)
	{
		return RENDER_SHAPE;
	}
	
	@Override
	public BlockRenderType getRenderType (BlockState state)
	{
		return BlockRenderType.INVISIBLE;
	}
}
