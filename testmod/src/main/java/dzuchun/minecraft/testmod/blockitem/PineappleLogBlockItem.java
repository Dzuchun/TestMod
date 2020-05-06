package dzuchun.minecraft.testmod.blockitem;

import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;

public class PineappleLogBlockItem extends BlockItem {

	public PineappleLogBlockItem() {

		super(new PineappleLogBlock(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));
	}

}

class PineappleLogBlock extends LogBlock {

	PineappleLogBlock() {
		super(MaterialColor.WOOD, Block.Properties.create(Material.WOOD, MaterialColor.WOOD)
				.hardnessAndResistance(3.0f, 3.0f).harvestTool(ToolType.AXE).notSolid().sound(SoundType.WOOD));
	}

}
