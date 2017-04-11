package jammy.gui.dialogs;

import jammy.catalogModel.Media;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DecimalFormat;

public class InputFieldPanel extends JPanel implements FocusListener{

	public JTextField sourceTextField;
	public JTextField mediaNameTextField;
	public JTextField discNameTextField;
	public JLabel sourceLabel;
	public JLabel discNameLabel;
	public JLabel mediaNameLabel;
	DecimalFormat decimalFormatter = new DecimalFormat("0000");
	
	public void editMode(){
		sourceLabel.hide();
		sourceTextField.hide();
		
	}
	
	public InputFieldPanel(){
		// input Field panel

		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		sourceTextField = new JTextField(20);
		sourceLabel = new JLabel("Source directory:");
		mediaNameTextField = new JTextField(20);
		mediaNameTextField.addFocusListener(this);
		mediaNameLabel = new JLabel("Media name:");
		discNameTextField = new JTextField(20);
		discNameTextField.addFocusListener(this);
		discNameLabel = new JLabel("Disc name:");
		mediaNameTextField.setText(decimalFormatter.format(Media.nextMediaID));
		add(sourceLabel);
		add(sourceTextField);
		add(discNameLabel);
		add(discNameTextField);
		add(mediaNameLabel);
		add(mediaNameTextField);
	}
	@Override
	public void focusGained(FocusEvent e) {
		JTextField tf = (JTextField)e.getSource();
		
		tf.setSelectionStart(0);
		tf.setSelectionEnd(tf.getText().length());
		
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		JTextField tf = (JTextField)e.getSource();

		tf.setSelectionStart(0);
		tf.setSelectionEnd(0);
		
	}

}
