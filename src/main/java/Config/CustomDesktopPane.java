/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;

/**
 *
 * @author SwichBlade15
 */
public class CustomDesktopPane extends JDesktopPane {
    private BufferedImage image;
    private Image scaledImage;

    public CustomDesktopPane(String imagePath) {
        try {
            image = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        scaledImage = getScaledImage(image);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scaledImage != null) {
            g.drawImage(scaledImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private Image getScaledImage(Image srcImg) {
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0) {
            // In case the component has not been laid out yet
            width = Toolkit.getDefaultToolkit().getScreenSize().width;
            height = Toolkit.getDefaultToolkit().getScreenSize().height;
        }

        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = resizedImg.createGraphics();
        g.drawImage(srcImg, 0, 0, width, height, null);
        g.dispose();
        return resizedImg;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        scaledImage = getScaledImage(image);
        repaint();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        scaledImage = getScaledImage(image);
        repaint();
    }
}
