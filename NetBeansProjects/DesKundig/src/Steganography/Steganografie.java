package Steganography;
/*
 *import list
 */
import java.io.File;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/*
 *Klasse: Steganografie
 */
public class Steganografie
{
	
	/*
	 *een standaar lege constructor
	 */
	public Steganografie()
	{
	}
	
	/*
         *Een afbeelding vercijferen aan de hand van tekst, het uitvoerbestand zal van het type .png zijn
	 *@param pad      Het pad (map) waar die de afbeelding bevat waarin de tekst vercijferd moet worden.
	 *@param org      De naam van de afbeelding die aangepast wordt
	 *@param ext1     De extensie van de afbeelding (jpg of png)
	 *@param stegan	  De naal van het uitvoerbestand
	 *@param bericht  De teskt die verscholen zit in de afbeelding
         *@param type	  representeert de basis vercijfering of de ingewikkelde vercijfering
	 */
	public boolean vercijferen(String pad, String org, String ext1, String stegan, String bericht)
	{
		String	bestandsNaam                = AfbeeldingsPad(pad,org,ext1);                     //naam van de afbeelding opvragen en ind e variabele bestandsNaam steken
		BufferedImage AfbeeldingOrigineel   = getAfbeelding(bestandsNaam);                      //De originele afbeelding wordt opgehaald uit de map en in de variabele afbeeldingOrigineel gestoken
		
		//gebruikerGrootte
		BufferedImage Afb = gebruikersGrootte(AfbeeldingOrigineel);
		Afb = VoegToeTekst(Afb,bericht);                                                        //Tekst wordt in de afbeelding gestoken via de zelf gedefinieerde functie voegToeTekst()
		
		return(setAfbeelding(Afb,new File(AfbeeldingsPad(pad,stegan,"png")),"png"));            //terugkeerwaarde: ???
	}
	
	/*
         *Neemt aan dat de afbeelding van het juiste type is en haalt the verborgen tekst uit de afbeeling
	 *@param pad  Het pad waarin de afbeelding staat die ontcijferd moet worden
	 *@param naam De naam ven de afbeelding die ontcijferd moet worden
	 *@param type nummer die aangeeft of er een standaard ontcijfering moet gebeuren of een ingewikkeld ontcijfering
	 */
	public String ontcijferen(String pad, String naam)
	{
		byte[] ontc;                                                    //array van bytes maken
		try
		{
			//GebruikersGrootte moet bepaald worden, hier wel nodig
			BufferedImage afbeelding  = gebruikersGrootte(getAfbeelding(AfbeeldingsPad(pad,naam,"png")));
			ontc = ontcijferTekst(rekenenBytes(afbeelding));        //de tekst uit de afbeelding ontcijferen en in de variabele ont steken om deze later te gebruiken
			return(new String(ontc));                               //terugkeerwaarde: de ontcijferde tekst
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,                     //er wordt een popu getoond als de afbeelding niet ontcijferd kan worden doordat er geen vercijferde tekst in zit
				"Er is geen verborgen tekst in deze afbeelding!","Fout!",
				JOptionPane.ERROR_MESSAGE); 
			return "";                                              //een lege string teruggeven, want er kan niets ontcijferd worden
		}
	}
	
	/*
	 *Terugkeerwaarde: het volledige pad van de afbeelding
	 *@param pad   het pad naar het bestand
	 *@param naam  de naam van het bestand
	 *@param ext   de extensie van het bestand
	 *@terugkeerwaarde: een string die het volledige pas bevat naar het bestand
	 */
	private String AfbeeldingsPad(String pad, String naam, String ext)
	{
		return pad + "/" + naam + "." + ext;
	}
	
	/*
	 *Get methode om de afbeelding op te halen
	 *@param p Het complete padnaam van de afbeelding.
	 *@terugkeerwaarde: een afbeelding van het type .jpg of van het type .png
	 */
	private BufferedImage getAfbeelding(String p)
	{
		BufferedImage 	afbeelding	= null;                                 //een nieuwe instantie van een BufferedImage aanmaken en deze voolopig leeg laten.
		File 		bestand = new File(p);                          //een nieuwe instantie van een bestand (file) aanmaken door middel van het pad op te geven
		
		try
		{
			afbeelding = ImageIO.read(bestand);                          //het bestand lezen dat in het opegegeven pad wordt teruggevonden
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, 
				"Afbeelding kon niet gelezen wordn!","Fout!",JOptionPane.ERROR_MESSAGE);
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
	private boolean setAfbeelding(BufferedImage afbeelding, File bestand, String ext)
	{
		try
		{
			bestand.delete();                                       //delete resources die het bestand gebruikt
			ImageIO.write(afbeelding,ext,bestand);                  //schrijf de afbeelding naar een bestand
                                                                                //met als naam(bestand), de afbeelding afbeelding, met als extensie(extensie)
			return true;                                            //als het succesvol verloopt, dan terugkeerwaarde: true
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"Bestand kon niet worden opgeslagen!","Fout!",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/*
	 *Behandelt de manier waarop de tekst in de afbeelding vercijferd moet worden
	 *@param    afbeelding  De afbeelding waarin de tekst vercijferd moet worden
	 *@param    tekst	De tekst die in de afbeelding vercijferd moet worden
	 *@terugkeerwaarde:     De afbeelding met een tekst erin vercijferdit
	 */
	private BufferedImage VoegToeTekst(BufferedImage afbeelding, String tekst)
	{
		//Conversie van alle items naar een array van bytes
		byte afb[]  = rekenenBytes(afbeelding);                         //Conversie van de afbeelding naar een array van bytes
		byte msg[]  = tekst.getBytes();                                 //Conversie van de tekst naar een array van bytes
		byte len[]  = bitConversie(msg.length);                         //Conversie van de lengte naar een array van bytes
		try
		{
			vercijferTekst(afb, len,  0);                           //0 als eerste positie
			vercijferTekst(afb, msg, 32);                           //4 bytes als grootte nemen van de lengte, want 4 bytes * 8 bits = 32 bits 
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
"Target Dit bestand kan geen vercijferde tekst bevatten!", "Fout!",JOptionPane.ERROR_MESSAGE);
		}
		return afbeelding;                                              //terugkeerwaarde: de afbeelding
	}
	
	/*
	 *Creates a user space version of a Buffered Image, for editing and saving bytes
	 *@param image The image to put into user space, removes compression interferences
	 *@return The user space version of the supplied image
	 */
	private BufferedImage gebruikersGrootte(BufferedImage image)
	{
		//create new_img with the attributes of image
		BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D	graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose(); //release all allocated memory for this image
		return new_img;
	}
	
	/*
	 *Gets the byte array of an image
	 *@param image The image to get byte data from
	 *@return Returns the byte array of the image supplied
	 *@see Raster
	 *@see WritableRaster
	 *@see DataBufferByte
	 */
	private byte[] rekenenBytes(BufferedImage image)
	{
		WritableRaster raster   = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
	
	/*
	 *Gernerates proper byte format of an integer
	 *@param i The integer to convert
	 *@return Returns a byte[4] array converting the supplied integer into bytes
	 */
	private byte[] bitConversie(int i)
	{
		//originally integers (ints) cast into bytes
		//byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
		//byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
		//byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
		//byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);
		
		//only using 4 bytes
		byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
		byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
		byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
		byte byte0 = (byte)((i & 0x000000FF)	   );
		//{0,0,0,byte0} is equivalent, since all shifts >=8 will be 0
		return(new byte[]{byte3,byte2,byte1,byte0});
	}
	
	/*
	 *Encode an array of bytes into another array of bytes at a supplied offset
	 *@param image	 Array of data representing an image
	 *@param addition Array of data to add to the supplied image data array
	 *@param offset	  The offset into the image array to add the addition data
	 *@return Returns data Array of merged image and addition data
	 */
	private byte[] vercijferTekst(byte[] Afbeeld, byte[] toevoeg, int j)
	{
                //checken of j en de data wel in de afbeelding passen
		if(toevoeg.length + j > Afbeeld.length)
		{
			throw new IllegalArgumentException("Bestand is niet groot genoeg om deze tekst er in te vercijferen!");
		}
		//doorheen elke toegvoegde byte lopen
		for(int i=0; i<toevoeg.length; ++i)
		{
			//doorheen elke 8 bits van de byte lopen
			int add = toevoeg[i];
                        //zorgen dat de nieuwe j de waarde bevat doorheen de 2 lussen
			for(int bit=7; bit>=0; --bit, ++j)
			{
				//assign an integer to b, shifted by bit spaces AND 1
				//a single bit of the current byte
				int b = (add >>> bit) & 1;
				//assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
				//changes the last bit of the byte in the image to be the bit of addition
				Afbeeld[j] = (byte)((Afbeeld[j] & 0xFE) | b );
			}
		}
		return Afbeeld;
	}
	
	/*
         * verborgen tekst uit de afbeeling halen
	 *@param afbeeld array van data die een afbeelding voorstelt
	 *@return Array van data die de verborgen tekst bevat
	 */
	private byte[] ontcijferTekst(byte[] afbeeld)
	{
		int lengte = 0;
		int j  = 32;
                //doorheen 32 bytes gaan om de lengte van de tekst te bepalen
		for(int i=0; i<32; ++i) 
                {
			lengte = (lengte << 1) | (afbeeld[i] & 1);
		}
		//een nieuwe arra van het type byte maken van de lengte die in variabele lengte zit
		byte[] resultaat = new byte[lengte];
		
                //doorheen elke byte van tekst gaan
		for(int b=0; b<resultaat.length; ++b )
		{
                        //doorheen elke bit gaat in een byte tekst
			for(int i=0; i<8; ++i, ++j)
			{
				//de bit eraan toevoegen: [(nieuwe byte waarde) << 1] OF [(tekst byte) en 1]
				resultaat[b] = (byte)((resultaat[b] << 1) | (afbeeld[j] & 1));
			}
		}
                //teruhkeerwaarde: resultaat
		return resultaat;
	}
}

