package com.example.proyectounidad_i;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Ejercicio001 extends AppCompatActivity {

    private EditText etDni, etNombre, etApellido, etEdad, etFechaNacimiento;
    private RadioGroup rgGenero;
    private Spinner spLugarNacimiento;
    private CheckBox cbRock, cbPop, cbKpop, cbElectronica, cbDonacionOrganos;
    private Button btnEnviar;
    private ImageButton btnBack;

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ejercicio001);

        // Ajuste de Padding para el Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar las vistas
        etDni = findViewById(R.id.et_dni);
        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etEdad = findViewById(R.id.et_edad);
        etFechaNacimiento = findViewById(R.id.et_fecha_nacimiento);

        rgGenero = findViewById(R.id.rg_genero);
        spLugarNacimiento = findViewById(R.id.sp_lugar_nacimiento);

        cbRock = findViewById(R.id.cb_rock);
        cbPop = findViewById(R.id.cb_pop);
        cbKpop = findViewById(R.id.cb_kpop);
        cbElectronica = findViewById(R.id.cb_electronica);
        cbDonacionOrganos = findViewById(R.id.cb_donacion_organos);

        btnEnviar = findViewById(R.id.btn_enviar);
        btnBack = findViewById(R.id.btn_back);

        // Configurar Spinner
        String[] regiones = getResources().getStringArray(R.array.regiones_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, regiones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLugarNacimiento.setAdapter(adapter);

        // Acción del botón enviar
        btnEnviar.setOnClickListener(v -> {
            if (validarCampos()) {
                mostrarDatos();
            }
        });

        // Acción del botón atrás
        btnBack.setOnClickListener(v -> finish());

        // Solicitar permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean validarCampos() {
        if (TextUtils.isEmpty(etDni.getText().toString())) {
            etDni.setError("El DNI es requerido");
            return false;
        }
        if (TextUtils.isEmpty(etNombre.getText().toString())) {
            etNombre.setError("El nombre es requerido");
            return false;
        }
        if (TextUtils.isEmpty(etApellido.getText().toString())) {
            etApellido.setError("El apellido es requerido");
            return false;
        }
        if (TextUtils.isEmpty(etEdad.getText().toString())) {
            etEdad.setError("La edad es requerida");
            return false;
        }
        if (TextUtils.isEmpty(etFechaNacimiento.getText().toString())) {
            etFechaNacimiento.setError("La fecha de nacimiento es requerida");
            return false;
        }
        if (rgGenero.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Seleccione un género", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (spLugarNacimiento.getSelectedItemPosition() == 0) { // Suponiendo que el primer item es un "Seleccione..."
            Toast.makeText(this, "Seleccione un lugar de nacimiento", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void mostrarDatos() {
        StringBuilder datos = new StringBuilder();

        datos.append("DNI: ").append(etDni.getText().toString()).append("\n");
        datos.append("Nombre: ").append(etNombre.getText().toString()).append("\n");
        datos.append("Apellido: ").append(etApellido.getText().toString()).append("\n");
        datos.append("Edad: ").append(etEdad.getText().toString()).append("\n");
        datos.append("Fecha de Nacimiento: ").append(etFechaNacimiento.getText().toString()).append("\n");

        int selectedGeneroId = rgGenero.getCheckedRadioButtonId();
        RadioButton selectedGenero = findViewById(selectedGeneroId);
        datos.append("Género: ").append(selectedGenero.getText().toString()).append("\n");

        datos.append("Lugar de Nacimiento: ").append(spLugarNacimiento.getSelectedItem().toString()).append("\n");

        ArrayList<String> preferencias = new ArrayList<>();
        if (cbRock.isChecked()) preferencias.add("Rock");
        if (cbPop.isChecked()) preferencias.add("Pop");
        if (cbKpop.isChecked()) preferencias.add("K-pop");
        if (cbElectronica.isChecked()) preferencias.add("Electrónica");
        datos.append("Preferencias Musicales: ").append(TextUtils.join(", ", preferencias)).append("\n");

        if (cbDonacionOrganos.isChecked()) {
            datos.append("Donación de Órganos: Sí\n");
        } else {
            datos.append("Donación de Órganos: No\n");
        }

        // Guardar datos en un archivo
        guardarDatosEnArchivo(datos.toString());

        // Mostrar los datos en un AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Datos Ingresados")
                .setMessage(datos.toString())
                .setPositiveButton("OK", (dialog, which) -> {
                    limpiarCampos();
                })
                .show();
    }

    private void guardarDatosEnArchivo(String datos) {
        String estado = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(estado)) {
            File directorio = new File(Environment.getExternalStorageDirectory(), "DatosEjercicio001");
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            File archivo = new File(directorio, "datos.txt");
            try {
                FileWriter writer = new FileWriter(archivo, true);
                writer.append(datos);
                writer.append("\n");
                writer.flush();
                writer.close();
                Toast.makeText(this, "Datos guardados en datos.txt", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se puede acceder al almacenamiento externo", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        etDni.setText("");
        etNombre.setText("");
        etApellido.setText("");
        etEdad.setText("");
        etFechaNacimiento.setText("");
        rgGenero.clearCheck();
        spLugarNacimiento.setSelection(0);
        cbRock.setChecked(false);
        cbPop.setChecked(false);
        cbKpop.setChecked(false);
        cbElectronica.setChecked(false);
        cbDonacionOrganos.setChecked(false);
    }
}
