package com.vertacnik.conversor_moneda;

import android.app.Application;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    // Nosotros hicimos la lógica de conversion en base al valor de cambio entre dólar a euro
    private MutableLiveData<Double> unidadDeCambio;
    private MutableLiveData<String> valorString;
    private MutableLiveData<String> stringEuro;
    private MutableLiveData<String> stringDolar;
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getValorString() {
        if (valorString==null) {
            valorString = new MutableLiveData<>();
            valorString.setValue("0.87");
        }
        return valorString;
    }
    public LiveData<String> getStringEuro() {
        if (stringEuro==null) {
            stringEuro = new MutableLiveData<>();
            stringEuro.setValue("");
        }
        return stringEuro;
    }
    public LiveData<String> getStringDolar() {
        if (stringDolar==null) {
            stringDolar = new MutableLiveData<>();
            stringDolar.setValue("");
        }
        return stringDolar;
    }
    public LiveData<Double> getUnidadDeCambio() {
        if (unidadDeCambio==null) {
            unidadDeCambio = new MutableLiveData<>();
            unidadDeCambio.setValue(0.87);
        }
        return unidadDeCambio;
    }


    // Antes de cambiar el valor de conversión por el de pasado por paramatro, verificamos que radio button está seleccionado para aplicar la lógica de conversion según corresponda
    public void cambiarUnidadDeCambio(String unidadNueva, boolean isDolar) {
        double valor;

        try {
            valor = Double.parseDouble(unidadNueva);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "El campo de Unidad de conversión posee debe ser un número", Toast.LENGTH_LONG).show();
            return;
        }

        if (isDolar) {
            unidadDeCambio.setValue(valor);
            valorString.setValue(String.valueOf(valor));
        }else {
            unidadDeCambio.setValue(1/valor);
            valorString.setValue(String.valueOf(1/valor));
        }
    }

    public void setUnidadDeCambio(boolean euro) {
        if (euro) {
            valorString.setValue(unidadDeCambio.getValue().toString());
        }else {
            valorString.setValue(String.valueOf(1/unidadDeCambio.getValue()));
        }
    }

    // Recuerdos de errores pasados
    private void convertir(RadioButton rbDolar, EditText etEuro, EditText etDolar, RadioButton rbEuro) {
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

    public void convertir(boolean dolar, boolean euro, String stringEuro, String stringDolar) {
        if (dolar) {
            convertirDolar(stringEuro);
        }else if (euro) {
            convertirEuro(stringDolar);
        }else {
            Toast.makeText(getApplication(), "No se ha seleccionado ningúna opción a que convertir", Toast.LENGTH_LONG).show();
        }
    }
    private void convertirDolar(String etEuro) {
        // Verificamos que el campo de euro no esté vacío
        if (etEuro.isEmpty()) {
            Toast.makeText(getApplication(), "El campo de euro está vacío", Toast.LENGTH_LONG).show();
        }
        // Seteamos el valor de conversion en el otro campo
        stringDolar.setValue(String.valueOf(Double.parseDouble(etEuro) * 1/getUnidadDeCambio().getValue() ));
    }

    private void convertirEuro(String etDolar) {
        // Verificamos que el campo de dólar no esté vacío
        if (etDolar.isEmpty()) {
            Toast.makeText(getApplication(), "El campo de dólar está vacío", Toast.LENGTH_LONG).show();
        }
        // Seteamos el valor de conversion en el otro campo
        stringEuro.setValue(String.valueOf(Double.parseDouble(etDolar) * getUnidadDeCambio().getValue() ));
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
