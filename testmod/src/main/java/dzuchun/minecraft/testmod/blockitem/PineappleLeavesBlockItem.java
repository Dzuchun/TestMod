package dzuchun.minecraft.testmod.blockitem;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class PineappleLeavesBlockItem extends BlockItem {

	public PineappleLeavesBlockItem() {
		super(new PineappleLeavesBlock(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
	}

}

class PineappleLeavesBlock extends LeavesBlock {

	PineappleLeavesBlock() {
		super(Block.Properties.create(Material.LEAVES, MaterialColor.LIME).hardnessAndResistance(0.2f, 0.0f)
				.harvestTool(ToolType.AXE).notSolid().sound(SoundType.PLANT).lightValue(3));
	}
}
