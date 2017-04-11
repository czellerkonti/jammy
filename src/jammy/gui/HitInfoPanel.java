package jammy.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class HitInfoPanel extends JPanel{
	
	private JLabel header;
	
	public HitInfoPanel(String expression){
		super();
		header = new JLabel("Hits for the search expression: "+expression);
		add(header);
	}
}
