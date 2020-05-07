package dzuchun.minecraft.testmod.client.render.misc;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderState.ShadeModelState;
import net.minecraft.client.renderer.RenderState.TransparencyState;
import net.minecraft.client.renderer.RenderState.WriteMaskState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = TestMod.MODID, bus = Bus.FORGE)
public class WingsRenderer {

	private static final Logger LOGGER = LogManager.getLogger();
	private static LinkedHashMap<PlayerEntity, WingsRenderer> LIST = new LinkedHashMap<PlayerEntity, WingsRenderer>(0);
	private static final RenderType BARE_COLOR = RenderType.makeType("bare_color", DefaultVertexFormats.POSITION_COLOR,
			7, 262144, true, true, RenderType.State.getBuilder().writeMask(new WriteMaskState(true, false))
					.transparency(new RenderState.TransparencyState("translucent_transparency", () -> {
						RenderSystem.enableBlend();
						RenderSystem.defaultBlendFunc();
					}, () -> {
						RenderSystem.disableBlend();
					})).shadeModel(new ShadeModelState(false)).build(true));
	private static final RenderType renderType = RenderType.getLightning();

	@SubscribeEvent
	public static void onRenderPlayerEventPre(RenderPlayerEvent.Pre event) {

//		if (event.isCancelable()) {
//			event.setCanceled(true);
//		}

		// should draw wings and player here, if wings mode enabled

	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void handleRenderEvent(RenderPlayerEvent event) {

		// Used to draw wings with a player.
		if (event.getPlayer() == null) {
			LOGGER.warn("Can't handle a null-player render. Skipping");
			return;
		}
		if (!LIST.containsKey(event.getPlayer())) {
			LOGGER.info("Registering wings for player {}", event.getPlayer().getName().getString());
			new WingsRenderer(event.getPlayer());
		}
		LIST.get(event.getPlayer()).drawWingsInWorld(event);

	}

	private PlayerEntity player;

	public WingsRenderer(PlayerEntity player) {
		if (player == null) {
			throw (new IllegalArgumentException("Player cannot be null!"));
		}
		this.player = player;
		LIST.put(player, this);
	}

	private void drawWingsInWorld(RenderPlayerEvent event) {
		renderWings((BufferBuilder) event.getBuffers().getBuffer(BARE_COLOR), event.getMatrixStack());
	}

	private static MatrixStack matrixStack;
	private static ClientPlayerEntity player1;
	private static BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();

	@SuppressWarnings("resource")
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void drawWingsAtPlayerView(RenderWorldLastEvent event) {
		// Possibly have to handle arm render
		if (!Minecraft.getInstance().getRenderManager().info.isThirdPerson()) {
			matrixStack = event.getMatrixStack();
			player1 = Minecraft.getInstance().player;
			matrixStack.push();
			matrixStack.translate(0, -player1.getHeight(), 0);
//			renderWings(event.getBuffers().getBuffer(renderType), event.getMatrixStack());
			bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
//			bufferBuilder.color(1f, 1f, 1f, 0.5f);
//			bufferBuilder.setDefaultColor(127, 127, 127, 127);
			renderWings(bufferBuilder, matrixStack);
			bufferBuilder.finishDrawing();
			WorldVertexBufferUploader.draw(bufferBuilder);
			matrixStack.pop();
		}
	}

	@SuppressWarnings("resource")
	private static void renderWings(BufferBuilder builder, MatrixStack matrixStack) {
		matrixStack.push();

		if (Minecraft.getInstance().player.getPose() == Pose.CROUCHING) {
			matrixStack.translate(0, player1.getHeight() - player1.getEyeHeight(), 0);
		}
		matrixStack.translate(0, Minecraft.getInstance().player.getEyeHeight(), 0);

		matrixStack.push();

		matrixStack.translate(1.0d, 0.0d, 1.0d);
		matrixStack.rotate(new Quaternion(new Vector3f(1, 0, 0), 30, true)); // An axis vector must be length of 1 to
																				// rotate without distortion
		matrixStack.push();

		RenderUtils.drawCuboid(matrixStack.getLast().getMatrix(), builder, new Vector3f(0.5f, 1.0f, 1.5f),
				new Vector4f(0.5f, 0.5f, 0.5f, 0.5f));

		matrixStack.pop();

		matrixStack.pop();
		matrixStack.rotate(new Quaternion(new Vector3f(0, -1, 0), 90, true));

		matrixStack.push();

		matrixStack.translate(1.0d, 0.0d, 1.0d);
		matrixStack.rotate(new Quaternion(new Vector3f(1, 0, 0), 30, true)); // An axis vector must be length of 1 to
																				// rotate without distortion
		matrixStack.push();

		RenderUtils.drawCuboid(matrixStack.getLast().getMatrix(), builder, new Vector3f(0.5f, 1.0f, 1.5f),
				new Vector4f(0.5f, 0.5f, 0.5f, 0.5f));

		matrixStack.pop();

		matrixStack.pop();
		matrixStack.rotate(new Quaternion(new Vector3f(0, -1, 0), 90, true));

		matrixStack.push();

		matrixStack.translate(1.0d, 0.0d, 1.0d);
		matrixStack.rotate(new Quaternion(new Vector3f(1, 0, 0), 30, true)); // An axis vector must be length of 1 to
																				// rotate without distortion
		matrixStack.push();

		RenderUtils.drawCuboid(matrixStack.getLast().getMatrix(), builder, new Vector3f(0.5f, 1.0f, 1.5f),
				new Vector4f(0.5f, 0.5f, 0.5f, 0.5f));

		matrixStack.pop();

		matrixStack.pop();
		matrixStack.rotate(new Quaternion(new Vector3f(0, -1, 0), 90, true));

		matrixStack.push();

		matrixStack.translate(1.0d, 0.0d, 1.0d);
		matrixStack.rotate(new Quaternion(new Vector3f(1, 0, 0), 30, true)); // An axis vector must be length of 1 to
																				// rotate without distortion
		matrixStack.push();

		RenderUtils.drawCuboid(matrixStack.getLast().getMatrix(), builder, new Vector3f(0.5f, 1.0f, 1.5f),
				new Vector4f(0.5f, 0.5f, 0.5f, 0.5f));

		matrixStack.pop();

		matrixStack.pop();
		matrixStack.rotate(new Quaternion(new Vector3f(0, 0, -1), 90, true));

		matrixStack.pop();
	}
}
