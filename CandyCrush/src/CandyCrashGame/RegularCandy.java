package CandyCrashGame;

import javax.swing.ImageIcon;

public class RegularCandy extends Candy {

	public RegularCandy(Candy.Color color, Board board) {
		this.color = color;
		this.board = board;
	}

	@Override
	public void visit(RegularCandy regular) {
	
	}
	
	
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

	public void crash() {
		this.remove();
	}

	@Override
	public ImageIcon getIcon() {
		return new ImageIcon("./pictures/" + color.toString() + ".png");
	}

	@Override
	public void visit(StripCandyVertical stripV) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StripCandyHorizontal stripH) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(WrappedCandy candy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ColorBomb bomb) {
		bomb.visit(this);
	}
	
	public String toString(){
		return color.name();
	}

}
