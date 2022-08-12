package com.Rev.Core.Primitive;

import com.Rev.Core.Primitive.A_I.iCollection;

public class BubbleSorter {

	iCollection<Integer> subject;

	public int bubbleIndex = 0;
	private int bubbleNext = 0; // (bubbleIndex+1)%subject.getSize();

	private int min = Integer.MAX_VALUE;
	private int max = 0;

	public BubbleSorter(aList L) {
		this.subject = L;
	}

	public void sortAscending() {
		
		int tmp = 0;
		for (int i = 0; i < subject.getSize(); i++) {
			for (int j = 1; j < (subject.getSize() - i); j++) {
				if (subject.get(j - 1) > subject.get(j)) {
					tmp = subject.get(j - 1);
					subject.setAt((j - 1), subject.get(j));
					subject.setAt(j, tmp);
				}

			}
		}
	}

	public void sortDescending() {

		int tmp = 0;
		for (int i = 0; i < subject.getSize(); i++) {
			for (int j = 1; j < (subject.getSize() - i); j++) {
				if (subject.get(j - 1) < subject.get(j)) {
					tmp = subject.get(j - 1);
					subject.setAt((j - 1), subject.get(j));
					subject.setAt(j, tmp);
				}

			}
		}
	}

}