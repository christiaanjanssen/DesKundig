package Steganography;
/*
 *Import Lijst
 */

import java.io.*;

/*
 *Klasse: Afbeeldingenfilter
 */
public class AfbeelingenFilter extends javax.swing.filechooser.FileFilter {
    /*
     *Bekijkt de extenstie en kijkt if deze supported zijn 
     *@param ext extensie van een bestand
     *@return terugkeerwaarde: true als de extensies van het type .jpg of .png zijn en false als de extensie van een ander type blijkt te zijn
     */
    protected boolean AfbeeldingToegelaten(String ext) {
        return (ext.equals("jpg") || ext.equals("png"));              //geeft true terug als de extensie eindigt op jpg of als de extensie eindigt op png, anders false      
    }

    /*
     *Bekijkt of het bestand of directory supported is samen met de extensie
     *@param f Geeft een bestand mee dat gecontroleerd moet worden
     *@return terugkeerwaarde: geeft true terug als het bestand voldoet aan de eisen
     */
    @Override
    public boolean accept(File f) //overrided methode
    {
        if (f.isDirectory()) //als het bestand een directory is
        {
            return true;                                        //dan terugkeerwaarde: true
        }

        String extensie = getExtensie(f);                              //anders variabelen extensie wordt gevuld met de terugkeerwaarde van de methode getExtensie die de extensie gaat opvragen van het bestand
        if (extensie.equals("jpg") || extensie.equals("png")) //als extensie gelijk is aan jpg of als extensie gelijk is aan png
        {
            return true;                                        //dan terugkeerwaarde true
        }
        return false;                                               //anders terugkeerwaarde: false
    }

    /*
     *Supplies bestandstype omschrijving
     *@return terugkeerwaarde: string die een omschrijving bevat van het bestand
     */
    @Override
    public String getDescription() //overrided methode
    {
        return "Afbeeldingen die toegelaten zijn om te gebruiken";
    }

    /*
     *Gaat zoeken welke extensie de afbeelding heeft
     *@param b het bestand waarvan de extensie bepaalde van moet worden
     *@return geeft als terugkeerwaarde de extensie terug
     */
    public static String getExtensie(File b) {
        String s = b.getName();                     //verkrijg de naam van het bestand
        int i = s.lastIndexOf('.');                 //verkrijg de positie van de '.' in de bestandsnaam
        if (i > 0 && i < s.length() - 1)            //als i groter is als 1 en i is kleiner als de lengte van de bestandsnaam -1 
        {
            return s.substring(i + 1).toLowerCase();//dan terugkeerwaarde: de extensie van het bestand
        }
        return "";                                  //anders terugkeerwaarde: een lege string
    }
}