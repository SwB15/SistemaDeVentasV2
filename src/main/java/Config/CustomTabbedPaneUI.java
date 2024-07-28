package Config;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

/**
 *
 * @author SwichBlade15
 */
public class CustomTabbedPaneUI extends MetalTabbedPaneUI {

    private final Color selectedColor = new Color(242, 242, 242); // Color para la pestaña seleccionada
    private final Color hoverColor = new Color(224, 224, 224); // Color para la pestaña sobre la que está el mouse
    private final Color borderColor = new Color(194, 194, 194); // Color del borde para la pestaña seleccionada
    private int currentHoverIndex = -1;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        JTabbedPane tabPane = (JTabbedPane) c;
        tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    @Override
    protected void installListeners() {
        super.installListeners();
        tabPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int hoverIndex = getTabAtLocation(e.getX(), e.getY());
                if (hoverIndex != currentHoverIndex) {
                    currentHoverIndex = hoverIndex;
                    tabPane.repaint();
                }
            }
        });
        tabPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                currentHoverIndex = -1;
                tabPane.repaint();
            }
        });
    }

    private int getTabAtLocation(int x, int y) {
        for (int i = 0; i < tabPane.getTabCount(); i++) {
            Rectangle rect = getTabBounds(tabPane, i);
            if (rect.contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isSelected) {
            g2.setColor(selectedColor);
            g2.fillRect(x, y, w, h + 1); // Extiende el fondo para cubrir el borde inferior
        } else if (tabIndex == currentHoverIndex) {
            g2.setColor(hoverColor);
            g2.fillRect(x, y, w, h);
        } else {
            g2.setColor(tabPane.getBackground());
            g2.fillRect(x, y, w, h);
        }
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        g.setColor(borderColor);
        if (isSelected) {
            g.drawRect(x, y, w, h);
            g.fillRect(x, y - 2, w, 4);  // Borde superior para la pestaña seleccionada
        } else {
            g.drawLine(x, y + h - 1, x + w, y + h - 1); // Borde inferior para pestañas no seleccionadas
        }
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        g.setColor(borderColor);
        int tabAreaHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
        g.drawLine(tabPane.getBounds().x, tabPane.getBounds().y + tabAreaHeight - 1,
                tabPane.getBounds().x + tabPane.getBounds().width, tabPane.getBounds().y + tabAreaHeight - 1);
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // No pintar el indicador de foco (el borde punteado)
    }
}
