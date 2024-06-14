package com.example.proyectounidad_i;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void Ejercicio1(View view) {
        startActivity(new Intent(this, Ejercicio001.class));
    }
    public void Ejercicio2(View view) {
        startActivity(new Intent(this, Ejercicio002.class));
    }

    public void Ejercicio3(View view)  {
        startActivity(new Intent(this, Ejercicio003.class));
    }
    public void acercade(View view) {
        startActivity(new Intent(this, acercade.class));
    }
    public void Salir(View view) {
        finishAffinity(); // Cierra la actividad actual y todas las actividades asociadas con la tarea.
    }
}