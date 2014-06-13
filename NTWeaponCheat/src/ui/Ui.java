package ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Ui extends JFrame {
	String[] weapons;
	private JList list;
	private JScrollPane listScroller;
	private JButton apply;
	
	public Ui() {
		setSize(480, 240);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		loadList();
		init();
	}
	
	private void loadList() {
		Path listPath = Paths.get("res\\weapons.list");
		List<String> lines = null;
		try {
			lines = Files.readAllLines(listPath);
		} catch (IOException e) {
			System.out.println("Error reading weapon list.");
		}
		weapons = new String[lines.size()];
		weapons = lines.toArray(weapons);		
	}
	
	private void init() {
		list = new JList<String>(weapons);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listScroller = new JScrollPane(list);
		apply = new JButton("Set this weapon for all characters");
		add(listScroller, BorderLayout.WEST);
		add(apply, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		final Ui ui = new Ui();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ui.setVisible(true);
			}
		});
	}
}
