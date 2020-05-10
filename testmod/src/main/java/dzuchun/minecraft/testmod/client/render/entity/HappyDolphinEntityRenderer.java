package dzuchun.minecraft.testmod.client.render.entity;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.client.render.entity.model.HappyDolphinModel;
import dzuchun.minecraft.testmod.entity.passive.HappyDolphinEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.util.ResourceLocation;

public class HappyDolphinEntityRenderer extends MobRenderer<HappyDolphinEntity, DolphinModel<HappyDolphinEntity>> {

	private static final ResourceLocation PROFESSOR_TEXTURE = TestMod.HAPPY_DOLPHIN_TEXTURE;

	public HappyDolphinEntityRenderer(final EntityRendererManager manager) {
		super(manager, new HappyDolphinModel<>(), 0.7F);
	}

	@Override
	public ResourceLocation getEntityTexture(final HappyDolphinEntity entity) {
		return PROFESSOR_TEXTURE;
	}

}

