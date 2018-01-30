package org.usfirst.frc.team4946.robot.pathplanning;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.usfirst.frc.team4946.robot.pathplanning.data.ScriptBundle;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DelayAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;
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
	private static ArrayList<Action<?>> loadScript(Element parent) {
		ArrayList<Action<?>> s = new ArrayList<>();

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
					StringTokenizer fileReader = new StringTokenizer(curEl.getTextContent());

					int numLeft = Integer.parseInt(fileReader.nextToken());
					for (int j = 0; j < numLeft; j++) {
						DriveAction.Segment segment = new DriveAction.Segment();

						segment.pos = Double.parseDouble(fileReader.nextToken());
						segment.vel = Double.parseDouble(fileReader.nextToken());
						segment.accel = Double.parseDouble(fileReader.nextToken());
						segment.jerk = Double.parseDouble(fileReader.nextToken());
						segment.heading = Double.parseDouble(fileReader.nextToken());
						segment.dt = Double.parseDouble(fileReader.nextToken());
						segment.x = Double.parseDouble(fileReader.nextToken());
						segment.y = Double.parseDouble(fileReader.nextToken());

						((DriveAction) curAction).addSegment(true, segment);
					}

					int numRight = Integer.parseInt(fileReader.nextToken());
					for (int j = 0; j < numRight; j++) {
						DriveAction.Segment segment = new DriveAction.Segment();

						segment.pos = Double.parseDouble(fileReader.nextToken());
						segment.vel = Double.parseDouble(fileReader.nextToken());
						segment.accel = Double.parseDouble(fileReader.nextToken());
						segment.jerk = Double.parseDouble(fileReader.nextToken());
						segment.heading = Double.parseDouble(fileReader.nextToken());
						segment.dt = Double.parseDouble(fileReader.nextToken());
						segment.x = Double.parseDouble(fileReader.nextToken());
						segment.y = Double.parseDouble(fileReader.nextToken());

						((DriveAction) curAction).addSegment(false, segment);
					}
				}

				s.add(curAction);
			}
		}

		return s;
	}
}
