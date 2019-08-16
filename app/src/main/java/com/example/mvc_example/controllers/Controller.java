package com.example.mvc_example.controllers;

import com.example.mvc_example.MainActivity;
import com.example.mvc_example.models.Model;

public class Controller  {

    private Model model;
    private MainActivity view;

    public Controller(Model model, MainActivity view) {
        this.model = model;
        this.view = view;
    }

    public void makeApiCall() {
        model.execute(new Model.OnResult() {
                    @Override
                    public void onSuccess(String result) {
                        view.printResponse(result);
                    }
                    @Override
                    public void onError(Exception error) {
                      prepareError(error);
                    }
                }
        );
    }

    public void prepareError(Exception error){
        view.printError(error.toString());
    }

}
