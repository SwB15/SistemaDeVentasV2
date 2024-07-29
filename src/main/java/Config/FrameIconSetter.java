package Config;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author SwichBlade15
 */
public class FrameIconSetter {

    private final ImageIcon icon = new ImageIcon(getClass().getResource("/img/icono.png"));

    public FrameIconSetter() {
    
    }

    public void setIcon(JFrame frame) {
        if (frame != null) {
            frame.setIconImage(icon.getImage());
        }
    }

    public void setIcon(JDialog dialog) {
        if (dialog != null) {
            dialog.setIconImage(icon.getImage());
        }
    }

    public void setIcon(JInternalFrame internalFrame) {
        if (internalFrame != null) {
            internalFrame.setFrameIcon(icon);
        }
    }
}
