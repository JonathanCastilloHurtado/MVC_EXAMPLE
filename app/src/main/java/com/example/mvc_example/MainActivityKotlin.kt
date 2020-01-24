package com.example.mvc_example

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivityKotlin : AppCompatActivity(), View.OnClickListener {

    private lateinit var button: Button
    private lateinit var textView: TextView
    lateinit var progressDialog:ProgressDialog
    lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)

        progressDialog = ProgressDialog(this)

        button = findViewById(R.id.button) as Button
        textView = findViewById(R.id.textView) as TextView
        //progress_label = Loading...
        progressDialog.setTitle(resources.getString(R.string.progress_label))
        progressDialog.setProgress(0)
        progressDialog.setCancelable(false)


        button.setOnClickListener(this)

    }

    fun printResponse(response: String?) {
        textView!!.text = response
        progressDialog!!.cancel()
    }

    fun printError(error: String?) {
        textView!!.text = error
        progressDialog!!.cancel()
    }

    override fun onClick(view: View?) {
        progressDialog!!.show()
        controller = Controller(Model(), this)
        controller.makeApiCall()
    }
}
