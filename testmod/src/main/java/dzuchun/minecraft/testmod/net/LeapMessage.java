package dzuchun.minecraft.testmod.net;

import net.minecraft.network.PacketBuffer;

public class LeapMessage {
	
	private float dxv, dyv, dzv;
	
	public LeapMessage (float dxv, float dyv, float dzv) {
		this.dxv = dxv;
		this.dyv = dyv;
		this.dzv = dzv;
	}
	
	public void encode (PacketBuffer buf) {
		buf.writeFloat(this.dxv);
		buf.writeFloat(this.dyv);
		buf.writeFloat(this.dzv);
	}
	
	public static LeapMessage decode(PacketBuffer buf) {
		return new LeapMessage(buf.readFloat(), buf.readFloat(), buf.readFloat());
	}
}
