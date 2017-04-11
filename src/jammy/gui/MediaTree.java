package jammy.gui;

import jammy.catalogModel.Media;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.JScrollPane;

public class MediaTree extends JPanel{
	
	private JTree tree;
	private JScrollPane scrollpane;
	
	public MediaTree(Media media){
		super();
		tree = new JTree(MutableTreeNodeCreator.generateNode(media));
		add(tree);
		//setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrollpane = new JScrollPane(this);
	}
	
	public JScrollPane getScrollPane(){
		return scrollpane;
	}

}
