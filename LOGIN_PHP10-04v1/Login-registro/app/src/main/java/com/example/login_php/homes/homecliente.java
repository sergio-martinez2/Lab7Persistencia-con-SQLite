package com.example.login_php.homes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.login_php.Database;
import com.example.login_php.R;
import com.example.login_php.funcionalidades.crearCarga;

public class homecliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homecliente);

        Toolbar toolbar = findViewById(R.id.toolbar); // Inflar el Toolbar desde el layout
        setSupportActionBar(toolbar);  // Configurar el Toolbar como la ActionBar de la actividad
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Ocultar el título del ActionBar
        Database database = new Database(this);  // Obtener una instancia de la base de datos

        String email = getIntent().getStringExtra("CORREO_ELECTRONICO");
        String nombreUsuario = obtenerNombreUsuario(database, email); // Obtener el nombre del usuario desde la base de datos

        // Mostrar el nombre del usuario en un TextView
        TextView textView = findViewById(R.id.inicioUsuario);
        textView.setText("Bienvenido, cliente " + nombreUsuario + "!");

        // Inicializar botones
        Button btnCrearCarga = findViewById(R.id.btn_crearcarga);
        Button btnGestionCargas = findViewById(R.id.btn_gestion_de_cargas);
        Button btnHistoricoCargas = findViewById(R.id.btn_historico_de_cargas);

        // Configurar acciones para los botones
        btnCrearCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.login_php.funcionalidades.crearCarga.class);
                intent.putExtra("CORREO_ELECTRONICO", email);
                // Agregar la lógica para abrir la actividad de crear carga
                startActivity(intent);
            }
        });

        btnGestionCargas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.login_php.funcionalidades.gestionCargas.class);
               // intent.putExtra("CORREO_ELECTRONICO", email);
                // OPCIONAL SI SE NECESITA ALGUN PARAMETRO
                startActivity(intent);
            }
        });

        btnHistoricoCargas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agregar la lógica para abrir la actividad del histórico de cargas
                // Por ejemplo:
                // startActivity(new Intent(homecliente.this, HistoricoCargasActivity.class));
            }
        });
    }

    // Método obtener el nombre del usuario desde la base de datos
    private String obtenerNombreUsuario(Database database, String email) {
        String nombreUsuario = database.getUserName(email);

        // Retornar el nombre del usuario obtenido
        return nombreUsuario;
    }
}
