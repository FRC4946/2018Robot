package org.usfirst.frc.team4946.robot.pathplanning.data;

import java.util.ArrayList;

import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;

public class Script {

	private ArrayList<Action<?>> script;

	public int selectedAction = -1;

	public static Script newScript() {
		Script sc = new Script();
		sc.addAction(new DriveAction());
		return sc;
	}
	
	public Script() {
		script = new ArrayList<>();
	}

	public ArrayList<Action<?>> getActions() {
		return script;
	}

	public void moveActionUp(Action<?> a) {
		int index = script.indexOf(a);
		if (index == 0)
			return;

		if (a == getSelectedAction())
			selectedAction = index - 1;

		script.remove(a);
		script.add(index - 1, a);

	}

	public void moveActionDown(Action<?> a) {
		int index = script.indexOf(a);
		if (index == script.size() - 1)
			return;

		if (a == getSelectedAction())
			selectedAction = index + 1;

		script.remove(a);
		script.add(index + 1, a);
	}

	public void removeAction(Action<?> a) {

		if (a == getSelectedAction())
			selectedAction = -1;

		script.remove(a);
	}

	public void addAction(Action<?> a) {

		if (a instanceof DriveAction)
			selectedAction = script.size();

		script.add(a);
	}

	public void setSelectedAction(Action<?> a) {
		selectedAction = script.indexOf(a);
	}

	public Action<?> getSelectedAction() {
		if (selectedAction == -1)
			return null;
		return script.get(selectedAction);
	}

	public Action<?> getAction(int index) {
		return script.get(index);
	}

}
