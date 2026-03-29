package com.vertacnik.conversor_moneda;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    // Nosotros hicimos la lógica de conversion en base al valor de cambio entre dólar a euro
    private MutableLiveData<Double> unidadDeCambio;
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Double> getUnidadDeCambio() {
        if (unidadDeCambio==null) {
            unidadDeCambio = new MutableLiveData<>();
            unidadDeCambio.setValue(0.87);
        }
        return unidadDeCambio;
    }

    // Antes de cambiar el valor de conversión por el de pasado por paramatro, verificamos que radio button está seleccionado para aplicar la lógica de conversion según corresponda
    public void setUnidadDeCambio(double unidadNueva, boolean isDolar) {
        if (isDolar) {
            unidadDeCambio.setValue(unidadNueva);
        }else {
            unidadDeCambio.setValue(1/unidadNueva);
        }
    }

}
