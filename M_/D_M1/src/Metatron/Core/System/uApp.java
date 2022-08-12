package Metatron.Core.System;

import java.io.IOException;

import Metatron.Core.Math.Primitive.aVector;
import Metatron.Core.System.A_I.iApplet;
import Metatron.Core.System.ECS.aEnvironment;

public class uApp extends aShell implements iApplet {

	// memory registers & Object buffers

	protected boolean running = false;

	@Override
	public void create() {

	}

	@Override
	public void next() throws IOException {
		if (running)
			try {
				this.step(0f);
			} finally {
				this.dispose();
			}

	}

	@Override
	public void step(Float delta) {

	}

	@Override
	public void resize(Number... basis) {

	}

	@Override
	public aVector size() {
		return null;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

}
