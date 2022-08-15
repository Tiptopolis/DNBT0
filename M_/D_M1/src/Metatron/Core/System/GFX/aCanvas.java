package Metatron.Core.System.GFX;

import java.awt.Canvas;
import java.awt.Graphics;

import Metatron.Core.System.GFX.A_I.iRenderTarget;

public class aCanvas extends Canvas implements iRenderTarget {

	protected boolean repaint = true;

	@Override
	public void init() {
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		if (repaint)
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
	}


}
