package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class MultiButton extends Button {

	private final GenericHID m_joystick;
	private final int[] m_buttonNumbers;

	/**
	 * Create a group of joystick button for triggering a command. <br>
	 * <br>
	 * <b> Note that {@code MultiButton}s will only fire
	 * {@link Button#whenReleased(edu.wpi.first.wpilibj.command.Command)} once ALL
	 * buttons in the group have been released!</b>
	 *
	 * @param joystick
	 *            The GenericHID object that has the button (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param buttonNumbers
	 *            The button numbers (see {@link GenericHID#getRawButton(int) }
	 */
	public MultiButton(GenericHID joystick, int... buttonNumbers) {
		// mmmmmmmmmmmmmm check that vararg tho ^

		m_joystick = joystick;
		m_buttonNumbers = buttonNumbers;
	}

	/**
	 * Gets the value of the joystick buttons.
	 *
	 * @return {@code true} if any one of the buttons is active
	 */
	public boolean get() {
		for (int i : m_buttonNumbers)
			if (m_joystick.getRawButton(i))
				return true;

		return false;
	}

}
