package org.usfirst.frc.team4946.robot.pathplanning.data.actions;

import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action.ActionOption;

/**
 * Abstract class describing an action the robot can perform in autonomous.
 * These roughly correspond to WPILib Commands. Each {@code Action} allows the
 * selection of one of several possible action to perform
 * 
 * @author Matthew Reynolds
 *
 * @param <T>
 *            The {@link ActionOption} that the implementing subclass implements
 * @see Action.ActionOption
 * @see Action.Behaviour
 */
public abstract class Action<T extends Enum<T> & ActionOption> {

	/**
	 * {@code ActionOption} is implemented in each class that inherits from
	 * {@code Action}. It describes the specific action that this {@code Action}
	 * will perform. For example, a {@code ClawAction} might have options to close
	 * and open the claw
	 * 
	 * @author Matthew Reynolds
	 *
	 */
	public interface ActionOption {
	}

	/**
	 * {@code Behaviour} describes the way the actions are run when they are
	 * converted to WPILib commands on the robot.
	 * <li>{@code kSequential} is used for running commands sequentially, one after
	 * anther
	 * <li>{@code kParallel} is used for running commands simultaneously
	 * 
	 * @author Matthew Reynolds
	 *
	 */
	public static enum Behaviour {
		kSequential, kParallel
	}

	/**
	 * Create an {@code Action} with the specified {@link ActionOption}
	 * 
	 * @param option
	 *            the option to set
	 */
	protected Action(T option) {
		this.option = option;
	}

	public Enum<T> option;
	public Behaviour behaviour = Behaviour.kSequential;
	public double delay = 0.0;
	public double timeout = -1.0;
	public double data = 0.0;

	/**
	 * @return the human-readable name of the {@code Action}. For example, "Claw",
	 *         "Arm", "Elevator", etc
	 */
	public abstract String getName();

	/**
	 * @return the default {@code ActionOption} if no other option is specified
	 */
	public abstract T getDefaultOption();

	/**
	 * @return the human-readable label for the usage of the {@code data} field. For
	 *         example, "Height", "Speed", etc
	 */
	public abstract String getDataLabel();

	@SuppressWarnings("unchecked")
	public Action<T> clone() {
		Action<T> a = null;

		try {
			a = this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		a.option = option;
		a.behaviour = behaviour;
		a.delay = delay;
		a.timeout = timeout;
		a.data = data;

		return a;
	}

}