package com.example.apppetanca;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText playerCountEditText;
    private Button calculateButton, agreeButton;
    private TextView dupletTextView, tripletTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerCountEditText = findViewById(R.id.playerCountEditText);
        calculateButton = findViewById(R.id.calculateButton);
        agreeButton = findViewById(R.id.agreeButton);
        dupletTextView = findViewById(R.id.dupletTextView);
        tripletTextView = findViewById(R.id.tripletTextView);
        // Botón Salir
        Button exitButton = findViewById(R.id.exitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cierra la actividad actual (la app se cerrará)
                finish();
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = playerCountEditText.getText().toString();

                if (input.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor ingresa un número válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                int playerCount = Integer.parseInt(input);
                calculateCombinations(playerCount);

                // Hacer visible el botón "De acuerdo"
                agreeButton.setVisibility(View.VISIBLE);
            }
        });

        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Una vez el usuario haga clic en "De acuerdo", se puede navegar a la siguiente pantalla
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);  // Esto abrirá la pantalla ScoreActivity
            }
        });
    }

    private void calculateCombinations(int playerCount) {
        if (playerCount < 2) {
            Toast.makeText(this, "El número de jugadores debe ser al menos 2", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calcular el número de dupletas (parejas)
        int dupletCount = playerCount / 2;  // Divide el número de jugadores entre 2
        int remainingPlayers = playerCount % 2;  // Jugadores sobrantes después de hacer dupletas

        // Si sobra un jugador, deshacemos una dupleta para formar una tripleta
        if (remainingPlayers == 1 && dupletCount > 0) {
            dupletCount--;  // Quitamos una dupleta
            remainingPlayers = 0;  // Ya no queda ningún jugador sobrante
        }

        // Ahora calculamos cuántas tripletas se pueden formar
        int tripletCount = (playerCount - dupletCount * 2) / 3;  // Calculamos el número de tripletas

        // Mostrar los resultados en los TextView correspondientes
        dupletTextView.setText("Dupletas: " + dupletCount);
        tripletTextView.setText("Tripletas: " + tripletCount);

        // Hacer visibles los resultados
        dupletTextView.setVisibility(View.VISIBLE);
        tripletTextView.setVisibility(View.VISIBLE);
    }
}
