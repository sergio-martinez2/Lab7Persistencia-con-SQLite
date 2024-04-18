package com.example.login_php.homes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.login_php.Database;
import com.example.login_php.R;

public class homeconductor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeconductor);


        Toolbar toolbar = findViewById(R.id.toolbar); // Inflar el Toolbar desde el layout
        setSupportActionBar(toolbar);         // Configurar el Toolbar como la ActionBar de la actividad
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Ocultar el título del ActionBar
        Database database = new Database(this);  // Obtener una instancia de la base de datos

        String email = getIntent().getStringExtra("CORREO_ELECTRONICO");
        String nombreUsuario = obtenerNombreUsuario(database, email); // Obtener el nombre del usuario desde la base de datos

        // Mostrar el nombre del usuario en un TextView
        TextView textView = findViewById(R.id.inicioUsuario);
        textView.setText("Bienvenido, cliente " + nombreUsuario + "!");

    }

    // Método obtener el nombre del usuario desde la base de datos
    private String obtenerNombreUsuario(Database database, String email) {
        String nombreUsuario = database.getUserName(email);

        // Retornar el nombre del usuario obtenido
        return nombreUsuario;
    }
}


