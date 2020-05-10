package dzuchun.minecraft.testmod.init;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = TestMod.MODID, bus = Bus.FORGE)
public class ForgeEventBusSubscriber {
	
	@SubscribeEvent
	public static void registerDimensions(final RegisterDimensionsEvent e){
		if (DimensionType.byName(TestMod.TEST_DIMENSION_TYPE_LOCATION) == null)
		{
			DimensionManager.registerDimension(TestMod.TEST_DIMENSION_TYPE_LOCATION, TestMod.TEST_DIMENSION_TYPE_REGISTRY_OBJECT.get(), null, true);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onPlayerRender(final RenderPlayerEvent.Pre event) {
//		WingsRenderer.handleRenderEvent(event);
	}
}
