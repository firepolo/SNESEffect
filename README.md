# SNESEffect
Small app reproducing the cool SNES perspective effect

Preview:
--------
![alt tag](https://raw.githubusercontent.com/firepolo/SNESEffect/master/preview.png)

Perspective equation:
---------------------
'x = x / z<br />
'y = y / z

More an object is far, more it is small.<br />
More an object is near, more it is big.

Rotation equation:
-------------------
x = x * cos(angle) - y * sin(angle)<br />
y = x * sin(angle) + y * cos(angle)

This equation apply a rotation on Y axe.<br />
This is the same equation that 3D but adapted for the 2D vectors.

Config keys:
-------------
- Forward : W or Up
- Backward : S or Down
- Turn left : Left
- Turn right : Right
- Strafe left : A
- Strafe right : D
- Go up : Space
- Go down : CTRL
- Look up : E
- Look down : Q
