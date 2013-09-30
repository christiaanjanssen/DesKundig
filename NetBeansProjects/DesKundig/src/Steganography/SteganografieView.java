package Steganography;

/*
 *Import Lijst
 */
import java.awt.Color;
import java.awt.Insets;
import java.awt.Container;
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
        //set the title of the JFrame
        super(name);

        //Menubar
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        vercijfer = new JMenuItem("Encode");
        vercijfer.setMnemonic('E');
        file.add(vercijfer);
        ontcijfer = new JMenuItem("Decode");
        ontcijfer.setMnemonic('D');
        file.add(ontcijfer);
        file.addSeparator();
        exit = new JMenuItem("Exit");
        exit.setMnemonic('x');
        file.add(exit);

        menu.add(file);
        setJMenuBar(menu);

        // display rules
        setResizable(true);                                                     //allow window to be resized: true?false
        setBackground(Color.lightGray);                                         //background color of window: Color(int,int,int) or Color.name
        setLocation(100, 100);                                                  //location on the screen to display window
        setDefaultCloseOperation(EXIT_ON_CLOSE);                                //what to do on close operation: exit, do_nothing, etc
        setSize(breedte, hoogte);                                               //set the size of the window
        setVisible(true);                                                       //show the window: true?false
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
        return new Image_Panel();
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
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints layoutConstraints = new GridBagConstraints();
            setLayout(layout);

            invoer = new JTextArea();
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = 0;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            layoutConstraints.fill = GridBagConstraints.BOTH;
            layoutConstraints.insets = new Insets(0, 0, 0, 0);
            layoutConstraints.anchor = GridBagConstraints.CENTER;
            layoutConstraints.weightx = 1.0;
            layoutConstraints.weighty = 50.0;
            JScrollPane scroll = new JScrollPane(invoer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout.setConstraints(scroll, layoutConstraints);
            scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            add(scroll);

            vercijferKnop = new JButton("Encode Now");
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = 1;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            layoutConstraints.fill = GridBagConstraints.BOTH;
            layoutConstraints.insets = new Insets(0, -5, -5, -5);
            layoutConstraints.anchor = GridBagConstraints.CENTER;
            layoutConstraints.weightx = 1.0;
            layoutConstraints.weighty = 1.0;
            layout.setConstraints(vercijferKnop, layoutConstraints);
            add(vercijferKnop);

            //set basic display
            setBackground(Color.lightGray);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
    }

    /*
     *Class Image_Panel
     */
    private class Image_Panel extends JPanel {
        /*
         *Constructor for displaying an image to be decoded
         */

        public Image_Panel() {
            //setup GridBagLayout
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints layoutConstraints = new GridBagConstraints();
            setLayout(layout);

            afbeeldingInvoer = new JLabel();
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = 0;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            layoutConstraints.fill = GridBagConstraints.BOTH;
            layoutConstraints.insets = new Insets(0, 0, 0, 0);
            layoutConstraints.anchor = GridBagConstraints.CENTER;
            layoutConstraints.weightx = 1.0;
            layoutConstraints.weighty = 50.0;
            JScrollPane scroll2 = new JScrollPane(afbeeldingInvoer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            layout.setConstraints(scroll2, layoutConstraints);
            scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            afbeeldingInvoer.setHorizontalAlignment(JLabel.CENTER);
            add(scroll2);

            ontcijferKnop = new JButton("Ontcijfer");
            layoutConstraints.gridx = 0;
            layoutConstraints.gridy = 1;
            layoutConstraints.gridwidth = 1;
            layoutConstraints.gridheight = 1;
            layoutConstraints.fill = GridBagConstraints.BOTH;
            layoutConstraints.insets = new Insets(0, -5, -5, -5);
            layoutConstraints.anchor = GridBagConstraints.CENTER;
            layoutConstraints.weightx = 1.0;
            layoutConstraints.weighty = 1.0;
            layout.setConstraints(ontcijferKnop, layoutConstraints);
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