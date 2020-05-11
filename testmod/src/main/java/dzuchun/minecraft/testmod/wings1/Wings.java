package dzuchun.minecraft.testmod.wings1;

import java.util.HashSet;
import java.util.Set;
import javax.swing.Renderer;

import dzuchun.minecraft.testmod.wings1.trick.AbstractTrick;

public class Wings implements IWings {

	private static final float INITIAL_MAX_MANA = 100;
	private static final float INITIAL_MANA = 0;
	private static final float WINGS_CAPACITY = 10;

	public Wings() {
		this.availableTypes = new HashSet<Wings.Type>() {
			private static final long serialVersionUID = 1L;

			{
				this.add(Wings.Type.NONE);
			}
		};
		this.availableTricks = new HashSet<Class<? extends AbstractTrick>>(0);
		this.manaState = new ManaState(INITIAL_MANA, INITIAL_MAX_MANA);
		this.wingsMana = new WingsManaState(0, WINGS_CAPACITY);
		this.activeType = Wings.Type.NONE;
		this.wingsActive = false;
	}

	public Wings(Set<Wings.Type> availableTypes, Set<Class<? extends AbstractTrick>> availableTricks) {
		this.availableTypes = availableTypes;
		this.availableTricks = availableTricks;
		this.wingsActive = false;
	}

	public static enum Type {
		NONE, NORMAL, CHAOS, ZAPHKIEL;

		public float getActivationCost() {
			switch (this) {
			case NONE:
				return 0f;
			case NORMAL:
				return 0f;
			case CHAOS:
				return 0f;
			case ZAPHKIEL:
				return 0f;
			}
			return 0f;
		}
		
		public Class<? extends Renderer> getRenderer()	{
			switch(this) {
			case CHAOS:
				break;
			case NONE:
				break;
			case NORMAL:
				break;
			case ZAPHKIEL:
				break;
			}
			return null;
		}
	}

	Set<Wings.Type> availableTypes;
	Wings.Type activeType;

	public Type getActiveType() {
		return this.activeType;
	}
	
	public float getActivationCost() {
		return this.activeType.getActivationCost();
	}

	/**
	 * Sets current wings type
	 * 
	 * @param type Type to set
	 * @return If a type was successfully set
	 */
	public boolean setActiveType(Wings.Type type) {
		if (!this.availableTypes.contains(type)) {
			return false;
		}
		if (type.getActivationCost() > this.manaState.currentMana && this.wingsActive) {
			return false;
		} else {
			this.activeType = type;
			return true;
		}
	}

	ManaState manaState;

	public class ManaState {
		public ManaState(float currentMana, float maxMana) {
			this.currentMana = currentMana;
			this.maxMana = maxMana;
		}

		private float currentMana;

		public float getMana() {
			return this.currentMana;
		}

		private float maxMana;

		public float getMaxMana() {
			return this.maxMana;
		}

		public float getManaPart() {
			return this.currentMana / this.maxMana;
		}

		public void gainMana(float amount) {
			if (amount < 0) {
				return;
			}
			this.currentMana = Math.min(this.maxMana, this.currentMana + amount);
		}

		public boolean takeMana(float amount) {
			if (amount < 0) {
				return false;
			}
			if (this.currentMana < amount) {
				return false;
			} else {
				this.currentMana -= amount;
				return false;
			}
		}
	}

	public Wings.ManaState getManaState() {
		return this.manaState;
	}

	@Override
	public float getMana() {
		return this.manaState.currentMana;
	}

	@Override
	public float getMaxMana() {
		return this.manaState.maxMana;
	}

	@Override
	public void setMaxMana(float amount) {
		if (amount < this.manaState.currentMana) {
			this.manaState.currentMana = amount;
		}
		this.manaState.maxMana = amount;
	}

	@Override
	public boolean takeMana(float amount) {
		if (this.manaState.currentMana < amount) {
			return false;
		}
		this.manaState.currentMana -= amount;
		return true;
	}

	@Override
	public void gainMana(float amount) {
		this.manaState.currentMana += amount;
		if (this.manaState.currentMana > this.manaState.maxMana) {
			this.manaState.currentMana = this.manaState.maxMana;
		}
	}

	Set<Class<? extends AbstractTrick>> availableTricks;

	public boolean cast(AbstractTrick trick) {
		if (!this.availableTricks.contains(trick.getClass())) {
			return false;
		}

		if (trick.getManaCost() > this.manaState.currentMana) {
			return false;
		} else {
			this.manaState.currentMana -= trick.getManaCost();
			trick.cast(this);
			return true;
		}
	}

	boolean wingsActive;

	@Override
	public boolean isWingsActive() {
		return this.wingsActive;
	}

	@Override
	public boolean setActiveState(boolean state) {
		if (!state) {
			this.wingsActive = false;
			return true;
		} else {
			if (this.activeType == Wings.Type.NONE) {
				return false;
			} else {
				if (this.manaState.currentMana < this.activeType.getActivationCost()) {
					return false;
				} else {
					this.takeMana(this.activeType.getActivationCost());
					this.wingsActive = true;
					return true;
				}
			}
		}
	}

	Wings.WingsManaState wingsMana;

	public class WingsManaState extends ManaState {

		public WingsManaState(float currentMana, float maxMana) {
			super(currentMana, maxMana);
		}

		@Override
		public boolean takeMana(float amount) {
			return false;
		}
	}

	@Override
	public float getMaxWingsMana() {
		return this.wingsMana.getMaxMana();
	}

	@Override
	public float getWingsMana() {
		return this.wingsMana.getMana();
	}

	public float getWingsManaPart() {
		return this.wingsMana.getManaPart();
	}
	
	@Override
	public void channelWingsMana(float amount) {
		this.wingsMana.gainMana(amount);
	}

	@Override
	public Class<? extends Renderer> getRenderer() {
		//TODO
		if (!this.wingsActive) {
			return null;
		} else {
			return this.activeType.getRenderer();
		}
	}

}
