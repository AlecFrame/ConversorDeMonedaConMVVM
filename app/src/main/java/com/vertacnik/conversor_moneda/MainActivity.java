package com.vertacnik.conversor_moneda;

import static android.app.ProgressDialog.show;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.vertacnik.conversor_moneda.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        viewModel.getUnidadDeCambio().observe(this, unidadDeCambio -> {

        });

        // Al seleccionar el radio button de Dolar se activan y desactivan los campos correspondientes y se setea el texto del valor de conversión
        binding.rbDolar.setOnClickListener(v -> {
            binding.etDolar.setEnabled(false);
            binding.etDolar.setText("");
            binding.etEuro.setEnabled(true);
            binding.etEuro.setText("");
            binding.etUnidad.setEnabled(true);
            binding.etUnidad.setText(
                    String .valueOf(1/viewModel.getUnidadDeCambio().getValue())
            );
        });

        // Al seleccionar el radio button de Euro se activan y desactivan los campos correspondientes y se setea el texto del valor de conversión
        binding.rbEuro.setOnClickListener(v -> {
            binding.etDolar.setEnabled(true);
            binding.etDolar.setText("");
            binding.etEuro.setEnabled(false);
            binding.etEuro.setText("");
            binding.etUnidad.setEnabled(true);
            binding.etUnidad.setText(
                    String .valueOf(viewModel.getUnidadDeCambio().getValue())
            );
        });

        // Al apretar el boton de convertir ve que radio button está seleccionado para convertir de dólar a euro o euro a dólar
        binding.btConvertir.setOnClickListener(v -> {
            if (binding.rbDolar.isChecked()) {
                // Verificamos que el campo de euro no esté vacío
                if (binding.etEuro.getText().toString().isEmpty()) {
                    Toast.makeText(this, "El campo de euro está vacío", Toast.LENGTH_LONG).show();
                    return;
                }
                // Seteamos el valor de conversion en el otro campo
                binding.etDolar.setText(
                        String.valueOf(
                                 Double.parseDouble(binding.etEuro.getText().toString()) * 1/viewModel.getUnidadDeCambio().getValue()
                        )
                );
            }
            if (binding.rbEuro.isChecked()) {
                // Verificamos que el campo de dólar no esté vacío
                if (binding.etEuro.getText().toString().isEmpty()) {
                    Toast.makeText(this, "El campo de dólar está vacío", Toast.LENGTH_LONG).show();
                    return;
                }
                // Seteamos el valor de conversion en el otro campo
                binding.etEuro.setText(
                        String.valueOf(
                                Double.parseDouble(binding.etDolar.getText().toString()) * viewModel.getUnidadDeCambio().getValue()
                        )
                );
            }
        });

        // Al apretar el botón de Cambiar valor, se guarda el valor en el model que se haya ingresado en el campo de etUnidad y control de errores (campo vacío o valor nulo o negativo)
        binding.btCambiarValor.setOnClickListener(v -> {
            if (binding.etUnidad.getText().toString().isEmpty()) {
                Toast.makeText(this, "El campo de valor de conversión no puede estar vacío", Toast.LENGTH_LONG).show();
                return;
            }
            if (Double.parseDouble(binding.etUnidad.getText().toString()) <= 0) {
                Toast.makeText(this, "El campo de valor de conversión no puede ser 0 o negativo", Toast.LENGTH_LONG).show();
                return;
            }
            // guarda en el viewModel el valor
            viewModel.setUnidadDeCambio(Double.parseDouble(binding.etUnidad.getText().toString()), binding.rbEuro.isChecked());
        });
    }
}