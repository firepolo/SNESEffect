package sneseffect;

public enum Key
{
    LEFT(0x25),
    UP(0x26),
    RIGHT(0x27),
    DOWN(0x28),
    A(0x41),
    W(0x57),
    D(0x44),
    S(0x53),
    E(0x45),
    Q(0x51),
    SPACE(0x20),
    CTRL(0x11);
    
    public final Integer code;
    
    private Key(Integer code)
    {
        this.code = code;
    }
}
