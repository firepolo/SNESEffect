package sneseffect;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import javax.swing.JFrame;

public class SNESEffect extends JFrame implements Runnable, KeyListener
{    
    private final int SNES_WIDTH = 256;
    private final int SNES_HEIGHT = 240;
    private final int SNES_SCALE = 2;
    
    private final int FRAME_TIME = 1000 / 60;
    
    private final HashMap<Integer, Boolean> keys;
    
    private boolean started;
    
    private BufferedImage backBuffer;
    private Bitmap render;
    
    private Bitmap texture;
    private Camera camera;

    public SNESEffect()
    {
        super("SNES cool effect");
        
        keys = new HashMap<>();
        Key[] virtualKeys = Key.values();
        for (int i = 0; i < virtualKeys.length; ++i) keys.put(virtualKeys[i].code, false);
        
        backBuffer = new BufferedImage(SNES_WIDTH, SNES_HEIGHT, BufferedImage.TYPE_INT_RGB);
        render = new Bitmap(SNES_WIDTH, SNES_HEIGHT, ((DataBufferInt)backBuffer.getRaster().getDataBuffer()).getData());
        
        //texture = Bitmap.fromFile("textures/rainbowroad.png");
        texture = Bitmap.fromFile("textures/darksymbol.png");
        
        camera = new Camera();
        
        initWindow();
    }
    
    private void initWindow()
    {
        addKeyListener(this);
        setSize(SNES_WIDTH * SNES_SCALE, SNES_HEIGHT * SNES_SCALE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    private void update()
    {
        if (keys.get(Key.LEFT.code)) camera.yRot = (camera.yRot - 5) & Trigo.ANGLE_PRECISION_CLAMP;
        else if (keys.get(Key.RIGHT.code)) camera.yRot = (camera.yRot + 5) & Trigo.ANGLE_PRECISION_CLAMP;

        if (keys.get(Key.UP.code) || keys.get(Key.W.code))
        {
            camera.xPos += 4 * Trigo.SIN[camera.yRot];
            camera.zPos -= 4 * Trigo.COS[camera.yRot];
        }
        else if (keys.get(Key.DOWN.code) || keys.get(Key.S.code))
        {
            camera.xPos -= 4 * Trigo.SIN[camera.yRot];
            camera.zPos += 4 * Trigo.COS[camera.yRot];
        }
        
        if (keys.get(Key.A.code))
        {
            camera.xPos -= 4 * Trigo.COS[camera.yRot];
            camera.zPos -= 4 * Trigo.SIN[camera.yRot];
        }
        else if (keys.get(Key.D.code))
        {
            camera.xPos += 4 * Trigo.COS[camera.yRot];
            camera.zPos += 4 * Trigo.SIN[camera.yRot];
        }
        
        if (keys.get(Key.SPACE.code)) camera.yPos -= 0.02f;
        else if (keys.get(Key.CTRL.code)) camera.yPos += 0.02f;
        
        if (keys.get(Key.E.code)) camera.xzRot += 2;
        else if (keys.get(Key.Q.code)) camera.xzRot -= 2;
    }
    
    private void render()
    {
        render.clear(0);
        render.drawBitmap(texture, camera);
    }
    
    private void swapBuffer()
    {
        BufferStrategy bs = getBufferStrategy();
        
        if (bs == null)
        {
            createBufferStrategy(2);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        Insets border = getInsets();
        g.drawImage(backBuffer, border.left, border.top, getContentPane().getWidth(), getContentPane().getHeight(), null);
        g.setColor(Color.WHITE);
        g.drawString("Position (" + camera.xPos + "," + camera.yPos + "," + camera.zPos + ")", 10, border.top + 20);
        g.drawString("Direction (" + Trigo.SIN[camera.yRot] + "," + -Trigo.COS[camera.yRot] + ")", 10, border.top + 40);
        //g.drawLine((int)(border.left + camera.xPos), (int)(border.top + camera.zPos), border.left + (int)(camera.xPos + camera.xDir * 10), border.top + (int)(camera.zPos + camera.zDir * 10));
        g.dispose();
        bs.show();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        keys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        keys.put(e.getKeyCode(), false);
    }
    
    @Override
    public void run()
    {
        long lastTime = System.currentTimeMillis();
        
        while (started)
        {
            long nowTime = System.currentTimeMillis();
            
            if (nowTime - lastTime > FRAME_TIME)
            {
                update();
                render();
                swapBuffer();
                lastTime = nowTime;
            }
            
            try { Thread.sleep(1); }
            catch (InterruptedException ex) { stop(); }
        }
    }
    
    public void start()
    {
        if (!started)
        {
            started = true;
            new Thread(this).start();
        }
    }
    
    public void stop()
    {
        if (started) started = false;
    }
    
    public static void main(String[] args)
    {
        new SNESEffect().start();
    }
}
