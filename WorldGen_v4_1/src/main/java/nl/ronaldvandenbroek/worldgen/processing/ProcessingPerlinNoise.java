package nl.ronaldvandenbroek.worldgen.processing;

import nl.ronaldvandenbroek.worldgen.NoiseMapGenerator;
import nl.ronaldvandenbroek.worldgen.properties.Config;
import processing.core.PApplet;

public class ProcessingPerlinNoise implements NoiseMapGenerator {
    private PApplet processing;

    public ProcessingPerlinNoise(PApplet processing) {
        this.processing = processing;
    }

    @Override
    public float[][] generate(int height, int width, int seed, int octaves, float falloff, float intensity) {
        falloff = falloff / Config.PERLIN_FALLOFF_MODIFIER;
        intensity = intensity / Config.PERLIN_INTENSITY_MODIFIER;

        processing.noiseSeed(seed);
        processing.noiseDetail(octaves, falloff);

        float[][] noiseMap = new float[height][width];
        float y = 0;
        for (int h = 0; h < height; h++) {
            float x = 0;
            for (int w = 0; w < width; w++) {
                noiseMap[h][w] = processing.noise(x, y);
                x += intensity;
            }
            y += intensity;
        }
        return noiseMap;
    }
}
