package com.example.a68.firstapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String height;
    private String weigth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d("onStart");
    }

    @SuppressLint("SetTextI18n")
    public void calcular(View view) {


        EditText editTextHeight = (EditText) findViewById(R.id.et_height);
        EditText editTextWeigth = (EditText) findViewById(R.id.et_weigth);

        if (!validate(editTextHeight, editTextWeigth)){
            float altura = Float.valueOf(height)/100;
            float peso = Float.valueOf(weigth);

            float imc = peso / (altura * altura);
            Log.d("", "IMC: " + imc);
            Logger.d("", "IMC: " + imc);
            String result = "";
            if (imc < 18 ){
                result = "Peso bajo. Necesario valorar signos de desnutrición";
            }else if (imc < 25){
                result = "Normal";
            }else if (imc < 27){
                result = "Sobrepeso";
            }else if (imc < 30){
                result = "Obesidad grado I. Riesgo relativo alto para desarrollar enfermedades cardiovasculares";
            }else if (imc < 40){
                result = "Obesidad grado II. Riesgo relativo muy alto para el desarrollo de enfermedades cardiovasculares";
            }else{
                result = "Obesidad grado III Extrema o Mórbida. Riesgo relativo extremadamente alto para el desarrollo de enfermedades cardiovasculares";
            }

            ((TextView)findViewById(R.id.textView))
                    .setText("Resultado: " + result);
        }
    }

    private boolean validate(EditText editTextHeight, EditText editTextWeigth) {
        height = editTextHeight.getText().toString();
        weigth = editTextWeigth.getText().toString();
        boolean flag = false;
        if (height.isEmpty()){
            editTextHeight.requestFocus();
            editTextHeight.setError("Este campo es obligatorio");
            flag = true;
        }else if(!height.matches("\\d{2,3}")){
            editTextHeight.requestFocus();
            editTextHeight.setError("Digite un numero con una longitud minimo de 2 caracteres");
            flag = true;
        }else if (weigth.isEmpty()){
            editTextWeigth.requestFocus();
            editTextWeigth.setError("Este campo es obligatorio");
            flag = true;
        }else if(!weigth.matches("\\d{2,3}")){
            editTextWeigth.requestFocus();
            editTextWeigth.setError("Digite un numero con una longitud minimo de 2 caracteres");
            flag = true;
        }
        return flag;

    }
}
