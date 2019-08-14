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
        textView = findViewById(R.id.textView);
        //progress_label = Loading...
        progressDialog.setTitle(getResources().getString(R.string.progress_label));
        progressDialog.setProgress(0);
        progressDialog.setCancelable(true);

        button.setOnClickListener(this);
    }

    public void printResponse(final String response) {
        runOnUiThread(new Runnable() {
            public void run() {
                textView.setText(response);
                progressDialog.cancel();
            }
        });
    }

    public void printError(final String error) {
        runOnUiThread(new Runnable() {
            public void run() {
                textView.setText(error);
                progressDialog.cancel();
            }
        });
    }

    @Override
    public void onClick(View view) {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog.show();
            }
        });
        controller = new Controller(new Model(), this);
        //url = http://cardfindercdmx.com/personal/get_book.php
        controller.makeApiCall(BuildConfig.url);
    }

}

