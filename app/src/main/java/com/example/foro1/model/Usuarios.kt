package com.example.foro1.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.foro1.db.HelperDB

class Usuarios(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        //TABLA USUARIOS
        val TABLE_NAME_USUARIOS = "usuarios"

        //nombre de los campos de la tabla
        val COL_ID = "idUsuario"
        val COL_NOMBRE = "nombre"
        val COL_USUARIO = "usuario"
        val COL_PASSWORD = "password"

        //sentencia SQL para crear la tabla
        val CREATE_TABLE_USUARIOS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_USUARIOS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(50) NOT NULL,"
                        + COL_USUARIO + " varchar(50) UNIQUE NOT NULL,"
                        + COL_PASSWORD + " varchar(50) NOT NULL)"
                )
    }

    fun generarContentValues(
        nombre: String?,
        usuario: String?,
        password: String?
    ): ContentValues {
        val valores = ContentValues()
        valores.put(COL_NOMBRE, nombre)
        valores.put(COL_USUARIO, usuario)
        valores.put(COL_PASSWORD, password)
        return valores
    }

    fun insertValuesDefault() {
        val usuarios = arrayOf(
            arrayOf("Usuario1", "usuario1", "123456"),
        )

        // Verificación si existen registros precargados
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_USUARIO, COL_PASSWORD)
        val cursor: Cursor? = db?.query(TABLE_NAME_USUARIOS, columns, null, null, null, null, null)

        // Validando que se ingrese la información solamente una vez, cuando se instala por primera vez la aplicación
        if (cursor == null || cursor.count <= 0) {
            // Registrando usuarios por defecto
            for (usuario in usuarios) {
                db?.insert(TABLE_NAME_USUARIOS, null, generarContentValues(usuario[0], usuario[1], usuario[2]))
            }
        }
        cursor?.close()
    }

    fun showAllUsuarios(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_USUARIO, COL_PASSWORD)
        return db?.query(
            TABLE_NAME_USUARIOS, columns,
            null, null, null, null, null
        )
    }

    fun searchUsuario(nombreUsuario: String, password: String): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE, COL_USUARIO, COL_PASSWORD)
        return db?.query(
            TABLE_NAME_USUARIOS, columns,
            "$COL_USUARIO=? AND $COL_PASSWORD=?", arrayOf(nombreUsuario, password), null, null, null
        )
    }

    fun insertUsuario(nombre: String?, usuario: String?, password: String?) {
        db?.insert(TABLE_NAME_USUARIOS, null, generarContentValues(nombre,usuario,password))
    }

}
