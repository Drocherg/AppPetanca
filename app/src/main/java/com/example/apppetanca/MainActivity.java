package com.example.apppetanca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private EditText playerCountEditText;
    private Button calculateButton, agreeButton, exitButton;
    private TextView dupletTextView, tripletTextView, instructionTextView;

    // Instancia del ViewModel
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular las vistas
        playerCountEditText = findViewById(R.id.playerCountEditText);
        calculateButton = findViewById(R.id.calculateButton);
        dupletTextView = findViewById(R.id.dupletTextView);
        tripletTextView = findViewById(R.id.tripletTextView);
        instructionTextView = findViewById(R.id.instruction);
        agreeButton = findViewById(R.id.agreeButton);
        exitButton = findViewById(R.id.exitButton);

        // Inicialmente ocultar los resultados
        dupletTextView.setVisibility(View.GONE);
        tripletTextView.setVisibility(View.GONE);
        agreeButton.setVisibility(View.GONE);

        // Obtener el ViewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Observadores de LiveData
        mainViewModel.getDupletCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer dupletCount) {
                // Mostrar el resultado de las dupletas
                dupletTextView.setText("Dupletas: " + dupletCount);
            }
        });

        mainViewModel.getTripletCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer tripletCount) {
                // Mostrar el resultado de las tripletas
                tripletTextView.setText("Tripletas: " + tripletCount);
            }
        });

        mainViewModel.getCanProceed().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean canProceed) {
                if (canProceed) {
                    // Mostrar el botón "De acuerdo" cuando se pueda proceder
                    agreeButton.setVisibility(View.VISIBLE);
                } else {
                    // Ocultar el botón "De acuerdo" si no se puede proceder
                    agreeButton.setVisibility(View.GONE);
                }
            }
        });

        // Botón "Calcular"
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = playerCountEditText.getText().toString();

                if (input.isEmpty()) {
                    // Si no se ingresó un número, mostrar un mensaje de error
                    Toast.makeText(MainActivity.this, "Por favor ingrese un número de jugadores", Toast.LENGTH_SHORT).show();
                    return;
                }

                int playerCount = Integer.parseInt(input);

                // Calcular las dupletas y tripletas a través del ViewModel
                mainViewModel.calculateCombinations(playerCount);

                // Cambiar la visibilidad de los resultados a VISIBLE
                dupletTextView.setVisibility(View.VISIBLE);
                tripletTextView.setVisibility(View.VISIBLE);
            }
        });

        // Botón "De acuerdo" (llevar al marcador)
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el número de jugadores del EditText
                String input = playerCountEditText.getText().toString();
                int playerCount = Integer.parseInt(input);

                // Crear un Intent para ir a la pantalla de marcador (ScoreActivity)
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("playerCount", playerCount);  // Pasar el número de jugadores

                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });

        // Botón "Salir" para salir de la aplicación
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad y termina la app
            }
        });
    }
}
