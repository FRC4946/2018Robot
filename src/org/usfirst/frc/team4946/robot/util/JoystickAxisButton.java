package org.usfirst.frc.team4946.robot.util;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A {@link Button} that gets its state from a {@link GenericHID}.
 */
public class JoystickAxisButton extends Button {

	private final GenericHID m_joystick;
	private final int m_buttonNumber;

	private double m_activationPercent = 0.5;
	private boolean m_isInverted = false;

	
	/**
	 * Create a joystick axis for triggering commands.
	 *
	 * @param joystick
	 *            The GenericHID object that has the button (e.g. Joystick,
	 *            KinectStick, etc)
	 * @param buttonNumber
	 *            The axis number (see {@link GenericHID#getRawAxis(int) }
	 */
	public JoystickAxisButton(GenericHID joystick, int buttonNumber) {
		m_joystick = joystick;
		m_buttonNumber = buttonNumber;
	}

	public void setActivationPercent(double activation){
		m_activationPercent = activation;
	}
	
	public void setIsInverted(boolean isInverted){
		m_isInverted = isInverted;
	}
	
	/**
	 * Gets the value of the joystick axis.
	 *
	 * @return The value of the joystick axis
	 */
	public boolean get() {
		double axis = m_joystick.getRawAxis(m_buttonNumber);
		if(m_isInverted)
			axis *= -1.;
		
		return  axis > m_activationPercent;
	}

}
