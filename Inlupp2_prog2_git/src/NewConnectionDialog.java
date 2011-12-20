import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewConnectionDialog extends JPanel {
	private JLabel lTitle, lName, lTime;
	private JTextField tName, tTime;
	private JPanel pName, pTime;

	public NewConnectionDialog(Stad from, Stad to) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		pName = new JPanel();
		pTime = new JPanel();

		lTitle = new JLabel("Förbindelse från " + from.getNamn() + " till "
				+ to.getNamn() + ".");
		add(lTitle);

		lName = new JLabel("Namn:");
		tName = new JTextField(15);
		pName.add(lName);
		pName.add(tName);
		add(pName);

		tTime = new JTextField(5);
		lTime = new JLabel("Tid:");
		pTime.add(lTime);
		pTime.add(tTime);
		add(pTime);

	}

	public String getName() {
		return tName.getText();
	}

	public int getTime() {
		return Integer.parseInt(tTime.getText());
	}

}
