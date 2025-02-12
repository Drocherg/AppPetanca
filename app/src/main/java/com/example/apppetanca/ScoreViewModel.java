package com.example.apppetanca;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {
    private final MutableLiveData<Integer> player1Score = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> player2Score = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> showRestartButtons = new MutableLiveData<>(false);
    private final int MAX_POINTS = 13;

    public LiveData<Integer> getPlayer1Score() {
        return player1Score;
    }

    public LiveData<Integer> getPlayer2Score() {
        return player2Score;
    }

    public LiveData<Boolean> getGameOver() {
        return gameOver;
    }

    public LiveData<Boolean> getShowRestartButtons() {
        return showRestartButtons;
    }

    public void addPlayer1Point() {
        if (!gameOver.getValue()) {
            int newScore = player1Score.getValue() + 1;
            player1Score.setValue(newScore);
            checkWinner();
        }
    }

    public void addPlayer2Point() {
        if (!gameOver.getValue()) {
            int newScore = player2Score.getValue() + 1;
            player2Score.setValue(newScore);
            checkWinner();
        }
    }

    public void resetGame() {
        player1Score.setValue(0);
        player2Score.setValue(0);
        gameOver.setValue(false);
        showRestartButtons.setValue(false);
    }

    public void resetPoints() {
        player1Score.setValue(0);
        player2Score.setValue(0);
        gameOver.setValue(false); // Asegurar que el juego no esté bloqueado
        showRestartButtons.setValue(false);
    }


    private void checkWinner() {
        if (player1Score.getValue() >= MAX_POINTS || player2Score.getValue() >= MAX_POINTS) {
            gameOver.setValue(true);
            showRestartButtons.setValue(true);
        }
    }
}
