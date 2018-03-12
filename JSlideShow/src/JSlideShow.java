import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class JSlideShow {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        new JSlideShowGUI();
		      }
		    });

	}

}

class JSlideShowGUI implements ActionListener{
	JFrame Frame;
	JPanel Panel;
	JPanel buttonPanel;
	JButton Play;
	JButton Pause;
	
	JMenuItem LoadURL;
	JMenuItem LoadFromPC;
	
	
	public JSlideShowGUI() {
		Frame = new JFrame();
		Frame.setSize(800, 600);
		Frame.setLocationRelativeTo(null);
		Frame.setLayout(new GridLayout(0,1));
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar MenuBar = new JMenuBar();
		JMenu Menu = new JMenu("File");
		LoadURL = new JMenuItem("use URL");
		LoadURL.addActionListener(this);
		LoadFromPC = new JMenuItem("file:// local");
		LoadFromPC.addActionListener(this);
		
		Menu.add(LoadURL);
		Menu.add(LoadFromPC);
		MenuBar.add(Menu);
		Frame.setJMenuBar(MenuBar);
		
		
		Panel = new JPanel();
		buttonPanel = new JPanel();
		
		
		Play = new JButton("Play");
		Pause = new JButton("Pause");
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 10, 10));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(Play);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(Pause);
		
		
		Frame.add(Panel);
		Frame.add(buttonPanel);
		
		Frame.setVisible(true);
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
