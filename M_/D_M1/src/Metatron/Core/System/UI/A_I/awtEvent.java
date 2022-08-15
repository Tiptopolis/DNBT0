package Metatron.Core.System.UI.A_I;

import java.awt.AWTEvent;
import java.awt.Event;

import Metatron.Core.System.Event.iEvent;

public class awtEvent extends AWTEvent implements iEvent {

	public awtEvent(Object source, int id) {
		super(source, id);
	}

	@Override
	public iEvent get() {

		return this;
	}

}
