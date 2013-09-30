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
	 *Encrypt an image with text, the output file will be of type .png
	 *@param pad        The path (folder) containing the image to modify
	 *@param org        The name of the image to modify
	 *@param ext1        The extension type of the image to modify (jpg, png)
	 *@param stegan	  The output name of the file
	 *@param bericht  The text to hide in the image
	 *@param type	  integer representing either basic or advanced encoding
	 */
	public boolean vercijferen(String pad, String org, String ext1, String stegan, String bericht)
	{
		String	bestandsNaam                = AfbeeldingsPad(pad,org,ext1); 
		BufferedImage AfbeeldingOrigineel   = getAfbeelding(bestandsNaam);
		
		//user space is not necessary for Encrypting
		BufferedImage Afb = user_space(AfbeeldingOrigineel);
		Afb = VoegToeTekst(Afb,bericht);
		
		return(setAfbeelding(Afb,new File(AfbeeldingsPad(pad,stegan,"png")),"png"));
	}
	
	/*
         *Neemt aan dat de afbeelding van het juiste type is en haalt the verborgen tekst uit de afbeeling
	 *@param pad   The path (folder) containing the image to extract the message from
	 *@param naam The name of the image to extract the message from
	 *@param type integer representing either basic or advanced encoding
	 */
	public String ontcijferen(String path, String name)
	{
		byte[] ontc;                                                    //array van bytes maken
		try
		{
			//user space is necessary for decrypting
			BufferedImage image  = user_space(getAfbeelding(AfbeeldingsPad(path,name,"png")));
			ontc = ontcijferTekst(rekenenBytes(image));
			return(new String(ontc));
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"Er is geen verborgen tekst in deze afbeelding!","Fout!",
				JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}
	
	/*
	 *Returns the complete path of a file, in the form: path\name.ext
	 *@param path   The path (folder) of the file
	 *@param name The name of the file
	 *@param ext	  The extension of the file
	 *@return A String representing the complete path of a file
	 */
	private String AfbeeldingsPad(String path, String name, String ext)
	{
		return path + "/" + name + "." + ext;
	}
	
	/*
	 *Get method to return an image file
	 *@param f The complete path name of the image.
	 *@return A BufferedImage of the supplied file path
	 *@see	Steganography.image_path
	 */
	private BufferedImage getAfbeelding(String f)
	{
		BufferedImage 	image	= null;
		File 		file 	= new File(f);
		
		try
		{
			image = ImageIO.read(file);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, 
				"Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	/*
	 *Set method to save an image file
	 *@param image The image file to save
	 *@param file	  File  to save the image to
	 *@param ext	  The extension and thus format of the file to be saved
	 *@return Returns true if the save is succesful
	 */
	private boolean setAfbeelding(BufferedImage image, File file, String ext)
	{
		try
		{
			file.delete(); //delete resources used by the File
			ImageIO.write(image,ext,file);
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"File could not be saved!","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	/*
	 *Handles the addition of text into an image
	 *@param image The image to add hidden text to
	 *@param text	 The text to hide in the image
	 *@return Returns the image with the text embedded in it
	 */
	private BufferedImage VoegToeTekst(BufferedImage image, String text)
	{
		//convert all items to byte arrays: image, message, message length
		byte img[]  = rekenenBytes(image);
		byte msg[] = text.getBytes();
		byte len[]   = bitConversie(msg.length);
		try
		{
			vercijferTekst(img, len,  0); //0 first positiong
			vercijferTekst(img, msg, 32); //4 bytes of space for length: 4bytes*8bit = 32 bits
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
"Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	/*
	 *Creates a user space version of a Buffered Image, for editing and saving bytes
	 *@param image The image to put into user space, removes compression interferences
	 *@return The user space version of the supplied image
	 */
	private BufferedImage user_space(BufferedImage image)
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

