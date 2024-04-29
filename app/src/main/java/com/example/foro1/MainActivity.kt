package com.example.foro1

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foro1.db.HelperDB
import com.example.foro1.model.Usuarios
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private lateinit var instanciaUsuarios: Usuarios

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
        instanciaUsuarios = Usuarios(this)
        instanciaUsuarios.insertValuesDefault()

        val botonLogin = findViewById<Button>(R.id.boton_login)
        botonLogin.setOnClickListener {
            login()
        }

        val registrateTextView = findViewById<TextView>(R.id.texto_registrate)
        registrateTextView.setOnClickListener {
            val intent = Intent(this, RegUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val emailEditText = findViewById<TextInputEditText>(R.id.campo_email)
        val passwordEditText = findViewById<TextInputEditText>(R.id.campo_password)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (usuarioValido(email, password)) {
            // Usuario valido
            Toast.makeText(this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)

        } else {
            // Usuario invalido
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun usuarioValido(email: String, password: String): Boolean {
        val cursor = instanciaUsuarios.searchUsuario(email, password)
        val usuarioExiste = (cursor?.count ?: 0) > 0
        cursor?.close()
        return usuarioExiste
    }
}