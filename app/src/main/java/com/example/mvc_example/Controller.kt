package com.example.mvc_example


class Controller(val model: Model, val view : MainActivityKotlin ) {

    fun makeApiCall() {
        val urlEndpoint = "apis/get_book.php"
        //url = http://johncastle.com.mx/
        model.execute(BuildConfig.url + urlEndpoint, object : Model.OnResult {
            override fun onSuccess(result: String?) {
                view.printResponse(result)
            }

            override fun onError(error: Exception?) {
                if (error != null) {
                    prepareError(error)
                }
            }

        }
        )
    }

    fun prepareError(error: Exception) {
        view.printError(error.toString())
    }

}