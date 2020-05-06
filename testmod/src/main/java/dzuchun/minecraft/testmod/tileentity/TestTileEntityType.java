package dzuchun.minecraft.testmod.tileentity;

import java.util.Set;
import java.util.function.Supplier;

import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

public class TestTileEntityType extends TileEntityType<TestTileEntity>{

	public TestTileEntityType(Supplier<? extends TestTileEntity> factoryIn, Set<Block> validBlocksIn,
			Type<?> dataFixerType) {
		super(factoryIn, validBlocksIn, dataFixerType);
		// TODO Auto-generated constructor stub
	}
}
