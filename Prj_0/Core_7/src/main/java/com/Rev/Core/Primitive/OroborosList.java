package com.Rev.Core.Primitive;

public class OroborosList<T> extends aList<T> {

	public float theta = 1; // increment size, points added/removed each frame
	public float maxTheta = 6; // will fill to maxTheta before removal happens
	public float scale = 1;

	public float I = 0;

	public OroborosList() {
		super();
		this.theta = 1;
		this.maxTheta = 32;
		this.scale = 1;

	}

	public OroborosList(float theta, float max, float scale) {
		super();
		this.theta = theta;
		this.maxTheta = max;
		this.scale = scale;

	}

	@Override
	public void append(T t) {
		this.step();
		float s = this.getSize();
		int iN = (int) (maxTheta * scale);
		float g = (this.theta * this.scale);
		// if(((currentSize-1)%(max)) + ((max)%(currentSize+1))<(max)) //generating set?
		if (((s - g) % iN) + (iN % (s + g)) < (iN)) {
			this.I++;
			this.I = (I % iN);
			super.append(t);
		}

	}

	protected void step() {
		if (!this.isEmpty()) {
			if ((this.getSize() % this.maxTheta) == 0) {
				for (int i = 0; i < (this.theta * scale); i++) {
					T index = this.get(i);
					this.remove(index);
				}
			}
		}
	}

}
