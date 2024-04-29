package com.example.foro1

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foro1.db.HelperDB
import com.example.foro1.model.Productos

class ProductosActivity : AppCompatActivity() {

    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_productos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botonCerrarSesion = findViewById<Button>(R.id.boton_cerrar_sesion)
        botonCerrarSesion.setOnClickListener {
            finish()
        }

        // Conexion a base de datos
        dbHelper = HelperDB(this)
        db = dbHelper!!.writableDatabase

        // Insertar productos por defecto
        val instanciaProductos = Productos(this)
        instanciaProductos.insertValuesDefault()

        // Traer productos de la base de datos
        val cursorProductos = instanciaProductos.showAllProductos()

        val contenedor = findViewById<LinearLayout>(R.id.contenedor)

        cursorProductos?.let {
            var layoutHorizontal: LinearLayout? = null
            var contadorProductos = 0

            while (it.moveToNext()) {
                if (contadorProductos % 2 == 0) {
                    layoutHorizontal = LinearLayout(this).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        orientation = LinearLayout.HORIZONTAL
                    }
                    contenedor.addView(layoutHorizontal)
                }

                val id = it.getInt(it.getColumnIndex(Productos.COL_ID))
                val nombre = it.getString(it.getColumnIndex(Productos.COL_NOMBRE_PRODUCTO))
                val precio = it.getDouble(it.getColumnIndex(Productos.COL_PRECIO))
                val rutaImagen = it.getString(it.getColumnIndex(Productos.COL_RUTA_IMAGEN))
                val producto = Productos.Producto(id, nombre, precio, rutaImagen)

                // imprimir en consola
                Log.d("MainActivity", producto.toString())
                val cardView = createProductCard(producto)

                layoutHorizontal?.addView(cardView)
                contadorProductos++
            }
            it.close()
        }
    }

    private fun createProductCard(producto: Productos.Producto): CardView {
        return CardView(this).apply {
            layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    setMargins(8, 8, 8, 8)

                }
            cardElevation = 4f
            radius = 8f

            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(16, 16, 16, 16)

                addView(TextView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    text = producto.nombre
                    textSize = 18f
                    gravity = android.view.Gravity.CENTER
                })

                addView(ImageView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        400
                    )
                    setImageResource(R.drawable.manzana)
                    scaleType = ImageView.ScaleType.CENTER_CROP
                })

                addView(TextView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    text = "$" + producto.precio.toString()
                    textSize = 18f
                    gravity = android.view.Gravity.CENTER
                })

                addView(Button(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 8, 0, 0)
                    }
                    text = "Añadir al carrito"
                })
            })
        }
    }

}