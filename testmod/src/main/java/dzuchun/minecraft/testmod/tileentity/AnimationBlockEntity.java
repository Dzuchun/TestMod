package dzuchun.minecraft.testmod.tileentity;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnimationBlockEntity extends TileEntity {

	public AnimationBlockEntity() {
		super(TestMod.ANIMATION_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT.get());
		// TODO Auto-generated constructor stub
	}


}
