package com.bitstd.utils;

public class TagsList {
	private String[] data;
	private int size = 0;

	public TagsList(int size) {
		data = new String[size];
	}

	public TagsList() {
		this(10);
	}

	public void add(String str) {
		ensureCapacity(size + 1);
		data[size++] = str;
	}

	public String get(int index) {
		if (index < size)
			return data[index];
		else
			return null;
	}

	public boolean remove(String str) {
		for (int index = 0; index < size; index++) {
			if (str.equals(data[index])) {
				data[index] = null;
				return true;
			}
		}
		return false;
	}

	public boolean remove(int index) {
		if (index < data.length) {
			data[index] = null;
			return true;
		}
		return false;
	}

	public int size() {
		return this.size;
	}

	public void ensureCapacity(int minSize) {
		int oldCapacity = data.length;
		if (minSize > oldCapacity) {
			int newCapacity = (oldCapacity * 3 / 2 + 1) > minSize ? oldCapacity * 3 / 2 + 1 : minSize;
			String[] newArray = new String[newCapacity];
			for (int i = 0; i < data.length; i++) {
				newArray[i] = data[i];
			}
			data = newArray;
		}
	}

}
