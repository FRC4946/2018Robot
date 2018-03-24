package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A {@link Button} that gets its state from a {@link GenericHID}.
 */
public class POVButton extends Button {

	private final GenericHID m_joystick;
	private final int m_angle;

	/**
	 * Create a POV button for triggering commands.
	 *
	 * @param joystick
	 *            The GenericHID object that has the POV (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param angle
	 *            The POV angle, in degrees CW from up (see
	 *            {@link GenericHID#getPOV() }
	 */
	public POVButton(GenericHID joystick, int angle) {
		m_joystick = joystick;
		m_angle = angle;
	}

	public boolean get() {
		return m_joystick.getPOV(0) == m_angle;
	}

}
