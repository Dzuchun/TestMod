package dzuchun.minecraft.testmod.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;

import dzuchun.minecraft.testmod.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TestMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManaBarRenderer { 
	private static final Object manaLock = new Object();
	
	private static Minecraft minecraft;
	private static TextureAtlasSprite MANA_BAR_TEXTURE;

	private static float maxMana = 100f;
	private static float currentMana = 80f; 
	private static float fullPartial;

	private static BufferBuilder bufferbuilder;

	@SubscribeEvent
	public static void drawManaBar(RenderGameOverlayEvent.Post event) {
		if (event.getType().equals(ElementType.HEALTH)) {
			if (minecraft == null) {
				minecraft = Minecraft.getInstance();
			}
			
			
			if (!Minecraft.isGuiEnabled())
	            return;
	
			if (minecraft.world == null) {
				return;
			}

			minecraft.getTextureManager().bindTexture(TestMod.MANA_BAR);
			
			RenderSystem.pushTextureAttributes();
			RenderSystem.enableAlphaTest();
			
			fullPartial = getCurrentMana()/getMaxMana();
			
//			MANA_BAR_TEXTURE = minecraft.getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(TestMod.MANA_BAR);
			
//			size = (MANA_BAR_TEXTURE.getMaxU() - MANA_BAR_TEXTURE.getMinU())/2f;
			
			bufferbuilder = Tessellator.getInstance().getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			
			//Rendering empty part
			bufferbuilder.pos(80f, 20f, 0).tex(0f, 0f).endVertex();
			bufferbuilder.pos(120f, 20f, 0).tex(0.5f, 0f).endVertex();
			bufferbuilder.pos(120f, 20f + (1-fullPartial) * 80f, 0).tex(0.5f, 1-fullPartial).endVertex();
			bufferbuilder.pos(80f, 20f + (1-fullPartial) * 80f, 0).tex(0f, 1-fullPartial).endVertex();

			//Rendering full part
			bufferbuilder.pos(80f, 20f + (1-fullPartial) * 80f, 0).tex(0.5f, 1-fullPartial).endVertex();
			bufferbuilder.pos(120f, 20f + (1-fullPartial) * 80f, 0).tex(1f, 1-fullPartial).endVertex();
			bufferbuilder.pos(120f, 100f, 0).tex(1f, 1).endVertex();
			bufferbuilder.pos(80f, 100f, 0).tex(0.5f, 1).endVertex();
			
			
			bufferbuilder.finishDrawing();
			WorldVertexBufferUploader.draw(bufferbuilder);
			
			RenderSystem.disableAlphaTest();
			RenderSystem.popAttributes();
			
			minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
		}	
	}

	public static float getMaxMana() {
		synchronized (manaLock) {
			return maxMana;
		}
	}

	public static void setMaxMana(float maxMana) {
		synchronized (manaLock) {
			ManaBarRenderer.maxMana = maxMana;
			if (maxMana < 1) {
				ManaBarRenderer.maxMana = 1;
			}
			if (maxMana  < currentMana) {
				currentMana = maxMana;
			}
		}
	}

	public static float getCurrentMana() {
		synchronized (manaLock) {
			return currentMana;	
		}
	}

	public static void setCurrentMana(float currentMana) {
		synchronized (manaLock) {
			ManaBarRenderer.currentMana = currentMana;
			if (currentMana < 0) {
				ManaBarRenderer.currentMana = 0;
			}
			if (maxMana < currentMana) {
				ManaBarRenderer.currentMana = maxMana;
			}
		}
	}
	
	public static void addMaxMana(float addition) {
		setMaxMana(getMaxMana() + addition);
	}

	
	public static void addCurrentMana(float addition) {
		setCurrentMana(getCurrentMana() + addition);
	}	
}
