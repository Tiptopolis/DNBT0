package Core._Console;

import static Core.AppUtils.*;

import Core._Banko.Views.MainMenu;
import Core._Console.UI.ConsoleView;
import Core._Console.UI.iConsoleListener;
import Core._PRIM.A_I.iCollection;

public class ConsoleUI implements iConsoleListener {

	public static ConsoleView CurrentView;
	public static ConsoleView PreviousView;

	public ConsoleUI() {
		this.setView(new MainMenu(this));
	}

	public void render() {
		CurrentView.renderFrame();
	}

	public void setView(ConsoleView view) {
		if (CurrentView != null) {
			PreviousView = CurrentView;
			CurrentView.dispose();			
		}
		Log("VIEW> " + view.getClass().getSimpleName());
		CurrentView = view;
		CurrentView.show();

	}
	
	public void back()
	{
		Log(">>>>>");
		if(PreviousView!=null)
			this.setView(PreviousView);
	}

	@Override
	public boolean input(String inp) {
		Log(this.getClass().getSimpleName() + ":> " + inp);

		if (CurrentView != null)
			return CurrentView.input(inp);
		return false;
	}

	@Override
	public iCollection<iConsoleListener> getSubscribers() {
		// TODO Auto-generated method stub
		return null;
	}

}
