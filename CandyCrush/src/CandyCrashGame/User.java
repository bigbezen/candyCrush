package CandyCrashGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class User {

	private int score;
	private String name;

	public User(String name, int score) {
		this.score = score;
		this.name = name;
	}

	@Override
	public String toString() {
		return name + ";" + score;
	}
	
	public JPanel getJpanel(){
		Font font=new Font("",1,20);
		JPanel colum=new JPanel(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		
		JLabel name=new JLabel(this.name);
		name.setFont(font);
		name.setPreferredSize(new Dimension(200,30));
		name.setBorder(new LineBorder(Color.black,1));
		c.gridx=0;
		c.gridy=0;
		colum.add(name,c);
		
		JLabel score=new JLabel(this.score+"");
		score.setFont(font);
		score.setPreferredSize(new Dimension(200,30));
		score.setBorder(new LineBorder(Color.black,1));
		c.gridx=1;
		c.gridy=0;
		colum.add(score,c);
		
		return colum;
	}
	public int getScore(){
		return score;
	}

}
