package dzuchun.minecraft.testmod.client.render.entity.model;

import net.minecraft.client.renderer.entity.model.DolphinModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class HappyDolphinModel<E extends Entity> extends DolphinModel<E>{
	
	private ModelRenderer horn;
	
	public HappyDolphinModel() {
		super();
		horn = new ModelRenderer(this, 28, 0);
		horn.addBox(-0.5f, -2, 0, 1.0f, 12, 1);
		horn.setRotationPoint(0, -2, 0);
		horn.rotateAngleX = (float)(-Math.PI*0.75);
		
		ModelRenderer base = this.getParts().iterator().next();
		base.addChild(horn);
	}
}