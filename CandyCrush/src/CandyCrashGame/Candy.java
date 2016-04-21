package CandyCrashGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Candy implements Visitor, Visited {

	public enum Color {
		BLUE, RED, GREEN, YELLOW, PURPLE, ORANGE
	};

	protected Color color;
	protected Board board;
	// indexes of i and j
	protected int row;
	protected int col;

	public void combine(Candy candy) {
		candy.accept(this);
	}

	public abstract void crash();
	
	public abstract ImageIcon getIcon();

	public Color getColor() {
		return color;
	}

	public void setCoord(int i, int j) {
		row = i;
		col = j;
	}

	protected void remove() {
		board.calcScore(10);
		board.getCandyBoard()[row][col] = null;
	}

	/*
	 * return true if the indexes i and j in the matrix
	 */
	protected boolean isLegal(int i, int j) {
		Candy[][] cb = board.getCandyBoard();
		return (i < cb.length & i >= 0) && (j < cb[i].length & j >= 0);
	}

	/*
	 * crashes 3 rows and 3 columns from the source candy position
	 */
	protected void crash3rows3cols(Candy other) {
		// TODO check if original 3 rows and columns has been deleted
		Candy[][] candyBoard = board.getCandyBoard();
		// delete 3 rows
		// upper row
		if (this.row - 1 >= 0) {
			for (int j = 0; j < candyBoard[this.row - 1].length; j++) {
				if (candyBoard[this.row - 1][j] != null)
					candyBoard[this.row - 1][j].crash();
			}
		}
		// center row
		for (int j = 0; j < candyBoard[this.row].length; j++) {
			if (candyBoard[this.row][j] != null)
				candyBoard[this.row][j].crash();
		}
		// lower row
		if (this.row + 1 < candyBoard.length) {
			for (int j = 0; j < candyBoard[this.row + 1].length; j++) {
				if (candyBoard[this.row + 1][j] != null)
					candyBoard[this.row + 1][j].crash();
			}
		}
		// delete 3 columns
		// left column
		if (this.col - 1 >= 0) {
			for (int i = 0; i < candyBoard.length; i++) {
				if (candyBoard[i][this.col - 1] != null)
					candyBoard[i][this.col - 1].crash();
			}
		}
		// center column
		for (int i = 0; i < candyBoard.length; i++) {
			if (candyBoard[i][this.col] != null)
				candyBoard[i][this.col].crash();
		}
		// right column
		if (this.col + 1 < candyBoard[this.row].length) {
			for (int i = 0; i < candyBoard.length; i++)
				if (candyBoard[i][this.col + 1] != null)
					candyBoard[i][this.col + 1].crash();
		}
	}// crash3rows3cols

	/*
	 * crashes the candies + shape on the source candy (this)
	 */
	protected void crashPlus(Candy other) {
		Candy[][] cb = board.getCandyBoard();
		// crash row
		for (int j = 0; j < cb[this.row].length; j++) {
			if (cb[this.row][j] != null)
				cb[this.row][j].crash();
		}
		// crash column
		for (int i = 0; i < cb.length; i++) {
			if (cb[i][this.col] != null)
				cb[i][this.col].crash();
		}
	}

}// class
