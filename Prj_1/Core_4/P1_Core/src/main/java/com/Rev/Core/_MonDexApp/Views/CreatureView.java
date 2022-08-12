package com.Rev.Core._MonDexApp.Views;

import static com.Rev.Core.AppUtils.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.Rev.Core.Console.ConsoleUI;
import com.Rev.Core.Console.UI.aConsoleView;
import com.Rev.Core.Math.Maths;
import com.Rev.Core.Primitive.aMap;
import com.Rev.Core.Primitive.Data.JSON.JSONObject;
import com.Rev.Core.Util.StringUtils;
import com.Rev.Core._MonDexApp.MonDirector;
import com.Rev.Core._MonDexApp.Data._Creature;
import com.Rev.Core._aBankApp.BankDirector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreatureView extends aConsoleView {

	// Account Interactor
	// deposit, withdraw

	private _Creature as;

	private float deltaBal = 0f;

	private boolean dioD = false;
	private boolean dioW = false;

	private boolean inputInvalid = false;

	public CreatureView(ConsoleUI manager, _Creature as) {
		super(manager);
		this.as = as;
	}

	@Override
	public void init() {
		super.init();

		// this.options = new aMap<String, String>();
		// this.options.put("^", "OUT");
		// this.options.put("X", "EXIT");

		// super.init();
		this.options = new aMap<String, String>();
		this.options.put("^", "OUT");
		this.options.put("<", "BACK");
		this.options.put("X", "EXIT");

		this.options.put("1", "DEPOSIT");
		this.options.put("2", "WITHDRAW");
		this.options.put("$", "SAVE");
		// this.options.put("2", "CLOSE ACCT"); //call customer service for that

	}

	/////////////////

	// print to console
	@Override
	public void render() {
		super.render();

		Log(this.as.toString());
		Log("___________");
		Log(this.options.toString());
		Log("");
		if (inputInvalid)
			Log("*INVALID INPUT <<<");

		this.inputInvalid = false;
		this.dioD = false;
		this.dioW = false;
	}

	// handle incoming console input
	@Override
	public boolean handle(String inp) {

		if (!dioD && !dioW)
			if (super.handle(inp))
				return true;

		if (dioD) {
			if (StringUtils.validAmount(inp)) {
				float f = Float.parseFloat(inp);
				this.deposit(Maths.round(f, 2));
			} else {
				this.inputInvalid = true;
				ThrowFancyException(" >>INVALID AMOUNT<<");
				return false;
			}

		}

		if (dioW) {

			//
			if (StringUtils.validAmount(inp)) {
				float f = Float.parseFloat(inp);
				this.withdraw(f);
			} else {
				this.inputInvalid = true;
				ThrowFancyException(" >>INVALID AMOUNT<<");
				return false;
			}

		}

		if (inp.equals("1")) {
			Log("*DEPOSIT");
			Log("ENTER AMOUNT: ");
			this.dioD = true;
			return dioD;
		}

		if (inp.equals("2")) {
			Log("*WITHDRAW");
			Log("ENTER AMOUNT: ");
			this.dioW = true;
			return dioW;
		}

		if (inp.equals("$")) {
			Log("SAVING: ");
			this.serialize();
			return false;
		}

		return false;
	}

	//////
	//

	public void deposit(float amt) {
		this.crebit(Math.abs(amt));
	}

	public void withdraw(float amt) {
		this.crebit(-(Math.abs(amt)));
	}

	private void crebit(float amt) {
		// submit lol
		// refresh & comit

		this.deltaBal += amt;
		float current = as.Balance(as.Balance() + deltaBal);
		this.deltaBal = 0;
		MonDirector.Creatures.update(as.dbIndex(), as);
	}

	public void serialize()
	{
		Log("<< " + as.getClass().getSimpleName());
		JSONObject o = new JSONObject();
		o.put("Label", "Creature");
		o.put("wut",as);
		Log(o.toString()); // isthere
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(new File("src/main/resources/data/Creature.json"), as);
			Log(mapper.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
	}

	public void serializeX() {
		Log("<< " + as.getClass().getSimpleName());
		JSONObject o = new JSONObject();
		o.put("Label", "Creature");
		o.put("wut", as);
		Log(o.toString()); // isthere
		ObjectMapper mapper = new ObjectMapper();
		/*
		 * try { String j = mapper.writeValueAsString(o.toString()); Log(" >> " + j); }
		 * catch (JsonProcessingException e1) { e1.printStackTrace(); }
		 */

		/*
		 * try {
		 * 
		 * // Writing to a file Log("TRY SAVE>"); mapper.writeValue(new
		 * File("src/main/resources/data/Creature.json"), o);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 */

		try {

			// Writing to a file
			File file = new File("src/main/resources/data/Creature.json");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			System.out.println("Writing JSON object to file");
			System.out.println("-----------------------");
			System.out.print(o);

			fileWriter.write(o.toJSONString());
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
