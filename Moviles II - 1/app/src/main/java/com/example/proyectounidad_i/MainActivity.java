package com.example.proyectounidad_i;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;

    // Credenciales del administrador
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                // Inicio de sesión exitoso, dirigir a la actividad del menú
                Intent intent = new Intent(MainActivity.this, menu.class);
                startActivity(intent);
                finish(); // Esto finalizará la actividad actual para que no se pueda volver atrás desde la actividad del menú.
            } else {
                // Si las credenciales son incorrectas, muestra un mensaje de error
                Toast.makeText(MainActivity.this, "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
