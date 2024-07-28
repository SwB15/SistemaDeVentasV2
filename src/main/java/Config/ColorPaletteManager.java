
package Config;

import java.awt.Color;

/**
 *
 * @author SwichBlade15
 */
public class ColorPaletteManager {
    // Fondo principal
    public static final Color BACKGROUND_MAIN = new Color(0, 0, 0);

    // Paneles y bordes secundarios
    public static final Color PANELS_AND_BORDERS_SECONDARY = new Color(40, 40, 40);

    // Botones secundarios
    public static final Color SECONDARY_BUTTONS = new Color(100, 100, 100);

    // Texto principal y bordes destacados
    public static final Color TEXT_MAIN_AND_HIGHLIGHTED_BORDERS = new Color(200, 200, 200);

    // Texto y elementos importantes
    public static final Color TEXT_AND_IMPORTANT_ELEMENTS = new Color(255, 255, 255);
    
    public static Color BACKGROUND_MAIN() {
        return BACKGROUND_MAIN;
    }

    public static Color PANELS_AND_BORDERS_SECONDARY() {
        return PANELS_AND_BORDERS_SECONDARY;
    }

    public static Color SECONDARY_BUTTONS() {
        return SECONDARY_BUTTONS;
    }

    public static Color TEXT_MAIN_AND_HIGHLIGHTED_BORDERS() {
        return TEXT_MAIN_AND_HIGHLIGHTED_BORDERS;
    }

    public static Color TEXT_AND_IMPORTANT_ELEMENTS() {
        return TEXT_AND_IMPORTANT_ELEMENTS;
    }
}
