package sneseffect;

public class Camera
{
    public float xPos, yPos, zPos;
    public int xzRot, yRot;

    public Camera()
    {
        xPos = zPos = 0;
        yPos = 1;
        xzRot = yRot = 0;
    }
}
