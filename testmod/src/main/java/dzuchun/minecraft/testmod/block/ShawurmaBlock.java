package dzuchun.minecraft.testmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ShawurmaBlock extends net.minecraft.block.CakeBlock {
	
	protected ShawurmaBlock(Properties properties) {
		super(properties);
	}
	
	public static ShawurmaBlock getExample() {
		return new ShawurmaBlock(Block.Properties.create(Material.CAKE).hardnessAndResistance(1.0F, 5.0F).sound(SoundType.WET_GRASS));
	}

}