package Steganography;

/*
 *Import Lijst
 */
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


/*
 *Klasse: steganografieView
 */
public class SteganografieView extends JFrame {
    
    //Variabelen voor instellingen van het scherm
    private static int breedte = 1200;                                           //een breedte van 500 pixels
    private static int hoogte = 800;                                            //een hoogte van 400 pixels
    
    //JPanel elementen
    private JTextArea invoer;                                                   //een tekstveld voor de invoertekst die vercijferd moet worden in de afbeelding
    private JButton vercijferKnop, ontcijferKnop;                               //een vercijfer en ontcijferknop aanmaken
    private JLabel afbeeldingInvoer;                                            //een label
    
    
    /*
     *Constructor for Steganography_View class
     *@param name Used to set the title on the JFrame
     */
    public SteganografieView(String name) {
        //een titel geven aan de view
        super(name);

        //Set look and feel to Nimbus
        //http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/nimbus.html
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        // display rules
        setResizable(true);                                                     //laat toe dat het scherm van hoogte en breedte aangepast kan worden door middel van de muis
        setBackground(Color.lightGray);                                         //een achtergrondkleur: lichtgrijs
        setLocation(100, 100);                                                  //locatie van het opstartende scherm instellen
        setDefaultCloseOperation(EXIT_ON_CLOSE);                                //als het venster gesloten wordt, wordt het geexit
        setSize(breedte, hoogte);                                               //de dimensies van het scherm met de eerder aangemaakte breedte en hoogte
        setVisible(true);                                                       //laat het scherm zien door het zichtbaar te maken
    }

    //getters
    
    
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
            ontcijferKnop = new JButton("Ontcijfer");                           //nieuwe ontcijfer knop
           
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
            add(ontcijferKnop);

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
            layoutInstellingen2.fill = GridBagConstraints.BOTH;                 //dimensies van de gridbaglayout bepalen
            layoutInstellingen2.anchor = GridBagConstraints.CENTER;             //dimensies van de gridbaglayout bepalen
            JScrollPane scroll2 = new JScrollPane(afbeeldingInvoer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,           //scroll toevoegen van zodra het nodig is
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout2.setConstraints(scroll2, layoutInstellingen2);
            scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            afbeeldingInvoer.setHorizontalAlignment(JLabel.CENTER);
            add(scroll2);

            

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