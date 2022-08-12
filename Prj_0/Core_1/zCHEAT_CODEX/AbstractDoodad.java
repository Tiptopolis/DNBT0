package Core.zCHEAT_CODEX;

import static Core.AppUtils.*;

public abstract class AbstractDoodad {

	public AbstractDoodad() {
		this.init();
	}

	public void init() {

	}

	//this.getClass() will return the class of the object instantiating this doodad
	protected void logTo() {
		Log("<" + this.getClass() + ">   instantiated AbstractDoodad");
	}

}
