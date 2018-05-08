package com.example.johnny.hangman;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Johnny on 24/10/17.
 */

public class Login extends AppCompatActivity {

    EditText username, password;
    Button OK;
    boolean auth = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        OK = (Button) findViewById(R.id.login);


        class AsyncTaskURL extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    String un = username.getText().toString();
                    System.out.println(un);
                    String pw = password.getText().toString();
                    System.out.println(pw);
                    RestClient client = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/service/validate/" + un + "/" + pw);


                    try {
                        client.Execute(RestClient.RequestMethod.GET);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String response = client.getResponse();
                    if(response != null)
                        auth = true;
                    System.out.println(response);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if(auth) {
                    Intent intent = new Intent(Login.this, MainMenu.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "Forkert brugernavn/kodeord", Toast.LENGTH_LONG).show();
                }
            }
        }


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTaskURL().execute();
            }
        });

    }
}
