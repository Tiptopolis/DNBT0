package Metatron.Core.System.UI;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;

import Metatron.Core.Primitive.A_I.iSpace;
import Metatron.Core.System.GFX.aCanvas;
import Metatron.Core.System.GFX.A_I.iRenderTarget;

public class aFrame extends JFrame {

	// blank frame containing only a canvas
	public iRenderTarget mainBuffer;
	protected boolean repaint = true;

	public aFrame(String title)
	{
		super(title);
		this.mainBuffer = new aCanvas();
	}
	
	public aFrame(String title,iRenderTarget c) {
		this(title);
		this.mainBuffer = c;
	}



}
