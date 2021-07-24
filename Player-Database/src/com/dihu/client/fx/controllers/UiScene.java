package com.dihu.client.fx.controllers;

import java.util.ArrayList;
import java.util.List;

public class UiScene {
    private UiScene prevScene;
    private final List<UiScene> nextScenes;
    private final String fileName;

    public UiScene(String fileName) {
        this.fileName = fileName;
        nextScenes = new ArrayList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public UiScene getPrevScene() {
        return prevScene;
    }

    public void setPrevScene(UiScene scene) {
        prevScene = scene;
    }

    public void addNextScene(UiScene scene) { nextScenes.add(scene); }

    public List<UiScene> getNextScenes() {
        return nextScenes;
    }
}