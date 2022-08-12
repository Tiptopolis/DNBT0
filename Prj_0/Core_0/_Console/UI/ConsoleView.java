package Core._Console.UI;

public class ConsoleView {

	iConsoleListener InputFocus;

	public void input(String inp) {
		if (this.InputFocus != null)
			InputFocus.input(inp);
	}

	public void clearFocus() {
		this.InputFocus = null;
	}
}
