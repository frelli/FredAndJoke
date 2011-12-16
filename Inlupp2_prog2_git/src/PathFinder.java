import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PathFinder extends JFrame {
	// Menyn
	private JMenuBar menuBar; // menyraden
	private JMenu arkiv, operations; // de olika menyflikarna
	private JMenuItem menuNew, menuOpen, menuSave, menuSaveAs, menuQuit; // menyvalen
																			// till
	// arkiv
	private JMenuItem menuFindPath, menuShowConnection, menuNewNode,
			menuNewConnection, menuChangeConnection; // menyvalen till
														// operationer

	// Knappar
	private JButton buttonFindPath, buttonShowConnection, buttonNewNode,
			buttonNewConnection, buttonChangeConnection; // operationsknapparna

	// Paneler
	private JPanel panelNorth, panelWest; // huvudpanelerna

	private CenterPanel center = new CenterPanel();
	private JFileChooser chooser = new JFileChooser(
			System.getProperty("user.dir"));
	private MouseListener mL = new MouseListener();

	/*
	 * Konstruktorn
	 */
	public PathFinder() {
		super("PathFinder");

		panelNorth = new JPanel(); // Den norra panelen
		add(BorderLayout.NORTH, panelNorth);
		add(BorderLayout.CENTER, center);

		initMenu(); // Sätter upp menyn
		initButtons(); // Sätter upp knapparna

		setJMenuBar(menuBar);
		setVisible(true);
		setSize(new Dimension(600, 100));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/*
	 * Initierar menyn
	 */
	private void initMenu() {
		menuBar = new JMenuBar();

		// Arkivfliken
		arkiv = new JMenu("Arkiv");
		arkiv.setMnemonic(KeyEvent.VK_A);
		menuBar.add(arkiv);

		menuNew = new JMenuItem("Nytt");
		menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));
		arkiv.add(menuNew);
		menuNew.addActionListener(new newItemListener());

		menuOpen = new JMenuItem("Öppna");
		arkiv.add(menuOpen);

		menuSave = new JMenuItem("Spara");
		arkiv.add(menuSave);

		menuSaveAs = new JMenuItem("Spara som...");
		arkiv.add(menuSaveAs);

		menuQuit = new JMenuItem("Avsluta");
		arkiv.add(menuQuit);

		// Operationsfliken
		operations = new JMenu("Operationer");
		operations.setMnemonic(KeyEvent.VK_O);
		menuBar.add(operations);

		menuFindPath = new JMenuItem("Hitta väg");
		operations.add(menuFindPath);
		menuFindPath.addActionListener(new findPathListener());

		menuShowConnection = new JMenuItem("Visa förbindelse");
		operations.add(menuShowConnection);
		menuShowConnection.addActionListener(new showConnectionListener());

		menuNewNode = new JMenuItem("Ny plats");
		operations.add(menuNewNode);
		menuNewNode.addActionListener(new newNodeListener());

		menuNewConnection = new JMenuItem("Ny förbindelse");
		operations.add(menuNewConnection);
		menuNewConnection.addActionListener(new newConnectionListener());

		menuChangeConnection = new JMenuItem("Ändra förbindelse");
		operations.add(menuChangeConnection);
		menuChangeConnection.addActionListener(new changeConnectionListener());
		// System.exit(0);

	}

	/*
	 * Initierar knapparna
	 */
	private void initButtons() {
		buttonFindPath = new JButton("Hitta väg");
		buttonFindPath.addActionListener(new findPathListener());

		buttonShowConnection = new JButton("Visa förbindelse");
		buttonShowConnection.addActionListener(new showConnectionListener());

		buttonNewNode = new JButton("Ny plats");
		buttonNewNode.addActionListener(new newNodeListener());

		buttonNewConnection = new JButton("Ny förbindelse");
		buttonNewConnection.addActionListener(new newConnectionListener());

		buttonChangeConnection = new JButton("Ändra förbindelse");
		buttonChangeConnection
				.addActionListener(new changeConnectionListener());

		panelNorth.add(buttonFindPath);
		panelNorth.add(buttonShowConnection);
		panelNorth.add(buttonNewNode);
		panelNorth.add(buttonNewConnection);
		panelNorth.add(buttonChangeConnection);
	}

	/*
	 * Skapa en ny karta
	 */
	private class newItemListener implements ActionListener {

		public void actionPerformed(ActionEvent ave) {
			FileNameExtensionFilter fnef = new FileNameExtensionFilter(
					"Bilder", "jpg", "jpeg", "gif", "png");
			chooser.addChoosableFileFilter(fnef);
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileFilter(fnef);
			int status = chooser.showOpenDialog(null);

			// Om användaren trycker på open
			if (status == JFileChooser.APPROVE_OPTION) {
				String filePath = chooser.getSelectedFile().getAbsolutePath()
						.toLowerCase(); // hela filnamnet+sökväg

				// Kollar så att filen är en bildfil som blivit vald
				if (filePath.endsWith(".jpg") || filePath.endsWith("jpeg")
						|| filePath.endsWith(".gif")
						|| filePath.endsWith(".png")) {
					center.addImage(chooser.getSelectedFile().toString());

					validate();
					pack();
					setLocationRelativeTo(null);
				}
			}

		}

	}

	private class findPathListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class showConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	/*
	 * Skapa en ny node
	 */

	private class newNodeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
			center.setCursor(c);
			center.addMouseListener(mL);
		}
	}
	private class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			String nodeName = "";
			nodeName = JOptionPane.showInputDialog("Platsens namn:");

			if (nodeName != null) {
				if (!nodeName.equals("")) {
					Node nod = new Node(nodeName, mev.getX(), mev.getY());
					center.add(nod);
					center.repaint();
				}
			}
			center.removeMouseListener(mL);
			center.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class newConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class changeConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	public static void main(String[] args) {
		new PathFinder();
	}
}
