package CandyCrashGame;

import java.util.ConcurrentModificationException;
import java.util.Vector;

import CandyCrashGame.Candy.Color;

public class Board {
	private Candy[][] candyBoard;
	private final int size = 9;
	private int score;
	private int multy;
	private Vector<Candy> activeCandies;
	private Game game;
	private boolean initFinish;

	public Board(Game game) {
		this.game = game;
		activeCandies = new Vector<Candy>();
		candyBoard = new Candy[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				candyBoard[i][j] = null;
			}
		}
		score = 0;
		multy = 1;
		insert();
	}

	// remove candy from the board;
	public void remove(Candy candy) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (candyBoard[i][j] != null && candyBoard[i][j].equals(candy))
					candyBoard[i][j] = null;
			}
		}
	}

	public int getScore() {
		return score;
	}

	// TODO set the multy by chain reaction
	public void calcScore(int score) {
		if (initFinish)
			this.score += score * this.multy;
		// TODO delete
//		System.out.println("score: " + this.score + "multy: " + this.multy);
	}

	public void resetScore() {
		this.score = 0;
		this.multy = 1;
	}

	public void increaseMulty() {
		if (initFinish)
			this.multy *= 2;
	}

	public void resetMulty() {
		this.multy = 1;
	}

	public Candy[][] getCandyBoard() {
		return candyBoard;
	}

	// check if same candy on the board is null
	public boolean isEmpty() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (candyBoard[i][j] == null)
					return true;
			}
		}
		return false;
	}

	// insert candy to the empty cells
	public void insert() {
		initFinish = false;
		while (isEmpty()) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (candyBoard[i][j] == null||!(candyBoard[i][j] instanceof RegularCandy)) {
						candyBoard[i][j] = candyRandom();
						candyBoard[i][j].setCoord(i, j);
						activeCandies.add(candyBoard[i][j]);
					}
				}// for j
			}// for i
			for (Candy candy : activeCandies) {
				if (move(candy, candy)) {
					increaseMulty();
				}
			}
		}// while
		activeCandies.removeAllElements();
		initFinish = true;
	}// insert()

	public boolean move(Candy from, Candy to) {
		boolean sequence = true;
		Vector<Candy> up = new Vector<Candy>();
		Vector<Candy> left = new Vector<Candy>();
		Vector<Candy> rigth = new Vector<Candy>();
		Vector<Candy> down = new Vector<Candy>();

		for (int i = 1; i <= 2 & sequence; i++) {
			if (to.row + i < size && candyBoard[to.row + i][to.col] != null
					&& !candyBoard[to.row + i][to.col].equals(from)
					&& candyBoard[to.row + i][to.col].color != null
					&& candyBoard[to.row + i][to.col].color.equals(from.color))
				up.add(candyBoard[to.row + i][to.col]);
			else
				sequence = false;
		}
		sequence = true;
		for (int i = 1; i <= 2 & sequence; i++) {
			if (to.row - i >= 0 && candyBoard[to.row - i][to.col] != null
					&& !candyBoard[to.row - i][to.col].equals(from)
					&& candyBoard[to.row - i][to.col].color != null
					&& candyBoard[to.row - i][to.col].color.equals(from.color))
				down.add(candyBoard[to.row - i][to.col]);
			else
				sequence = false;
		}
		sequence = true;
		for (int i = 1; i <= 2 & sequence; i++) {
			if (to.col + i < size && candyBoard[to.row][to.col + i] != null
					&& !candyBoard[to.row][to.col + i].equals(from)
					&& candyBoard[to.row][to.col + i].color != null
					&& candyBoard[to.row][to.col + i].color.equals(from.color)) {
				rigth.add(candyBoard[to.row][to.col + i]);
			} else
				sequence = false;
		}
		sequence = true;
		for (int i = 1; i <= 2 & sequence; i++) {
			if (to.col - i >= 0 && candyBoard[to.row][to.col - i] != null
					&& !candyBoard[to.row][to.col - i].equals(from)
					&& candyBoard[to.row][to.col - i].color != null
					&& candyBoard[to.row][to.col - i].color.equals(from.color)) {
				left.add(candyBoard[to.row][to.col - i]);
			} else
				sequence = false;
		}
		int verti = up.size() + down.size();
		int horiz = left.size() + rigth.size();
		// if have at least 3 in sequence
		// make replace
		if (verti >= 2 | horiz >= 2) {
			int x = to.row;
			int y = to.col;
			swich(from, to);
			// if need to be here chocolate
			if (verti == 4) {
				candyBoard[x][y] = new ColorBomb(this);
				candyBoard[x][y].setCoord(x, y);
				crush(down, up);
			} else if (horiz == 4) {
				candyBoard[x][y] = new ColorBomb(this);
				candyBoard[x][y].setCoord(x, y);
				crush(left, rigth);
				// if need to be here WrappedCandy
			} else if (horiz >= 2 & verti >= 2) {
				candyBoard[x][y] = new WrappedCandy(this, from.color);
				candyBoard[x][y].setCoord(x, y);
				crush(left, rigth);
				crush(up, down);
			} else if (horiz == 3) {
				candyBoard[x][y] = new StripCandyHorizontal(this, from.color);
				candyBoard[x][y].setCoord(x, y);
				crush(left, rigth);
			} else if (verti == 3) {
				candyBoard[x][y] = new StripCandyVertical(this, from.color);
				candyBoard[x][y].setCoord(x, y);
				crush(up, down);
			} else if (verti == 2) {
				crush(up, down);
				if (candyBoard[x][y] != null)
					candyBoard[x][y].crash();
			} else {
				crush(left, rigth);
				if (candyBoard[x][y] != null)
					candyBoard[x][y].crash();
			}
			return true;
		}
		return false;
	}

	private void crush(Vector<Candy> left, Vector<Candy> rigth) {
		for (int i = 0; i <= 1; i++) {
			if (left.size() > i && left.elementAt(i) != null)
				left.elementAt(i).crash();
			if (rigth.size() > i && rigth.elementAt(i) != null)
				rigth.elementAt(i).crash();
		}
	}

	// TODO delete
	public void printBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (candyBoard[i][j] != null)
					System.out.print(candyBoard[i][j] + ", ");
				else
					System.out.print("null" + ", ");
			}
			System.out.println();
		}
	}

	// return random candy;
	public Candy candyRandom() {
		double random = Math.random();
		if (random > 0 & random <= 0.16) {
			return new RegularCandy(Color.BLUE, this);
		} else if (random > 0.16 & random <= 0.32) {
			return new RegularCandy(Color.GREEN, this);
		} else if (random > 0.32 & random <= 0.49) {
			return new RegularCandy(Color.ORANGE, this);
		} else if (random > 0.49 & random <= 0.66) {
			return new RegularCandy(Color.PURPLE, this);
		} else if (random > 0.66 & random <= 0.83) {
			return new RegularCandy(Color.RED, this);
		} else {
			return new RegularCandy(Color.YELLOW, this);
		}
	}

	public void chainReaction() {
		game.chainReaction();
	}

	public void insertTop() {
		for (int j = 0; j < size; j++) {
			if (candyBoard[0][j] == null) {
				candyBoard[0][j] = candyRandom();
				candyBoard[0][j].setCoord(0, j);
				activeCandies.add(candyBoard[0][j]);
				
				
//				try {
//					System.out.println("sleep inset top");
//					Thread.sleep(150);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				game.updateGUI();
			}
		}
	}

	public void moveDown() {
		for (int i = size - 1; i >= 0; i--) {
			for (int j = 0; j < size; j++) {
				if (candyBoard[i][j] != null) {
					int k = i;
					while ((k + 1) < size && candyBoard[k + 1][j] == null) {
						candyBoard[k + 1][j] = candyBoard[k][j];
						candyBoard[k + 1][j].setCoord(k + 1, j);
						candyBoard[k][j] = null;
						activeCandies.add(candyBoard[k + 1][j]);
						k++;
						
						
//						try {
//							System.out.println("sleep move down");
//							Thread.sleep(150);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						game.updateGUI();
					}
				}
			}
		}
		try{
			java.util.Iterator<Candy> iter=activeCandies.iterator(); 
			while(iter.hasNext()){
				Candy candy=iter.next();
				if (move(candy, candy)) {
				increaseMulty();
				}
			}
		}catch(ConcurrentModificationException e){System.out.println("matan");}
		activeCandies.removeAllElements();
	}

	public void swich(Candy one, Candy two) {
		int twoX = two.row;
		int twoY = two.col;
		int oneX = one.row;
		int oneY = one.col;
		candyBoard[oneX][oneY] = two;
		candyBoard[twoX][twoY] = one;
		candyBoard[oneX][oneY].setCoord(oneX, oneY);
		candyBoard[twoX][twoY].setCoord(twoX, twoY);

	}

}
