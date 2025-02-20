package src.models;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageGenerator {
    private int r, c;
    private int[][] b;

    public ImageGenerator(int r, int c, int[][] b) {
        this.r = r;
        this.c = c;
        this.b = b;
    }

    // Mapper karakter ke warna A -> 0, B -> 1, dst
    private Color getColor(char l) {
        Color[] clrs = {
                Color.RED,
                Color.GREEN,
                Color.YELLOW,
                Color.BLUE,
                Color.MAGENTA,
                Color.CYAN,
                new Color(255, 105, 180),
                new Color(128, 0, 128),
                new Color(255, 140, 0),
                new Color(0, 128, 128),
                new Color(0, 255, 127),
                new Color(0, 191, 255),
                Color.LIGHT_GRAY,
                Color.DARK_GRAY,
                new Color(255, 165, 0),
                new Color(0, 100, 0),
                new Color(75, 0, 130),
                new Color(199, 21, 133),
                new Color(173, 216, 230),
                new Color(240, 230, 140),
                new Color(32, 178, 170),
                new Color(220, 20, 60),
                new Color(65, 105, 225),
                new Color(255, 20, 147),
                new Color(64, 224, 208),
                new Color(210, 105, 30)
        };
        int idx = l - 'A';
        if (idx < 0 || idx >= clrs.length)
            return Color.BLACK;
        return clrs[idx];
    }

    public void genImg(String file) {
        int cs = 50;
        int w = c * cs;
        int h = r * cs;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, w, h);

        g.setColor(Color.BLACK);
        for (int i = 0; i <= r; i++) {
            g.drawLine(0, i * cs, w, i * cs); // garis horizontal
        }
        for (int j = 0; j <= c; j++) {
            g.drawLine(j * cs, 0, j * cs, h); // garis vertikal
        }

        Font f = new Font("SansSerif", Font.BOLD, 24);
        g.setFont(f);
        FontMetrics m = g.getFontMetrics();

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                String l = "";
                if (b[i][j] > 0) {
                    l = String.valueOf((char) (b[i][j] + 'A' - 1));
                }
                int tw = m.stringWidth(l);
                int th = m.getHeight();
                int x = j * cs + (cs - tw) / 2;
                int y = i * cs + ((cs - th) / 2) + m.getAscent();

                if (!l.equals("")) {
                    g.setColor(getColor(l.charAt(0)));
                    g.drawString(l, x, y);
                }
            }
        }
        g.dispose(); // tutup objek Graphics2D

        try {
            ImageIO.write(img, "png", new File(file));
            System.out.println("Gambar disimpan pada file " + file);
        } catch (IOException e) {
            System.out.println("Error: pembuatan gambar gagal");
        }
    }
}
