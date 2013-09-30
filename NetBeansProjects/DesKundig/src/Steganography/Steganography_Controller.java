package Steganography;

//Imports
import java.io.File;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

//Steganography_Controller Class
public class Steganography_Controller
{
	//Instanties van de klassen Steganography en Steganography_View
	private Steganography_View	view;
	private Steganography		model;
	
	//JPanel
	private JPanel		ontcijfer_paneel;                   //Panel waardat de decode elementen in komen
	private JPanel		vercijfer_paneel;                   //Panel waardat de encode elementen in komen
        
	//Variabelen op de panels
	private JTextArea 	input;                          //TextArea om tekst in te plakken die in de image geplaatst moet worden
	private JButton		vercijferKnop,ontcijferKnop;      //Buttons om te encoden en te decoden
	private JLabel		image_input;                    //Label waardat de locatie van de image is
        
	//Variabelen van het menuitem
	private JMenuItem 	exit;                           //Het menu zelf
        private JMenuItem 	vercijfer;                      //De vercijfer-knop in het menu
	private JMenuItem 	ontcijfer;                      //De ontcijfer-knop in het menu
        
	//Action event classes
	private Vercijfer          ver;                            //action event class om te vercijferen
	private Ontcijfer		ont;                            //action event class om te ontcijferen
	private EncodeButton    verKnop;                        //action event class op de knop om te vercijferen
	private DecodeButton    ontKnop;                        //action event class op de knop om te ontcijferen
	
	//decode variable
	private String          stat_path = "";
	private String		stat_name = "";
	
	/*
	 *Constructor om view, model en variabelen te initialiseren
	 *@param aView  A GUI klasse, opgeslagen als een view
	 *@param aModel A model klasse, opgeslagen als een model
	 */
	public Steganography_Controller(Steganography_View aView, Steganography aModel)
	{
		//Vraiabelen Programma
		view  = aView;          //De view
		model = aModel;         //het model
		
		//Variabelen view
		//2 views
		vercijfer_paneel	= view.getTextPanel();              //view om text van te verkrijgen en deze gebruiken om te vercijferen
		ontcijfer_paneel	= view.getImagePanel();             //view om de image te verkrijgen en deze te ontcijferen
                
		//2 data opties, text en een image
		input			= view.getText();                   //data via text
		image_input		= view.getImageInput();             //data via een image
                
		//2 knoppen
		vercijferKnop           = view.getEButton();                //knop om te vercijferen
		ontcijferKnop           = view.getDButton();                //knop om te ontcijferen
                
		//menu
		vercijfer               = view.getEncode();                 //vercijferitem van het menu
		ontcijfer               = view.getDecode();                 //het ontcijferitem van het menu
		exit			= view.getExit();                   //de exit knop in het menu
		
		//action events koppelen aan de items
		ver = new Vercijfer();                                      //een nieuwe instantie van de vercijferklasse maken
		vercijfer.addActionListener(ver);                           //de event aan
		ont = new Ontcijfer();
		ontcijfer.addActionListener(ont);
		exit.addActionListener(new Exit());
		verKnop = new EncodeButton();
		vercijferKnop.addActionListener(verKnop);
		ontKnop = new DecodeButton();
		ontcijferKnop.addActionListener(ontKnop);
		
		//encode view as default
		encode_view();
	}
	
	/*
	 *Updates the single panel to display the Encode View.
	 */
	private void encode_view()
	{
		update();
		view.setContentPane(vercijfer_paneel);
		view.setVisible(true);
	}
	
	/*
	 *Updates the single panel to display the Decode View.
	 */
	private void decode_view()
	{
		update();
		view.setContentPane(ontcijfer_paneel);
		view.setVisible(true);
	}
	
	/*
	 *Encode Class - handles the Encode menu item
	 */
	private class Vercijfer implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			encode_view(); //show the encode view
		}
	}
	
	/*
	 *Decode Class - handles the Decode menu item
	 */
	private class Ontcijfer implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			decode_view(); //show the decode view
			
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String image = directory.getPath();
					stat_name = directory.getName();
					stat_path = directory.getPath();
					stat_path = stat_path.substring(0,stat_path.length()-stat_name.length()-1);
					stat_name = stat_name.substring(0, stat_name.length()-4);
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
				}
				catch(Exception except) {
				//msg if opening fails
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	/*
	 *Exit Class - handles the Exit menu item
	 */
	private class Exit implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0); //exit the program
		}
	}
	
	/*
	 *Encode Button Class - handles the Encode Button item
	 */
	private class EncodeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String text = input.getText();
					String ext  = Image_Filter.getExtension(directory);
					String name = directory.getName();
					String path = directory.getPath();
					path = path.substring(0,path.length()-name.length()-1);
					name = name.substring(0, name.length()-4);
					
					String stegan = JOptionPane.showInputDialog(view,
									"Enter output file name:", "File name",
									JOptionPane.PLAIN_MESSAGE);
					
					if(model.encode(path,name,ext,stegan,text))
					{
						JOptionPane.showMessageDialog(view, "The Image was encoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
					}
					else
					{
						JOptionPane.showMessageDialog(view, "The Image could not be encoded!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
					//display the new image
					decode_view();
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stegan + ".png"))));
				}
				catch(Exception except) {
				//msg if opening fails
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
	}
	
	/*
	 *Decode Button Class - handles the Decode Button item
	 */
	private class DecodeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			String message = model.decode(stat_path, stat_name);
			System.out.println(stat_path + ", " + stat_name);
			if(message != "")
			{
				encode_view();
				JOptionPane.showMessageDialog(view, "The Image was decoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
				input.setText(message);
			}
			else
			{
				JOptionPane.showMessageDialog(view, "The Image could not be decoded!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/*
	 *Updates the variables to an initial state
	 */
	public void update()
	{
		input.setText("");			//clear textarea
		image_input.setIcon(null);	//clear image
		stat_path = "";				//clear path
		stat_name = "";				//clear name
	}
	
	/*
	 *Main Method for testing
	 */
	public static void main(String args[])
	{
		new Steganography_Controller(
									new Steganography_View("Steganography"),
									new Steganography()
									);
	}
}