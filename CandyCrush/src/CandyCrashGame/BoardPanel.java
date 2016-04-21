package CandyCrashGame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class BoardPanel extends JPanel {
	private Board board;
	private CandyL[][] candyBoard;
	private final int size = 9;

	public BoardPanel(Game game, Board board) {
		candyBoard = new CandyL[size][size];
		this.board = board;
		this.setLayout(new GridBagLayout());
		this.setBackground(java.awt.Color.gray.brighter());
		this.setBorder(new LineBorder(java.awt.Color.darkGray, 2));
		GridBagConstraints c = new GridBagConstraints();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				candyBoard[i][j] = new CandyL(board.getCandyBoard()[i][j]);
				candyBoard[i][j].setBorder(new LineBorder(java.awt.Color.white,
						1));
				candyBoard[i][j].addMouseListener(game);
				c.gridx = j;
				c.gridy = i;
				add(candyBoard[i][j], c);
			}
		}

	}

	public void updateGameBoard(Candy[][] values) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (values[i][j] != null){
					candyBoard[i][j].setCandy(values[i][j]);
				}
				else {
					candyBoard[i][j].setIcon(new ImageIcon(
							"./pictures/empty.png"));
				}
			}
		}
	}

	public void setBoard(Board _board) {
		this.board = _board;

	}
}
