package dzuchun.minecraft.testmod.net;

import java.util.Optional;
import java.util.function.Supplier;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.server.SmashLogic;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TestModPacketHandler {
	public static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(TestMod.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);

	public static void init() {
		INSTANCE.registerMessage(47, LeapMessage.class, LeapMessage::encode, LeapMessage::decode,
				TestModPacketHandler::leapMessageHandler, Optional.of(NetworkDirection.PLAY_TO_SERVER));
		INSTANCE.registerMessage(49, SmashMessage.class, SmashMessage::encode, SmashMessage::decode,
				TestModPacketHandler::smashMessageHandler, Optional.of(NetworkDirection.PLAY_TO_SERVER));
	}

	public static void leapMessageHandler(LeapMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			// Work that needs to be threadsafe (most work)
			ServerPlayerEntity sender = ctx.get().getSender(); // the client that sent this packet
			sender.fallDistance = 0f;
			sender.addVelocity(0, 0.2f, 0);
			sender.velocityChanged = true;
		});
		ctx.get().setPacketHandled(true);
	}

	static void smashMessageHandler(SmashMessage m, Supplier<NetworkEvent.Context> c) {
		SmashLogic.addSmashEvent(c.get().getSender(), m);
		c.get().setPacketHandled(true);
	}
}
