package dzuchun.minecraft.testmod.common.capability;

import dzuchun.minecraft.testmod.wings1.IWings;
import dzuchun.minecraft.testmod.wings1.Wings;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityWings {

	@CapabilityInject(IWings.class)
	public static Capability<IWings> WINGS = null;

	private static CompoundNBT res;
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IWings.class, new IStorage<IWings>() {
			@Override
			public synchronized INBT writeNBT(Capability<IWings> capability, IWings instance, Direction side) {
				res = new CompoundNBT();
//				res.put
				return res;
//                return IntNBT.valueOf(instance.getEnergyStored());
				
			}

			@Override
			public void readNBT(Capability<IWings> capability, IWings instance, Direction side, INBT nbt) {
//                if (!(instance instanceof EnergyStorage))
//                    throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
//                ((EnergyStorage)instance).energy = ((IntNBT)nbt).getInt();
			}
		}, () -> new Wings());
	}
}
