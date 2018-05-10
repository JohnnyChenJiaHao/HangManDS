package com.example.johnny.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONStringer;

import static com.example.johnny.hangman.RequestMethod.POST;

/**
 * Created by Johnny on 24/10/17.
 */

public class Login extends AppCompatActivity {

    EditText username, password;
    Button OK, FPW;
    boolean auth = false;
    public static String usn;
    static SharedPreferences pref;
    static SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        OK = (Button) findViewById(R.id.login);
        FPW = (Button) findViewById(R.id.ForgotPassword);


        class AsyncTaskURL extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    JSONObject logininfo = new JSONObject();
                    String un = username.getText().toString();
                    String pw = password.getText().toString();


                    RestClient client = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/login/" );
                    String inputJsonString = "{" +
                            "    \"username\"          :" + un +
                            "," +
                            "    \"password\"          : " + pw +
                            "}";

                    System.out.println(inputJsonString);

                    client.setJSONString(inputJsonString);
                    client.addHeader("Content-Type", "appication/json"); // if required

                    try {
                        client.execute(RequestMethod.POST);
                        if (client.getResponseCode() == 200) {
                            auth = true;
                        }
                    } catch (Exception e) {
                        // handle error
                    }
                    pref = getSharedPreferences(usn, Context.MODE_PRIVATE);
                    edit = pref.edit();
                    edit.putString("usname", usn);
                    edit.commit();

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
                    Toast.makeText(Login.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                }

            }
        }


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTaskURL().execute();
            }
        });

        FPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPass.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
