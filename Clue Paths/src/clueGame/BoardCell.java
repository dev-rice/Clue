package clueGame;

public abstract class BoardCell {
	private int row;
	private int column;

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	public boolean isWalkway(){
		return false;
	}
	public boolean isRoom(){
		return false;
	}
	public boolean isDoorway(){
		return false;
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return "BoardCell (" + row + ", " + column + ")";
	}
	
	public abstract void draw();
	
	
	
}
