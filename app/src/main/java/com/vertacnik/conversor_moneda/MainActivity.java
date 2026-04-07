package com.vertacnik.conversor_moneda;

import static android.app.ProgressDialog.show;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.vertacnik.conversor_moneda.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        viewModel.getUnidadDeCambio().observe(this, s -> {});
        viewModel.getValorString().observe(this, s -> {
            valor = s;
        });
        viewModel.getStringEuro().observe(this, s -> {
            binding.etEuro.setText(s);
        });
        viewModel.getStringDolar().observe(this, s -> {
            binding.etDolar.setText(s);
        });

        // Al seleccionar el radio button de Dolar se activan y desactivan los campos correspondientes y se setea el texto del valor de conversión
        binding.rbDolar.setOnClickListener(v -> {
            viewModel.setUnidadDeCambio(binding.rbEuro.isChecked());
            binding.etDolar.setEnabled(false);
            binding.etDolar.setText("");
            binding.etEuro.setEnabled(true);
            binding.etEuro.setText("");
            binding.etUnidad.setEnabled(true);
            binding.etUnidad.setText(valor);
        });

        // Al seleccionar el radio button de Euro se activan y desactivan los campos correspondientes y se setea el texto del valor de conversión
        binding.rbEuro.setOnClickListener(v -> {
            viewModel.setUnidadDeCambio(binding.rbEuro.isChecked());
            binding.etDolar.setEnabled(true);
            binding.etDolar.setText("");
            binding.etEuro.setEnabled(false);
            binding.etEuro.setText("");
            binding.etUnidad.setEnabled(true);
            binding.etUnidad.setText(valor);
        });

        // Al apretar el botón convertir llama al método del model convertir y les pasamos los componentes que requiere
        binding.btConvertir.setOnClickListener(v -> {
            viewModel.convertir(
                    binding.rbDolar.isChecked(),
                    binding.rbEuro.isChecked(),
                    binding.etEuro.getText().toString(),
                    binding.etDolar.getText().toString()
            );
        });

        // Al apretar el botón de Cambiar valor, se guarda el valor en el model que se haya ingresado en el campo de etUnidad y control de errores (campo vacío o valor nulo o negativo)
        binding.btCambiarValor.setOnClickListener(v -> {
            viewModel.controlDeValorDeConversion(binding.etUnidad);

            // guarda en el viewModel el valor
            viewModel.cambiarUnidadDeCambio(binding.etUnidad.getText().toString(), binding.rbEuro.isChecked());
        });
    }
}