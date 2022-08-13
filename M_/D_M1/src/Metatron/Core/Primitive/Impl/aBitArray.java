package Metatron.Core.Primitive.Impl;

import Metatron.Core.Primitive.Struct._Array;

public class aBitArray extends _Array<Boolean> {

	public aBitArray(String bin) {
		for (int i = 0; i < bin.length(); i++) {
			if (bin.charAt(i) == '0')
				this.append(false);
			switch (bin.charAt(i)) {
			case '0' -> this.append(false);
			case '1' -> this.append(true);
			}

		}
	}

}
