
/*

import framebuffer.*;
import framebuffer.FrameBuffer;
import java.awt.Color;
*/

// java .\Hw1.java "RebelTrooper.ppm"

import framebuffer.*;
import framebuffer.FrameBuffer.Viewport;
import java.util.Random;
import java.util.function.Function;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Math.*;

/**
 * Alexander Lowther Assignment 1
 */
public class Hw1 {

   public static void main(String[] args) {
      // Check for a file name on the command line.
      if (0 == args.length) {
         // System.err.println("Usage: java Hw1 <PPM-file-name>");
         System.err.println("Usage: java Hw1 <PPM-file-name>");
         System.exit(-1);
      }

      // This framebuffer holds the image that will be embedded
      // within two viewports of our larger framebuffer.
      final FrameBuffer fbEmbedded = new FrameBuffer(args[0]);
      final String savedFileName = "Hw1.ppm";
      /******************************************/
      // Colors for checkerboard
      Color redBoard = new Color(192, 56, 14);
      Color orangeBoard = new Color(255, 189, 95);
      int w1 = 0;
      int h1 = 0;
      int w2 = 100;
      int h2 = 100;
      String cmd = args[0];
      FrameBuffer fb = new FrameBuffer(1000, 600);
      // Create a framebuffer. Fill it with the checkerboard pattern.
      // iterate 10 times for 10 rows, we will fill the orange color "over" the red

      // CHECKERBOARD

      fb.clearFB(redBoard);
      for (int i = 0; i < 10; i++) {
         // logic to decipher which square to fill first
         if (i % 2 == 0) {
            w1 = 0;
            w2 = 100;
         } else {
            w1 = 100;
            w2 = 200;
         }
         fillLine(w1, h1, w2, h2, orangeBoard, fb);
         fillLine(w1 + 200, h1, w2 + 200, h2, orangeBoard, fb);
         fillLine(w1 + 400, h1, w2 + 400, h2, orangeBoard, fb);
         h1 = h1 + 100;
         h2 = h2 + 100;
      }
      // Create a viewport and fill it with a flipped copy of the command-line PPM
      // file.
      // Had a hard time reducing redundancy here please inform me how I could do
      // better
      Viewport vp1 = fb.new Viewport(75, 125, cmd);
      Color[] cArray = new Color[65536];
      int index = 0;
      // Incrementally store the entire viewport in an array
      for (int i = 0; i <= vp1.getWidthVP() - 1; i++) {
         for (int j = 0; j <= vp1.getHeightVP() - 1; j++) {
            Color c1 = vp1.getPixelVP(i, j);
            cArray[index] = c1;
            index++;
         }
      }
      // Flip the viewport
      for (int i = vp1.getWidthVP() - 1; i >= 0; i--) {
         for (int j = 0; j <= vp1.getHeightVP() - 1; j++) {
            vp1.setPixelVP(i, j, cArray[index - 1]);
            index--;
         }
      }
      // Remove the white pixels by iterating through the viewport and setting white
      // pixels
      // to the checkerboard piece 400 pixels over
      for (int i = 0; i < vp1.getWidthVP(); i++) {
         for (int j = 0; j < vp1.getHeightVP(); j++) {
            if ((vp1.getPixelVP(i, j).getRed() > 250 && vp1.getPixelVP(i, j).getBlue() > 250)
                  && (vp1.getPixelVP(i, j).getGreen() > 250)) {
               vp1.setPixelVP(i, j, vp1.getPixelVP(i + 400, j));
            }
         }
      }

      // Create another viewport and fill it with another flipped copy of the
      // command-line PPM file.

      Viewport vp2 = fb.new Viewport(331, 125, cmd);

      Color[] cArray2 = new Color[66049];
      int index2 = 0;
      for (int i = 0; i <= vp2.getWidthVP() - 1; i++) {
         for (int j = 0; j <= vp2.getHeightVP() - 1; j++) {
            Color c2 = vp2.getPixelVP(i, j);
            cArray2[index2] = c2;
            index2++;
         }
      }

      for (int i = 0; i <= vp2.getWidthVP() - 1; i++) {
         for (int j = vp2.getHeightVP() - 1; j >= 0; j--) {
            vp2.setPixelVP(i, j, cArray2[index2 - 1]);
            index2--;
         }
      }

      for (int i = 0; i < vp2.getWidthVP(); i++) {
         for (int j = 0; j < vp2.getHeightVP(); j++) {

            if ((vp2.getPixelVP(i, j).getRed() > 250 && vp2.getPixelVP(i, j).getBlue() > 250)
                  && (vp2.getPixelVP(i, j).getGreen() > 250)) {
               if (i < 128) {
                  vp2.setPixelVP(i, j, fb.getPixelFB(i - 169, j + 26));
               } else {
                  vp2.setPixelVP(i, j, fb.getPixelFB(i - 169, j + 25));
               }
            }
         }
      }

      // Draw the striped pattern.
      Color gStripe = new Color(152, 203, 74);
      var rStripe = new Color(241, 95, 116);
      Color bStripe = new Color(84, 129, 230);
      Viewport vp3 = fb.new Viewport(610, 420, 300, 119);
      int boxHeight = 0;
      int boxWidth = vp3.getWidthVP() - 1;
      Color[] stripes = { gStripe, rStripe, bStripe };
      int in = 0;
      // Begining at the top left corner, we iterate draw the pixels "down and over"
      // each stripe is 30 px wide, so we use an array to change colors every 30 px
      for (int k = vp3.getWidthVP() + 119; k >= 0; k -= 30) {
         boxWidth = k;
         while ((boxHeight <= vp3.getHeightVP())) { // fill in 30 times
            for (int x = 0; x <= 30; x++) {
               if ((boxWidth - x >= 0) && (boxWidth - x + 120 < 420)) {
                  vp3.setPixelVP(boxWidth - x, boxHeight, stripes[in]);
               }
            }
            boxHeight++;
            boxWidth--;
         }
         boxHeight = 0;
         if (in == stripes.length - 1) {
            in = 0;
         } else {
            in++;
         }
      }

      // Create another viewport that covers the selected region of the framebuffer.
      Viewport selectedRegion = fb.new Viewport(500, 200, 200, 300);
      // Create another viewport to hold a "framed" copy of the selected region.
      Viewport framedCopy = fb.new Viewport(725, 25, 250, 350);
      // Give this viewport a grayish background color.
      framedCopy.clearVP(new Color(192, 192, 192));
      // Create another viewport inside the last one.
      Viewport innerCopy = fb.new Viewport(750, 50, selectedRegion);
      // Copy the selected region's viewport into this last viewport.
      // Load Dumbledore into another FrameBuffer.
      FrameBuffer ddFB = new FrameBuffer("Dumbledore.ppm");
      // // Create a viewport to hold Dumbledore's ghost.
      Viewport ddVP = fb.new Viewport(400, 100, 500, 475);
      // Blend Dumbledore from the framebuffer into the viewport.
      for (int i = 0; i < ddFB.getHeightFB(); i++) {
         for (int j = 0; j < ddFB.getWidthFB(); j++) {
            Color ddColor = ddFB.getPixelFB(i, j);
            if ((ddColor.getRed() > 250 && ddColor.getBlue() > 250) && (ddColor.getGreen() > 250)) {
               ddVP.setPixelVP(i, j, fb.getPixelFB(i + 400, j + 100));
            } else {
               ddVP.setPixelVP(i, j,
                     new Color((ddColor.getRed() * 7 / 10 + fb.getPixelFB(i + 400, j + 100).getRed() * 3 / 10),
                           (ddColor.getGreen() * 7 / 10 + fb.getPixelFB(i + 400, j + 100).getGreen() * 3 / 10),
                           (ddColor.getBlue() * 7 / 10 + fb.getPixelFB(i + 400, j + 100).getBlue() * 3 / 10)));
            }
         }
      }
      // FrameBuffer fb = null;
      /******************************************/
      // Save the resulting image in a file.
      // final String savedFileName = "Hw1.ppm";
      fb.dumpFB2File(savedFileName);
      System.err.println("Saved " + savedFileName);

   }

   private static Color setPixelVP(int i, int j, Color c1) {
      return null;
   }

   static void fillLine(int x1, int y1, int x2, int y2, Color c, FrameBuffer b) {
      while (x1 < x2) {
         for (int i = y1; i < y2; i++) {
            b.setPixelFB(i, x1, c);
         }
         ++x1;
      }
   }
}
