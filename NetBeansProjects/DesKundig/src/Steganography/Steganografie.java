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
		String	bestandsNaam                = pad + "/" + org + "." + ext1;                     //naam van de afbeelding opvragen en ind e variabele bestandsNaam steken
		BufferedImage AfbeeldingOrigineel   = getAfbeelding(bestandsNaam);                      //De originele afbeelding wordt opgehaald uit de map en in de variabele afbeeldingOrigineel gestoken
		
		//gebruikerGrootte
		BufferedImage Afb = gebruikersGrootte(AfbeeldingOrigineel);
		Afb = VoegToeTekst(Afb,bericht);                                                        //Tekst wordt in de afbeelding gestoken via de zelf gedefinieerde functie voegToeTekst()
		
		return(setAfbeelding(Afb,new File(pad + "/" + stegan + "." + "png"),"png"));            //terugkeerwaarde: ???
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
			BufferedImage afbeelding  = gebruikersGrootte(getAfbeelding(pad + "/" + naam + "." + "png"));
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
	 *een gebruiksvriendelijk versie van de afbeelding maken die aangepast kan worden en waardat bytes van verkregen kan worden
	 *@param afbeelding de afbeelding die geheugen moet krijgen en compressie moeilijkheden verwijderen
	 *@return de gebruikersruimteversie van de afbeelding, dus geheugen
	 */
	private BufferedImage gebruikersGrootte(BufferedImage afbeelding)
	{
		//een nieuwe afbeeldingNieuw aanmaken met de attributen van een afbeelding
		BufferedImage afbeeldingNieuw  = new BufferedImage(afbeelding.getWidth(), afbeelding.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D	grafisch = afbeeldingNieuw.createGraphics();
		grafisch.drawRenderedImage(afbeelding, null);//??
		grafisch.dispose(); //laat het geheugen dat gebruikt werd voor deze afbeelding vrij
		return afbeeldingNieuw;//terugkeerwaarde: de nieuwe afbeelding
	}
	
	/*
	 *De array van bytes verkrijgen van een afbeelding, dus een afbeelding omzetten naar een array van bytes
	 *@param afbeelding de afbeelding waar we de bytes van moete hebben
	 *@terugkeerwaarde: de array van bytes van de opgegeven afbeelding
         *@see Raster
	 *@see WritableRaster
	 *@see DataBufferByte
	 */
	private byte[] rekenenBytes(BufferedImage afbeelding)
	{
		WritableRaster raster   = afbeelding.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
	
	/*
	 *een int omzetten naar een array van 4 bytes
	 *@param i de int die geconverteerd moet worden
	 *@terugkeerwaarde: een array van 4 bytes die een int converteren de voorziene int naar bytes
	 */
	private byte[] bitConversie(int i)
	{
		//originele ints worden omgezet naar bytes
		//byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
		//byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
		//byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
		//byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);
		
		//we gebruiken maar 4 bytes
		byte byte3 = (byte)((i & 0xFF000000) >>> 24); //van 32 tot en met 24
		byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //van 16 tot en met 23
		byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //van 8 tot en met 15
		byte byte0 = (byte)((i & 0x000000FF)	   );//van 7 tot en met 0
		//{0,0,0,byte0} is hetzelfde, omdat alle shifts grotes zijn dan 8 dus gelijk aan 0
		return(new byte[]{byte3,byte2,byte1,byte0});//de int is in 4 delen gedeeld in een array ervan en deze wordt teruggegeven
	}
	
	/*
	 *Vercijfert een array van bytes in een andere array van bytes van in de vorm van een supplied offset
	 *@param afbeeld de data die de afbee
	 *@param toevoeg array van data die in de afbeelig moet vercijferd worden
	 *@param j	 de plaats vanaf waar de bytes worden opgeteld
         *@terugkeerwaarde: geeft een array van van de samengevoegde bytes terug waarin de tekst vercijferd in zit
         */
	private byte[] vercijferTekst(byte[] afbeeld, byte[] toevoeg, int j)
	{
                //checken of j en de data wel in de afbeelding passen
		if(toevoeg.length + j > afbeeld.length)
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
				afbeeld[j] = (byte)((afbeeld[j] & 0xFE) | b );//!!!
			}
		}
		return afbeeld;
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
                        //bit shift opperator
			lengte = (lengte << 1) | (afbeeld[i] & 1);
		}
		//een nieuwe array van het type byte maken van de lengte die in variabele lengte zit
		byte[] resultaat = new byte[lengte];
		
                //doorheen elke byte van tekst gaan
		for(int b=0; b<resultaat.length; ++b )
		{
                        //elke bit in een byte doorlopen
			for(int i=0; i<8; ++i, ++j)
			{
				//de bit eraan toevoegen: [(nieuwe byte waarde) << 1] OF [(tekst byte) en 1] als dan
                                //bit shift opperator
				resultaat[b] = (byte)((resultaat[b] << 1) | (afbeeld[j] & 1));
			}
		}
                //terugkeerwaarde: resultaat
		return resultaat;
	}
}

