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
            //layout van het scherm instellen, hier dus een gridbaglayout
            GridBagLayout layout = new GridBagLayout();                         //een gridbaglayout initialiseren
            GridBagConstraints layoutInstellingen = new GridBagConstraints();   //de instellingeninstantie aanmake waardoor we deze layout gedetaileerd kunnen aanpassen
            setLayout(layout);                                                  //de layout ven het tekstpaneel zetten op de variabele layour waar dat een gridbaglayout in zit

            //layout van het tekstpaneel
            invoer = new JTextArea();         
            ontcijferKnop = new JButton("Ontcijferen");                         //nieuwe ontcijfer knop
            vercijferKnop = new JButton("Vercijferen");                         //een knop aanmaken met als tekst "vercijferen!"       
            layoutInstellingen.gridx = 0;                                       //de vercijferknop op in de eerste kolom zetten, ook de enige gebruikte kolom
            layoutInstellingen.gridy = 1;                                       //de plaats van het textareas op de eerste plaats zetten (y = verticaal = rij)
            layoutInstellingen.fill = GridBagConstraints.BOTH;                  //plaats van het tekstveld specifiëren
            layoutInstellingen.anchor = GridBagConstraints.CENTER;              //het midden van het tekstveld vastzetten aan het midden van het deel van het scherm waardat deze geplaatst wordt
            layoutInstellingen.weighty = 100.0;                                 //voldoende plaats van het scherm opvullen met het tekstveld
            
            //scrollpane
            JScrollPane scroll = new JScrollPane(invoer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout.setConstraints(scroll, layoutInstellingen);                  //layoutinstellingen van het tekstveld hieraan koppelen
            add(scroll);                                                        //scrollpane toevoegen aan het scherm, alleen als het nodig is
            
            //layout van de vercijferknop
            layoutInstellingen.gridx = 0;                                       //de vercijferknop op in de eerste kolom zetten, ook de enige gebruikte kolom
            layoutInstellingen.gridy = 2;                                       //de plaats van de vercijferknop op de tweede plaats zetten (y = verticaal = rij)
            layoutInstellingen.anchor = GridBagConstraints.LAST_LINE_START;     //dimensies van de gridbaglayout bepalen
            layoutInstellingen.weightx = 1.0;                                   //dimensies van de gridbaglayout bepalen
            layoutInstellingen.weighty = 1.0;                                   //dimensies van de gridbaglayout bepalen
            layout.setConstraints(vercijferKnop, layoutInstellingen);           //layoutinstelling toepassen op de vercijferknop
            add(vercijferKnop);

            //layout van de ontcijferknop
            layoutInstellingen.gridx = 0;                                       //de vercijferknop op in de eerste kolom zetten, ook de enige gebruikte kolom
            layoutInstellingen.gridy = 3;                                       //dimensies van de gridbaglayout bepalen
            layoutInstellingen.anchor = GridBagConstraints.LAST_LINE_END;       //de plaats van de ontcijferknop vanonder op de laatste plaats zetten
            layoutInstellingen.weightx = 1.0;                                   //dimensies van de gridbaglayout bepalen
            layoutInstellingen.weighty = 1.0;                                   //dimensies van de gridbaglayout bepalen
            layout.setConstraints(ontcijferKnop, layoutInstellingen);           //layoutinstellingen toepassen op de ontcijferknop
            add(ontcijferKnop);

            //basisinstellingen van het scherm
            setBackground(Color.lightGray);                                     //lichtgrijze achtergrond
        
            afbeeldingInvoer = new JLabel();                                    //een nieuw JLabel voor de invoer van de afbeelding die ontcijferd moet worden
            layoutInstellingen.gridx = 1;                                       //de vercijferknop op in de eerste kolom zetten, ook de enige gebruikte kolom
            layoutInstellingen.gridy = 1;
            layoutInstellingen.anchor   = GridBagConstraints.CENTER;             //dimensies van de gridbaglayout bepalen
            JScrollPane scroll2 = new JScrollPane(afbeeldingInvoer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout.setConstraints(scroll2, layoutInstellingen);
            scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            afbeeldingInvoer.setHorizontalAlignment(JLabel.CENTER);
            add(scroll2);
        }
    }

    /*
     *Main Methode om te testen
     */
    public static void main(String args[]) {
        new SteganografieView("Steganografie");
    }
}