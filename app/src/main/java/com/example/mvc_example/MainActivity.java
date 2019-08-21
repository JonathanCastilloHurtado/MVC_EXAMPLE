package com.example.mvc_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mvc_example.model.Model;

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

