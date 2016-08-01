package implementationSMA.Vue;

import javax.swing.DefaultListModel;

public class MyListModel extends DefaultListModel<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void fireContentsChanged(Object source, int firstIndex, int lastIndex) {
		   super.fireContentsChanged(source, firstIndex, lastIndex);
		 }

}
