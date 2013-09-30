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
public class SteganografieController
{
	//Instanties van de klassen Steganography en Steganography_View
	private SteganografieView	view;
	private Steganografie		model;
	
	//JPanel
	private JPanel		ontcijfer_paneel;                   //Panel waardat de decode elementen in komen
	private JPanel		vercijfer_paneel;                   //Panel waardat de encode elementen in komen
        
	//Variabelen op de panels
	private JTextArea 	invoer;                              //TextArea om tekst in te plakken die in de image geplaatst moet worden
	private JButton		vercijferKnop,ontcijferKnop;        //Buttons om te encoden en te decoden
	private JLabel		afbeelding_invoer;                        //Label waardat de locatie van de image is
        
	//Variabelen van het menuitem
	private JMenuItem 	exit;                               //Het menu zelf
        private JMenuItem 	vercijfer;                          //De vercijfer-knop in het menu
	private JMenuItem 	ontcijfer;                          //De ontcijfer-knop in het menu
        
	//Action event classes
	private Vercijfer           ver;                             //action event class om te vercijferen
	private Ontcijfer           ont;                             //action event class om te ontcijferen
	private VercijferKnop    verKnop;                            //action event class op de knop om te vercijferen
	private OntcijferKnop    ontKnop;                            //action event class op de knop om te ontcijferen
	
	//decode variable
	private String          stat_pad = "";
	private String		stat_naam = "";
	
	/*
	 *Constructor om view, model en variabelen te initialiseren
	 *@param aView  A GUI klasse, opgeslagen als een view
	 *@param aModel A model klasse, opgeslagen als een model
	 */
	public SteganografieController(SteganografieView aView, Steganografie aModel)
	{
		//Variabelen Programma
		view  = aView;                                              //De view
		model = aModel;                                             //het model
		
		//Variabelen view
		//2 views
		vercijfer_paneel	= view.getTekstPaneel();              //view om text van te verkrijgen en deze gebruiken om te vercijferen
		ontcijfer_paneel	= view.getAfbeeldingPaneel();             //view om de image te verkrijgen en deze te ontcijferen
                
		//2 data opties, text en een image
		invoer			= view.getTekst();                   //data via text
		afbeelding_invoer       = view.getAfbeeldingInvoer();             //data via een image
                
		//2 knoppen
		vercijferKnop           = view.getVercijferKnop();                //knop om te vercijferen
		ontcijferKnop           = view.getOntcijferKnop();                //knop om te ontcijferen
                
		//menu
		vercijfer               = view.getVercijfer();                 //vercijferitem van het menu
		ontcijfer               = view.getOntcijfer();                 //het ontcijferitem van het menu
		exit			= view.getExit();                   //de exit knop in het menu
		
		//action events koppelen aan de items
		ver = new Vercijfer();                                      //Een nieuwe instantie van de vercijferklasse maken
		vercijfer.addActionListener(ver);                           //Het event aan het vercijfer menuitem koppelen
		ont = new Ontcijfer();                                      //Een nieuwe instantie van de klasse Ontcijfer maken
		ontcijfer.addActionListener(ont);                           //Het event koppelen aan het ontcijfer menuitem koppelen
		exit.addActionListener(new Exit());                         //Het exit event koppelen aan de exitknop in het menu
		verKnop = new VercijferKnop();                              //een nieuwe instantie van vercijferknop aanmaken
		vercijferKnop.addActionListener(verKnop);                   //deze instantie koppelen aan de vercijferknop van de view
		ontKnop = new OntcijferKnop();                              //een nieuwe instantie maken van de ontcijferknop 
		ontcijferKnop.addActionListener(ontKnop);                   //deze instantie koppelen aan de ontcijferknop van de view
		
		//vercijfer view als standaar zetten, zodat deze altijd als eerste geladen wordt
                //men wilt namelijk altijd eerst vercijferen vooraleer men kan ontcijferen
		vercijfer_view();
	}
	
	/*
	 *Men gaat hier de vercijferview updaten, het vercijfer_paneel aan deze view koppelen en deze zichtbaar zetten om deze te tonen.
	 */
	private void vercijfer_view()
	{
		update();                                                   //alle items updaten
		view.setContentPane(vercijfer_paneel);                      //vercijfer_paneel eerst en vooral toevoegen aan de view
		view.setVisible(true);                                      //dit vercijferpaneel zichtbaar maken
	}
	
	/*
	 *Men gaat hier de ontcijfer_view updaten, het ontcijfer_paneel aan deze view koppelen en deze zichtbaar zetten om deze te tonen.
	 */
	private void ontcijfer_view()
	{
		update();                                                   //alle items updaten
		view.setContentPane(ontcijfer_paneel);                      //het ontcijfer_paneel aan deze view koppelen
		view.setVisible(true);                                      //het paneel ook effectief zichtbaar maken
	}
	
	/*
	 *Klasse: Vercijfer: behandelt het vercijfer menuitem
	 */
	private class Vercijfer implements ActionListener
	{
		/*
		 *klikevent voor als er op het menuitem geklikt wordt
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			vercijfer_view();                                   //Laat de Vercijfer view zien
		}
	}
	
	/*
	 *Klasse: Ontcijfer: behandelt het ontcijfer menuitem
	 */
	private class Ontcijfer implements ActionListener
	{
		/*
		 *klikevent voor als er op het menuitem geklikt wordt
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			ontcijfer_view();                                   //laat de ontcijfer view zien
			
			JFileChooser Kiezer = new JFileChooser("./");                       //zet standaard pad voor het bestand te kiezen dat ontcijfert moet worden
			Kiezer.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);    //men gaat hier de manier van kiezen koppelen aan de kiezer, dus zowel bestanden als mappen
			Kiezer.setFileFilter(new AfbeelingenFilter());                           //Er wordt een filter op het soort bestanden gezet zodat alleen images gekozen kunnen worden
			int Terugkeerwaarde = Kiezer.showOpenDialog(view);                   
			if (Terugkeerwaarde == JFileChooser.APPROVE_OPTION){                //als de terugkeerwaarde goedgekeurd is
				File directory = Kiezer.getSelectedFile();                  //dan gaat men het bestand ophalen
				try{            
					String afbeelding = directory.getPath();            //1. het pad ophalen waardat de afbeelding gestockeerd staat
					stat_naam = directory.getName();                    //2. De naam van het gekozen bestand ophalen
					stat_pad = directory.getPath();                     //3. Het effectieve pad ophalen
					stat_pad = stat_pad.substring(0,stat_pad.length()-stat_naam.length()-1);    //het laatste karakter verwijderen van het pad
					stat_naam = stat_naam.substring(0, stat_naam.length()-4);                   //de extensie verwijderen van de naam dus .jpg of .png
					afbeelding_invoer.setIcon(new ImageIcon(ImageIO.read(new File(afbeelding))));    //het icoon veranderen naar de ingeladen afbeelding
				}
				catch(Exception except) {
				//msg if opening fails
				JOptionPane.showMessageDialog(view, "The File cannot be opened!",                   //een popup wordt getoond als er een fout voorkomt
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	/*
	 *Klasse: Exit - Behandelt het exit menuitem
	 */
	private class Exit implements ActionListener
	{
		/*
		 *klikevent voor als er op het menuitem geklikt wordtt
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);                         //het programma afsluiten
		}
	}
	
	/*
	 *Klasse: VercijferKnop - behandelt de vercijferknopevents
	 */
	private class VercijferKnop implements ActionListener
	{
		/*
		 *klikevent voor als er op de knop geklikt wordt
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");                          //zet standaard pad voor het bestand te kiezen dat ontcijfert moet worden
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);       //men gaat hier de manier van kiezen koppelen aan de kiezer, dus zowel bestanden als mappen
			chooser.setFileFilter(new AfbeelingenFilter());                              //Er wordt een filter op het soort bestanden gezet zodat alleen images gekozen kunnen worden
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){                          //als de terugkeerwaarde goedgekeurd is
				File directory = chooser.getSelectedFile();                     //dan gaat men het bestand ophalen
				try{
					String tekst = invoer.getText();                        //de tekst uit het textveld wordt opgehaald en opgeslagen in de variabele text
					String ext  = AfbeelingenFilter.getExtensie(directory);     //extensie van de afbeelding wordt opgehaald
					String naam = directory.getName();                      //naam van de directory wordt opgehaald en in de variabele naam gestoken
					String pad = directory.getPath();                       //het pad naar de directory wordt opgehaald en in de variabele pad gestoken
					pad = pad.substring(0,pad.length()-naam.length()-1);    //de slash achteraan het pas wordt verwijderd
					naam = naam.substring(0, naam.length()-4);              //de extensie van de afbeelding wordt verwijderd
					
					String stegan = JOptionPane.showInputDialog(view,                           //er wordt gevraagt naar een naam voor het uitvoerbestand
									"Geef een bestandnaam op:", "bestandsnaam",
									JOptionPane.PLAIN_MESSAGE);
					
					if(model.vercijferen(pad,naam,ext,stegan,tekst))                                 //als de vercijfering lukt
					{
						JOptionPane.showMessageDialog(view, "De afbeelding is succesvol vercijferd",    //laat een bericht zien dat het succevol was
							"Success!", JOptionPane.INFORMATION_MESSAGE);
					}
					else                                                                        //anders
					{
						JOptionPane.showMessageDialog(view, "The Image could not be encoded!",          //laat een bericht zien dat de tekst niet vercijfert kan worden
							"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
					ontcijfer_view();                                                           //laat de vercijferde afbeelding zien
					afbeelding_invoer.setIcon(new ImageIcon(ImageIO.read(new File(pad + "/" + stegan + ".png"))));  
				}
				catch(Exception except) {                                                           //als er een fout opgegooid wordt
				JOptionPane.showMessageDialog(view, "Het bestand kan niet worden gevonden of het kan niet geopend worden",      
					"Foutmelding!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
	}
	
	/*
	 *Klasse: ontcijferknop - behandelt het event achter de ontcijferknop
	 */
	private class OntcijferKnop implements ActionListener
	{
		/*
		 *klikevent voor als er op de ontcijferknop geklikt wordt
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			String bericht = model.ontcijferen(stat_pad, stat_naam);                                     //De tekst gaan vercijfer door middel van de vercijfermethode in de staganography klasse
			System.out.println(stat_pad + ", " + stat_naam);                                        
			if(bericht != "")                                                                       //als de tekst niet leeg was
			{
				vercijfer_view();                                                               //laat de ontcijferde tekst zien
				JOptionPane.showMessageDialog(view, "De afbeelding is succesvol ontcijferd", 
							"Succes!", JOptionPane.INFORMATION_MESSAGE);
				invoer.setText(bericht);                                                        //zet de invoer om naar de inhoud van de variabelen bericht
			}       
			else                                                                                    //anders
			{
				JOptionPane.showMessageDialog(view, "De afbeelding kon niet worden ontcijferd!", 
							"Fout!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/*
	 *De variabelen terug leegmaken en updaten
	 */
	public void update()
	{
		invoer.setText("");			//textveld leegmaken
		afbeelding_invoer.setIcon(null);        //afbeelding leegmaken
		stat_pad = "";				//pad leegmaken
		stat_naam = "";				//naam leegmaken
	}
	
	/*
	 *Main Method om te kunnen testen
	 */
	public static void main(String args[])
	{
		new SteganografieController(
									new SteganografieView("Steganography"),
									new Steganografie()
									);
	}
}