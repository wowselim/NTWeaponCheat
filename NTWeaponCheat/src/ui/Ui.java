package ui;

import globals.Globals;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import util.Modifier;

public class Ui extends JFrame {
	private List<String> weapons;
	private JList list;
	private JScrollPane listScroller;
	private JButton apply;

	public Ui() {
		super("Nuclear Throne Weapon Modifier");
		setSize(480, 240);
		setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("res" + Globals.getSeparator()
				+ "icon.png").getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		loadList();
		init();
	}

	private void loadList() {
		Path listPath = Paths.get("res" + Globals.getSeparator()
				+ "weapons.list");
		try {
			weapons = Files.readAllLines(listPath);
		} catch (IOException e) {
			System.out.println("Error reading weapon list.");
		}
	}

	private void init() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (String weapon : weapons)
			model.addElement(weapon);
		list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		listScroller = new JScrollPane(list);
		apply = new JButton("Set this weapon for all characters");
		add(listScroller, BorderLayout.WEST);
		add(apply, BorderLayout.CENTER);
		initActionListeners();
	}

	private void initActionListeners() {
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() == -1)
					return;
				String msg = Modifier.setWeapon(list.getSelectedIndex());
				JOptionPane.showMessageDialog(Ui.this, msg);
			}
		});
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
