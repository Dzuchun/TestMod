package dzuchun.minecraft.testmod.item;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TestItem extends ArmorItem {

	public static final IArmorMaterial MATERIAL = new IArmorMaterial(){

		@Override
		public int getDurability(EquipmentSlotType slotIn) {
			// TODO Auto-generated method stub
			return 100;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public int getEnchantability() {
			// TODO Auto-generated method stub
			return 200;
		}

		@Override
		public SoundEvent getSoundEvent() {
			// TODO Auto-generated method stub
			return SoundEvents.AMBIENT_UNDERWATER_LOOP;
		}

		@Override
		public Ingredient getRepairMaterial() {
			// TODO Auto-generated method stub
			return Ingredient.fromItems(() -> TestMod.TEST_BLOCK_BLOCK_ITEM);
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public String getName() {
			// TODO Auto-generated method stub
			return TestMod.MODID + ":test_chestplate";
		}

		@Override
		public float getToughness() {
			// TODO Auto-generated method stub
			return 30.0F;
		}
		
	};
	public static final EquipmentSlotType SLOT = EquipmentSlotType.CHEST;
	public static final Properties PROPERTIES = new Properties()
			.group(TestMod.TEST_ITEM_GROUP)
			.defaultMaxDamage(100)
			.rarity(Rarity.EPIC);
	
	public TestItem() 
	{
		super(MATERIAL, SLOT, PROPERTIES);
	}

}
