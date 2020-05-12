package dzuchun.minecraft.testmod.net;

import java.util.function.Supplier;

import dzuchun.minecraft.testmod.server.SmashLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;

public class SmashMessage {

	private double x, y, z, maxDistance, force;

	public static SmashMessage create(Vec3d forward, double force, double maxDistance) {
		return new SmashMessage(forward.x, forward.y, forward.z, force, maxDistance);
	}

	private SmashMessage(double x, double y, double z, double force, double maxDistance) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.force = force;
		this.maxDistance = maxDistance;
	}

	public void encode(PacketBuffer b) {
		b.writeDouble(x);
		b.writeDouble(y);
		b.writeDouble(z);
		b.writeDouble(getForce());
		b.writeDouble(getMaxDistance());
	}

	public static SmashMessage decode(PacketBuffer b) {
		return new SmashMessage(b.readDouble(), b.readDouble(), b.readDouble(), b.readDouble(), b.readDouble());
	}

	public Vec3d forward() {
		return new Vec3d(x, y, z);
	}

	public double getForce() {
		return force;
	}

	public double getMaxDistance() {
		return maxDistance;
	}

	public static void smashMessageHandler(SmashMessage m, Supplier<NetworkEvent.Context> c) {
		SmashLogic.addSmashEvent(c.get().getSender(), m);
		c.get().setPacketHandled(true);
	}
}
