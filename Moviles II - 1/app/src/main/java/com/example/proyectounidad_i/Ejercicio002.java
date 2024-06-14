package com.example.proyectounidad_i;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Ejercicio002 extends AppCompatActivity {

    // Variables para almacenar operandos y operación actual
    private StringBuilder operand = new StringBuilder();
    private double operand1 = Double.NaN;
    private double operand2 = Double.NaN;
    private char operation = ' ';

    // Componentes de la interfaz
    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio002);

        // Vinculación de componentes
        display = findViewById(R.id.display);

        // Asignar listeners a los botones de la calculadora
        setupCalculatorButtons();
    }

    private void setupCalculatorButtons() {
        // Array con los IDs de los botones numéricos y operadores
        int[] buttonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
                R.id.btn_8, R.id.btn_9, R.id.btn_add, R.id.btn_sub,
                R.id.btn_mul, R.id.btn_div, R.id.btn_dot,
                R.id.btn_eq
        };

        // Asignar OnClickListener a cada botón
        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick(v);
                }
            });
        }
    }

    // Método para manejar el clic de los botones
    private void onButtonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "+":
            case "-":
            case "*":
            case "/":
                // Al presionar un operador, evaluar la operación pendiente
                if (!operand.toString().isEmpty()) {
                    operand1 = Double.parseDouble(operand.toString());
                    operation = buttonText.charAt(0);
                    operand.setLength(0); // Limpiar el StringBuilder para el siguiente operando
                }
                break;
            case "=":
                // Al presionar "=", realizar la operación pendiente
                if (!Double.isNaN(operand1) && !operand.toString().isEmpty()) {
                    operand2 = Double.parseDouble(operand.toString());
                    double result = performOperation(operand1, operand2, operation);
                    display.setText(String.valueOf(result));
                    operand.setLength(0); // Limpiar el StringBuilder
                    operand1 = result;
                    operand2 = Double.NaN;
                    operation = ' ';
                }
                break;
            case "CC":
                // Al presionar "CC", borrar el último dígito
                if (operand.length() > 0) {
                    operand.deleteCharAt(operand.length() - 1);
                    display.setText(operand.toString()); // Actualizar el display en tiempo real
                }
                break;
            case "<":
                // Al presionar "<", limpiar todo el operando actual
                operand.setLength(0);
                display.setText(""); // Limpiar el display
                break;
            default:
                // Agregar dígitos o "." al operando actual
                operand.append(buttonText);
                display.setText(operand.toString()); // Actualizar el display en tiempo real
                break;
        }
    }

    // Método para realizar la operación matemática
    private double performOperation(double operand1, double operand2, char operation) {
        switch (operation) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    // División por cero
                    return Double.NaN;
                }
            default:
                return Double.NaN; // Operación desconocida
        }
    }

    public void BtnAtras(View view) {
        startActivity(new Intent(this, menu.class));
    }
}

