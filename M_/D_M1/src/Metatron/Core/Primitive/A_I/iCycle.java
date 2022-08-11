package Metatron.Core.Primitive.A_I;

import java.io.IOException;

public interface iCycle {

	public void next() throws IOException;

	public default void resume() {

	}

	public default void pause() {

	};

	public default void terminate() {

	};
}