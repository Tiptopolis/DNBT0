package com.Rev.Core.zCHEAT_CODEX;

import static com.Rev.Core.AppUtils.*;

import com.Rev.Core.Primitive.aList;
import com.Rev.Core.Primitive.aMap;
import com.Rev.Core.Primitive.aMultiMap;
import com.Rev.Core._Math.Maths;

public class ChangeMarching {

	public static aMap<String, Float> denominations = new aMap<String, Float>();

	static {

		denominations.put("Dollar", 1.0f);
		denominations.put("Quarter", 0.25f);
		denominations.put("Dime", 0.10f);
		denominations.put("Nickle", 0.05f);
		denominations.put("Penny", 0.01f);

	}

	public static void main(String[] args) {
		aList<Integer> L = dispense(104.66f);// <=enter the ammount
		for (int i = 0; i < L.getSize(); i++) {
			Log(denominations.getKeys().get(i) + ":" + L.get(i));
		}
	}

	// interpolate by largest possible increment, count of that ++
	public static aList<Integer> dispense(float amount) {
		aList<Integer> result = new aList<Integer>();
		for (int i = 0; i < 5; i++)
			result.append(0);
		float amtLeft = amount;

		while (amtLeft > 0.001) {
			Log(">>" + Maths.round(amtLeft, 2));
			if (Maths.round(amtLeft, 2) >= Maths.round(denominations.get("Dollar"), 2)) {
				amtLeft -= Maths.round(denominations.get("Dollar"), 2);
				int i = result.get(0).intValue() + 1;
				result.set(0, i);
			} else if (Maths.round(amtLeft, 2) >= Maths.round(denominations.get("Quarter"), 2)) {
				amtLeft -= Maths.round(denominations.get("Quarter"), 2);
				int i = result.get(1).intValue() + 1;
				result.set(1, i);
			} else if (Maths.round(amtLeft, 2) >= Maths.round(denominations.get("Dime"), 2)) {
				amtLeft -= Maths.round(denominations.get("Dime"), 2);
				int i = result.get(2).intValue() + 1;
				result.set(2, i);
			} else if (Maths.round(amtLeft, 2) >= Maths.round(denominations.get("Nickle"), 2)) {
				amtLeft -= Maths.round(denominations.get("Nickle"), 2);
				int i = result.get(3).intValue() + 1;
				result.set(3, i);
			} else if (Maths.round(amtLeft, 2) >= Maths.round(denominations.get("Penny"), 2)) {
				amtLeft -= Maths.round(denominations.get("Penny"), 2);
				int i = result.get(4).intValue() + 1;
				result.set(4, i);
			}

		}

		return result;

	}

}
