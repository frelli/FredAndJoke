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

import Graphs.Edge;
import Graphs.Graph;
import Graphs.GraphMethods;
import Graphs.ListGraph;

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

	private CenterPanel center;

	// �vrigt
	private JFileChooser chooser = new JFileChooser(
			System.getProperty("user.dir"));

	private JList listFrom, listTo;
	private SortModel listModel;
	private List<Node> allNodes = new ArrayList<Node>();
	private MouseListener mL = new MouseListener();
	private Stad stadFrom, stadTo;
	private Graph<Stad> graph;

	/*
	 * Konstruktorn
	 */
	public PathFinder() {
		super("PathFinder");

		panelNorth = new JPanel(); // Den norra panelen
		add(BorderLayout.NORTH, panelNorth);

		initMenu(); // S�tter upp menyn
		initButtons(); // S�tter upp knapparna

		setJMenuBar(menuBar);
		setVisible(true);
		setSize(new Dimension(600, 100));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/*
	 * Kontrollmetod f�r att se s� att anv�ndaren har markerat tv� stycken
	 * st�der
	 */
	private int twoNodesCheck() {
		if (stadFrom != null && stadTo != null)
			return 1;
		else {
			JOptionPane
					.showMessageDialog(
							null,
							"Du m�ste ha tv� st�der markerade f�r att kunna utf�ra denna operation.",
							"Fel antal st�der markerade",
							JOptionPane.ERROR_MESSAGE);
			return -1;
		}
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

		menuOpen = new JMenuItem("�ppna");
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

		menuFindPath = new JMenuItem("Hitta v�g");
		operations.add(menuFindPath);
		menuFindPath.addActionListener(new FindPathListener());

		menuShowConnection = new JMenuItem("Visa f�rbindelse");
		operations.add(menuShowConnection);
		menuShowConnection.addActionListener(new ShowConnectionListener());

		menuNewNode = new JMenuItem("Ny plats");
		operations.add(menuNewNode);
		menuNewNode.addActionListener(new NewNodeListener());

		menuNewConnection = new JMenuItem("Ny f�rbindelse");
		operations.add(menuNewConnection);
		menuNewConnection.addActionListener(new NewConnectionListener());

		menuChangeConnection = new JMenuItem("�ndra f�rbindelse");
		operations.add(menuChangeConnection);
		menuChangeConnection.addActionListener(new ChangeConnectionListener());
		// System.exit(0);

	}

	/*
	 * Initierar knapparna
	 */
	private void initButtons() {
		buttonFindPath = new JButton("Hitta v�g");
		buttonFindPath.addActionListener(new FindPathListener());

		buttonShowConnection = new JButton("Visa f�rbindelse");
		buttonShowConnection.addActionListener(new ShowConnectionListener());

		buttonNewNode = new JButton("Ny plats");
		buttonNewNode.addActionListener(new NewNodeListener());

		buttonNewConnection = new JButton("Ny f�rbindelse");
		buttonNewConnection.addActionListener(new NewConnectionListener());

		buttonChangeConnection = new JButton("�ndra f�rbindelse");
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
			if (center != null)
				remove(center);
			center = new CenterPanel();
			add(BorderLayout.CENTER, center);

			FileNameExtensionFilter fnef = new FileNameExtensionFilter(
					"Bilder", "jpg", "jpeg", "gif", "png");
			chooser.addChoosableFileFilter(fnef);
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setFileFilter(fnef);
			int status = chooser.showOpenDialog(null);

			// Om anv�ndaren trycker p� open
			if (status == JFileChooser.APPROVE_OPTION) {
				String filePath = chooser.getSelectedFile().getAbsolutePath()
						.toLowerCase(); // hela filnamnet+s�kv�g

				// Kollar s� att filen �r en bildfil som blivit vald
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

					// Skapar listorna med tillbeh�r
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

					// Skapar grafen
					graph = new ListGraph<Stad>();

					validate();
					pack();
					setLocationRelativeTo(null);
				}
			}

		}

	}

	private class FindPathListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (center == null)
				return;
			twoNodesCheck();
		}
	}

	private class ShowConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (center == null)
				return;
			if (twoNodesCheck() == 1) {
				if (GraphMethods.pathExists(graph, stadFrom, stadTo)){
					ShowConnectionDialog dialog = new ShowConnectionDialog(graph.getEdgesBetween(stadFrom, stadTo), stadFrom, stadTo);
					JOptionPane.showMessageDialog(null, dialog, "Visa f�rbindelse", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(
							getContentPane(),
							"Det finns ingen f�rbindelse mellan "
									+ stadFrom.getNamn() + " och "
									+ stadTo.getNamn() + ".", "Fel",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/*
	 * Skapa en ny node
	 */
	private class NewNodeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (center != null) {
				Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
				center.setCursor(c);
				center.addMouseListener(mL);
			}
		}
	}

	/*
	 * Aktiveras n�r anv�ndaren tryckt p� "ny stad"
	 */
	private class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			String nodeName = "";
			nodeName = JOptionPane.showInputDialog("Platsens namn:");

			if (nodeName != null) {
				if (!(nodeName.trim()).equals("")) {
					Node nod = new Node(nodeName, mev.getX(), mev.getY());
					center.add(nod);
					nod.addMouseListener(new NodeClickListener());
					center.repaint();
					allNodes.add(nod);
					graph.add(nod.getStad());
					listModel.addSorted(nod.getStad());
				}
			}
			center.removeMouseListener(mL);
			center.setCursor(Cursor.getDefaultCursor());
		}
	}

	/*
	 * N�r noder p� kartan klickas
	 */
	private class NodeClickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			Node tempNode = (Node) mev.getSource();
			Stad tempStad = tempNode.getStad();

			// S�tta stad fr�n
			if (stadFrom == null && stadTo != tempStad) {
				stadFrom = tempStad;
				tempNode.clicked();
				listFrom.setSelectedValue(stadFrom, true);

			}

			// Avmarkera stad fr�n
			else if (stadFrom == tempStad) {
				stadFrom = null;
				tempNode.clicked();
				listFrom.clearSelection();
			}

			// S�tta stad till
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
	 * N�r man v�ljer en stad fr�n n�on av listorna
	 */
	private class ListClickListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent lev) {
			JList listTemp = (JList) lev.getSource();
			Stad stadTemp = (Stad) listTemp.getSelectedValue();

			// Undviker dubbla event
			if (!listTemp.getValueIsAdjusting()) {

				// Om eventet kommer fr�n listFrom
				if (listTemp == listFrom) {
					// Kollar s� att man inte har samma stad som "to"
					if (stadTemp != ((Stad) listTo.getSelectedValue())) {

						// Avmarkerar markerade staden p� kartan
						for (Node nod : allNodes) {
							if (nod.getStad() == stadFrom) {
								nod.clicked();
								break;
							}
						}

						stadFrom = stadTemp;

						// Markerar nya staden p� kartan
						for (Node nod : allNodes) {
							if (nod.getStad() == stadFrom) {
								nod.clicked();
								break;
							}
						}
					}

					else if (stadFrom != null) {
						listFrom.setSelectedValue(stadFrom, true);
					} else {
						listFrom.clearSelection();
					}

				}// listFrom

				// Om eventet kommer fr�n listTo
				else if (listTemp == listTo) {
					// Kollar s� att man inte har samma stad som "from"
					if (stadTemp != ((Stad) listFrom.getSelectedValue())) {

						// Avmarkerar markerade staden p� kartan
						for (Node nod : allNodes) {
							if (nod.getStad() == stadTo) {
								nod.clicked();
								break;
							}
						}

						stadTo = stadTemp;

						// Markerar nya staden p� kartan
						for (Node nod : allNodes) {
							if (nod.getStad() == stadTo) {
								nod.clicked();
								break;
							}
						}
					}
					// Flyttar tillbaka selection till f�reg�ende om
					else if (stadTo != null) {
						listTo.setSelectedValue(stadTo, true);
					} else {
						listTo.clearSelection();
					}

				}// listTo

			}// getValueIsAdjusting

		}// valueChanged
	}

	/*
	 * Ny f�rbindelse
	 */
	private class NewConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (center == null) // Om ingen karta finns
				return;
			int check = twoNodesCheck();

			if (check == 1) { // Om tv� st�der �r markerade
				NewConnectionDialog dialog = new NewConnectionDialog(stadFrom,
						stadTo);
				for (;;) { // Metoden g�r runt tills r�tt input eller avbuten
					try {

						int answer = JOptionPane.showConfirmDialog(null,
								dialog, "Ny f�rbindelse",
								JOptionPane.YES_NO_OPTION);
						if (answer != JOptionPane.YES_OPTION) {
							return;

						} else { // Anv�ndaren
									// tycker p� JA
							if ((dialog.getName().trim()).equals("")) {
								JOptionPane
										.showMessageDialog(
												null,
												"Du m�ste skriva in ett namn p� f�rbindelsen.",
												"Skriv in namn",
												JOptionPane.ERROR_MESSAGE);
							} else { // Om allt lyckas och anv�ndaren trycker p�
										// JA
								graph.connect(stadFrom, stadTo,
										dialog.getName(), dialog.getTime());
								return;
							}

						} // Om svar = ja (else)

					} catch (NumberFormatException err) {
						JOptionPane.showMessageDialog(null,
								"F�ltet tid kr�ver ett numeriskt heltal.",
								"Fel v�rde i f�ltet tid",
								JOptionPane.ERROR_MESSAGE);
					}
				} // For
			}
		}
	}

	private class ChangeConnectionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int check = twoNodesCheck();
			if (check == 1 && graph.getEdgesBetween(stadFrom, stadTo).size() != 0){
				if(graph.getEdgesBetween(stadFrom, stadTo).size() > 1){
					Edge<Stad> selection = (Edge<Stad>) JOptionPane.showInputDialog(null, "Vilken f�rbindelse vill du �ndra?", "�ndra f�rbindelse", JOptionPane.QUESTION_MESSAGE, null, graph.getEdgesBetween(stadFrom, stadTo).toArray(), null);
				}else
					System.out.println("bara en yO!");
				
			}
		}
	}

	public static void main(String[] args) {
		new PathFinder();
	}
}
