package dzuchun.minecraft.testmod.net;

import net.minecraft.network.PacketBuffer;

public class LeapMessage {
	public LeapMessage(){
	}
	
	public void encode (PacketBuffer buf) {
		buf.writeBoolean(true);
	}
	
	public static LeapMessage decode(PacketBuffer buf) {
//		return null; 
		
		if (buf.readBoolean()){
			return new LeapMessage();	
		} else {
			return null;
		}
	}
}
