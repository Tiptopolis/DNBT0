package Metatron.Core.System;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import Metatron.Core.Primitive.aNode;
import Metatron.Core.Primitive.iFunctor;
import Metatron.Core.Primitive.A_I.iComponent;
import Metatron.Core.System.ECS.aComponent;
import Metatron.Core.System.Event.iEvent;

public class IO_Port<I,O> implements iComponent, iFunctor<iEvent> {

	protected BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	protected BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

	protected aNode shell;
	
	protected I lastIn; //E -> shell
	protected O lastOut; //shell -> E

	public IO_Port(aNode shell) {
		this.shell = shell;
		shell.shared.put("COM",this);
	}

	public int read() throws IOException {
		int r = reader.read();
		
		
		
		return r;
	}

	public String readLine() throws IOException {
		return reader.readLine();

	}

	public void write(String str) throws IOException {
		this.writer.write(str);
	}

	public void writeLine(String str) throws IOException {
		this.write(str);
	}

	@Override
	public aNode getOwner() {
		return this.shell;
	}

	@Override
	public void setOwner(aNode owner) {
		this.shell = owner;
		
	}

}
