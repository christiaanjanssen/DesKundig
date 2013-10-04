package Steganography;

/*
 *Import Lijst
 */
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

/*
 *Klasse: steganografieView
 */
public class SteganografieView extends JFrame {
    
    //Variabelen voor instellingen van het scherm
    private static int breedte = 500;                                           //een breedte van 500 pixels
    private static int hoogte = 400;                                            //een hoogte van 400 pixels
    
    //JPanel elementen
    private JTextArea invoer;                                                   //een tekstveld voor de invoertekst die vercijferd moet worden in de afbeelding
    private JScrollBar scroll, scroll2;                                         //2 scrollelementen aanmaken
    private JButton vercijferKnop, ontcijferKnop;                               //een vercijfer en ontcijferknop aanmaken
    private JLabel afbeeldingInvoer;                                            //een label
    
    //Menu elementen
    private JMenu bestand;                                                      //een bestand
    private JMenuItem vercijfer;                                                //vercijfer menu item
    private JMenuItem ontcijfer;                                                //ontcijfer menu item
    private JMenuItem exit;                                                     //exit menu item

    /*
     *Constructor for Steganography_View class
     *@param name Used to set the title on the JFrame
     */
    public SteganografieView(String name) {
        //een titel geven aan de view
        super(name);

        //Menubar
        JMenuBar menu = new JMenuBar();                 //Nieuwe menubar
            
        JMenu bstand = new JMenu("Bestand");            //naam ven het JMenu wordt bestand
        bstand.setMnemonic('F');                        
        vercijfer = new JMenuItem("Vercijfer");         //een nieuw menuitem met als tekst vercijfer
        vercijfer.setMnemonic('E');
        bstand.add(vercijfer);                          //menuitem toevoegen aan JMenu
        ontcijfer = new JMenuItem("Ontcijfer");         //een nieuw menuitem met als tekst ontcijfer
        ontcijfer.setMnemonic('D');
        bstand.add(ontcijfer);                          //menuItem toevoegen aan JMenu
        bstand.addSeparator();                          //een seperator toevoegen tussen vercijfer en ontcijfer
        exit = new JMenuItem("Exit");                   //een nieuwe menuitem met als tekst exit
        exit.setMnemonic('x');
        bstand.add(exit);                               //een menuitem toevoegen aan JMenu

        menu.add(bstand);                               //Het JMenu aan de JMenuBar toevoegen
        setJMenuBar(menu);                              //als menuBar het JMenu zetten

        // display rules
        setResizable(true);                                                     //laat toe dat het scherm van hoogte en breedte aangepast kan worden door middel van de muis
        setBackground(Color.lightGray);                                         //een achtergrondkleur: lichtgrijs
        setLocation(100, 100);                                                  //locatie van het opstartende scherm instellen
        setDefaultCloseOperation(EXIT_ON_CLOSE);                                //als het venster gesloten wordt, wordt het geexit
        setSize(breedte, hoogte);                                               //de dimensies van het scherm met de eerder aangemaakte breedte en hoogte
        setVisible(true);                                                       //laat het scherm zien door het zichtbaar te maken
    }

    //getters
    /*
     *@Terugkeerwaarde: menuitem vercijfer
     */
    public JMenuItem getVercijfer() {
        return vercijfer;
    }
    
    /*
     *@Terugkeerwaarde: menuitem ontcijfer
     */
    public JMenuItem getOntcijfer() {
        return ontcijfer;
    }
    
    /*
     *@Terugkeerwaarde: menuitem exit
     */
    public JMenuItem getExit() {
        return exit;
    }
    
    /*
     *@Terugkeerwaarde: tekstveld die de tekst bevat die vercijferd moet worden
     */
    public JTextArea getTekst() {
        return invoer;
    }
    
    /*
     *@Terugkeerwaarde: het label die de afbeelding bevat die ontcijferd moet worden
     */
    public JLabel getAfbeeldingInvoer() {
        return afbeeldingInvoer;
    }
    
    /*
     *@Terugkeerwaarde: JPaneel waardat de vercijferview op komt te staan
     */
    public JPanel getTekstPaneel() {
        return new tekstPaneel();
    }
    
    /*
     *@Terugkeerwaarde: JPaneel waardat de ontcijferview op komt te staan
     */
    public JPanel getAfbeeldingPaneel() {
        return new afbeeldingsPaneel();
    }
    
    /*
     *@Terugkeerwaarde: vercijferKnop
     */
    public JButton getVercijferKnop() {
        return vercijferKnop;
    }
    
    /*
     *@Terugkeerwaarde: ontcijferKnop
     */
    public JButton getOntcijferKnop() {
        return ontcijferKnop;
    }

    /*
     *Klasse: TesktPaneel
     */
    private class tekstPaneel extends JPanel {
        /*
         *Constructor to enter text to be encoded
         */

        public tekstPaneel() {
            //setup GridBagLayout
            GridBagLayout layout = new GridBagLayout();                         //een gridbaglayout initialiseren
            GridBagConstraints layoutInstellingen = new GridBagConstraints();   //de instellingeninstantie aanmake waardoor we deze layout gedetaileerd kunnen aanpassen
            setLayout(layout);                                                  //de layout ven het tekstpaneel zetten op de variabele layour waar dat een gridbaglayout in zit

            invoer = new JTextArea();                                           //de variabele invoer vullen met een tekstveld
            layoutInstellingen.gridx = 0;                                       //dimensies van de gridbaglayout bepalen
            layoutInstellingen.gridy = 0;                                       //dimensies van de gridbaglayout bepalen
            layoutInstellingen.gridwidth = 1;                                   //dimensies van de gridbaglayout bepalen
            layoutInstellingen.gridheight = 1;                                  //dimensies van de gridbaglayout bepalen
            layoutInstellingen.fill = GridBagConstraints.BOTH;                  
            layoutInstellingen.insets = new Insets(0, 0, 0, 0);
            layoutInstellingen.anchor = GridBagConstraints.CENTER;
            layoutInstellingen.weightx = 1.0;
            layoutInstellingen.weighty = 50.0;
            JScrollPane scroll = new JScrollPane(invoer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,          //scrollpane toegvoegen, maar alleen als het noodzakelijk is, deze is dus niet altijd zichtbaar
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout.setConstraints(scroll, layoutInstellingen);                                              //scrollitem effectief toevoegen
            scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            add(scroll);

            vercijferKnop = new JButton("Vercijferen!");                        //een knop aanmaken met als tekst "vercijferen!"
            layoutInstellingen.gridx = 0;                                       //dimensies van de gridbaglayout bepalen
            layoutInstellingen.gridy = 1;                                       //dimensies van de gridbaglayout bepalen
            layoutInstellingen.gridwidth = 1;                                   //dimensies van de gridbaglayout bepalen
            layoutInstellingen.gridheight = 1;                                  //dimensies van de gridbaglayout bepalen
            layoutInstellingen.fill = GridBagConstraints.BOTH;                  //dimensies van de gridbaglayout bepalen
            layoutInstellingen.insets = new Insets(0, -5, -5, -5);              //dimensies van de gridbaglayout bepalen
            layoutInstellingen.anchor = GridBagConstraints.CENTER;              //dimensies van de gridbaglayout bepalen
            layoutInstellingen.weightx = 1.0;                                   //dimensies van de gridbaglayout bepalen
            layoutInstellingen.weighty = 1.0;                                   //dimensies van de gridbaglayout bepalen
            layout.setConstraints(vercijferKnop, layoutInstellingen);           
            add(vercijferKnop);

            //basisinstellingen
            setBackground(Color.lightGray);                                     //lichtgrijze achtergrond
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));          //zwarte border
        }
    }

    /*
     *Klasse: AfbeeldingsPaneel
     */
    private class afbeeldingsPaneel extends JPanel {
        
        /*
         *constructor om een afbeelding te tonen die ontcijferd moet worden
         */
        public afbeeldingsPaneel() {
            //setup GridBagLayout
            GridBagLayout layout2 = new GridBagLayout();                        //een gridbaglayout initialiseren                          
            GridBagConstraints layoutInstellingen2 = new GridBagConstraints();
            setLayout(layout2);

            afbeeldingInvoer = new JLabel();                                    //een nieuw JLabel voor de invoer van de afbeelding die ontcijferd moet worden
            layoutInstellingen2.gridx = 0;                                      //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.gridy = 0;                                      //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.gridwidth = 1;                                  //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.gridheight = 1;                                 //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.fill = GridBagConstraints.BOTH;                 //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.insets = new Insets(0, 0, 0, 0);                //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.anchor = GridBagConstraints.CENTER;             //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.weightx = 1.0;                                  //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.weighty = 50.0;                                 //dimensies van de gridbaglayout bepalen
            JScrollPane scroll2 = new JScrollPane(afbeeldingInvoer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,           //scroll toevoegen van zodra het nodig is
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout2.setConstraints(scroll2, layoutInstellingen2);
            scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            afbeeldingInvoer.setHorizontalAlignment(JLabel.CENTER);
            add(scroll2);

            ontcijferKnop = new JButton("Ontcijfer");                           //nieuwe ontcijfer knop
            layoutInstellingen2.gridx = 0;                                      //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.gridy = 1;                                      //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.gridwidth = 1;                                  //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.gridheight = 1;                                 //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.fill = GridBagConstraints.BOTH;                 //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.insets = new Insets(0, -5, -5, -5);             //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.anchor = GridBagConstraints.CENTER;             //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.weightx = 1.0;                                  //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.weighty = 1.0;                                  //dimensies van de gridbaglayout bepalen
            layout2.setConstraints(ontcijferKnop, layoutInstellingen2);
            add(ontcijferKnop);

            //set basisscherm
            setBackground(Color.lightGray);                                     //achtergrondkleur: lichtgrijs
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));          //lijn zwarte lijn errond
        }
    }

    /*
     *Main Methode om te testen
     */
    public static void main(String args[]) {
        new SteganografieView("Steganografie");
    }
}