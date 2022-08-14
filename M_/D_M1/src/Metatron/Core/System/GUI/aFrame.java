package Metatron.Core.System.GUI;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;

import Metatron.Core.System.GFX.aCanvas;

public class aFrame extends JFrame {

	// blank frame containing only a canvas
	public Canvas canvas;
	protected boolean repaint = true;

	public aFrame(String title)
	{
		super(title);
		this.canvas = new aCanvas();
	}
	
	/*public aFrame(Canvas c) {
		this.canvas = c;
	}*/

}
