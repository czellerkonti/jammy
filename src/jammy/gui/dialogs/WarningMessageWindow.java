package jammy.gui.dialogs;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class WarningMessageWindow extends JFrame{

	public WarningMessageWindow(String msg){
		JOptionPane.showMessageDialog(this, msg);
	}
}
