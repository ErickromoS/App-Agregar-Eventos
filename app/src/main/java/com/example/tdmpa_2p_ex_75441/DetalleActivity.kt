package com.example.tdmpa_2p_ex_75441

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val nombre = intent.getStringExtra("nombre")
        val lugar = intent.getStringExtra("lugar")
        val fecha = intent.getStringExtra("fecha")
        val categoria = intent.getStringExtra("categoria")

        val tvNombre = findViewById<TextView>(R.id.txtNombre)
        val tvLugar = findViewById<TextView>(R.id.txtLugarDetalle)
        val tvFecha = findViewById<TextView>(R.id.txtFechaDEtalle)
        val tvCategoria = findViewById<TextView>(R.id.txtCategoriaDetalle)

        tvNombre.text = "Nombre del evento: $nombre"
        tvLugar.text = "Lugar del evento: $lugar"
        tvFecha.text = "Fecha del evento: $fecha"
        tvCategoria.text = "Categoria del evento: $categoria"
    }
}