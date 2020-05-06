package dzuchun.minecraft.testmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.container.TestContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestScreen extends ContainerScreen<TestContainer>
{

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(TestMod.MODID, "textures/gui/container/test_container.png");

	public TestScreen(TestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		
		this.blit(50, 20, 0, 0, this.xSize, this.ySize);
	}
	
}
