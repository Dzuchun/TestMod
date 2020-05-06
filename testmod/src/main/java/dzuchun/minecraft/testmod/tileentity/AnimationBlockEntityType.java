package dzuchun.minecraft.testmod.tileentity;

import java.util.Set;
import java.util.function.Supplier;

import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

public class AnimationBlockEntityType extends TileEntityType<AnimationBlockEntity>{

	public AnimationBlockEntityType(Supplier<? extends AnimationBlockEntity> factoryIn, Set<Block> validBlocksIn,
			Type<?> dataFixerType) {
		super(factoryIn, validBlocksIn, dataFixerType);
		// TODO Auto-generated constructor stub
	}

}
