package org.usfirst.frc.team4946.robot.pathplanning;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.usfirst.frc.team4946.robot.pathplanning.data.Script;
import org.usfirst.frc.team4946.robot.pathplanning.data.ScriptBundle;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DelayAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction.Segment;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.ElevatorAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.IntakeAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.OutputAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileIO {

	public static ScriptBundle loadScript(File file) throws ParserConfigurationException, SAXException, IOException {
		ScriptBundle scripts = new ScriptBundle();

		// Read the contents of the file into the Document object `doc`
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		// Optional, but recommended. See here for info:
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		scripts.name = ((Element) doc.getElementsByTagName("script").item(0)).getAttribute("name");

		// // All of the nodes of the main element (the "script" element)
		// NodeList nodeList = doc.getFirstChild().getChildNodes();

		Element ll = (Element) doc.getElementsByTagName("ll").item(0);
		Element lr = (Element) doc.getElementsByTagName("lr").item(0);
		Element rl = (Element) doc.getElementsByTagName("rl").item(0);
		Element rr = (Element) doc.getElementsByTagName("rr").item(0);

		scripts.LL = loadScript(ll);
		scripts.LR = loadScript(lr);
		scripts.RL = loadScript(rl);
		scripts.RR = loadScript(rr);

		// Return the newly-read data
		return scripts;
	}

	@SuppressWarnings("unchecked")
	private static Script loadScript(Element parent) {
		Script s = new Script();

		NodeList list = parent.getElementsByTagName("action");

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element curEl = (Element) list.item(i);

				@SuppressWarnings("rawtypes")
				Action curAction = null;

				String type = curEl.getAttribute("type");
				switch (type) {
				case "Delay":
					curAction = new DelayAction();
					break;
				case "Elevator":
					curAction = new ElevatorAction();
					break;
				case "Intake":
					curAction = new IntakeAction();
					break;
				case "Output":
					curAction = new OutputAction();
					break;
				case "Drive":
					curAction = new DriveAction();
					break;
				}

				if (curAction == null)
					continue;

				curAction.options = Enum.valueOf(curAction.options.getDeclaringClass(), curEl.getAttribute("options"));
				curAction.behaviour = Enum.valueOf(curAction.behaviour.getDeclaringClass(),
						curEl.getAttribute("behaviour"));
				curAction.data = Integer.parseInt(curEl.getAttribute("data"));
				curAction.timeout = Integer.parseInt(curEl.getAttribute("timeout"));

				if (curAction instanceof DriveAction) {
					// TODO: Parse

					// NodeList waypoints = curEl.getElementsByTagName("waypoint");
					// for (int j = 0; j < waypoints.getLength(); j++) {
					// if (waypoints.item(j).getNodeType() == Node.ELEMENT_NODE) {
					//
					// Waypoint pt = new Waypoint();
					// Element curPtEl = (Element) waypoints.item(j);
					//
					// pt.setX(Double.parseDouble(curPtEl.getAttribute("x")));
					// pt.setY(Double.parseDouble(curPtEl.getAttribute("y")));
					// pt.setR(Double.parseDouble(curPtEl.getAttribute("radius")));
					// pt.setHeading(Double.parseDouble(curPtEl.getAttribute("heading")));
					// pt.setAutomaticHeading(curPtEl.getAttribute("autoHeading") == "true" ? true :
					// false);
					// pt.setMagnet(curPtEl.getAttribute("magnet") == "true" ? true : false);
					//
					// ((DriveAction) curAction).waypoints.add(pt);
					// }
					// }
				}

				s.addAction(curAction);
			}
		}

		return s;
	}

	// private static String printPath(DriveAction a) {
	// String path = "";
	// path += a.left.size() + "\n";
	// for (Segment s : a.left) {
	// path += s.pos + " ";
	// path += s.vel + " ";
	// path += s.accel + " ";
	// path += s.jerk + " ";
	// path += s.heading + " ";
	// path += s.dt + " ";
	// path += s.x + " ";
	// path += s.y + "\n";
	// }
	// path += a.right.size() + "\n";
	// for (Segment s : a.left) {
	// path += s.pos + " ";
	// path += s.vel + " ";
	// path += s.accel + " ";
	// path += s.jerk + " ";
	// path += s.heading + " ";
	// path += s.dt + " ";
	// path += s.x + " ";
	// path += s.y + "\n";
	// }
	//
	// return path;
	// }
}
