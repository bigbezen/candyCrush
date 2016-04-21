package CandyCrashGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GameOver extends JFrame implements ActionListener {
	private JButton save;
	private JButton restart;
	private JButton end;
	private int score;
	private JTextField name;
	private JPanel button;
	private JLabel head;

	public GameOver(Game e) {
		super("Game over");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(new GridBagLayout());
		this.repaint();
		score = 0;

		GridBagConstraints c = new GridBagConstraints();

		Icon icon = new ImageIcon("./pictures/gameOver.png");
		JLabel picOver = new JLabel();
		picOver.setIcon(icon);
		c.gridx = 0;
		c.gridy = 0;
		this.add(picOver, c);

		name = new JTextField(30);
		c.gridy = 2;
		c.gridx = 0;
		this.add(name, c);

		Font font = new Font("", 1, 30);
		 head = new JLabel("your score " + score);
		head.setFont(font);
		head.setForeground(Color.DARK_GRAY);
		c.gridy = 1;
		this.add(head, c);

		this.setSize(icon.getIconWidth() + 200, icon.getIconHeight() + 200);
		button = new JPanel(new GridBagLayout());
		font = new Font("", 1, 20);
		restart = new JButton();
		restart.setMargin(new Insets(2, 2, 2, 2));
		restart.setBorder(null);
		restart.addMouseListener(e);
		restart.setText("restart");
		restart.setFont(font);
		c.gridy = 0;
		c.gridx = 1;
		c.insets = new Insets(4, 25, 4, 25);
		button.add(restart, c);

		end = new JButton();
		end.addActionListener(this);
		end.setText("cancel");
		end.setFont(font);
		end.setMargin(new Insets(2, 2, 2, 2));
		end.setBorder(null);
		c.gridy = 0;
		c.gridx = 2;
		button.add(end, c);

		save = new JButton();
		Icon SaveIcon = new ImageIcon("./pictures/save.gif");
		save.setIcon(SaveIcon);
		save.addMouseListener(e);
		save.setMargin(new Insets(0, 0, 0, 0));
		;
		c.gridx = 0;
		c.gridy = 0;
		button.add(save, c);

		c.gridx = 0;
		c.gridy = 3;
		this.add(button, c);
	}
	public JButton getRestart(){
		return this.restart;
	}
	
	public JButton getSave(){
		return this.save;
	}

	public void setScore(int score) {
		this.score = score;
		head.setText("your score " + score);
	}
	
	public String getName(){
		return name.getText();
	}
	public boolean checkName(){
		if(name.getText().matches("[a-zA-Z]*")&(name.getText().length()>0)&(name.getText().length()<=14))return true;
		name.setText(null);
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == end) {
			this.dispose();
		}
	}
}

