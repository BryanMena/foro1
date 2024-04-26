package com.example.foro1.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.foro1.db.HelperDB

class Detalle_Factura(context: Context?) {

    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.writableDatabase
    }

    companion object {
        // TABLA DETALLE FACTURA
        val TABLE_NAME_DETALLE_FACTURA = "detalle_factura"

        // nombre de los campos de la tabla
        val COL_ID = "idDetalle"
        val COL_FK_FACTURA = "FK_Factura"
        val COL_FK_PRODUCTO = "FK_Producto"
        val COL_CANTIDAD = "Cantidad"
        val COL_TOTAL = "Total"

        // sentencia SQL para crear la tabla
        val CREATE_TABLE_DETALLE_FACTURA = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_DETALLE_FACTURA + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_FK_FACTURA + " integer,"
                        + COL_FK_PRODUCTO + " integer,"
                        + COL_CANTIDAD + " integer,"
                        + COL_TOTAL + " real,"
                        + " FOREIGN KEY (" + COL_FK_FACTURA + ") REFERENCES facturas(idFactura),"
                        + " FOREIGN KEY (" + COL_FK_PRODUCTO + ") REFERENCES productos(idProducto))"
                )
    }

    fun generarContentValues(
        fkFactura: Long,
        fkProducto: Long,
        cantidad: Int,
        total: Double
    ): ContentValues {
        val valores = ContentValues()
        valores.put(COL_FK_FACTURA, fkFactura)
        valores.put(COL_FK_PRODUCTO, fkProducto)
        valores.put(COL_CANTIDAD, cantidad)
        valores.put(COL_TOTAL, total)
        return valores
    }

    fun insertDetalleFactura(fkFactura: Long, fkProducto: Long, cantidad: Int, total: Double): Long {
        return db?.insert(TABLE_NAME_DETALLE_FACTURA, null, generarContentValues(fkFactura, fkProducto, cantidad, total)) ?: -1
    }

    fun showAllDetallesFactura(): Cursor? {
        val columns = arrayOf(COL_ID, COL_FK_FACTURA, COL_FK_PRODUCTO, COL_CANTIDAD, COL_TOTAL)
        return db?.query(
            TABLE_NAME_DETALLE_FACTURA, columns,
            null, null, null, null, null
        )
    }

}
