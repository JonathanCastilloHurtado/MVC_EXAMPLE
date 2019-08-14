package com.example.mvc_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;
    TextView textView;
    Controller controller;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.texto);

        model = new Model();
        view = new MainActivity();
        controller = new Controller(model, view);

        progressDialog.setTitle("Loading...");
        progressDialog.setProgress(0);
        progressDialog.setCancelable(true);

        button.setOnClickListener(this);
    }
    MainActivity view;
    Model model;


    public void printResponse(final String response) {
        Log.d("JOHN","print response : "+response);
        runOnUiThread(new Runnable() {
            public void run() {
                Log.d("JOHN","TERMINA");
               // textView.setText(response);
               //progressDialog.cancel();
            }
        });
    }

    @Override
    public void onClick(View view) {
        runOnUiThread(new Runnable() {
            public void run() {
                Log.d("JOHN","COMIENZA");
                //progressDialog.show();
            }
        });
        controller.makeApiCall("http://cardfindercdmx.com/personal/get_book.php");
    }
}

