import framebuffer.*;
//import jdk.nashorn.internal.ir.CaseNode;
import framebuffer.FrameBuffer.Viewport;
import java.util.Random;
import java.util.function.Function;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.Math.*;

public class mterm {
    public static void main(String[] args) {

        FrameBuffer fb = new FrameBuffer(600, 400);
        Color[] cArray2 = new Color[fb.getWidthFB() * fb.getHeightFB()];
        fb.setPixelFB(257, 188, new Color(100, 100, 100));
        System.out.println(fb.getPixelFB(257, 188));
        Color c = new Color(fb.pixel_buffer[154388]);
        System.out.println(c.toString());

        System.out.println(fb.pixel_buffer[102988]);

        int index2 = 0;

        for (int i = 0; i < 600; ++i) {
            for (int j = 0; j < 400; ++j) {
                Color c2 = fb.getPixelFB(i, j);
                cArray2[index2] = c2;
                if (cArray2[index2].getRed() == 100) {
                    System.out.println("yeas");
                    System.out.println(index2);
                }
                index2++;
            }

        }
    }
}
