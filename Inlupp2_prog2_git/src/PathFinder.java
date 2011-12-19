import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
	private JPanel panelNorth, panelWest, panelWestNorth, panelWestSouth; // huvudpanelerna

	private CenterPanel center = new CenterPanel();

	// Övrigt
	private JFileChooser chooser = new JFileChooser(
			System.getProperty("user.dir"));

	private JList listFrom, listTo;
	private SortModel listModel;
	private List<Node> allNodes = new ArrayList<Node>();
	private MouseListener mL = new MouseListener();
	private Stad stadFrom, stadTo;

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
		menuNew.addActionListener(new NewItemListener());

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
		menuFindPath.addActionListener(new FindPathListener());

		menuShowConnection = new JMenuItem("Visa förbindelse");
		operations.add(menuShowConnection);
		menuShowConnection.addActionListener(new ShowConnectionListener());

		menuNewNode = new JMenuItem("Ny plats");
		operations.add(menuNewNode);
		menuNewNode.addActionListener(new NewNodeListener());

		menuNewConnection = new JMenuItem("Ny förbindelse");
		operations.add(menuNewConnection);
		menuNewConnection.addActionListener(new NewConnectionListener());

		menuChangeConnection = new JMenuItem("Ändra förbindelse");
		operations.add(menuChangeConnection);
		menuChangeConnection.addActionListener(new ChangeConnectionListener());
		// System.exit(0);

	}

	/*
	 * Initierar knapparna
	 */
	private void initButtons() {
		buttonFindPath = new JButton("Hitta väg");
		buttonFindPath.addActionListener(new FindPathListener());

		buttonShowConnection = new JButton("Visa förbindelse");
		buttonShowConnection.addActionListener(new ShowConnectionListener());

		buttonNewNode = new JButton("Ny plats");
		buttonNewNode.addActionListener(new NewNodeListener());

		buttonNewConnection = new JButton("Ny förbindelse");
		buttonNewConnection.addActionListener(new NewConnectionListener());

		buttonChangeConnection = new JButton("Ändra förbindelse");
		buttonChangeConnection
				.addActionListener(new ChangeConnectionListener());

		panelNorth.add(buttonFindPath);
		panelNorth.add(buttonShowConnection);
		panelNorth.add(buttonNewNode);
		panelNorth.add(buttonNewConnection);
		panelNorth.add(buttonChangeConnection);
	}

	/*
	 * Skapa en ny karta
	 */
	private class NewItemListener implements ActionListener {

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

					center.removeAll();
					center.addImage(chooser.getSelectedFile().toString());

					if (panelWest != null) {
						remove(panelWest);
						remove(panelWestNorth);
						remove(panelWestSouth);
					}

					panelWest = new JPanel();
					panelWestNorth = new JPanel();
					panelWestSouth = new JPanel();
					panelWest.setLayout(new GridLayout(2, 1));
					listModel = new SortModel();

					listFrom = new JList(listModel);
					listFrom.setName("listFrom");
					listFrom.setFixedCellWidth(150);
					listFrom.setVisibleRowCount(10);
					listFrom.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					listFrom.addListSelectionListener(new ListClickListener());
					panelWestNorth.add(new JScrollPane(listFrom));

					listTo = new JList(listModel);
					listFrom.setName("listTo");
					listTo.setFixedCellWidth(150);
					listTo.setVisibleRowCount(10);
					listTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					listTo.addListSelectionListener(new ListClickListener());
					panelWestSouth.add(new JScrollPane(listTo));

					panelWest.add(panelWestNorth);
					panelWest.add(panelWestSouth);
					add(panelWest, BorderLayout.WEST);

					validate();
					pack();
					setLocationRelativeTo(null);
				}
			}

		}

	}

	private class FindPathListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class ShowConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	/*
	 * Skapa en ny node
	 */
	private class NewNodeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
			center.setCursor(c);
			center.addMouseListener(mL);
		}
	}

	/*
	 * Aktiveras när användaren tryckt på "ny stad"
	 */
	private class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			String nodeName = "";
			nodeName = JOptionPane.showInputDialog("Platsens namn:");

			if (nodeName != null) {
				if (!nodeName.equals("")) {
					Node nod = new Node(nodeName, mev.getX(), mev.getY());
					center.add(nod);
					nod.addMouseListener(new NodeClickListener());
					center.repaint();
					allNodes.add(nod);
					listModel.addSorted(nod.getStad());
				}
			}
			center.removeMouseListener(mL);
			center.setCursor(Cursor.getDefaultCursor());
		}
	}

	/*
	 * När noder på kartan klickas
	 */
	private class NodeClickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			Node tempNode = (Node) mev.getSource();
			Stad tempStad = tempNode.getStad();

			// Sätta stad från
			if (stadFrom == null && stadTo != tempStad) {
				stadFrom = tempStad;
				tempNode.clicked();
				listFrom.setSelectedValue(stadFrom, true);

			}

			// Avmarkera stad från
			else if (stadFrom == tempStad) {
				stadFrom = null;
				tempNode.clicked();
				listFrom.clearSelection();
			}

			// Sätta stad till
			else if (stadTo == null && stadFrom != tempStad) {
				stadTo = tempStad;
				tempNode.clicked();
				listTo.setSelectedValue(stadTo, true);
			}

			// Avmarkera stad till
			else if (stadTo == tempStad) {
				stadTo = null;
				tempNode.clicked();
				listTo.clearSelection();
			}
		}
	}
	
	/*
	 * När man väljer en stad från nåon av listorna
	 */
	private class ListClickListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent lev) {
			JList listTemp = (JList) lev.getSource();
			Stad stadTemp = (Stad) listTemp.getSelectedValue();
			
			//Undviker dubbla event
			if (!listTemp.getValueIsAdjusting()) {
				
				//Om eventet kommer från listFrom
				if (listTemp == listFrom) {
					//Kollar så att man inte har samma stad som "to"
					if(stadTemp != ((Stad) listTo.getSelectedValue())){
						
						//Avmarkerar markerade staden på kartan
						for(Node nod : allNodes){
							if (nod.getStad() == stadFrom){
								nod.clicked();
								break;
							}
						}
						
						stadFrom = stadTemp;
						
						//Markerar nya staden på kartan
						for(Node nod : allNodes){
							if (nod.getStad() == stadFrom){
								nod.clicked();
								break;
							}
						}
					}
					
					else if(stadFrom != null){
						listFrom.setSelectedValue(stadFrom, true);
					}
					else{
						listFrom.clearSelection();
					}

				}//listFrom
				
				//Om eventet kommer från listTo
				else if (listTemp == listTo) {
					//Kollar så att man inte har samma stad som "from"
					if(stadTemp != ((Stad) listFrom.getSelectedValue())){
						
						//Avmarkerar markerade staden på kartan
						for (Node nod : allNodes){
							if (nod.getStad()==stadTo){
								nod.clicked();
								break;
							}
						}
						
						stadTo = stadTemp;
						
						//Markerar nya staden på kartan
						for (Node nod : allNodes){
							if (nod.getStad() == stadTo){
								nod.clicked();
								break;
							}
						}
					}
					// Flyttar tillbaka selection till föregående om 
					else if(stadTo != null){
						listTo.setSelectedValue(stadTo, true);
					}
					else{
						listTo.clearSelection();
					}
					
				}//listTo

			}//getValueIsAdjusting
			
		}//valueChanged
	}

	private class NewConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class ChangeConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	public static void main(String[] args) {
		new PathFinder();
	}
}
