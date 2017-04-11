package jammy.gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;



import java.awt.Dimension;

public class TabComponent extends JPanel{
	
	MediaTreeTabbedPane tabbedPane;
	
	public TabComponent(MediaTreeTabbedPane tabbedPane){
		this.tabbedPane = tabbedPane;
		JLabel label = new JLabel(tabbedPane.getTitleAt(tabbedPane.getTabCount()-1));
		add(label);
		add(new TabButton());
		label.setOpaque(true);
		this.setOpaque(true);
		
	}
	


private class TabButton extends JButton implements MouseListener{
    
	Ellipse2D.Double circle = new Ellipse2D.Double(0.5,0.5,10,10);
	
	
	
	public TabButton() {
        setPreferredSize(new Dimension(12,12));
        setBorderPainted(true);
		setContentAreaFilled(false);
		setFocusable(false);
		setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        setRolloverEnabled(true);
        setOpaque(false);
        this.addMouseListener(this);
    }
    
    //paint the cross
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        if(getModel().isPressed()){
    		g2.setColor(new Color(0,0,0));
          	g2.draw(circle);
            g2.fill(circle);
            g2.setColor(new Color(249,235,235));
            g2.drawLine(3,8,8,3);
          	g2.drawLine(3,7,8,2);
          	g2.drawLine(3,3,8,8);
          	g2.drawLine(3,2,8,7);
            g2.dispose();    	
    	}
      
        
        if(getModel().isRollover()){
            g2.setColor(new Color(193,53,53));
        	g2.draw(circle);
            g2.fill(circle);
            g2.setColor(new Color(249,235,235));
            g2.drawLine(3,8,8,3);
        	g2.drawLine(3,7,8,2);
        	g2.drawLine(3,3,8,8);
        	g2.drawLine(3,2,8,7);
            g2.dispose();    	
        }else{
        	g2.setColor(new Color(136,144,151));
        	g2.drawLine(3,8,8,3);
        	g2.drawLine(3,7,8,2);
        	g2.drawLine(3,3,8,8);
        	g2.drawLine(3,2,8,7);
        }
    	
    
        
    }

    
    
	public void mouseClicked(MouseEvent e) {
		
		int i = tabbedPane.indexOfTabComponent(this.getParent());
		if (i != -1) {
        	System.out.println("remove: "+i);
        	tabbedPane.remove(i);
        }

		
	}

	public void mouseEntered(MouseEvent e) {
		
		
	}

	public void mouseExited(MouseEvent e) {
		
		
	}

	public void mousePressed(MouseEvent e) {
		// DO NOTHING
		
	}

	public void mouseReleased(MouseEvent e) {
		//DO NOTHING
	}



}
}