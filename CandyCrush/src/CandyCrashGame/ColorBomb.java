package CandyCrashGame;

import javax.swing.ImageIcon;

public class ColorBomb extends Candy {

	public ColorBomb(Board board) {
		this.board = board;
	}

	public ColorBomb(Board board, int i, int j) {
		this(board);
		this.row = i;
		this.col = j;
	}

	@Override
	public void visit(RegularCandy regular) {
		this.color = regular.color;

		crash();
	}

	@Override
	public void visit(StripCandyVertical stripV) {
		// turn all the
		this.color = stripV.color;
		Candy[][] CandyBoard = board.getCandyBoard();
		for (int i = 0; i < CandyBoard.length; i++) {
			for (int j = 0; j < CandyBoard[i].length; j++) {
				if (CandyBoard[i][j] != null
						&& CandyBoard[i][j].color == this.color) {
					CandyBoard[i][j] = null;
					CandyBoard[i][j] = new StripCandyVertical(board, color);
					CandyBoard[i][j].setCoord(i, j);
					CandyBoard[i][j].crash();
				}
			}
		}
		remove();
	}

	@Override
	public void visit(StripCandyHorizontal stripH) {
		this.color = stripH.color;
		Candy[][] CandyBoard = board.getCandyBoard();
		for (int i = 0; i < CandyBoard.length; i++) {
			for (int j = 0; j < CandyBoard[i].length; j++) {
				if (CandyBoard[i][j] != null
						&& CandyBoard[i][j].color == this.color) {
					CandyBoard[i][j] = null;
					CandyBoard[i][j] = new StripCandyHorizontal(board, color);
					CandyBoard[i][j].setCoord(i, j);
					CandyBoard[i][j].crash();
				}
			}
		}
		remove();
	}

	@Override
	public void visit(WrappedCandy candy) {
		// get random color different from candy color
		Color randColor;
		do {
			randColor = board.candyRandom().getColor();
		} while (randColor != candy.getColor());

		// first time - bomb for the warpped color
		this.color = candy.color;
		Candy[][] CandyBoard = board.getCandyBoard();
		for (int i = 0; i < CandyBoard.length; i++) {
			for (int j = 0; j < CandyBoard[i].length; j++) {
				if (CandyBoard[i][j] != null
						&& CandyBoard[i][j].color == this.color)
					CandyBoard[i][j].crash();
			}
		}

		// second time - bomb for random color
		this.color = candy.color;
		for (int i = 0; i < CandyBoard.length; i++) {
			for (int j = 0; j < CandyBoard[i].length; j++) {
				if (CandyBoard[i][j] != null
						&& CandyBoard[i][j].color == this.color)
					CandyBoard[i][j].crash();
			}
		}

		remove();
	}

	@Override
	public void visit(ColorBomb bomb) {
		Candy[][] CandyBoard = board.getCandyBoard();
		for (int i = 0; i < CandyBoard.length; i++) {
			for (int j = 0; j < CandyBoard[i].length; j++) {
				if (CandyBoard[i][j] != null)
					CandyBoard[i][j].crash();
			}
		}

	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	@Override
	public void crash() {

		Color randColor = board.candyRandom().getColor();
		if (color == null)
			color = randColor;

		Candy[][] CandyBoard = board.getCandyBoard();
		for (int i = 0; i < CandyBoard.length; i++) {
			for (int j = 0; j < CandyBoard[i].length; j++) {
				if (CandyBoard[i][j] == this)
					remove();
				else if (CandyBoard[i][j] != null
						&& CandyBoard[i][j].color == this.color)
					CandyBoard[i][j].crash();
			}// for j
		}// for i
	}// crash

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon("pictures/chocolate.png");
	}
}
