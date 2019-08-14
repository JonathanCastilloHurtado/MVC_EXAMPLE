package com.example.mvc_example;

public class Controller  {

    private Model model;
    private MainActivity view;

    public Controller(Model model, MainActivity view) {
        this.model = model;
        this.view = view;
    }

    public void makeApiCall(String url) {
        model.execute(url, new Model.OnResult() {
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
