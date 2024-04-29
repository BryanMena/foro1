package com.example.foro1

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foro1.db.HelperDB
import com.example.foro1.model.Usuarios
import com.google.android.material.textfield.TextInputEditText

class RegUserActivity : AppCompatActivity() {

    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private lateinit var instanciaUsuarios: Usuarios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_req_user)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        instanciaUsuarios = Usuarios(this) // Inicializa la clase Usuarios

        val botonCrearCuenta = findViewById<Button>(R.id.boton_crear_cuenta)
        botonCrearCuenta.setOnClickListener {
            crearCuenta()
        }
    }

    private fun crearCuenta() {
        val nombreEditText = findViewById<TextInputEditText>(R.id.campo_reg_nombre)
        val correoEditText = findViewById<TextInputEditText>(R.id.campo_reg_correo)
        val passwordEditText = findViewById<TextInputEditText>(R.id.campo_reg_password)

        val nombre = nombreEditText.text.toString()
        val correo = correoEditText.text.toString()
        val password = passwordEditText.text.toString()

        instanciaUsuarios.insertUsuario(nombre, correo, password)
        Toast.makeText(
                this,
                "Usuario registrado correctamente",
                Toast.LENGTH_SHORT
            ).show()

        finish()
    }
}