package dzuchun.minecraft.testmod.client.render.entity;
import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.entity.passive.ProfessorEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.util.ResourceLocation;

public class ProfessorRenderer extends MobRenderer<ProfessorEntity, PigModel<ProfessorEntity>> {

	private static final ResourceLocation PROFESSOR_TEXTURE = TestMod.PROFESSOR_TEXTURE;

	public ProfessorRenderer(final EntityRendererManager manager) {
		super(manager, new PigModel<>(), 0.7F);
	}

	@Override
	public ResourceLocation getEntityTexture(final ProfessorEntity entity) {
		return PROFESSOR_TEXTURE;
	}

}
