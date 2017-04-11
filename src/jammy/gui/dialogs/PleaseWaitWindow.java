package jammy.gui.dialogs;

import javax.swing.JWindow;
import javax.swing.JLabel;

import java.awt.BorderLayout;

public class PleaseWaitWindow extends JWindow {
	
	private String waitMSG = "Reading Media, Please wait...";
	
	public PleaseWaitWindow(){
		getContentPane().add(new JLabel(waitMSG), BorderLayout.CENTER);
		setSize(100,50);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public PleaseWaitWindow(String waitMSG){
		this.waitMSG = waitMSG;
		
	}
}
