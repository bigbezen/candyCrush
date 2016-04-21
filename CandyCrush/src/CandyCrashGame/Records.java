package CandyCrashGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.ColorChooserComponentFactory;

import Exceptions.*;

public class Records extends JFrame {

	private PriorityQueue<User> _users;
	private BufferedReader reader;
	private PrintWriter writer;
	private File dataBase;
	private JPanel table;

	public Records() throws IOException, DataBaseSabotageException {
		super("Records Table");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(new GridBagLayout());

		// this.setSize(new Dimension(402,600));

		// try {

		dataBase = new File("./Records.txt");

		if (!(dataBase.isFile() & dataBase.canRead() & dataBase.canWrite()))
			dataBase.createNewFile();

		writer = new PrintWriter(new FileWriter(dataBase, true));

		_users = readUsers();

		// } catch (IOException e) {
		// JOptionPane
		// .showMessageDialog(
		// this,
		// "Cannot load files of database, please re-install the software",
		// "File Missing Error", JOptionPane.ERROR_MESSAGE);
		// } catch (DataBaseSabotageException e) {
		// JOptionPane
		// .showMessageDialog(
		// this,
		// "Cannot load files correctly of database, please re-install the software or delete the file \' Records.txt \'",
		// "Data Corrupt", JOptionPane.ERROR_MESSAGE);
		// }
		GridBagConstraints c = new GridBagConstraints();

		JPanel head = new JPanel(new GridBagLayout());
		JLabel image = new JLabel(new ImageIcon("./pictures/record.gif"));
		head.add(image);

		Font font = new Font("", 1, 30);
		JLabel text = new JLabel("Record Table");
		text.setFont(font);
		text.setForeground(Color.gray);
		head.add(text);
		c.gridy = 0;
		this.add(head, c);
		
		
		table = new JPanel(new GridBagLayout());
		int i = 0;
		while (!_users.isEmpty()) {
			c.gridy = i;
			JPanel newUser = _users.poll().getJpanel();
			if (i % 2 == 0)
				newUser.setBackground(Color.LIGHT_GRAY);
			else
				newUser.setBackground(Color.white);
			table.add(newUser, c);
			i++;
		}
		
		JPanel headline=new JPanel(new GridBagLayout());
		JLabel name=new JLabel("Name");
		name.setForeground(Color.gray);
		name.setFont(new Font("1",1,25));
		name.setPreferredSize(new Dimension(209,30));
		c.gridx=0;
		c.gridy=0;
		headline.add(name,c);
		
		JLabel score=new JLabel("Score");
		score.setFont(new Font("1",1,25));
		score.setForeground(Color.gray);
		score.setPreferredSize(new Dimension(209,30));
		c.gridx=1;
		c.gridy=0;
		headline.add(score,c);
		c.gridy=1;
		c.gridx=0;
		this.add(headline,c);

		c.gridy = 2;
		
		JScrollPane scroll=new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(420,600));
		this.add(scroll,c);
		this.pack();
	}
	
	private PriorityQueue<User> readUsers() throws IOException,
			DataBaseSabotageException {

		PriorityQueue<User> users = new PriorityQueue<User>(10,
				new CompareUser());
		reader = new BufferedReader(new FileReader(dataBase));

		while (reader.ready()) {
			String str = reader.readLine();

			String[] arr = str.split(";");
			int score;
			String name;
			if (arr.length != 2)
				throw new DataBaseSabotageException();

			name = arr[0];
			try {
				score = Integer.parseInt(arr[1]);
			} catch (NumberFormatException e) {
				throw new DataBaseSabotageException();
			}

			users.add(new User(name, score));

		}

		return users;
	}

	public boolean insertUser(User user) {
		try {
			writeUser(user);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void update() throws IOException, DataBaseSabotageException {
		_users = readUsers();
		table.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		int i = 0;
		while (!_users.isEmpty()) {
			c.gridy = i;
			JPanel newUser = _users.poll().getJpanel();
			if (i % 2 == 0)
				newUser.setBackground(Color.white);
			else
				newUser.setBackground(Color.LIGHT_GRAY);
			table.add(newUser, c);
			i++;
		}
		this.pack();
	}

	private void writeUser(User user) throws IOException {
		writer.println(user);
		writer.flush();
	}
}
