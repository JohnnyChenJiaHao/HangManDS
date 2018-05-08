package com.example.johnny.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sammy on 08-05-2018.
 */

public class Highscore extends AppCompatActivity {
    Button menu;
    ListView hs;
    ArrayList<User> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);

        menu = (Button) findViewById(R.id.menu);
        hs = (ListView) findViewById(R.id.lv);



        class AsyncTaskURL extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    RestClient client = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/service/score");
                    try {
                        client.Execute(RestClient.RequestMethod.GET);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String response = client.getResponse();
                    JSONArray jsonArray = new JSONArray(response);
                    data = new ArrayList<User>();

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        User user = new User("", "","","");
                        user.setStudent_Id(object.getString("student_Id"));
                        user.setScore(object.getString("score"));
                        user.setNumberOfTries(object.getString("number_of_tries"));
                        user.setTime_used(object.getString("time_used"));
                        data.add(user);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                UserListAdapter adapter = new UserListAdapter(getApplicationContext(), R.layout.postlist, data);
                hs.setAdapter(adapter);

            }
        }
        new AsyncTaskURL().execute();


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Highscore.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
