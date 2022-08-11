package Metatron.Core.Primitive.Struct;

import java.io.IOException;

import Metatron.Core.Primitive.A_I.iCycle;

public class _RingBuffer<T> extends _Array<T> implements iCycle{

	public int max = 32;
	private int step = 1; // insertion/deletion size, each step is appended/removed # times
	
	
	
	public boolean isFull() {
		return this.size() % this.max == 0;
	}

	@Override
	public void append(T t) {
		try {
			this.next();
			for (int i = 0; i < step; i++) {
				super.append(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void next() throws IOException {
		if (!this.isEmpty())
			if (this.isFull())
				for (int i = 0; i < step; i++)
					this.remove(0);
		
	}

}
