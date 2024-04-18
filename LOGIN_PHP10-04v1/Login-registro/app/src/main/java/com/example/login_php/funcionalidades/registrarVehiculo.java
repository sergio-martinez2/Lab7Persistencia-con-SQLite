package com.example.login_php.funcionalidades;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_php.Database;
import com.example.login_php.R;

public class registrarVehiculo extends AppCompatActivity {

    // Variables para los elementos de la interfaz de usuario
    private EditText etMarca, etModelo, etPlaca, etColor;
    private Button btnRegistrar;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarvehiculos);

        // Inicializar los elementos de la interfaz de usuario
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etPlaca = findViewById(R.id.etPlaca);
        etColor = findViewById(R.id.etColor);
        btnRegistrar = findViewById(R.id.btn_register);

        database = new Database(this);

        // Configurar el listener para el botón de registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarVehiculo();
            }
        });
    }

    // Método para registrar el vehículo
    private void registrarVehiculo() {
        // Obtener los datos ingresados por el usuario
        String marca = etMarca.getText().toString().trim();
        String modelo = etModelo.getText().toString().trim();
        String placa = etPlaca.getText().toString().trim();
        String color = etColor.getText().toString().trim();

        // Aquí puedes agregar la lógica para guardar los datos en tu base de datos
        database.insertVehiculo(marca, modelo, placa, color);
        // Por ejemplo, podrías llamar a un método de la clase Database para insertar los datos

        // Mostrar un mensaje de confirmación
        Toast.makeText(this, "Vehículo registrado correctamente", Toast.LENGTH_SHORT).show();
    }
}
