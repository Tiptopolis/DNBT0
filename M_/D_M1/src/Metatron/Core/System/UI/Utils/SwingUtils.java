package Metatron.Core.System.UI.Utils;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;

import javax.swing.JFrame;

import Metatron.Core.uChumpEngine;
import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.Primitive.Struct.aMap;

public class SwingUtils {

	public static aVector mousePos() {
		Point d = MouseInfo.getPointerInfo().getLocation();
		return new aVector(d.x, d.y);
	}

	public static JFrame getMainFrame() {
		return uChumpEngine.MainFrame;
	}

	public static aVector getRootWindowSize() {

		int w = (int) uChumpEngine.MainFrame.getSize().getWidth();
		int h = (int) uChumpEngine.MainFrame.getSize().getHeight();

		aVector v = new aVector(w, h);

		return v;
	}

	public static aVector getRootWindowPosition() {
		aVector v = new aVector();

		return v;
	}

	public static aMap<Component, aVector> mapComponentLocations(JFrame f) {
		aMap<Component, aVector> M = new aMap<Component, aVector>();
		M.put(f, new aVector(f.getLocation().x, f.getLocation().y));
		for (int i = 0; i < f.getComponentCount(); i++) {
			Point p = f.getComponent(i).getLocation();
			M.put(f.getComponent(i), new aVector(p.x, p.y));
		}

		return M;

	}

}
