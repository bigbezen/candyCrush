package CandyCrashGame;

import javax.swing.ImageIcon;

import CandyCrashGame.Candy.Color;

public class StripCandyHorizontal extends Candy {

	public StripCandyHorizontal(Board board, Color color) {
		this.board = board;
		this.color = color;
	}

	@Override
	public void visit(RegularCandy regular) {

	}

	/*
	 * need to crash plus
	 */
	@Override
	public void visit(StripCandyVertical stripV) {
		crashPlus(stripV);
	}

	/*
	 * need to crash plus
	 */
	@Override
	public void visit(StripCandyHorizontal stripH) {
		crashPlus(stripH);
	}

	/*
	 * crash 3 rows ans 3 columns
	 */
	@Override
	public void visit(WrappedCandy candy) {
		crash3rows3cols(candy);
	}

	/*
	 * the same as ColorBomb
	 */
	@Override
	public void visit(ColorBomb bomb) {
		bomb.visit(this);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	/*
	 * crash Horizontal all the candies in the row
	 */
	@Override
	public void crash() {
		Candy[][] cb = board.getCandyBoard();
		for (int j = 0; j < cb[this.row].length; j++) {
			if (cb[this.row][j] == this)
				remove();
			else if (cb[this.row][j] != null)
				cb[this.row][j].crash();
		}
	}

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon("pictures/horizontal" + color.toString() + ".png");
	}
}
