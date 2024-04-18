package com.example.login_php.funcionalidades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login_php.Database;
import com.example.login_php.R;

public class crearCarga extends AppCompatActivity {

    private EditText etNombreDeCarga, etPeso, etCiudadOrigen, etCiudadDestino;
    private Button btnRegistrarCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcarga);


        // Obtener referencias a los elementos de la interfaz de usuario
        etNombreDeCarga = findViewById(R.id.etNombredeCarga);
        etPeso = findViewById(R.id.etPeso);
        etCiudadOrigen = findViewById(R.id.etCiudadOrigen);
        etCiudadDestino = findViewById(R.id.etCiudadDestino);
        btnRegistrarCarga = findViewById(R.id.btn_register);
        Database database = new Database(this);
        // Configurar un Listener de clic para el botón de registro
        btnRegistrarCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                String nombreDeCarga = etNombreDeCarga.getText().toString();
                int peso = Integer.parseInt(etPeso.getText().toString()); // Convertir a entero
                String ciudadOrigen = etCiudadOrigen.getText().toString();
                String ciudadDestino = etCiudadDestino.getText().toString();
                String estado = "Disponible"; // Estado predeterminado, puedes cambiar según tu lógica de negocio

                String email = getIntent().getStringExtra("CORREO_ELECTRONICO");
                String creadorCarga = obtenerNombreUsuarioLogueado(database, email);
                // creadorCarga= "pepe2"; SI NO APARECE LA BASE DE DATOS
                //INTENTE CON EL CODIGO QUE TENGO (creadorCarga="nombre")


                // Realizar alguna acción, como enviar los datos a un servidor
                // Aquí puedes agregar tu lógica para manejar los datos ingresados por el usuario
                database.insertCarga(nombreDeCarga, peso, ciudadOrigen, ciudadDestino, estado, creadorCarga);

                // Ejemplo: Mostrar un mensaje de éxito
                Toast.makeText(crearCarga.this, "Carga registrada: " + nombreDeCarga, Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Método obtener el nombre del usuario desde la base de datos
    private String obtenerNombreUsuarioLogueado(Database database, String email) {
        String nombreUsuario = database.getUserName(email);

        // Retornar el nombre del usuario obtenido
        return nombreUsuario;
    }

}