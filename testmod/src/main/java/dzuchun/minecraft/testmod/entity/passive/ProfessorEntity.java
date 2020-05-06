package dzuchun.minecraft.testmod.entity.passive;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProfessorEntity extends SheepEntity{

	public ProfessorEntity(EntityType<? extends SheepEntity> type, World worldIn) {
		super(type, worldIn);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack)
	{
		return stack.getItem() == TestMod.SHAWURMA.asItem();
	}

}