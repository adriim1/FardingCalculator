package com.adriim1.fardingcalculator;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvPantalla;
    private double primerNumero = 0;
    private String operacionActual = "";
    private boolean nuevaEntrada = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPantalla = findViewById(R.id.tvPantalla);

        int[] idBotones = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnSumar, R.id.btnRestar, R.id.btnMultiplicar, R.id.btnDividir,
                R.id.btnIgual, R.id.btnPunto, R.id.btnC
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String botonTexto = b.getText().toString();
                manejarBoton(botonTexto);
            }
        };

        for (int id : idBotones) {
            View view = findViewById(id);
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }


    private void manejarBoton(String botonTexto) {
        switch (botonTexto) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                manejarNumero(botonTexto);
                break;
            case "+": case "-": case "*": case "/":
                manejarOperacion(botonTexto);
                break;
            case "=":
                manejarIgual();
                break;
            case "C":
                manejarClear();
                break;
            case ".":
                manejarPunto();
                break;
        }
    }

    private void manejarNumero(String digito) {
        if (nuevaEntrada) {
            tvPantalla.setText("");
            nuevaEntrada = false;
        }
        if (tvPantalla.getText().toString().equals("0") && digito.equals("0")) {
            return;
        }
        if (tvPantalla.getText().toString().equals("0")) {
            tvPantalla.setText(digito);
        } else {
            tvPantalla.append(digito);
        }
    }

    private void manejarOperacion(String op) {
        if (!tvPantalla.getText().toString().isEmpty()) {
            primerNumero = Double.parseDouble(tvPantalla.getText().toString());
            operacionActual = op;
            nuevaEntrada = true;
        }
    }

    private void manejarIgual() {
        if (!operacionActual.isEmpty() && !tvPantalla.getText().toString().isEmpty()) {
            double segundoNumero = Double.parseDouble(tvPantalla.getText().toString());
            double resultado = calcular(primerNumero, segundoNumero, operacionActual);

            if (resultado == (long) resultado) {
                tvPantalla.setText(String.format("%d", (long) resultado));
            } else {
                tvPantalla.setText(String.valueOf(resultado));
            }

            primerNumero = resultado;
            operacionActual = "";
            nuevaEntrada = true;
        }
    }

    private void manejarClear() {
        tvPantalla.setText("0");
        primerNumero = 0;
        operacionActual = "";
        nuevaEntrada = true;
    }

    private void manejarPunto() {
        if (nuevaEntrada) {
            tvPantalla.setText("0.");
            nuevaEntrada = false;
        } else if (!tvPantalla.getText().toString().contains(".")) {
            tvPantalla.append(".");
        }
    }

    private double calcular(double num1, double num2, String op) {
        switch (op) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/":
                if (num2 == 0) {
                    Toast.makeText(this, "Error: División por cero", Toast.LENGTH_LONG).show();
                    return 0;
                }
                return num1 / num2;
            default: return 0;
        }
    }
}