package com.example.mvc_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvc_example.controllers.Controller;
import com.example.mvc_example.models.Model;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView textView;
    private ProgressDialog progressDialog;
    Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        //progress_label = Loading...
        progressDialog.setTitle(getResources().getString(R.string.progress_label));
        progressDialog.setProgress(0);
        progressDialog.setCancelable(true);

        button.setOnClickListener(this);
    }

    // Quite todas las llamadas al runOnUiThread.
    // El problema era que querías regresar la respuesta en el doInBackground de la petición y eso no es lo correcto.
    // Acuerdate que el AsyncTask tiene 3 métodos fundamentales (onPreExecute // doInBackground // onPostExecute)
    // * onPreExecute  --> Corre en el MainThread y es por si necesitas hacer validaciones antes de doInBackground
    // * doInBackground --> Thread secundario --> Peticiones / BAse de datos // Procesos pesados en 2 plano y después le pasa la respuesta al onPostExecute
    // * onPostExecute --> Corre en el MainThread y es que último que decide que hacer con la respuesta en este caso mandar llamar el callback.
    public void printResponse(final String response) {
        textView.setText(response);
        progressDialog.cancel();
    }

    public void printError(final String error) {
        textView.setText(error);
        progressDialog.cancel();
    }

    @Override
    public void onClick(View view) {
        progressDialog.show();
        controller = new Controller(new Model(), this);
        controller.makeApiCall();
    }

}

