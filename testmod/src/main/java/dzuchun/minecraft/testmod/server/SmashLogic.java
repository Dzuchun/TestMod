package dzuchun.minecraft.testmod.server;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dzuchun.minecraft.testmod.TestMod;
import dzuchun.minecraft.testmod.net.SmashMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SmashLogic {

	public static void init() {

	}

	private static final Logger LOGGER = LogManager.getLogger();

	private static LinkedHashMap<ServerPlayerEntity, SmashLogic> processes = new LinkedHashMap<ServerPlayerEntity, SmashLogic>(
			0);
	private static final Object processesLock = new Object();

	public static void addSmashEvent(ServerPlayerEntity player, SmashMessage msg) {
		synchronized (processesLock) {
			if (processes.containsKey(player)) {
				endPlayerSmash(player);
			}
			processes.put(player, new SmashLogic(player, msg.forward(), msg.getForce(), msg.getMaxDistance()));
		}
	}

	private static ServerPlayerEntity tmp_player;

	@SubscribeEvent
	public static void processSmashLogic(TickEvent.ServerTickEvent event) {
//		LOGGER.info("Processing smash logic");

		synchronized (processesLock) {
			for (SmashLogic instance : processes.values()) {
				tmp_player = instance.player;
				if (tmp_player.hasDisconnected()
						|| (tmp_player.getDistanceSq(instance.beginPos) >= instance.maxDistanceSq)
						|| (tmp_player.collided && tmp_player.getDistanceSq(instance.beginPos) != 0)) {
					processes.remove(instance.player);
				} else {

					tmp_player.setMotion(instance.direction
							.scale((tmp_player.getDistanceSq(instance.beginPos) + 1d) * instance.force));
					tmp_player.fallDistance = 0;
					tmp_player.velocityChanged = true;

				}
			}
		}
	}

	public static boolean isPlayerInSmash(ServerPlayerEntity player) {
		synchronized (processesLock) {
			if (processes.containsKey(player)) {
				return true;
			}
		}
		return false;
	}

	public static void endPlayerSmash(ServerPlayerEntity player) {
		synchronized (processesLock) {
			processes.remove(player);
		}
	}

	private ServerPlayerEntity player;
	private Vec3d direction;
	private double force;
	private double maxDistanceSq;
	private Vec3d beginPos;

	private SmashLogic(ServerPlayerEntity player, Vec3d direction, double force, double maxDistance) {
		this.player = player;
		this.direction = direction;
		this.force = force;
		this.maxDistanceSq = maxDistance * maxDistance;
		this.beginPos = player.getPositionVec();
	}
}
