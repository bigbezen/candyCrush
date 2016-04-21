package CandyCrashGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.xml.transform.Source;

import Exceptions.DataBaseSabotageException;

public class Game extends JFrame implements MouseListener {
	private Board _board;
	private BoardPanel _boardpanel;
	private Vector<CandyL> choose;
	private HeadlinePanel headLine;
	private int score;
	private int moves;
	private GameOver _gameOver;
	private Records _records;
	private JMenuBar _menuBar;
	private JMenuItem newGame;
	private JMenuItem exit;
	private JMenuItem topScore;

	public Game() {
		super("Candy Crush - Idan And Matan");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.score = 0;
		this.moves = 20;

		_board = new Board(this);

		// TODO delete stubs
//		_board.getCandyBoard()[5][5] = new ColorBomb(_board);
//		_board.getCandyBoard()[5][5].setCoord(5, 5);
//		_board.getCandyBoard()[5][4] = new ColorBomb(_board);
//		_board.getCandyBoard()[5][4].setCoord(5, 4);
//		_board.getCandyBoard()[5][7] = new WrappedCandy(_board,
//				CandyCrashGame.Candy.Color.GREEN);
//		_board.getCandyBoard()[5][7].setCoord(5, 7);
//		_board.getCandyBoard()[2][1] = new WrappedCandy(_board,
//				CandyCrashGame.Candy.Color.RED);
//		_board.getCandyBoard()[2][1].setCoord(2, 1);
//		_board.getCandyBoard()[8][8] = new WrappedCandy(_board,
//				CandyCrashGame.Candy.Color.PURPLE);
//		_board.getCandyBoard()[8][8].setCoord(8, 8);

		_gameOver = new GameOver(this);
		_boardpanel = new BoardPanel(this, _board);
		choose = new Vector<CandyL>();

		headLine = new HeadlinePanel();
		this.getContentPane().add(headLine, BorderLayout.CENTER);

		try {
			_records = new Records();
			_records.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Cannot load files of database, please re-install the software",
							"File Missing Error", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		} catch (DataBaseSabotageException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Cannot load files correctly of database, please re-install the software or delete the file \' Records.txt \'",
							"Data Corrupt", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}

		_menuBar = new JMenuBar();

		JMenu tMenu = new JMenu("Options");
		newGame = new JMenuItem("New Game");
		newGame.addMouseListener(this);
		tMenu.add(newGame);
		topScore = new JMenuItem("Top Scores");
		topScore.addMouseListener(this);
		tMenu.add(topScore);

		exit = new JMenuItem("Exit");
		exit.addMouseListener(this);
		tMenu.add(exit);
		_menuBar.add(tMenu);

		tMenu = new JMenu("About");
		_menuBar.add(tMenu);
		tMenu.add(new JMenuItem("this game create and Designed by Idan & Matan"));
		this.getContentPane().add(_menuBar, BorderLayout.BEFORE_FIRST_LINE);

		JPanel center = new JPanel(new GridBagLayout());
		GridBagConstraints tConst = new GridBagConstraints();
		tConst.anchor = GridBagConstraints.NORTHWEST;
		tConst.gridy = 0;
		tConst.gridx = 0;
		tConst.weightx = 0.0;
		tConst.weighty = 0.0;
		tConst.fill = GridBagConstraints.HORIZONTAL;

		tConst.gridy = -1;
		center.add(_boardpanel, tConst);
		this.getContentPane().add(center, BorderLayout.AFTER_LAST_LINE);

		headLine.setMoves(moves);

		this.pack();
		Dimension minimumSize = this.getPreferredSize();
		this.setMinimumSize(minimumSize);
		this.setFocusable(true);
		this.setVisible(true);
	}
	
	private void updateScore(){
		this.score = _board.getScore();
		this.headLine.setScore(this.score);
	}

	private void gameOver(int score) {
		_gameOver.setScore(score);
		_gameOver.setVisible(true);
	}

	public void restart() {
		moves = 20;
		headLine.setMoves(moves);
		score = 0;
		headLine.setScore(score);
		this._board = new Board(this);
		_boardpanel.setBoard(_board);
		_boardpanel.updateGameBoard(_board.getCandyBoard());
	}
	
	public void updateGUI(){
		_boardpanel.updateGameBoard(_board.getCandyBoard());
	}

	public void chainReaction() {
		while (_board.isEmpty()) {
			_board.insertTop();
			_board.moveDown();
			_boardpanel.updateGameBoard(_board.getCandyBoard());
			updateScore();
			
		}
		_board.resetMulty();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object object = e.getSource();
		if (object instanceof CandyL) {
			CandyL candy = (CandyL) object;
			if (choose.size() == 0) {
				candy.setBorder(new LineBorder(Color.blue, 1));
				choose.add(candy);
			} else if (choose.size() == 1) {
				candy.setBorder(new LineBorder(Color.red, 1));
				choose.add(candy);

				if (isNeighbor(choose.elementAt(0).getCandy(), choose
						.elementAt(1).getCandy())) {
					choose.elementAt(0).getCandy().combine(choose.elementAt(1).getCandy());
					if(_board.isEmpty()){
//						_board.moveDown();
						chainReaction();
						moves--;
						headLine.setMoves(moves);	
					}
					if (_board.move(choose.elementAt(0).getCandy(), choose
							.elementAt(1).getCandy())
							| _board.move(choose.elementAt(1).getCandy(),
									choose.elementAt(0).getCandy())) {
						
						//a legal move has performed
						
						if (choose.elementAt(1) != null)
							_board.move(choose.elementAt(1).getCandy(), choose
									.elementAt(1).getCandy());
						else
							_board.move(choose.elementAt(0).getCandy(), choose
									.elementAt(0).getCandy());
//						_board.moveDown();
						chainReaction();
						moves--;
						headLine.setMoves(moves);
						if (moves <= 0) {
							gameOver(score);
						}
						
					}//if move
				}

				setEmptyBorder();
				choose.removeAll(choose);
				this.requestFocusInWindow();
			}
		} else if (e.getSource() == _gameOver.getRestart()) {
			_gameOver.dispose();
			_gameOver = new GameOver(this);
			restart();
		} else if (e.getSource() == _gameOver.getSave()) {
			if (_gameOver.checkName()) {
				User user = new User(_gameOver.getName(), score);
				_records.insertUser(user);
				restart();
				_gameOver.dispose();
				_gameOver = new GameOver(this);
			} else
				JOptionPane.showMessageDialog(_gameOver,
						"Invalid expression please check your name!",
						"Inane error", JOptionPane.ERROR_MESSAGE);
		} else if (e.getSource() == newGame) {
			restart();
		} else if (e.getSource() == exit) {
			this.dispose();
		} else if (e.getSource() == topScore) {
			try {
				_records.update();
			} catch (IOException | DataBaseSabotageException e1) {
				JOptionPane
						.showMessageDialog(
								this,
								"Cannot load files of database, please re-install the software",
								"File Missing Error", JOptionPane.ERROR_MESSAGE);
			}
			_records.setVisible(true);

		}
	}

	private void setEmptyBorder() {
		if (choose.size() > 0) {
			for (CandyL candy : choose) {
				if (candy != null)
					candy.setBorder(new LineBorder(Color.white, 1));
			}
		}
	}

	private boolean isNeighbor(Candy candySource, Candy candyTarget) {
		if (candySource.col == candyTarget.col
				& Math.abs(candySource.row - candyTarget.row) <= 1)
			return true;
		else if (candySource.row == candyTarget.row
				& Math.abs(candySource.col - candyTarget.col) <= 1)
			return true;
		return false;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Game game = new Game();
	}
}
