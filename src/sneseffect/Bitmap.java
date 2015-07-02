package sneseffect;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bitmap
{
    protected int width, height;
    protected int[] pixels;

    public Bitmap(int width, int height, int[] pixels)
    {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }
    
    public void clear(int color)
    {
        for (int i = 0; i < pixels.length; ++i) pixels[i] = color;
    }
    
    public void drawBitmap(Bitmap bitmap, Camera camera)
    {
        int centerWidth = width >> 1;
        int centerHeight = height >> 1;
        
        for (int y = 0; y < height; ++y)
        {
            int vy = y - centerHeight - camera.xzRot;
            int z = (int)((y + height - camera.xzRot) * camera.yPos);
            
            for (int x = 0; x < width; ++x)
            {
                int vx = x - centerWidth;
                
                int rx = (int)(vx * Trigo.COS[camera.yRot] - vy * Trigo.SIN[camera.yRot]);
                int ry = (int)(vx * Trigo.SIN[camera.yRot] + vy * Trigo.COS[camera.yRot]);
                
                int px = (int)(rx / (float)z * width) + (int)camera.xPos;
                int py = (int)(ry / (float)z * height) + (int)camera.zPos;
                
                if (px < 0 || px >= bitmap.width) continue;
                if (py < 0 || py >= bitmap.height) continue;
                
                pixels[y * width + x] = bitmap.pixels[py * bitmap.width + px];
            }
        }
    }
    
    public static Bitmap fromFile(String filename)
    {
        BufferedImage img;
        
        try { img = ImageIO.read(ClassLoader.getSystemResourceAsStream(filename)); }
        catch (IOException ex) { return null; }
        
        int w = img.getWidth();
        int h = img.getHeight();
        int[] pixels = new int[w * h];
        img.getRGB(0, 0, w, h, pixels, 0, w);
        
        return new Bitmap(w, h, pixels);
    }
}
