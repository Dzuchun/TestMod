package dzuchun.minecraft.testmod.entity.passive;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HappyDolphinEntity extends AnimalEntity implements IFlyingAnimal {

	private static final Vec3d WANDER_RADIUS = new Vec3d(5, 5, 5);
	private static final Vec3d EAT_RADIUS = new Vec3d(2, 20, 2);
	private static final BlockState EAT_TARGET = Blocks.DANDELION.getDefaultState();
	private static final BlockState EAT_RESULT = Blocks.AIR.getDefaultState();
	private static final int EAT_TIMER = 40;
	private static final IParticleData PARTICLE = new BlockParticleData(ParticleTypes.BLOCK, EAT_TARGET);
	private static final int HUNGER_FOFCE = 30;

	// TODO move this to util
	private static int tmp_counter1, tmp_counter2, tmp_counter3;
	private static BlockPos tmp_blockPos;

	private synchronized static Set<BlockPos> findBlockPositionIn(World world, AxisAlignedBB region,
			BlockState target) {
//		LOGGER.info("Starting area check");
		Set<BlockPos> res = new LinkedHashSet<BlockPos>(0);
		for (tmp_counter1 = MathHelper.floor(region.minX); tmp_counter1 <= region.maxX; tmp_counter1++) {

			for (tmp_counter2 = MathHelper.floor(region.minY); tmp_counter2 <= region.maxY; tmp_counter2++) {

				for (tmp_counter3 = MathHelper.floor(region.minZ); tmp_counter3 <= region.maxZ; tmp_counter3++) {

					tmp_blockPos = new BlockPos(tmp_counter1, tmp_counter2, tmp_counter3);
//					LOGGER.info("   Checking at " + tmp_blockPos.toString() + ", got " + world.getBlockState(tmp_blockPos).toString());
					if (world.getBlockState(tmp_blockPos).equals(target)) {
						res.add(tmp_blockPos);
					}
				}
			}
		}
//		LOGGER.info("Finished area check");
		return res;
	}

	// TODO move this to util
	private static Random tmp_rand = new Random();
	@SuppressWarnings("rawtypes")
	private static Iterator tmp_iterator;
	private static int tmp_counter4, tmp_size;
	private static double tmp_double;

	@SuppressWarnings("unchecked")
	private synchronized static <E> E getRandomElement(Collection<E> list) {
		tmp_iterator = list.iterator();
		tmp_size = list.size();
		tmp_double = 1d / (double) tmp_size;
		tmp_size--;
		for (tmp_counter4 = 0; tmp_counter4 < tmp_size; tmp_counter4++) {
			if (tmp_rand.nextDouble() < tmp_double) {
				return (E) tmp_iterator.next();
			} else {
				tmp_iterator.next();
			}
		}
		return (E) tmp_iterator.next();
	}
	
	private static int tmp_counter5;
	private static BlockPos tmp_blockPos1;
	//TODO move to util
	private synchronized static int getHeightOverSurface(World world, BlockPos pos) {
		tmp_counter5=0;
		tmp_blockPos1 = pos.down(tmp_counter5);
		while(world.getBlockState(tmp_blockPos1).equals(Blocks.AIR.getDefaultState())) {
			tmp_counter5++;
			tmp_blockPos1 = pos.down(tmp_counter5);
		}
		return tmp_counter5;
	}

	public HappyDolphinEntity(EntityType<HappyDolphinEntity> entityType, World world) {
		super(entityType, world);
		this.setAIMoveSpeed(0.5f);
		this.moveController = new FlyingMovementController(this, 5, true);
	}
	@Override
	/**
	 * Returns new PathNavigateGround instance
	 */
	protected PathNavigator createNavigator(World worldIn) {
		FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn) {

			{
				this.setCanEnterDoors(true);
				this.setCanOpenDoors(false);
				this.setCanSwim(true);
			}

			@Override
			public boolean canEntityStandOnPos(BlockPos pos) {
				return true;
			}

		};
		this.navigator = flyingpathnavigator;
		return flyingpathnavigator;
	}

	@Override
	public CreatureAttribute getCreatureAttribute() {
		return CreatureAttribute.UNDEFINED;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new HappyDolphinEntity.WanderGoal());
		this.goalSelector.addGoal(1, new HappyDolphinEntity.EatFlowerGoal(this));

//		this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, getAIMoveSpeed()));
	}

	@Override
	public AgeableEntity createChild(AgeableEntity ageable) {
		return null;
	}

	@Override
	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double) 0.6F);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
	}

	private int hunger = 0;
	
	@Override
	public void livingTick() {
		super.livingTick();
		if (this.fallDistance != 0) {
			this.fallDistance=0;
		}
		hunger++;
	}
	
//*****       GOALS       *****
	
	private boolean justAte = false;
	class EatFlowerGoal extends Goal {

		private LivingEntity entity;
		private World world;
		private Vec3d pos;
		private BlockPos target;

		public EatFlowerGoal(LivingEntity entity) {
			this.entity = entity;
			this.world = entity.world;
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() {
			if (isBusy) {
				return false;
			}

			if (!notExecuting) {
				// IMPORTANT!!
				LOGGER.info("Already executing, shouldn`t do again");
				return false;
			}

			pos = entity.getPositionVec();
			if (findBlockPositionIn(entity.world, new AxisAlignedBB(pos.add(EAT_RADIUS.scale(-1)), pos.add(EAT_RADIUS)),
					EAT_TARGET).isEmpty()) {
				LOGGER.info("Should execute returned false");
				justWandered = false;
				return false;
			}
			LOGGER.info("Should execute returned true");
			return true;
		}

		private boolean notExecuting = true;

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean shouldContinueExecuting() {
			LOGGER.info("Should continue returned " + !notExecuting);
			return !notExecuting;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void startExecuting() {
			justWandered = false;
			super.startExecuting();
			setAIMoveSpeed(0.7f);
			target = getRandomElement(findBlockPositionIn(entity.world,
					new AxisAlignedBB(pos.add(EAT_RADIUS.scale(-1)), pos.add(EAT_RADIUS)), EAT_TARGET));
			reachedTarget = false;
			if (!HappyDolphinEntity.this.getNavigator().tryMoveToXYZ(target.getX() + 0.5, target.getY() + 0.7,
					target.getZ() + 0.5, entity.getAIMoveSpeed())) {
				// Failed to execute
				LOGGER.info("Start executing failed");
				notExecuting = true;
			} else {
				LOGGER.info("Start executing suceed");
				eatTimer = EAT_TIMER;
				notExecuting = false;
				isBusy = true;
			}
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by
		 * another one
		 */
		@Override
		public void resetTask() {
			super.resetTask();
			LOGGER.info("Resetting task");
			notExecuting = true;
			target = null;
			isBusy = false;
			justAte = true;
		}

		private boolean reachedTarget;
		private int eatTimer;

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			super.tick();

			if (target == null) {
				LOGGER.info("Tick while no target");
				return;
			}

			if (notExecuting) {
				LOGGER.info("Tick while failed");
				return;
			}

			// Checking if entity got to location
			if (!reachedTarget) {
				if (entity.getDistanceSq(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5) < 0.4) {
					LOGGER.info("Reached target");
					// Reached target
					reachedTarget = true;
					HappyDolphinEntity.this.getNavigator().clearPath();
//					entity.rotateTowards(entity.rotationYaw, 90);
					entity.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(target.getX(), target.getY(), target.getZ()));
				} else {
					LOGGER.info("Going to target, target distance square "
							+ entity.getDistanceSq(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5));

					if (HappyDolphinEntity.this.getNavigator().noPath() && !reachedTarget) {
						HappyDolphinEntity.this.setAIMoveSpeed(0.7f);
						HappyDolphinEntity.this.getNavigator().tryMoveToXYZ(target.getX(), target.getY(), target.getZ(),
								getAIMoveSpeed());
					}
					return;
				}
			}
			// Reached target
			if (!world.getBlockState(target).equals(EAT_TARGET)) {
				LOGGER.info("Target disappeared");
				notExecuting = true;
				return;
			}
			if (eatTimer > 0) {
				entity.lookAt(EntityAnchorArgument.Type.EYES, new Vec3d(target.getX(), target.getY(), target.getZ()));
				eatTimer--;
				world.addParticle(ParticleTypes.CRIT, target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5,
						tmp_rand.nextDouble() - 0.5, tmp_rand.nextDouble() - 0.5, tmp_rand.nextDouble() - 0.5);
				LOGGER.info("Eating, " + notExecuting);
			} else {
				world.setBlockState(target, EAT_RESULT);
				notExecuting = true;
				hunger =0 ;
				LOGGER.info("Target was eaten! Yummy!");
			}
		}

	}

	private boolean isBusy = false;
	private boolean justWandered = false;

	class WanderGoal extends Goal {

		private boolean inProgress = false;
		private BlockPos target;

		public WanderGoal () {
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}
		
		@Override
		public boolean shouldExecute() {
			LOGGER.info("Should execute wander returned " + (HappyDolphinEntity.this.getNavigator().noPath() && !isBusy));
			return HappyDolphinEntity.this.getNavigator().noPath() && !isBusy && !justWandered;
		}

		@Override
		public boolean shouldContinueExecuting() {
			LOGGER.info("Should continue execute wander returned " + !(HappyDolphinEntity.this.getNavigator().noPath() || target.withinDistance(HappyDolphinEntity.this.getPositionVector(), 0.4d)));
			return !(HappyDolphinEntity.this.getNavigator().noPath() || target.withinDistance(HappyDolphinEntity.this.getPositionVector(), 0.4d));
		}

		private Set<BlockPos> tmp_set;

		@Override
		public void startExecuting() {
			justAte = false;
			LOGGER.info("Starting wander goal");
			super.startExecuting();
			tmp_set = findBlockPositionIn(world,
					new AxisAlignedBB(HappyDolphinEntity.this.getPositionVector().add(WANDER_RADIUS.scale(-1)),
							HappyDolphinEntity.this.getPositionVector().add(WANDER_RADIUS)).expand(0, -hunger/HUNGER_FOFCE, 0),
					Blocks.AIR.getDefaultState());
			
//			target = new BlockPos(RandomPositionGenerator.findRandomTarget(HappyDolphinEntity.this, 5, 2));
			
//			getHeightOverSurface(world, target);
			
			if (tmp_set.isEmpty()) {
				return;
			}
			target = getRandomElement(tmp_set);
			HappyDolphinEntity.this.setAIMoveSpeed(0.7f);
			if (HappyDolphinEntity.this.getNavigator().tryMoveToXYZ(target.getX() + 0.5, target.getY() + 0.7,
					target.getZ() + 0.5, getAIMoveSpeed())) {
				inProgress = true;
				isBusy = true;
			}
		}
		
//		@Override
//		public void tick() {
//			super.tick();
//			if (target.withinDistance(HappyDolphinEntity.this.getPositionVector(), 0.4d)) {
//				inProgress = false;
//			} else {
//				if (HappyDolphinEntity.this.getNavigator().noPath() && inProgress) {
//					HappyDolphinEntity.this.setAIMoveSpeed(0.7f);
//					HappyDolphinEntity.this.getNavigator().tryMoveToXYZ(target.getX(), target.getY(), target.getZ(),
//							getAIMoveSpeed());
//				}
//			}
//		}

		@Override
		public void resetTask() {
			LOGGER.info("Clearing wander goal");
			super.resetTask();
			inProgress = false;
			HappyDolphinEntity.this.getNavigator().clearPath();
			isBusy = false;
			justWandered = true;
		}
	}
}
