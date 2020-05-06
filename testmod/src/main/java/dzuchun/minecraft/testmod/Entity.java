package dzuchun.minecraft.testmod;

import dzuchun.minecraft.testmod.entity.passive.BarkingBucketEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(TestMod.MODID)
public class Entity{
	public static void init() {}
    @ObjectHolder("barking_bucket_entity")
    public static final EntityType<BarkingBucketEntity> barking_bucket_entity = null;
}