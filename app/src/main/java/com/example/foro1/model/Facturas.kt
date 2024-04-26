package com.example.foro1.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.foro1.db.HelperDB

class Facturas(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        // TABLA FACTURAS
        val TABLE_NAME_FACTURAS = "facturas"

        // nombre de los campos de la tabla
        val COL_ID = "idFactura"
        val COL_FK_USUARIO = "fk_usuario"
        val COL_TOTAL = "total"

        // sentencia SQL para crear la tabla
        val CREATE_TABLE_FACTURAS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FACTURAS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_FK_USUARIO + " integer,"
                        + COL_TOTAL + " real,"
                        + " FOREIGN KEY (" + COL_FK_USUARIO + ") REFERENCES usuario(idUsuario))"
                )
    }

    fun generarContentValues(
        fkUsuario: Long,
        total: Double
    ): ContentValues {
        val valores = ContentValues()
        valores.put(COL_FK_USUARIO, fkUsuario)
        valores.put(COL_TOTAL, total)
        return valores
    }

    fun insertFactura(fkUsuario: Long, total: Double): Long {
        return db?.insert(TABLE_NAME_FACTURAS, null, generarContentValues(fkUsuario, total)) ?: -1
    }

    fun showAllFacturas(): Cursor? {
        val columns = arrayOf(COL_ID, COL_FK_USUARIO, COL_TOTAL)
        return db?.query(
            TABLE_NAME_FACTURAS, columns,
            null, null, null, null, null
        )
    }

    fun searchFacturasByUsuario(usuarioId: Long): Cursor? {
        val columns = arrayOf(COL_ID, COL_FK_USUARIO, COL_TOTAL)
        return db?.query(
            TABLE_NAME_FACTURAS, columns,
            "$COL_FK_USUARIO=?", arrayOf(usuarioId.toString()), null, null, null
        )
    }

}
