package dzuchun.minecraft.testmod.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.entity.passive.BarkingBucketEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.ResourceLocation;

public class BarkingBuckerRenderer extends EntityRenderer<BarkingBucketEntity>{
	private static final float bottomRadius = 0.3f;
	private static final float topRadius = 0.4f;
	private static final float bottomOffset = 0.05f;
	private static final float topOffset = 0.7f;
	private static final float tearsOffset = 0.5f;
	private static final float tearsRadius = bottomRadius + (tearsOffset-bottomOffset)/(topOffset-bottomOffset)*(topRadius-bottomRadius);
	private static final float sideTurnAngle = (float) (-Math.atan((topRadius-bottomRadius)/(topOffset-bottomOffset)) - Math.PI/2);
	private static final float sideLength = (float) (Math.sqrt((topOffset-bottomOffset)*(topOffset-bottomOffset) + (topRadius-bottomRadius)*(topRadius-bottomRadius)));
	
	public BarkingBuckerRenderer(final EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getEntityTexture(BarkingBucketEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void render(BarkingBucketEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)	{
		TextureAtlasSprite spriteFront = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(TestMod.BUCKET_FRONT);
		TextureAtlasSprite spriteSide = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(TestMod.BUCKET_SIDE);
		TextureAtlasSprite spriteBottom = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(TestMod.BUCKET_BOTTOM);
		TextureAtlasSprite spriteTears = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(TestMod.BUCKET_TEARS);
		
		IVertexBuilder renderer = bufferIn.getBuffer(RenderType.getTranslucent());
		
		matrixStackIn.push();
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, -1, 0), entityIn.rotationYawHead + 90, true));
			
			matrixStackIn.push();
			//Drawing bottom
			matrixStackIn.translate(0, bottomOffset, 0);
			addQuad(renderer, matrixStackIn, bottomRadius, -bottomRadius, -bottomRadius, bottomRadius, spriteBottom, 1.0f);
			addQuad(renderer, matrixStackIn, bottomRadius, bottomRadius, -bottomRadius, -bottomRadius, spriteBottom, 1.0f);
			matrixStackIn.pop();
	
			matrixStackIn.push();
			//Drawing tears
			matrixStackIn.translate(0, tearsOffset, 0);
			addQuad(renderer, matrixStackIn, -tearsRadius, tearsRadius, tearsRadius, -tearsRadius, spriteTears, 0.5f);
			//addQuad(renderer, matrixStackIn, tearsRadius, tearsRadius, -tearsRadius, -tearsRadius, spriteTears, 0.5f);
			matrixStackIn.pop();

			matrixStackIn.push();
			//Drawing front
			matrixStackIn.translate(topRadius, topOffset, 0);
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, 0, 1), sideTurnAngle, false));
				matrixStackIn.push();
				addQuad(renderer, matrixStackIn, 
						0, -topRadius, 
						0, topRadius,
						sideLength, bottomRadius, 
						sideLength, -bottomRadius,
						spriteFront, 1.0f);
				addQuad(renderer, matrixStackIn,
						sideLength, -bottomRadius, 
						sideLength, bottomRadius, 
						0, topRadius, 
						0, -topRadius,
						spriteSide, 1.0f);
				matrixStackIn.pop();
			matrixStackIn.pop();
			
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, -1, 0), 90, true));
			
			matrixStackIn.push();
			//Drawing left
			matrixStackIn.translate(topRadius, topOffset, 0);
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, 0, 1), sideTurnAngle, false));
				matrixStackIn.push();
				addQuad(renderer, matrixStackIn, 
						0, -topRadius, 
						0, topRadius,
						sideLength, bottomRadius, 
						sideLength, -bottomRadius,
						spriteSide, 1.0f);
				addQuad(renderer, matrixStackIn,
						sideLength, -bottomRadius, 
						sideLength, bottomRadius, 
						0, topRadius, 
						0, -topRadius,
						spriteSide, 1.0f);
				matrixStackIn.pop();
			matrixStackIn.pop();
			
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, -1, 0), 90, true));

			matrixStackIn.push();
			//Drawing back
			matrixStackIn.translate(topRadius, topOffset, 0);
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, 0, 1), sideTurnAngle, false));
				matrixStackIn.push();
				addQuad(renderer, matrixStackIn, 
						0, -topRadius, 
						0, topRadius,
						sideLength, bottomRadius, 
						sideLength, -bottomRadius,
						spriteSide, 1.0f);
				addQuad(renderer, matrixStackIn,
						sideLength, -bottomRadius, 
						sideLength, bottomRadius, 
						0, topRadius, 
						0, -topRadius,
						spriteSide, 1.0f);
				matrixStackIn.pop();
			matrixStackIn.pop();
			
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, -1, 0), 90, true));

			matrixStackIn.push();
			//Drawing right
			matrixStackIn.translate(topRadius, topOffset, 0);
			matrixStackIn.rotate(new Quaternion(new Vector3f(0, 0, 1), sideTurnAngle, false));
				matrixStackIn.push();
				addQuad(renderer, matrixStackIn, 
						0, -topRadius, 
						0, topRadius,
						sideLength, bottomRadius, 
						sideLength, -bottomRadius,
						spriteSide, 1.0f);
				addQuad(renderer, matrixStackIn,
						sideLength, -bottomRadius, 
						sideLength, bottomRadius, 
						0, topRadius, 
						0, -topRadius,
						spriteSide, 1.0f);
				matrixStackIn.pop();
			matrixStackIn.pop();
	
		matrixStackIn.pop();
	}
	
	private void addQuad(IVertexBuilder renderer, MatrixStack matrixStackIn, float minX, float minZ, float maxX, float maxZ, TextureAtlasSprite texture, float alpha)	{
		addVertex(renderer, matrixStackIn, minX, 0, minZ, texture.getMinU(), texture.getMinV(), alpha);
		addVertex(renderer, matrixStackIn, maxX, 0, minZ, texture.getMaxU(), texture.getMinV(), alpha);
		addVertex(renderer, matrixStackIn, maxX, 0, maxZ, texture.getMaxU(), texture.getMaxV(), alpha);
		addVertex(renderer, matrixStackIn, minX, 0, maxZ, texture.getMinU(), texture.getMaxV(), alpha);
	}

	private void addQuad(IVertexBuilder renderer, MatrixStack matrixStackIn,
			float x1, float z1, float x2, float z2,
			float x3, float z3, float x4, float z4,
			TextureAtlasSprite texture, float alpha)	{
		addVertex(renderer, matrixStackIn, x1, 0, z1, texture.getMinU(), texture.getMinV(), alpha);
		addVertex(renderer, matrixStackIn, x2, 0, z2, texture.getMaxU(), texture.getMinV(), alpha);
		addVertex(renderer, matrixStackIn, x3, 0, z3, texture.getMaxU(), texture.getMaxV(), alpha);
		addVertex(renderer, matrixStackIn, x4, 0, z4, texture.getMinU(), texture.getMaxV(), alpha);
	}
	
	private void addVertex(IVertexBuilder renderer, MatrixStack matrixStackIn, float x, float y, float z, float u, float v, float alpha)
	{
		renderer
			.pos(matrixStackIn.getLast().getMatrix(), x, y, z)
			.color(1.0f, 1.0f, 1.0f, alpha)
			.tex(u, v)
			.lightmap(0, 100)
			.normal(matrixStackIn.getLast().getNormal(), 0, 0, 0)
			.endVertex();
	}
}
