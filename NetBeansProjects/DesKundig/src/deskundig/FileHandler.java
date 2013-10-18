
package deskundig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    
    private File file;
    
    /**
     * Default constructor van de file handler klasse.
     * 
     * Het pad van de file wordt meegegeven
     */
    public FileHandler(String path) {
        try {
            file = new File(path);
        } 
        catch (Exception ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Deze functie zet een bestand om naar bits
     */
    public void ConverteerNaarBitArray() {
        String bitString = "";
        
        try {
            FileInputStream fis = new FileInputStream(file);
            
            byte fileContent[] = new byte[(int)file.length()];
            fis.read(fileContent);
            
            for (int i = 0; i < fileContent.length; i++) {
                bitString += Integer.toBinaryString(fileContent[i]);
            }
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Rest ophalen
        int rest = bitString.length() % 64;
        
        // Aantal toegevoegde bits
        int aantalToegevoegd = 64 - rest;
        
        // Blok opvullen
        for (int i = 0; i < aantalToegevoegd; i++) {
            bitString += "0";
        }
        
        // Nieuw blok aanmaken en aantal toegevoegde opvul bits wegschrijven
        String tmp = Integer.toBinaryString(aantalToegevoegd);
        
        // Lengte voor tmp reserveren
        for (int i = 0; i < 64 - tmp.length(); i++) {
            bitString += "0";
        }
        
        bitString += tmp;
     }
}
