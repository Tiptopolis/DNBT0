package Metatron.Core.System.GFX;

import java.awt.Canvas;
import java.awt.Graphics;

public class aCanvas extends Canvas {

	protected boolean repaint = true;

	@Override
	public void paint(Graphics g) {
		if (repaint)
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
	}
}
