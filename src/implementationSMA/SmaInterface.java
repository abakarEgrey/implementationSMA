package implementationSMA;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SmaInterface extends JFrame implements Runnable{

	
	protected void buildContentPane(){
		JPanel panel = new JPanel();

		this.setSize(200, 200);
		panel.setLayout(new FlowLayout());
 
		JButton bouton = new JButton("Add Button");
		bouton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TestUI.getInstance().addButton();
			}
        });
		panel.add(bouton);
 
		JButton bouton2 = new JButton("Add Impress");
		bouton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TestUI.getInstance().addImpressJ();
			}
        });
		panel.add(bouton2);
		this.add(panel);
		
		JButton bouton3 = new JButton("Add Arduino");
		bouton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TestUI.getInstance().addArduino();
			}
        });
		panel.add(bouton3);
		this.add(panel);
	}
	
	
	public SmaInterface(String title) throws HeadlessException {
		super(title);
		// TODO Auto-generated constructor stub
		buildContentPane();
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		new SmaInterface("maFenetre").setVisible(true);
		
	}
	
	
}
