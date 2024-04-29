package com.example.foro1.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.foro1.db.HelperDB

class Productos(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        // TABLA PRODUCTOS
        val TABLE_NAME_PRODUCTOS = "productos"

        // nombre de los campos de la tabla
        val COL_ID = "idProducto"
        val COL_NOMBRE_PRODUCTO = "nombreProducto"
        val COL_PRECIO = "precio"
        val COL_RUTA_IMAGEN = "rutaImagen"

        // sentencia SQL para crear la tabla
        val CREATE_TABLE_PRODUCTOS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PRODUCTOS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE_PRODUCTO + " varchar(100) NOT NULL,"
                        + COL_PRECIO + " real NOT NULL,"
                        + COL_RUTA_IMAGEN + " varchar(100))"
                )
    }

    fun generarContentValues(
        nombreProducto: String?,
        precio: Double
    ): ContentValues {
        val valores = ContentValues()
        valores.put(COL_NOMBRE_PRODUCTO, nombreProducto)
        valores.put(COL_PRECIO, precio)
        return valores
    }

    fun insertValuesDefault() {
        val productos = arrayOf(
            arrayOf("Manzana", 1.5),
            arrayOf("Banana", 2.0),
            arrayOf("Naranja", 1.0)
        )

        // Verificación si existen registros precargados
        val columns = arrayOf(COL_ID, COL_NOMBRE_PRODUCTO, COL_PRECIO)
        val cursor: Cursor? = db?.query(TABLE_NAME_PRODUCTOS, columns, null, null, null, null, null)

        // Validando que se ingrese la información solamente una vez, cuando se instala por primera vez la aplicación
        if (cursor == null || cursor.count <= 0) {
            // Registrando productos por defecto
            for (producto in productos) {
                val nombreProducto = producto[0] as String?  // Conversión explícita a String
                val precio = producto[1] as Double         // Conversión explícita a Double
                db?.insert(TABLE_NAME_PRODUCTOS, null, generarContentValues(nombreProducto, precio))
            }
        }
        cursor?.close()
    }

    fun showAllProductos(): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE_PRODUCTO, COL_PRECIO)
        return db?.query(
            TABLE_NAME_PRODUCTOS, columns,
            null, null, null, null, null
        )
    }

    fun searchProducto(nombreProducto: String): Cursor? {
        val columns = arrayOf(COL_ID, COL_NOMBRE_PRODUCTO, COL_PRECIO)
        return db?.query(
            TABLE_NAME_PRODUCTOS, columns,
            "$COL_NOMBRE_PRODUCTO=?", arrayOf(nombreProducto), null, null, null
        )
    }
}
