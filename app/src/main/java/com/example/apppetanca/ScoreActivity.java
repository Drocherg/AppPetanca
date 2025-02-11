package com.example.apppetanca;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    private int player1Points = 0;
    private int player2Points = 0;

    private Button restartGameButton, resetPointsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score); // Asegúrate de que este sea el layout correcto

        // Botón Jugador 1
        Button player1Button = findViewById(R.id.player1AddPoint);
        player1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Points < 13) {
                    // Actualiza la casilla correspondiente
                    TextView pointView = findViewById(getResources().getIdentifier("player1Point" + (player1Points + 1), "id", getPackageName()));
                    pointView.setBackgroundColor(Color.GREEN); // Pone la casilla en verde
                    player1Points++; // Incrementa los puntos
                    checkForWinner();
                }
            }
        });

        // Botón Jugador 2
        Button player2Button = findViewById(R.id.player2AddPoint);
        player2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player2Points < 13) {
                    // Actualiza la casilla correspondiente
                    TextView pointView = findViewById(getResources().getIdentifier("player2Point" + (player2Points + 1), "id", getPackageName()));
                    pointView.setBackgroundColor(Color.GREEN); // Pone la casilla en verde
                    player2Points++; // Incrementa los puntos
                    checkForWinner();
                }
            }
        });

        // Botones de reinicio y reset
        restartGameButton = findViewById(R.id.restartGameButton);
        resetPointsButton = findViewById(R.id.resetPointsButton);

        // Inicialmente los botones están invisibles
        restartGameButton.setVisibility(View.INVISIBLE);
        resetPointsButton.setVisibility(View.INVISIBLE);

        // Acciones para los botones de reinicio y reset
        restartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reiniciar el juego
                player1Points = 0;
                player2Points = 0;
                resetPoints();
                restartGameButton.setVisibility(View.INVISIBLE);
                resetPointsButton.setVisibility(View.INVISIBLE);
            }
        });

        resetPointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear puntos
                player1Points = 0;
                player2Points = 0;
                resetPoints();
                restartGameButton.setVisibility(View.INVISIBLE); // Esconde el botón de reiniciar
                resetPointsButton.setVisibility(View.INVISIBLE); // Esconde el botón de resetear
            }
        });

        // Acción del botón Volver al sorteo
        Button backToDrawButton = findViewById(R.id.restartGameButton);
        backToDrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un intent para volver a la actividad inicial (MainActivity)
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esto asegura que la actividad anterior se borre
                startActivity(intent);

                // Finaliza esta actividad para eliminarla de la pila
                finish();
            }
        });
    }

    // Función para chequear si algún jugador alcanzó los 13 puntos
    private void checkForWinner() {
        if (player1Points == 13) {
            Toast.makeText(ScoreActivity.this, "¡Local ha ganado!", Toast.LENGTH_SHORT).show();
            showGameOverButtons();
        } else if (player2Points == 13) {
            Toast.makeText(ScoreActivity.this, "¡Visitante ha ganado!", Toast.LENGTH_SHORT).show();
            showGameOverButtons();
        }
    }

    // Función para mostrar los botones de reiniciar y resetear
    private void showGameOverButtons() {
        restartGameButton.setVisibility(View.VISIBLE);
        resetPointsButton.setVisibility(View.VISIBLE);
    }

    // Función para resetear los puntos a cero y poner las casillas en 0
    private void resetPoints() {
        // Resetea los puntos en los TextViews
        for (int i = 1; i <= 13; i++) {
            TextView player1Point = findViewById(getResources().getIdentifier("player1Point" + i, "id", getPackageName()));
            player1Point.setBackgroundColor(Color.TRANSPARENT); // Vuelve la casilla a su estado original

            TextView player2Point = findViewById(getResources().getIdentifier("player2Point" + i, "id", getPackageName()));
            player2Point.setBackgroundColor(Color.TRANSPARENT); // Vuelve la casilla a su estado original
        }

        // Asegúrate de que los puntos estén en 0
        player1Points = 0;
        player2Points = 0;
    }
}
