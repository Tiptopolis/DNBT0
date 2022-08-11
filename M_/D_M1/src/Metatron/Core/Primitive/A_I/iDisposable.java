package Metatron.Core.Primitive.A_I;

import java.io.Closeable;
import java.io.IOException;

public interface iDisposable {

	
	public void init();

	
	public default void dispose() {
		if (this instanceof Closeable)
			try {
				((Closeable) this).close();
			} catch (IOException e) {
				System.out.println(this.getClass().getSimpleName() + " cannot close for some reason :(");
			}
	}

}
