package com.example.foro1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.foro1.model.Usuarios
import com.example.foro1.model.Productos
import com.example.foro1.model.Facturas
import com.example.foro1.model.Detalle_Factura

class HelperDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "foro1.sqlite"
        private const val DB_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Usuarios.CREATE_TABLE_USUARIOS)
        db.execSQL(Productos.CREATE_TABLE_PRODUCTOS)
        db.execSQL(Facturas.CREATE_TABLE_FACTURAS)
        db.execSQL(Detalle_Factura.CREATE_TABLE_DETALLE_FACTURA)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

}