package dzuchun.minecraft.testmod.client.render.tileentity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.tileentity.AnimationBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class AnimationBlockEntityRenderer extends TileEntityRenderer<AnimationBlockEntity>{

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    
	public AnimationBlockEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void render(AnimationBlockEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
	
		//LOGGER.info("Rendering Test Tile Entity..");
		
		TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(TestMod.ANIMATION_SPRITE);
		IVertexBuilder renderer = bufferIn.getBuffer(RenderType.getLines());
		
		matrixStackIn.push();
		matrixStackIn.translate(0.5, 0.5, 0.5);
		
		addVertex(renderer, matrixStackIn, 0f, 0f, 0f, sprite.getMinU(), sprite.getMinV());
		addVertex(renderer, matrixStackIn, 1f, 0f, 0f, sprite.getMaxU(), sprite.getMinV());
		addVertex(renderer, matrixStackIn, 1f, 1f, 0f, sprite.getMaxU(), sprite.getMaxV());
		addVertex(renderer, matrixStackIn, 0f, 1f, 0f, sprite.getMinU(), sprite.getMaxV());
		
		addVertex(renderer, matrixStackIn, 1f, 0f, 0f, sprite.getMaxU(), sprite.getMinV());
		addVertex(renderer, matrixStackIn, 1f, 1f, 0f, sprite.getMaxU(), sprite.getMaxV());
		addVertex(renderer, matrixStackIn, 0f, 1f, 0f, sprite.getMinU(), sprite.getMaxV());
		addVertex(renderer, matrixStackIn, 0f, 0f, 0f, sprite.getMinU(), sprite.getMinV());
		
		matrixStackIn.pop();
		
		matrixStackIn.push();
		matrixStackIn.translate(0D, 2D, 0D);
		matrixStackIn.rotate(new Quaternion(new Vector3f(1f, 1f, 0f), 30, true));
		
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		ItemStack stack = new ItemStack(Items.ACACIA_BOAT);
		IBakedModel iBakedModel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
		itemRenderer.renderItem(stack, TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, iBakedModel);
		
		matrixStackIn.pop();
		
		matrixStackIn.push();
		matrixStackIn.translate(0D, -2D, 0D);
		matrixStackIn.rotate(new Quaternion(new Vector3f(1f, 1f, 0f), -30, true));
		
		BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
		BlockState state = Blocks.TUBE_CORAL_FAN.getDefaultState();
		blockRenderer.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
		
		matrixStackIn.pop();
	}
	
	private void addVertex(IVertexBuilder renderer, MatrixStack matrixStackIn, float x, float y, float z, float u, float v)
	{
		renderer
			.pos(matrixStackIn.getLast().getMatrix(), x, y, z)
			.color(.5f, .5f, .5f, .5f)
			.endVertex();
	}
	
	public static void register()
	{
    	ClientRegistry.bindTileEntityRenderer(TestMod.ANIMATION_BLOCK_ENTITY_TYPE_REGISTRY_OBJECT.get(), AnimationBlockEntityRenderer::new);
	}
	
	public boolean isGlobalRenderer(BeaconTileEntity te) 
	{
		return true;
	}
}