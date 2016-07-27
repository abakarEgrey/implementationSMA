package implementationSMA;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.irit.smac.libs.tooling.scheduling.contrib.twosteps.BadStepRuntimeException;

public class SmaInterface extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton addCompnent;
	private JButton addItem;
	private JComboBox<String> jComboBox1;
	private JButton removeComponent;
	private JButton removeItem;
	private String item;
	private JList<String> jList1;
	private JScrollPane jScrollPane1;
	private DefaultListModel<String> listModel;
	private String instanceName;

	public SmaInterface(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
		listModel = new DefaultListModel<String>();
		jList1 = new JList<>(listModel);
		jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jScrollPane1 = new JScrollPane(jList1);

		buildContentPane();

	}

	private void buildContentPane() {
		/*
		 * JPanel panel = new JPanel();
		 * 
		 * this.setSize(200, 200); panel.setLayout(new FlowLayout());
		 * 
		 * JButton bouton = new JButton("Add Button");
		 * bouton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) { // TODO
		 * Auto-generated method stub TestUI.getInstance().addButton(); } });
		 * panel.add(bouton);
		 * 
		 * JButton bouton2 = new JButton("Add Impress");
		 * bouton2.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) { // TODO
		 * Auto-generated method stub TestUI.getInstance().addImpressJ(); } });
		 * panel.add(bouton2); this.add(panel);
		 * 
		 * JButton bouton3 = new JButton("Add Arduino");
		 * bouton3.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) { // TODO
		 * Auto-generated method stub TestUI.getInstance().addArduino(); } });
		 * panel.add(bouton3); this.add(panel);
		 */

		addItem = new javax.swing.JButton();
		addItem.setEnabled(false);
		jComboBox1 = new javax.swing.JComboBox<>();
		removeItem = new javax.swing.JButton();
		removeItem.setEnabled(false);
		addCompnent = new javax.swing.JButton();
		removeComponent = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		addItem.setText("AddItem");
		addItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addItemActionPerformed(evt);
			}
		});

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "Button", "ImpressJ", "Winamp", "Ardino", "Joystick" }));
		jComboBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox1ActionPerformed(evt);
			}
		});

		removeItem.setText("RemoveItem");
		removeItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeItemActionPerformed(evt);
			}
		});

		addCompnent.setText("AddComponent");
		addCompnent.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addCompnentActionPerformed(evt);
			}
		});

		removeComponent.setText("RemoveComponent");
		removeComponent.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeComponentActionPerformed(evt);
			}
		});

		jList1.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				instanceName = e.getSource().toString();
			}
		});
		// jList1.setModel(this.listModel);
		jScrollPane1.setViewportView(jList1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(135, 135, 135).addComponent(addCompnent))
						.addGroup(layout.createSequentialGroup().addContainerGap()
								.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(28, 28, 28)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(removeItem, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(addItem, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 163,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(removeComponent, javax.swing.GroupLayout.Alignment.TRAILING))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(27, 27, 27)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(addItem).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(removeItem)
						.addGap(0, 0, Short.MAX_VALUE))
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
				.addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(addCompnent).addComponent(removeComponent))
				.addGap(23, 23, 23)));

		pack();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		setVisible(true);

	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	private void removeItem(Object index) {
		jComboBox1.removeItem(index);

	}

	private void addItem(String index) {
		jComboBox1.addItem(index);
	}

	private void removeComponentActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String componentName = (String) jComboBox1.getSelectedItem();
		// supprimer le composant
		System.out.println("MaFenetre.removeComponentActionPerformed()");
		// supression de l'index
		removeItem(componentName);

	}

	private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void addItemActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		addItem(item);
	}

	private void removeItemActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		/*
		 * int index = jComboBox1.getSelectedIndex();
		 * jComboBox1.removeItemAt(index);
		 */
		removeItem(jComboBox1.getSelectedItem());
	}

	private void addCompnentActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		String componentName = (String) jComboBox1.getSelectedItem();
		// ajouter le composant connaissant son nom

		switch (componentName) {
		case "Button":
			TestUI.getInstance().addButton();
			// showConnexion("test", "test");
			break;
		case "ImpressJ":
			TestUI.getInstance().addImpressJ();

			break;
		case "Winamp":
			TestUI.getInstance().addWinamp();
			break;
		case "Ardino":
			TestUI.getInstance().addArduino();
			break;
		case "Joystick":
			break;
		default:
			// throw new BadStepRuntimeException("case not covered");

		}
	}

	/**
	 * 
	 * @param beanName1
	 * @param beanName2
	 */
	public void showConnexion(String beanName1, String beanName2) {

		Runnable code = new Runnable() {
			public void run() {
				listModel.addElement(beanName1 + " -> " + beanName2);
			}
		};

		if (SwingUtilities.isEventDispatchThread()) {
			code.run();
		} else {
			SwingUtilities.invokeLater(code);
		}

	}

	public void addInstance(String s) {
		Runnable code = new Runnable() {
			public void run() {
				listModel.addElement(s);
			}
		};

		if (SwingUtilities.isEventDispatchThread()) {
			code.run();
		} else {
			SwingUtilities.invokeLater(code);
		}
	}
}
