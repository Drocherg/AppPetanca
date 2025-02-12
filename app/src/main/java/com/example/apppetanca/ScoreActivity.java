package com.example.apppetanca;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ScoreActivity extends AppCompatActivity {

    private Button player1Button, player2Button, restartGameButton, resetPointsButton;
    private ScoreViewModel scoreViewModel;
    private TextView[][] scoreBoxes = new TextView[2][13]; // Matriz de casillas para ambos jugadores

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Vincular botones
        player1Button = findViewById(R.id.player1AddPoint);
        player2Button = findViewById(R.id.player2AddPoint);
        restartGameButton = findViewById(R.id.restartGameButton);
        resetPointsButton = findViewById(R.id.resetPointsButton);

        // Obtener el ViewModel
        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        // Vincular casillas de puntos
        for (int i = 1; i <= 13; i++) {
            String player1Id = "player1Point" + i;
            String player2Id = "player2Point" + i;

            int resID1 = getResources().getIdentifier(player1Id, "id", getPackageName());
            int resID2 = getResources().getIdentifier(player2Id, "id", getPackageName());

            scoreBoxes[0][i - 1] = findViewById(resID1);
            scoreBoxes[1][i - 1] = findViewById(resID2);
        }

        // Observadores del ViewModel
        scoreViewModel.getPlayer1Score().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer score) {
                updatePlayerScore(0, score);
            }
        });

        scoreViewModel.getPlayer2Score().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer score) {
                updatePlayerScore(1, score);
            }
        });

        scoreViewModel.getGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameOver) {
                if (gameOver) {
                    Toast.makeText(ScoreActivity.this, "¡El juego ha terminado!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        scoreViewModel.getShowRestartButtons().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showButtons) {
                if (showButtons) {
                    restartGameButton.setVisibility(View.VISIBLE);
                    resetPointsButton.setVisibility(View.VISIBLE);
                }
            }
        });

        // Agregar puntos
        player1Button.setOnClickListener(v -> scoreViewModel.addPlayer1Point());
        player2Button.setOnClickListener(v -> scoreViewModel.addPlayer2Point());

        // Botón para reiniciar el juego y volver a MainActivity
        restartGameButton.setOnClickListener(v -> {
            scoreViewModel.resetGame();
            restartGameButton.setVisibility(View.INVISIBLE);
            resetPointsButton.setVisibility(View.INVISIBLE);

            Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Botón para resetear los puntos sin salir de la actividad
        resetPointsButton.setOnClickListener(v -> {
            scoreViewModel.resetPoints();
            restartGameButton.setVisibility(View.INVISIBLE);
            resetPointsButton.setVisibility(View.INVISIBLE);

            // Volver a activar los botones de sumar puntos si estaban bloqueados
            player1Button.setEnabled(true);
            player2Button.setEnabled(true);

            // Restaurar la apariencia de las casillas de puntuación
            updatePlayerScore(0, 0);
            updatePlayerScore(1, 0);
        });
    }

    // Actualizar las casillas de puntos
    private void updatePlayerScore(int player, int score) {
        for (int i = 0; i < 13; i++) {
            if (scoreBoxes[player][i] != null) {
                if (i < score) {
                    scoreBoxes[player][i].setBackgroundColor(Color.GREEN);
                } else {
                    scoreBoxes[player][i].setBackgroundColor(Color.DKGRAY);
                }
            }
        }
    }
}
