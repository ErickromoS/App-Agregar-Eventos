package com.example.tdmpa_2p_ex_75441

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.chip.ChipGroup
import com.google.android.material.chip.Chip
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

private val listaEventos = mutableListOf<Evento>()
private lateinit var chgEventos: ChipGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txtnombreEvento = findViewById<EditText>(R.id.txtNombreEvento)
        val txtlugarEvento = findViewById<EditText>(R.id.txtLugar)
        val txtfechaEvento = findViewById<EditText>(R.id.txtDateEvento)
        val txtdetalleEvento = findViewById<EditText>(R.id.txtDescricionEvento)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val chipGroupCategorias = findViewById<ChipGroup>(R.id.chgCategoria)
        val spinFiltro  =findViewById<Spinner>(R.id.spinFiltro)
        chgEventos = findViewById<ChipGroup>(R.id.chgEventos)

        val categorias = listOf("Concierto","Stand up", "Conferencia")
        categorias.forEach{ categoria ->
            val chip = Chip(this)
            chip.text = categoria
            chip.isCheckable = true
            chipGroupCategorias.addView((chip))
        }

        val opcionesSpinner = resources.getStringArray(R.array.filtro)

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item,opcionesSpinner )

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinFiltro.adapter = adaptador

        btnAgregar.setOnClickListener{
            val nombre = txtnombreEvento.text.toString()
            val lugar = txtlugarEvento.text.toString()
            val fecha = txtfechaEvento.text.toString()
            val detalle = txtdetalleEvento.text.toString()
            val categoria = chipGroupCategorias.checkedChipIds.map { id ->
                chipGroupCategorias.findViewById<Chip>(id)?.text.toString()
            }.firstOrNull()?:""

            if (nombre.isBlank() || lugar.isBlank() ||fecha.isBlank() || detalle.isBlank() || categoria.isBlank()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val evento = Evento(nombre, lugar, fecha, detalle, categoria)
            listaEventos.add(evento)

            val chip = Chip(this)
            chip.text = nombre
            chip.isCheckable = true
            chip.setOnClickListener {
                val intent = Intent(this, DetalleActivity::class.java)
                intent.putExtra("nombre", nombre)
                intent.putExtra("lugar", lugar)
                intent.putExtra("fecha", fecha)
                intent.putExtra("categoria", categoria)
                startActivity(intent)
            }

            chgEventos.addView(chip)

            // Limpiamos los campos
            txtnombreEvento.text.clear()
            txtlugarEvento.text.clear()
            txtfechaEvento.text.clear()
            txtdetalleEvento.text.clear()


        }

        spinFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                actualizarListaEventos(spinFiltro.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun actualizarListaEventos(filtro: String) {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = formato.parse(formato.format(Date()))

        chgEventos.removeAllViews()

        listaEventos.forEach { evento ->
            val fechaEvento = formato.parse(evento.fecha)
            when (filtro) {
                "Todos" -> true
                "Próximos" -> fechaEvento?.after(fechaActual) ?: false
                "Pasados" -> fechaEvento?.before(fechaActual) ?: false
                else -> false
            }.let { mostrar ->
                if (mostrar) {
                    val chip = Chip(this)
                    chip.text = evento.nombre
                    chip.isClickable = true
                    chip.setOnClickListener {
                        val intent = Intent(this, DetalleActivity::class.java)
                        intent.putExtra("nombre", evento.nombre)
                        intent.putExtra("lugar", evento.lugar)
                        intent.putExtra("fecha", evento.fecha)
                        intent.putExtra("categoria", evento.categoria)
                        startActivity(intent)
                    }
                    chgEventos.addView(chip)

                }
            }
        }
    }
}

data class Evento(
    val  nombre: String,
    val  lugar: String,
    val  fecha: String,
    val  detalle: String,
    val  categoria: String
)