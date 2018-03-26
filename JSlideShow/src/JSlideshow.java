// 
//    Name:  Lee, Kevin
//    Project: Extra
//    Due:       3/12/2018 
//    Course: CS-245-01-w18 
// 
//    Description: 
//     A Slideshow application that takes ina  text file with URLs and fiepaths and turns them into  slide show that can top and start.
//
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class JSlideshow {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		        try {
					new JSlideShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	BufferedImage img;
	Timer timer;
	
	int count;
	
	JList<String> list;
	DefaultListModel<String> model1;
	
	JMenuItem LoadURL;
	JMenuItem LoadFromPC;
	ImagePanel newpanel;
	
	
	public JSlideShowGUI() throws IOException { 
		Frame = new JFrame();
		Frame.setSize(800, 600);
		Frame.setLocationRelativeTo(null);
//		Frame.setLayout();
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar MenuBar = new JMenuBar();
		JMenu Menu = new JMenu("File");
		LoadURL = new JMenuItem("Upload Text File");
		LoadURL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser filechoose = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt", "text");
				filechoose.setFileFilter(filter);
				int value = filechoose.showOpenDialog(null);
				if (value == JFileChooser.APPROVE_OPTION) {
					File file = filechoose.getSelectedFile();
					try {
						readList(file);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		model1 = new DefaultListModel<String>();
		Menu.add(LoadURL);
		MenuBar.add(Menu);
		Frame.setJMenuBar(MenuBar);
		
		
		Panel = new JPanel();
		buttonPanel = new JPanel();
		
		
		Play = new JButton("Play");
		Play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				timer.start();
			}
		});
		Pause = new JButton("Pause");
		Pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				timer.stop();
			}
		});
		buttonPanel.setMaximumSize(new Dimension(800, 100));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(Play);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(Pause);
		list = new JList<String>();
		newpanel = new ImagePanel();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		timer =new Timer(3000, ae->  {
			if (list.getModel().getSize()!= 0) {
				if (count < list.getModel().getSize()) {
					
						list.setSelectedIndex(count);
						try {
							newpanel.setBackground(getPic(list.getSelectedValue()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						newpanel.setVisible(false);
						newpanel.setVisible(true);
						count++;
					
				} else {
					count = 0;
					
						list.setSelectedIndex(count);
						try {
							newpanel.setBackground(getPic(list.getSelectedValue()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						newpanel.setVisible(false);
						newpanel.setVisible(true);
						count++;
					
				}
			}
		});
		list.setModel(model1);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				System.exit(1);
			}
		});
		
		JMenuItem About = new JMenuItem("About");
		About.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				JOptionPane.showMessageDialog(Frame, "(C) Kevin Lee", "Information About Me", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		Menu.addSeparator();
		Menu.add(exit);
		Menu.add(About);
		panel.add(newpanel);
		panel.add(buttonPanel);
		Frame.add(panel);
		Frame.setVisible(true);
		
		
	}
	
  
	
	public void readList(File file) throws IOException {
		model1.removeAllElements();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String Line;
		while ((Line = br.readLine())!=null) {
			model1.addElement(Line);
			System.out.println(Line);
		}
		list.setSelectedIndex(count);
		newpanel.setBackground(getPic(list.getSelectedValue()));
		newpanel.setVisible(false);
		newpanel.setVisible(true);
		count = 1;
		
	}
	
	public BufferedImage getPic(String x) throws IOException {
		if (x.contains("image-file-spec(http")) {
			URL hold = new URL(x.substring(16, x.length()-1));
			HttpURLConnection uc = (HttpURLConnection) hold.openConnection(); 
			uc.addRequestProperty("User-Agent", "");
			BufferedImage image = ImageIO.read(uc.getInputStream());
			return image;
		} else {
			BufferedImage image = ImageIO.read((new File(x.substring(23, x.length()-1))));
			return image;
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
class ImagePanel extends JPanel {

    Image image;

    public void setBackground(Image image) {
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics G) {
        super.paintComponent(G);
        G.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    }

}
