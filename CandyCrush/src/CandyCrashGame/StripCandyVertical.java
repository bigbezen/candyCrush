package CandyCrashGame;

import javax.swing.ImageIcon;

import CandyCrashGame.Candy.Color;

public class StripCandyVertical extends Candy {

	public StripCandyVertical(Board board, Color color) {
		this.board = board;
		this.color = color;
	}

	@Override
	public void visit(RegularCandy regular) {
		
	}

	@Override
	public void visit(StripCandyVertical stripV) {
		crashPlus(stripV);
	}

	@Override
	public void visit(StripCandyHorizontal stripH) {
		crashPlus(stripH);
	}

	@Override
	public void visit(WrappedCandy candy) {
		crash3rows3cols(candy);
	}

	@Override
	public void visit(ColorBomb bomb) {
		bomb.visit(this);
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	/*
	 * crash all the column
	 */
	@Override
	public void crash() {
		Candy[][] cb = board.getCandyBoard();
		for (int i = 0; i < cb.length; i++) {
			if(cb[i][this.col] == this)
				remove();
			else if (cb[i][this.col] != null)
				cb[i][this.col].crash();
		}

	}

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon("pictures/vertical" + color.toString()
				+ ".png");
	}

}//class
