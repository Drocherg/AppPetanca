package com.example.apppetanca;

// MainViewModel.java
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Integer> dupletCount = new MutableLiveData<>(0);
    private MutableLiveData<Integer> tripletCount = new MutableLiveData<>(0);
    private MutableLiveData<Boolean> canProceed = new MutableLiveData<>(false); // Para controlar la visibilidad del botón "De acuerdo"

    public LiveData<Integer> getDupletCount() {
        return dupletCount;
    }

    public LiveData<Integer> getTripletCount() {
        return tripletCount;
    }

    public LiveData<Boolean> getCanProceed() {
        return canProceed;
    }

    // Método para calcular dupletas y tripletas
    public void calculateCombinations(int playerCount) {
        if (playerCount < 2) {
            dupletCount.setValue(0);
            tripletCount.setValue(0);
            canProceed.setValue(false); // No se puede proceder si no hay suficientes jugadores
            return;
        }

        // Calcular el número de dupletas (parejas)
        int dupletCountValue = playerCount / 2;  // Divide el número de jugadores entre 2
        int remainingPlayers = playerCount % 2;  // Jugadores sobrantes después de hacer dupletas

        // Si sobra un jugador, deshacemos una dupleta para formar una tripleta
        if (remainingPlayers == 1 && dupletCountValue > 0) {
            dupletCountValue--;  // Quitamos una dupleta
            remainingPlayers = 0;  // Ya no queda ningún jugador sobrante
        }

        // Ahora calculamos cuántas tripletas se pueden formar
        int tripletCountValue = (playerCount - dupletCountValue * 2) / 3;  // Calculamos el número de tripletas

        // Actualizamos los LiveData
        dupletCount.setValue(dupletCountValue);
        tripletCount.setValue(tripletCountValue);
        canProceed.setValue(true); // Permite que el usuario proceda
    }
}

