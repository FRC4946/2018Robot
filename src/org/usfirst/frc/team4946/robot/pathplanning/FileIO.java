package org.usfirst.frc.team4946.robot.pathplanning;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.usfirst.frc.team4946.robot.pathplanning.data.ScriptBundle;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.Action.Behaviour;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.ArmAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DelayAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.DriveAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.ElevatorAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.IntakeAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.OutputAction;
import org.usfirst.frc.team4946.robot.pathplanning.data.actions.TurnAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FileIO {

	public static DecimalFormat f = new DecimalFormat("0.00");

	public static File lastFileModified(String dir) {
		return lastFileModified(dir, ".xml");
	}

	public static File lastFileModified(String dir, String extension) {

		// List all the xml files in the directory
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile() && file.toString().toLowerCase().endsWith(extension);
			}
		});

		// If there are no valid files, return null
		if (files == null)
			return null;

		// Get the last modified file
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}

	public static ScriptBundle loadScript(File file) throws ParserConfigurationException, SAXException, IOException {
		ScriptBundle scripts = new ScriptBundle();

		// Read the contents of the file into the Document object `doc`
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = dBuilder.parse(file);

		// Optional, but recommended. See here for info:
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		scripts.name = ((Element) doc.getElementsByTagName("script").item(0)).getAttribute("name");
		scripts.notes = ((Element) doc.getElementsByTagName("script").item(0)).getAttribute("notes");

		// // All of the nodes of the main element (the "script" element)
		// NodeList nodeList = doc.getFirstChild().getChildNodes();

		Element ll = (Element) doc.getElementsByTagName("ll").item(0);
		Element lr = (Element) doc.getElementsByTagName("lr").item(0);
		Element rl = (Element) doc.getElementsByTagName("rl").item(0);
		Element rr = (Element) doc.getElementsByTagName("rr").item(0);

		scripts.LL = (ll == null) ? new ArrayList<>() : loadScript(ll);
		scripts.LR = (lr == null) ? new ArrayList<>() : loadScript(lr);
		scripts.RL = (rl == null) ? new ArrayList<>() : loadScript(rl);
		scripts.RR = (rr == null) ? new ArrayList<>() : loadScript(rr);

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
				case "Arm":
					curAction = new ArmAction();
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
				case "Turn":
					curAction = new TurnAction();
					break;
				}

				if (curAction == null)
					continue;

//				curAction.options = Enum.valueOf(curAction.options.getDeclaringClass(), curEl.getAttribute("options"));
//				curAction.behaviour = Enum.valueOf(curAction.behaviour.getDeclaringClass(),
//						curEl.getAttribute("behaviour"));
//				curAction.delay = Double.parseDouble(curEl.getAttribute("delay"));
//				curAction.data = Double.parseDouble(curEl.getAttribute("data"));
//				curAction.timeout = Double.parseDouble(curEl.getAttribute("timeout"));

				try {
					curAction.options = Enum.valueOf(curAction.options.getDeclaringClass(),
							curEl.getAttribute("options"));
				} catch (IllegalArgumentException e) {
					System.out.println("ERROR LOADING SCRIPT!");
					curAction.options = curAction.getDefaultOption();
				}

				try {
					curAction.behaviour = Enum.valueOf(curAction.behaviour.getDeclaringClass(),
							curEl.getAttribute("behaviour"));
				} catch (IllegalArgumentException e) {
					System.out.println("ERROR LOADING SCRIPT!");
					curAction.behaviour = Behaviour.kSequential;
				}
				curAction.delay = Double.parseDouble(curEl.getAttribute("delay") + "0");
				curAction.data = Double.parseDouble(curEl.getAttribute("data") + "0");
				curAction.timeout = Double.parseDouble(curEl.getAttribute("timeout") + "0");
				
				
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
