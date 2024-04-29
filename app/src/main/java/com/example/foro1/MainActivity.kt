package com.example.foro1

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foro1.db.HelperDB
import com.example.foro1.model.Usuarios

class MainActivity : AppCompatActivity() {

    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Conexion a base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        // Insertar usuarios por defecto
        val instanciaUsuarios = Usuarios(this)
        instanciaUsuarios.insertValuesDefault()
    }
}