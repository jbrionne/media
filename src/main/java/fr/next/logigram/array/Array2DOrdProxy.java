package fr.next.logigram.array;

import java.util.List;

public class Array2DOrdProxy<T, K> implements Array2DOrd<T, K> {

	private Array2DOrd<T, K> base;

	public Array2DOrdProxy(Array2DOrd<T, K> base) {
		this.base = base;
	}

	@Override
	public void setValue(K valueDomLine, K valueDomCol, T value) {
		this.base.setValue(valueDomCol, valueDomLine, value);
	}

	@Override
	public void setValue(int indexLine, int indexCol, T value) {
		this.base.setValue(indexCol, indexLine, value);

	}

	@Override
	public T getValue(int indexLine, int indexCol) {
		return this.base.getValue(indexCol, indexLine);
	}

	@Override
	public T[] getLine(int indexLine) {
		return this.base.getCol(indexLine);
	}

	@Override
	public T[] getCol(int indexCol) {
		return this.base.getLine(indexCol);
	}

	@Override
	public Axe<AxeValue<K>> getAxeLine() {
		return this.base.getAxeCol();
	}

	@Override
	public Axe<AxeValue<K>> getAxeCol() {
		return this.base.getAxeLine();
	}

	@Override
	public T getValue(K valueDomLine, K valueDomCol) {
		return this.base.getValue(valueDomCol, valueDomLine);
	}

	@Override
	public Coordinates getCoordinates() {
		return this.base.getCoordinates();
	}

	@Override
	public List<T> getAll() {
		return this.base.getAll();
	}

}
