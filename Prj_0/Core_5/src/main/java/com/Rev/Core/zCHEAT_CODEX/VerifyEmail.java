package com.Rev.Core.zCHEAT_CODEX;

public class VerifyEmail {
	// This sucks, it works but I should ask if there is a better way
	public static boolean verify(String s) {
		char[] charArr = s.toCharArray();
		// Checks if email address is valid
		if (s.matches("^[a-zA-Z0-9._%+!#$&'*/=?^`{|}~-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")) {
			// Checks to make sure a dot (.) is not the first or last character in the first
			// portion of the email
			if (charArr[0] == '.' | charArr[s.indexOf("@") - 1] == '.') {
				return false;
			}
			// Checks to make sure the first character after the @ isn't a dot or hyphen
			if (charArr[s.indexOf("@") + 1] == '.' | charArr[s.indexOf("@")] + 1 == '-') {
				return false;
			}
			// Checks to make sure the character before the .com isn't a dot or hyphen
			if (charArr[s.lastIndexOf(".") - 1] == '.' | charArr[s.lastIndexOf(".") - 1] == '-') {
				return false;
			}
			return true;
		}

		return false;
	}
}