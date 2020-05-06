package dzuchun.minecraft.testmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.container.TestContainer;
import dzuchun.minecraft.testmod.tileentity.TestTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestScreen extends ContainerScreen<TestContainer>
{

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(TestMod.MODID, "textures/gui/container/test_container.png");

	private TestTileEntity tileEntity;
	
	public TestScreen(TestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		// TODO Auto-generated constructor stub
	}

	@Override 
	public void render(final int mouseX, final int mouseY, final float partialTicks) {
		super.render(mouseX, mouseY, partialTicks);
		this.renderBackground();
		this.renderHoveredToolTip(mouseX, mouseY);
		
		//Handle UI toolTipsHere
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		//Handle text drawing here:
//		String s = this.title.getFormattedText();
//		this.font.drawString(s, (float) (this.xSize / 2 - this.font.getStringWidth(s) / 2), 6.0F, 0x404040);
//		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float) (this.ySize - 96 + 2), 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		
		RenderSystem.color4f(0.5F, 0.5F, 1.0F, 1.0F);
		getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		this.tileEntity = container.tileEntity;
		
		//Draw energy 
	}
	
}