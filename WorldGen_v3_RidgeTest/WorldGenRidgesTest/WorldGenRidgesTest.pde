import controlP5.*;

ControlP5 cp5;
float[][] terrainMap;
int mapWidth, mapHeight;

//Default starting values, see Presets for slider starter values
int seed = 1;
int sliderPosition = 0;
int buttonPosition = 0;
boolean guiShow = true;

public void setup() {
  //Setup window and map
  size(1024, 1024);
  terrainMap = new float[height][width];
  mapWidth = width;
  mapHeight = height;

  //Configure GUI
  cp5 = new ControlP5(this);
  createGUISlider("intensity1", 1, 30);
  createGUISlider("power1", 0, 50);
  createGUISlider("falloff1", 0, 10);
  createGUISlider("octaves1", 1, 15);
  createGUISlider("circularFalloff1", 0, 1);
  createGUISlider("blendPower12", 0, 1);
  createGUISlider("intensity2", 1, 30);
  createGUISlider("power2", 0, 50);
  createGUISlider("falloff2", 0, 10);
  createGUISlider("octaves2", 1, 15);
  createGUISlider("circularFalloff2", 0, 1);

  CallbackListener mapCallbackListener = new CallbackListener() {
    public void controlEvent(CallbackEvent theEvent) {
      switch (key) {
      case '1':
        drawTerrainMap();
        break;
      case '2':
        drawTerrainMap();
        break;
      }
    }
  };

  createGUIMapButton("Height Map", 1, mapCallbackListener);
  createGUIMapButton("Temperature Map", 2, mapCallbackListener);
  //Generate default map
  generateHeightMap();
}

public void createGUISlider(String name, int min, int max) {
  cp5.addSlider(name).setPosition(0, sliderPosition).setRange(min, max);
  sliderPosition += 10;
}

public void createGUIMapButton(String name, int value, CallbackListener callbackListener) {
  cp5.addButton(name).setPosition(width - 80, buttonPosition).setValue(value).onRelease(callbackListener);
  buttonPosition += 20;
}

public void test(int value) {
  println("success" + value);
}

public void draw() {
}

public void keyPressed() {
  switch (key) {
  case '1': //Previous Seed
    if (seed <= 0) {
      seed = 0;
    } else {
      seed -= 1;
    }
    generateHeightMap();
    break;
  case '2': //Next Seed
    seed += 1;
    generateHeightMap();
    break;
  case 'q': //Next Seed
    if (guiShow) {
      cp5.hide();
      guiShow = false;
    } else {
      cp5.show();
      guiShow = true;
    }
    drawTerrainMap();
    break;
  }
}

public void mouseReleased() {
  generateHeightMap();
}

public void generateHeightMap() {
  float[][] terrainMap1 = generateHeightMap(mapWidth, mapHeight, seed, octaves1, falloff1, intensity1, power1, circularFalloff1, false);
  float[][] terrainMap2 = generateHeightMap(mapWidth, mapHeight, seed+100, octaves2, falloff2, intensity2, power2, circularFalloff2, true);
  terrainMap = mergeHeightMaps(terrainMap1, terrainMap2, blendPower12);
  terrainMap = normaliseMinAndMaxHeight(terrainMap);
  drawTerrainMap();
}
