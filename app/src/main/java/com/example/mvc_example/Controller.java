package com.example.mvc_example;

import com.example.mvc_example.model.Model;

public class Controller  {

    private Model model;
    private MainActivity view;

    public Controller(Model model, MainActivity view) {
        this.model = model;
        this.view = view;
    }

    public void makeApiCall() {
        final String urlEndpoint="apis/get_book.php";

        //url = http://johncastle.com.mx/
        model.execute(BuildConfig.url+urlEndpoint,new Model.OnResult() {
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
