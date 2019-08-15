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
        //Quizá aquí lo que debería hacer el controller sería agregar parámetros a las petición
		//Al final el controller sería como un intermediarion, pero en este caso, no se me ocurre que puede mandar
		//quizá algún token o algo que necesite el modelo para hacer la petición.

		//Pero no es necesario en este momento es transparente, por eso parece que el controller no hace nada.
        model.execute(new Model.OnResult() {
                    @Override
                    public void onSuccess(String result) {
                        view.printResponse(result);
                    }

                    @Override
                    public void onError(String error) {
                        view.printError(error);
                    }
                }
        );
    }

}
