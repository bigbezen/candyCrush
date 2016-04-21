package CandyCrashGame;

import javax.swing.ImageIcon;

import CandyCrashGame.Candy.Color;

public class WrappedCandy extends Candy {

	public WrappedCandy(Board board, Color color) {
		this.board = board;
		this.color = color;
	}

	/*
	 * call 3X3 crash 2 times
	 */
	@Override
	public void visit(RegularCandy regular) {
		
	}

	@Override
	public void visit(StripCandyVertical stripV) {
		crash3rows3cols(stripV);
	}

	@Override
	public void visit(StripCandyHorizontal stripH) {
		crash3rows3cols(stripH);
	}

	/*
	 * crash 5X5 matrix around the source candy
	 */
	@Override
	public void visit(WrappedCandy candy) {
		// set the positions to start and end the crash
		int iStart = this.row - 2;
		int jStart = this.col - 2;
		int iEnd = this.row + 2;
		int jEnd = this.col + 2;

		// loop to crash the candies in the field
		for (int i = iStart; i <= iEnd; i++) {
			for (int j = jStart; j <= jEnd; j++) {
				if (isLegal(i, j) && board.getCandyBoard()[i][j] != null)
					board.getCandyBoard()[i][j].crash();
			}
		}
	}

	/*
	 * use ColorBomb combine (same)
	 */
	@Override
	public void visit(ColorBomb bomb) {
		bomb.visit(this);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
	
	private void crash3X3(){
		Candy[][] cb = board.getCandyBoard();
		// set the limits of the position 3X3
		int iStart = this.row - 1, iEnd = this.row + 1, jStart = this.col - 1, jEnd = this.col + 1;

		for (int i = iStart; i <= iEnd; i++) {
			for (int j = jStart; j <= jEnd; j++) {
				if (isLegal(i, j)) {
					if (cb[i][j] == this)
						remove();
					else if (cb[i][j] != null)
						cb[i][j].crash();
				}
			}// for j
		}// for i
	}// crash3X3()
	

	@Override
	public void crash() {
		crash3X3();
		board.chainReaction();
		crash3X3();
	}	

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon("pictures/wrapped" + color.toString() + ".png");
	}

}// class
