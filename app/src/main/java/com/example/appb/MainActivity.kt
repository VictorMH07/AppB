package com.example.appb

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private val tareasUri =
        Uri.parse("content://com.example.persistenciaandroid.provider/tareas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var tareas by remember {
                mutableStateOf<List<String>>(emptyList())
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "App B - Tareas de App A"
                )

                Button(
                    onClick = {
                        tareas = leerTareas()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Leer tareas")
                }

                tareas.forEach { tarea ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = tarea,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }

    private fun leerTareas(): List<String> {

        val resultado = mutableListOf<String>()

        val cursor = contentResolver.query(
            tareasUri,
            null,
            null,
            null,
            null
        )

        cursor?.use {

            while (it.moveToNext()) {

                val titulo =
                    it.getString(
                        it.getColumnIndexOrThrow("titulo")
                    )

                val descripcion =
                    it.getString(
                        it.getColumnIndexOrThrow("descripcion")
                    )

                resultado.add(
                    "Título: $titulo\n\nDescripción: $descripcion"
                )
            }
        }

        return resultado
    }
}