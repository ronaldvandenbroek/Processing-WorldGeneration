package nl.ronaldvandenbroek.worldgen.processing;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class ProcessingImageDrawer {
    private PApplet processing;

    public ProcessingImageDrawer(PApplet processing) {
        this.processing = processing;
    }

    public PImage draw(float[][] array) {
        int arrayHeight = array.length;
        int arrayWidth = array[0].length;

        PImage image = processing.createImage(arrayHeight, arrayWidth, PConstants.RGB);
        for (int h = 0; h < arrayHeight; h++) {
            for (int w = 0; w < arrayWidth; w++) {
                image.pixels[h * arrayWidth + w] = processing.color(array[h][w]);
            }
        }
        return image;
    }
}

