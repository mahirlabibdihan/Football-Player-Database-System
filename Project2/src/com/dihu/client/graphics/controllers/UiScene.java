package com.dihu.client.graphics.controllers;

import java.util.ArrayList;
import java.util.List;

public class UiScene {
    private UiScene prevScene;
    private List<UiScene> nextScenes;
    private String fileName;

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

    public void addNextScene(UiScene scene) {
        UiScene nextScene = scene;
        nextScenes.add(nextScene);
    }

    public List<UiScene> getNextScenes() {
        return nextScenes;
    }
}