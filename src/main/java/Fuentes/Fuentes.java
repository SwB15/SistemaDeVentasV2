
package Fuentes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author SwichBlade15
 */
public class Fuentes {
    
    private Font font = null;
    public String decker = "Decker.tff";
    
    public Font fuente(String fontName, int estilo, float tamano){
        try{
            InputStream is = getClass().getResourceAsStream(fontName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(FontFormatException | IOException e){
            font = new Font("Tahoma", Font.BOLD, 18);
        }
        Font tfont = font.deriveFont(estilo, tamano);
        return tfont;
    }
}
