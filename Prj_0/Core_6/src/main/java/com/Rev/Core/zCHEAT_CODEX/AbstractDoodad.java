package com.Rev.Core.zCHEAT_CODEX;

//import static Core.AppUtils.*;

public abstract class AbstractDoodad {

	public AbstractDoodad() {
		this.init();
	}

	public void init() {

	}

	// this.getClass() will return as the class of the object instantiating this
	// doodad
	protected void logTo() {
		// Log("<" + this.getClass() + "> instantiated AbstractDoodad");
		System.out.println("<" + this.getClass() + ">   instantiated AbstractDoodad");
	}

	private static void demonstrate() {
		AbstractDoodad A = new AbstractDoodad() {
			@Override
			public void init() {
				this.logTo();
			}

		}; // <this is crucial, dont forget it. still inside the pvt stc vd demo() mthd;
			// end of @Override pbl vd init();
		A.logTo();
	}

}
