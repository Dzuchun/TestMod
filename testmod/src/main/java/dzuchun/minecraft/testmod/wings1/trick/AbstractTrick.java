package dzuchun.minecraft.testmod.wings1.trick;

import dzuchun.minecraft.testmod.wings1.Wings;
import dzuchun.minecraft.testmod.wings1.Wings.Type;

public abstract class AbstractTrick {
	
	/**
	 * Used to cast a trick
	 * @param wings Wings to cast on
	 */
	public abstract void cast(Wings wings);
	
	/**
	 * @return Mana cost
	 */
	public abstract float getManaCost();
	
	/**
	 * @return Required type to use a trick
	 */
	public abstract Wings.Type getRequiredType();
}
