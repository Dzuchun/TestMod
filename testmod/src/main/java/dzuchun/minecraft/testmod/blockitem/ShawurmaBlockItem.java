package dzuchun.minecraft.testmod.blockitem;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.block.ShawurmaBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

public class ShawurmaBlockItem extends BlockItem{

    private static final Logger LOGGER = LogManager.getLogger();
	
	public ShawurmaBlockItem() {
		super(ShawurmaBlock.getExample(),
				new Item.Properties()
				.food(new Food.Builder()
						.hunger(10)
						.saturation(5)
//						.fastToEat()
						.meat()
						.build()
				)
				.group(TestMod.TEST_ITEM_GROUP)
				);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		//LOGGER.info("On item use called");
		if (context.getPlayer().canEat(false) && Screen.hasShiftDown())
		{
			return ActionResultType.PASS;
		}
		return super.onItemUse(context);
	}

}
