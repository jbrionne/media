package fr.next.logigram.array;

public class Coordinates {

	private int indexLine;
	private int indexCol;

	public Coordinates(int indexLine, int indexCol) {
		super();
		this.indexLine = indexLine;
		this.indexCol = indexCol;
	}

	public int getIndexLine() {
		return indexLine;
	}

	public void setIndexLine(int indexLine) {
		this.indexLine = indexLine;
	}

	public int getIndexCol() {
		return indexCol;
	}

	public void setIndexCol(int indexCol) {
		this.indexCol = indexCol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + indexCol;
		result = prime * result + indexLine;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (indexCol != other.indexCol)
			return false;
		if (indexLine != other.indexLine)
			return false;
		return true;
	}

}
