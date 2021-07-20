package com.dihu.client.graphics.controllers;

public class UiStructure {
    UiScene currentScene;

    public UiStructure() {
        UiScene playerDetails = new UiScene("PlayerDetails");
        UiScene playersMenu = new UiScene("PlayersMenu");
        UiScene clubMenu = new UiScene("ClubMenu");
        UiScene sellMenu = new UiScene("SellMenu");
        UiScene sellPlayerList = new UiScene("SellPlayerList");
        UiScene buyMenu = new UiScene("BuyMenu");
        UiScene auctionPlayerList = new UiScene("AuctionPlayerList");
        UiScene transferMenu = new UiScene("TransferMenu");
        UiScene mainMenu = new UiScene("MainMenu");
        UiScene loginForm = new UiScene("LoginForm");

        loginForm.addNextScene(mainMenu);

            mainMenu.addNextScene(playersMenu);
            mainMenu.addNextScene(clubMenu);
            mainMenu.addNextScene(transferMenu);
            mainMenu.setPrevScene(loginForm);

                playersMenu.addNextScene(playerDetails);
                playersMenu.setPrevScene(mainMenu);

                    playerDetails.setPrevScene(playersMenu);

                clubMenu.setPrevScene(mainMenu);

                transferMenu.setPrevScene(mainMenu);
                transferMenu.addNextScene(auctionPlayerList);
                transferMenu.addNextScene(sellPlayerList);

                    auctionPlayerList.setPrevScene(transferMenu);
                    auctionPlayerList.addNextScene(buyMenu);

                        buyMenu.setPrevScene(auctionPlayerList);
                        buyMenu.addNextScene(auctionPlayerList);

                    sellPlayerList.setPrevScene(transferMenu);
                    sellPlayerList.addNextScene(sellMenu);

                        sellMenu.setPrevScene(sellPlayerList);
                        sellMenu.addNextScene(sellPlayerList);

        currentScene = loginForm;
    }

    public UiScene getCurrentScene() {
        return currentScene;
    }

    public void back(){
        currentScene = currentScene.getPrevScene();
    }

    public void next(int idx){
        currentScene = currentScene.getNextScenes().get(idx);
    }
    public void next(){
        currentScene = currentScene.getNextScenes().get(0);
    }
}


