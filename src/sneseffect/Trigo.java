package sneseffect;

public class Trigo
{
    public static final int ANGLE_PRECISION = 1024;
    public static final int ANGLE_PRECISION_CLAMP = ANGLE_PRECISION - 1;
    
    public static final float[] COS = new float[ANGLE_PRECISION];
    public static final float[] SIN = new float[ANGLE_PRECISION];
    
    static
    {
        for (int i = 0; i < ANGLE_PRECISION; ++i)
        {
            float angle = (float)(Math.PI / (ANGLE_PRECISION >> 1) * i);
            COS[i] = (float)Math.cos(angle);
            SIN[i] = (float)Math.sin(angle);
        }
    }
}
