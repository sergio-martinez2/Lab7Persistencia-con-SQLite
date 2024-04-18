package com.example.login_php;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.login_php.objetos.Carga;
import com.example.login_php.objetos.Vehiculo;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "appcarga"; // el nombre de nuestra base de datos
    private static final int DB_VERSION = 1; // la versión de la base de datos

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USUARIOS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NOMBRE TEXT, "
                + "EMAIL TEXT, "
                + "PASSWORD TEXT, "
                + "TIPO_USUARIO TEXT);");

        db.execSQL("CREATE TABLE CARGAS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NOMBRE_DE_CARGA TEXT, "
                + "PESO INTEGER, "
                + "CIUDAD_ORIGEN TEXT, "
                + "CIUDAD_DESTINO TEXT, "
                + "ESTADO_DE_CARGA TEXT, "
                + "CREADOR_DE_CARGA TEXT);");


        db.execSQL("CREATE TABLE VEHICULOS (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "PLACA TEXT UNIQUE, " // Aquí se define la placa como única
                + "MARCA TEXT, "
                + "MODELO TEXT, "
                + "COLOR TEXT);");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes manejar la actualización de la versión de la base de datos si es necesario
    }

    public void insertUser(SQLiteDatabase db, String nombre, String email, String password, String tipoUsuario) {
        ContentValues values = new ContentValues();
        values.put("NOMBRE", nombre);
        values.put("EMAIL", email);
        values.put("PASSWORD", password);
        values.put("TIPO_USUARIO", tipoUsuario);
        db.insert("USUARIOS", null, values);
        db.close();
    }

    public void insertCarga(String nombreDeCarga, int peso, String ciudadOrigen, String ciudadDestino, String estado, String creadorCarga) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE_DE_CARGA", nombreDeCarga);
        values.put("PESO", peso);
        values.put("CIUDAD_ORIGEN", ciudadOrigen);
        values.put("CIUDAD_DESTINO", ciudadDestino);
        values.put("ESTADO_DE_CARGA", estado);
        values.put("CREADOR_DE_CARGA", creadorCarga); // Nuevo campo para el creador de la carga
        db.insert("CARGAS", null, values);
        db.close();

    }


    public void eliminarVehiculo(Vehiculo vehiculo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("VEHICULOS", "_id=?", new String[]{String.valueOf(vehiculo.getId())});
        db.close();
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("USUARIOS", // Tabla
                new String[]{"_id", "NOMBRE", "EMAIL", "PASSWORD", "TIPO_USUARIO"}, // Columnas
                "EMAIL=? AND PASSWORD=?", // Clausula WHERE
                new String[]{email, password}, // Valores de la clausula WHERE
                null, null, null);

        boolean userExists = (cursor.getCount() > 0);
        cursor.close();
        db.close();

        return userExists;
    }

    public void insertVehiculo(String marca, String modelo, String placa, String color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MARCA", marca);
        values.put("MODELO", modelo);
        values.put("PLACA", placa);
        values.put("COLOR", color);
        db.insert("VEHICULOS", null, values);
        db.close();
    }

    public List<Vehiculo> obtenerTodosLosVehiculos() {
        List<Vehiculo> listaVehiculos = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("VEHICULOS", // Tabla de vehículos
                null, // Todas las columnas
                null, // Sin cláusula WHERE
                null, // Sin argumentos para la cláusula WHERE
                null, // Sin agrupamiento
                null, // Sin condición de agrupamiento
                null); // Sin orden

        // Iterar sobre el cursor para obtener los vehículos
        if (cursor.moveToFirst()) {
            do {
                // Obtener los datos de cada vehículo del cursor
                int vehiculoId = cursor.getInt(cursor.getColumnIndex("_id")); // Utiliza "_id" en lugar de "vehiculoId"
                String marca = cursor.getString(cursor.getColumnIndex("MARCA"));
                String modelo = cursor.getString(cursor.getColumnIndex("MODELO"));
                String placa = cursor.getString(cursor.getColumnIndex("PLACA"));
                String color = cursor.getString(cursor.getColumnIndex("COLOR"));

                // Crear un nuevo objeto Vehiculo y agregarlo a la lista
                Vehiculo vehiculo = new Vehiculo(vehiculoId, marca, modelo, placa, color);
                listaVehiculos.add(vehiculo);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        // Devolver la lista de vehículos
        return listaVehiculos;
    }

    public List<Carga> obtenerTodasLasCargas() {
        List<Carga> listaCargas = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("CARGAS", // Tabla de cargas
                null, // Todas las columnas
                null, // Sin cláusula WHERE
                null, // Sin argumentos para la cláusula WHERE
                null, // Sin agrupamiento
                null, // Sin condición de agrupamiento
                null); // Sin orden

        // Iterar sobre el cursor para obtener las cargas
        if (cursor.moveToFirst()) {
            do {
                // Obtener los datos de cada carga del cursor
                int cargaId = cursor.getInt(cursor.getColumnIndex("_id")); // Utiliza "_id" en lugar de "cargaId"
                String nombreDeCarga = cursor.getString(cursor.getColumnIndex("NOMBRE_DE_CARGA"));
                int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
                String ciudadOrigen = cursor.getString(cursor.getColumnIndex("CIUDAD_ORIGEN"));
                String ciudadDestino = cursor.getString(cursor.getColumnIndex("CIUDAD_DESTINO"));
                String estado = cursor.getString(cursor.getColumnIndex("ESTADO_DE_CARGA"));
                String creadorDeCarga = cursor.getString(cursor.getColumnIndex("CREADOR_DE_CARGA"));

                // Crear un nuevo objeto Carga y agregarlo a la lista
                Carga carga = new Carga(cargaId, nombreDeCarga, peso, ciudadOrigen, ciudadDestino, estado, creadorDeCarga);
                listaCargas.add(carga);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        // Devolver la lista de cargas
        return listaCargas;
    }

    public void eliminarCarga(Carga carga) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("CARGAS", "_id=?", new String[]{String.valueOf(carga.getId())});
        db.close();
    }

    public void actualizarCarga(Carga carga) {
        // Obtener una instancia de la base de datos en modo de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Crear un objeto ContentValues para almacenar los nuevos valores de la carga
        ContentValues values = new ContentValues();
        values.put("NOMBRE_DE_CARGA", carga.getNombreDeCarga());
        values.put("PESO", carga.getPesoEnToneladas());
        values.put("CIUDAD_ORIGEN", carga.getCiudadDeOrigen());
        values.put("CIUDAD_DESTINO", carga.getCiudadDeDestino());
        values.put("ESTADO_DE_CARGA", carga.getEstadoDeCarga());

        // Definir la cláusula WHERE para identificar la carga que se actualizará
        String whereClause = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(carga.getId())};

        // Ejecutar el método update de la base de datos para actualizar el registro
        db.update("CARGAS", values, whereClause, whereArgs);

        // Cerrar la conexión de la base de datos
        db.close();
    }



    //VALIDA USUARIO A LA HORA DE REGISTRAR
    public boolean existeUsuario(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("USUARIOS", // Tabla
                new String[]{"_id"}, // Columnas (en este caso solo necesitamos verificar si existe algún registro)
                "EMAIL=?", // Clausula WHERE
                new String[]{email}, // Valores de la clausula WHERE
                null, null, null);

        boolean existeUsuario = (cursor.getCount() > 0);
        cursor.close();
        db.close();

        return existeUsuario;
    }

    public void actualizarVehiculo(Vehiculo vehiculo) {
        // Obtener una instancia de la base de datos en modo de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Crear un objeto ContentValues para almacenar los nuevos valores del vehículo
        ContentValues values = new ContentValues();
        values.put("MARCA", vehiculo.getMarca());
        values.put("MODELO", vehiculo.getModelo());
        values.put("PLACA", vehiculo.getPlaca());
        values.put("COLOR", vehiculo.getColor());

        // Definir la cláusula WHERE para identificar el vehículo que se actualizará
        String whereClause = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(vehiculo.getId())};

        // Ejecutar el método update de la base de datos para actualizar el registro
        db.update("VEHICULOS", values, whereClause, whereArgs);

        // Cerrar la conexión de la base de datos
        db.close();
    }
    public String getUserType(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT TIPO_USUARIO FROM USUARIOS WHERE EMAIL = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        String userType = null;
        if (cursor.moveToFirst()) {
            userType = cursor.getString(cursor.getColumnIndex("TIPO_USUARIO"));
        }

        cursor.close();
        db.close();

        return userType;
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT NOMBRE FROM USUARIOS WHERE EMAIL = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex("NOMBRE"));
        }

        cursor.close();
        db.close();

        return userName;
    }


}
