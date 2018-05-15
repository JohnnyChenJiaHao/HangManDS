package com.example.johnny.hangman;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import static com.example.johnny.hangman.RequestMethod.POST;

public class ForgotPass extends AppCompatActivity {
    Button OK;
    EditText Studienummer;
    boolean auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        OK = (Button) findViewById(R.id.OK);
        Studienummer = (EditText) findViewById(R.id.Studienummer);

        class AsyncTaskURL extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {

                    String studienr = Studienummer.getText().toString();
                    RestClient client = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/email");

                    String inputJsonString = "{" +
                            "    \"student\"          :" + studienr +
                            "}";
                    System.out.println(inputJsonString);
                    client.setJSONString(inputJsonString);
                    client.addHeader("Content-Type", "appication/json"); // if required
                    client.execute(POST);
                    System.out.println(client.getResponse());

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Missing connection");
                    Toast.makeText(getApplicationContext(), "Missing connection, please try again later", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                Toast.makeText(ForgotPass.this, "An email has been sent to your email with a new password. Please login again.", Toast.LENGTH_LONG).show();
                ;
                Intent intent = new Intent(ForgotPass.this, Login.class);
                startActivity(intent);
                finish();
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
