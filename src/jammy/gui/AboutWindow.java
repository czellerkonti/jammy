package jammy.gui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutWindow extends JFrame{

	public AboutWindow(){
		setTitle("about");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel pane = new JPanel();
		pane.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		pane.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		pane.setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));
		JLabel author = new JLabel("author: Konstantin Czeller");
		JLabel contact = new JLabel("contact: kontika-list@freemail.hu");
		JLabel release = new JLabel("version: 1.1");
		pane.add(author);
		pane.add(contact);
		pane.add(release);
		add(pane);
		this.setPreferredSize(new Dimension(300,200));
		pack();
		setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint());
		setVisible(true);
		setResizable(false);
	}
}
