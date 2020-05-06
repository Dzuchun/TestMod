package dzuchun.minecraft.testmod.blockitem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dzuchun.minecraft.testmod.block.tree.PineappleTree;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class PineappleBlockItem extends BlockItem {

	// Directly reference a log4j logger.
	private static final Logger LOGGER = LogManager.getLogger();

	public PineappleBlockItem() {

		super(new PineappleSaplingBlock(),
				new Item.Properties().group(ItemGroup.FOOD)
						.food(new Food.Builder().hunger(5).saturation(2)
								.effect(() -> new EffectInstance(Effect.get(23), 20), 1f).fastToEat().setAlwaysEdible()
								.build()));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		if (!Screen.hasShiftDown()) {
			return ActionResultType.PASS;
		}
		return super.onItemUse(context);
	}

	private static EffectInstance effectInstace_;

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		if (!entityLiving.getEntityWorld().isRemote) {
			if (stack.getItem() instanceof PineappleBlockItem) {
				effectInstace_ = entityLiving.getActivePotionMap().get(Effects.SPEED);
				if (effectInstace_ == null) {
					entityLiving.addPotionEffect(getNextFor(0, 0));
				} else {
					entityLiving.addPotionEffect(getNextFor(effectInstace_));
				}
			}
		}
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	private static EffectInstance getNextFor(EffectInstance instance) {
		if (instance == null) {
			return getNextFor(0, 0);
		} else {
//			LOGGER.info("Duration - " + instance.getDuration() + ", Amplifier - " + instance.getAmplifier());
			return getNextFor(instance.getDuration(), instance.getAmplifier());
		}
	}

	private static final int durationFactor = 20 * 20;
	private static final int amplifierDivisor = 1000;
	private static final int maxAmplifier = 5;
	private static final int maxDuration = 20 * 60 * 5;

	private static EffectInstance getNextFor(int duration, int amplifier) {

		return new EffectInstance(Effects.SPEED, applyLimiter(duration + durationFactor / (amplifier + 1), maxDuration),
				Math.max(applyLimiter(duration / amplifierDivisor, maxAmplifier), 1), false, false, true);
	}

	private static int applyLimiter(int value, int limiter) {
		return Math.min((int) (limiter * (1 - Math.exp(-value))), value);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 15;
	}
}

class PineappleSaplingBlock extends SaplingBlock {

	PineappleSaplingBlock() {
		super(new PineappleTree(),
				Block.Properties.create(Material.LEAVES, MaterialColor.LIME).hardnessAndResistance(0.0f, 0.0f)
						.notSolid().sound(SoundType.PLANT).doesNotBlockMovement().tickRandomly());
	}
}
