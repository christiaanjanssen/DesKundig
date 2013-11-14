package Steganography;
/*
 *import list
 */

import java.io.File;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/*
 *Klasse: Steganografie
 */
public class Steganografie {
    /*
     *een standaard lege constructor
     */

    public Steganografie() {
    }
    
    /*
     *Een afbeelding vercijferen aan de hand van tekst, het uitvoerbestand zal van het type .png zijn
     *@param pad      Het pad (map) waar die de afbeelding bevat waarin de tekst vercijferd moet worden.
     *@param org      De naam van de afbeelding die aangepast wordt
     *@param ext1     De extensie van de afbeelding (jpg of png)
     *@param stegan	  De naam van het uitvoerbestand
     *@param bericht  De teskt die verscholen zit in de afbeelding
     *@param type	  representeert de basis vercijfering of de ingewikkelde vercijfering
     */
    public boolean vercijferen(String pad, String org, String ext1, String stegan, String bericht) {
        String bestandsNaam = pad + "/" + org + "." + ext1;                                             //naam van de afbeelding opvragen en ind e variabele bestandsNaam steken
        BufferedImage AfbeeldingOrigineel = getAfbeelding(bestandsNaam);                                //De originele afbeelding wordt opgehaald uit de map en in de variabele afbeeldingOrigineel gestoken

        //gebruikerGrootte
        BufferedImage Afb = gebruikersGrootte(AfbeeldingOrigineel);
        Afb = VoegToeTekst(Afb, bericht);                                                               //Tekst wordt in de afbeelding gestoken via de zelf gedefinieerde functie voegToeTekst()

        return (setAfbeelding(Afb, new File(pad + "/" + stegan + "." + "png"), "png"));                 //terugkeerwaarde: ???
    }

    /*
     *Neemt aan dat de afbeelding van het juiste type is en haalt the verborgen tekst uit de afbeeling
     *@param pad  Het pad waarin de afbeelding staat die ontcijferd moet worden
     *@param naam De naam van de afbeelding die ontcijferd moet worden
     *@param type nummer die aangeeft of er een standaardontcijfering moet gebeuren of een ingewikkeld ontcijfering
     */
    //http://www.dreamincode.net/forums/topic/27950-steganography/
    public String ontcijferen(String pad, String naam) {
        byte[] ontc;                                                                                    //array van bytes maken
        try {
            //GebruikersGrootte moet bepaald worden, hier wel nodig
            BufferedImage afbeelding = gebruikersGrootte(getAfbeelding(pad + "/" + naam + "." + "png"));
            WritableRaster raster = afbeelding.getRaster();
            DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
            ontc = buffer.getData();//de tekst uit de afbeelding ontcijferen en in de variabele ontc steken om deze later te gebruiken
            int lengte = 0;
            int j = 32; //de lengte van het bericht wordt in 32 bits opgeslagen.
            
            for (int i = 0; i < 32; ++i) {
                //bit shift operator: 32 keer shiften we de bits van lengte naar links, dit wordt geOR'd met de 'least significant bit' van de image byte.
                //als we &1 doen, zullen alle bits gecleared 0 gezet worden, behalve de laatste bit zelf.
                //dus als de bits worden toegevoegd, worden ze automatisch in de 'least significant bit' plaats van de lengte geplaatst.
                lengte = (lengte << 1) | (ontc[i] & 1);
            }
            
            //een nieuwe array van het type byte maken van de lengte die in variabele lengte zit
            byte[] resultaat = new byte[lengte];

            //doorheen elke byte van tekst gaan
            for (int b = 0; b < resultaat.length; ++b) {
                //elke bit in een byte doorlopen
                for (int i = 0; i < 8; ++i, ++j) {
                    //het resultaat-array bestaat uit de least significant bit van elke byte.
                    //dit wordt op dezelfde manier opgehaald als de lengte.
                    resultaat[b] = (byte) ((resultaat[b] << 1) | (ontc[j] & 1));
                }
            }
            return (new String(resultaat));                                                                  //terugkeerwaarde: de ontcijferde tekst

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, //er wordt een popup getoond als de afbeelding niet ontcijferd kan worden doordat er geen vercijferde tekst in zit
                    "Er is geen verborgen tekst in deze afbeelding!", "Fout!",
                    JOptionPane.ERROR_MESSAGE);
            return "";                                              //een lege string teruggeven, want er kan niets ontcijferd worden
        }
    }

    /*
     *Get methode om de afbeelding op te halen
     *@param p Het complete padnaam van de afbeelding.
     *@terugkeerwaarde: een afbeelding van het type .jpg of van het type .png
     */
    private BufferedImage getAfbeelding(String p) {
        BufferedImage afbeelding = null;                                 //een nieuwe instantie van een BufferedImage aanmaken en deze voolopig leeg laten.
        File bestand = new File(p);                          //een nieuwe instantie van een bestand (file) aanmaken door middel van het pad op te geven

        try {
            afbeelding = ImageIO.read(bestand);                          //het bestand lezen dat in het opegegeven pad wordt teruggevonden
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Afbeelding kon niet gelezen wordn!", "Fout!", JOptionPane.ERROR_MESSAGE);
        }
        return afbeelding;
    }

    /*
     *Set methode om een afbeelding op te slaan
     *@param afbeelding is de afbeelding die opgeslagen moet worden
     *@param bestand    het bestand waarin de afbeelding opgeslagen moet wordeno
     *@param ext        de extensie van het bestand dat opgeslagen moet worden
     *@terugkeerwaarde: true als de afbeelding succesvol opgelsgane is, false als de afbeelding niet opgeslagen kon worden
     */
    private boolean setAfbeelding(BufferedImage afbeelding, File bestand, String ext) {
        try {
            bestand.delete();                                       //delete resources die het bestand gebruikt
            ImageIO.write(afbeelding, ext, bestand);                //schrijf de afbeelding naar een bestand
                                                                    //met als naam(bestand), de afbeelding afbeelding, met als extensie(extensie)
            return true;                                            //als het succesvol verloopt, dan terugkeerwaarde: true
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Bestand kon niet worden opgeslagen!", "Fout!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /*
     *Behandelt de manier waarop de tekst in de afbeelding vercijferd moet worden
     *@param    afbeelding  De afbeelding waarin de tekst vercijferd moet worden
     *@param    tekst	De tekst die in de afbeelding vercijferd moet worden
     *@terugkeerwaarde:     De afbeelding met een tekst erin vercijferdit
     */
    //http://www.dreamincode.net/forums/topic/27950-steganography/
    private BufferedImage VoegToeTekst(BufferedImage afbeelding, String tekst) {
        WritableRaster raster = afbeelding.getRaster();
        DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();

        //Conversie van alle items naar een array van bytes
        byte bits[] = null;
        byte afb[] = buffer.getData();                                          //Conversie van de afbeelding naar een array van bytes
        byte msg[] = tekst.getBytes();                                          //Conversie van de tekst naar een array van bytes
        int lengte = msg.length;                                                //Conversie van de lengte naar een array van bytes
        //originele ints worden omgezet naar bytes
        //we gebruiken maar 4 bytes, dus de lengte wordt hieronder verdeeld
        byte byte3 = (byte) ((lengte & 0xFF000000) >>> 24); //van 32 tot en met 24
        byte byte2 = (byte) ((lengte & 0x00FF0000) >>> 16); //van 16 tot en met 23
        byte byte1 = (byte) ((lengte & 0x0000FF00) >>> 8); //van 8 tot en met 15
        byte byte0 = (byte) ((lengte & 0x000000FF));//van 7 tot en met 0
        //{0,0,0,byte0} is hetzelfde, omdat alle shifts grotes zijn dan 8 dus gelijk aan 0

        bits = new byte[]{byte3, byte2, byte1, byte0};
        byte len[] = bits;//de int is in 4 delen gedeeld in een array ervan en deze wordt teruggegeven

        try {
            vercijferTekst(afb, len, 0);                           //0 als eerste positie
            vercijferTekst(afb, msg, 32);                           //4 bytes als grootte nemen van de lengte, want 4 bytes * 8 bits = 32 bits 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Target Dit bestand kan geen vercijferde tekst bevatten!", "Fout!", JOptionPane.ERROR_MESSAGE);
        }
        return afbeelding;                                              //terugkeerwaarde: de afbeelding
    }

    /*
     *een gebruiksvriendelijk versie van de afbeelding maken die aangepast kan worden en waardat bytes van verkregen kan worden
     *@param afbeelding de afbeelding die geheugen moet krijgen en compressie moeilijkheden verwijderen
     *@return de gebruikersruimteversie van de afbeelding, dus geheugen
     */
    private BufferedImage gebruikersGrootte(BufferedImage afbeelding) {
        //een nieuwe afbeeldingNieuw aanmaken met de attributen van een afbeelding
        BufferedImage afbeeldingNieuw = new BufferedImage(afbeelding.getWidth(), afbeelding.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D grafisch = afbeeldingNieuw.createGraphics();
        grafisch.drawRenderedImage(afbeelding, null);//??
        grafisch.dispose(); //laat het geheugen dat gebruikt werd voor deze afbeelding vrij
        return afbeeldingNieuw;//terugkeerwaarde: de nieuwe afbeelding
    }

    /*
     *Vercijfert een array van bytes in een andere array van bytes van in de vorm van een supplied offset
     *@param afbeeld de data die de afbee
     *@param toevoeg array van data die in de afbeelig moet vercijferd worden
     *@param j	 de plaats vanaf waar de bytes worden opgeteld
     *@terugkeerwaarde: geeft een array van van de samengevoegde bytes terug waarin de tekst vercijferd in zit
     */
     //http://www.dreamincode.net/forums/topic/27950-steganography/
    private byte[] vercijferTekst(byte[] afbeeld, byte[] toevoeg, int j) {
        if (toevoeg.length + j > afbeeld.length) {                              //checken of j en de data wel in de afbeelding passen
            throw new IllegalArgumentException("Bestand is niet groot genoeg om deze tekst er in te vercijferen!");
        }
        for (int i = 0; i < toevoeg.length; ++i) { 
            //doorheen elke toegevoegde byte lopen
            int add = toevoeg[i];           
            //gaat de HUIDIGE byte aan add toevoegen   
            
            for (int bit = 7; bit >= 0; --bit, ++j) {
                //gaat in de add byte alle 8 bits af
                int b = (add >>> bit) & 1;
                //add wordt naar rechts geshift met het aantal bits
                //Dus eerst geshift met 7, dan 6, dan 5, ..., de uitkomst hiervan wordt geAND met 1
                //zodat we de laatste bit telkens terug krijgen als deze 1 is
                
                afbeeld[j] = (byte) ((afbeeld[j] & 0xFE) | b); 
                //0xFE is hexadecimaal, in binair is het 11111110
                //de 7 eerste bits zullen dus hetzelfde zijn, maar de laatste bit zal sowieso gecleared worden (het is een and met 0)
                //de laatste bit (de least significant bit dus) zal hierna gezet worden op de waarde van b (d.m.v. de OR).
                
                //PS: de reden dat we de LENGTE in 4 aparte bytes hebben gezet, is dat we zo weten hoe veel bytes we moeten lezen achter de lengte om de boodschap te krijgen.

            }
        }
        return afbeeld;
    }
}
