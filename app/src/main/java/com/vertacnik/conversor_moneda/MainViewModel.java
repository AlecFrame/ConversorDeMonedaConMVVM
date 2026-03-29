package com.vertacnik.conversor_moneda;

import android.app.Application;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

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

    // ve que radio button está seleccionado para convertir de dólar a euro o euro a dólar
    public void convertir(RadioButton rbDolar, EditText etEuro, EditText etDolar, RadioButton rbEuro) {
        if (rbDolar.isChecked()) {
            // Verificamos que el campo de euro no esté vacío
            if (etEuro.getText().toString().isEmpty()) {
                Toast.makeText(getApplication(), "El campo de euro está vacío", Toast.LENGTH_LONG).show();
                return;
            }
            // Seteamos el valor de conversion en el otro campo
            etDolar.setText(
                    String.valueOf(
                            Double.parseDouble(etEuro.getText().toString()) * 1/getUnidadDeCambio().getValue()
                    )
            );
        }else
        if (rbEuro.isChecked()) {
            // Verificamos que el campo de dólar no esté vacío
            if (etDolar.getText().toString().isEmpty()) {
                Toast.makeText(getApplication(), "El campo de dólar está vacío", Toast.LENGTH_LONG).show();
                return;
            }
            // Seteamos el valor de conversion en el otro campo
            etEuro.setText(
                    String.valueOf(
                            Double.parseDouble(etDolar.getText().toString()) * getUnidadDeCambio().getValue()
                    )
            );
        }else {
            Toast.makeText(getApplication(), "No hay ningún opción seleccionado", Toast.LENGTH_LONG).show();
        }
    }

    public void controlDeValorDeConversion(EditText etUnidad) {
        if (etUnidad.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "El campo de valor de conversión no puede estar vacío", Toast.LENGTH_LONG).show();
            return;
        }
        if (Double.parseDouble(etUnidad.getText().toString()) <= 0) {
            Toast.makeText(getApplication(), "El campo de valor de conversión no puede ser 0 o negativo", Toast.LENGTH_LONG).show();
            return;
        }
    }

}
