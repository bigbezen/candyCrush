package CandyCrashGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CandyL  extends JLabel{
	private Candy candy;
	
	public CandyL(Candy candy){
		this.candy=candy;
		this.setIcon(candy.getIcon());
	}
	
	public CandyL(ImageIcon icon){
		this.candy=null;
		this.setIcon(icon);
	}
	
	public Candy getCandy(){
		return candy;
	}
	public void setCandy(Candy candy){
		this.candy=candy;
		this.setIcon(candy.getIcon());
	}
}
