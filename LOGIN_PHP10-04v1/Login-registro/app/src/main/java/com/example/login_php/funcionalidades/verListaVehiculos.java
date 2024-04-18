package com.example.login_php.funcionalidades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.login_php.Database;
import com.example.login_php.R;
import com.example.login_php.objetos.Vehiculo;
import java.util.ArrayList;
import java.util.List;

public class verListaVehiculos extends AppCompatActivity {

    private List<Vehiculo> listaVehiculos;
    private ArrayAdapter<Vehiculo> adapter;
    private ListView listViewVehiculos;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lista_vehiculos);

        listViewVehiculos = findViewById(R.id.listViewVehiculos);
        listaVehiculos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
        listViewVehiculos.setAdapter(adapter);

        // Inicializar la base de datos
        database = new Database(this);

        // Cargar los vehículos desde la base de datos
        cargarVehiculosDesdeBaseDeDatos();

        // Agregar el listener de clic largo al ListView
        registerForContextMenu(listViewVehiculos);
    }

    private void cargarVehiculosDesdeBaseDeDatos() {
        // Obtener todos los vehículos de la base de datos
        List<Vehiculo> vehiculos = database.obtenerTodosLosVehiculos();

        // Limpiar la lista actual de vehículos y agregar los vehículos recuperados
        listaVehiculos.clear();
        listaVehiculos.addAll(vehiculos);

        // Notificar al adaptador que los datos han cambiado
        adapter.notifyDataSetChanged();
    }

    // Método para crear el menú contextual para editar o eliminar un vehículo
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }

    // Método para manejar las acciones de los elementos del menú contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Vehiculo vehiculoSeleccionado = listaVehiculos.get(info.position);

        switch (item.getItemId()) {
            case R.id.editar:
                // Aquí puedes abrir la actividad de edición de vehículo
                // Puedes pasar el vehículo seleccionado como extra en el intent
                Intent intent = new Intent(verListaVehiculos.this, ActividadEdicion.class);
                intent.putExtra("vehiculoId", vehiculoSeleccionado.getId());
                startActivity(intent);

                return true;

            case R.id.eliminar:
                // Mostrar un diálogo de confirmación antes de eliminar el vehículo
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar vehículo")
                        .setMessage("¿Estás seguro de que deseas eliminar este vehículo?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Eliminar el vehículo de la base de datos y recargar la lista
                                database.eliminarVehiculo(vehiculoSeleccionado);
                                cargarVehiculosDesdeBaseDeDatos();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
                return true;

            case R.id.actualizar:
                cargarVehiculosDesdeBaseDeDatos();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
