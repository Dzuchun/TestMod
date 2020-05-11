package dzuchun.minecraft.testmod.wings1;

import javax.swing.Renderer;

/**
 * Defines basic interact method to implement for any wing-containment object.
 * @author dzuchun
 *
 */
public interface IWings {
	
	
	public boolean isWingsActive();
	/**
	 * Sets active state
	 * @param state State to set
	 * @return If operation suceeded
	 */
	public boolean setActiveState(boolean state);
	
	public float getMana();
	public float getMaxMana();
	public void setMaxMana(float amount);
	
	/**
	 * @param amount Mana amount to take if there is isuffisient mana, does noting and returns false
	 * @return If mana was taken
	 */
	public boolean takeMana(float amount);
	
	public void gainMana(float amount);
	
	public float getMaxWingsMana();
	public float getWingsMana();
	
	/**
	 * Channels mana to wings
	 * @return Mana amount channeled, due to limited wings capacity
	 */
    public void channelWingsMana(float amount);
    
    /**
     * ???
     */
    public Class<? extends Renderer> getRenderer();
}
