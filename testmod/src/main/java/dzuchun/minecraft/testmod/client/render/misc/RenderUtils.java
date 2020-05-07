package dzuchun.minecraft.testmod.client.render.misc;

import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.Vector4f;
import net.minecraft.util.math.Vec2f;

public class RenderUtils {

	public static void drawCuboid(Matrix4f matrixIn, IVertexBuilder builder, Vector3f size, Vector4f color) {
		
		drawRectangle(matrixIn, builder, Orientation.Y_AXIS, new Vector3f(size.getX(), size.getY(), 0.0f), new Vec2f(-size.getX(), size.getZ()), color);
		drawRectangle(matrixIn, builder, Orientation.Y_AXIS, new Vector3f(0.0f, 0.0f, 0.0f), new Vec2f(size.getX(), size.getZ()), color);
		
		drawRectangle(matrixIn, builder, Orientation.X_AXIS, new Vector3f(0.0f, size.getY(), 0.0f), new Vec2f(-size.getY(), size.getZ()), color);
		drawRectangle(matrixIn, builder, Orientation.X_AXIS, new Vector3f(size.getX(), 0.0f, 0.0f), new Vec2f(size.getY(), size.getZ()), color);
		
		drawRectangle(matrixIn, builder, Orientation.Z_AXIS, new Vector3f(0.0f, 0.0f, size.getZ()), new Vec2f(size.getX(), size.getY()), color);
		drawRectangle(matrixIn, builder, Orientation.Z_AXIS, new Vector3f(size.getX(), 0.0f, 0.0f), new Vec2f(-size.getX(), size.getY()), color);
		
	}

	public static void drawRectangle(Matrix4f matrixIn, IVertexBuilder builder, Orientation orientation, Vector3f beginPos,
			Vec2f size, Vector4f color) {
		switch (orientation)
		{
		case X_AXIS:
			
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY(), beginPos.getZ()), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY() + size.x, beginPos.getZ()), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY() + size.x, beginPos.getZ() + size.y), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY(), beginPos.getZ() + size.y), color);
			
			break;
		case Y_AXIS:
			
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY(), beginPos.getZ()), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX() + size.x, beginPos.getY(), beginPos.getZ()), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX() + size.x, beginPos.getY(), beginPos.getZ() + size.y), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY(), beginPos.getZ() + size.y), color);
			
			break;
		case Z_AXIS:
			
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY(), beginPos.getZ()), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX() + size.x, beginPos.getY(), beginPos.getZ()), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX() + size.x, beginPos.getY() + size.y, beginPos.getZ()), color);
			drawVertex(matrixIn, builder, new Vector3f(beginPos.getX(), beginPos.getY() + size.y, beginPos.getZ()), color);
			
			break;
		}
	}

	public static void drawVertex(Matrix4f matrixIn, IVertexBuilder builder, Vector3f pos, Vector4f color) {
		builder.pos(matrixIn, pos.getX(), pos.getY(), pos.getZ())
				.color(color.getX(), color.getY(), color.getZ(), color.getW()).endVertex();
	}

	public enum Orientation {
		X_AXIS, Y_AXIS, Z_AXIS
	}
}
