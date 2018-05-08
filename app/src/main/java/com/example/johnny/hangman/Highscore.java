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
    Button Home;
    ListView hs;
    ArrayList<User> data;
    User user1 = new User("", "","","");
    User user2 = new User("", "","","");
    User user3 = new User("", "","","");
    User user4 = new User("", "","","");
    User user5 = new User("", "","","");
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);

        Home = (Button) findViewById(R.id.Home);
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
                    data.add(user1);
                    data.add(user2);
                    data.add(user3);
                    data.add(user4);
                    data.add(user5);

                    for(int i = 0; i < jsonArray.length(); i++){
                         JSONObject object = jsonArray.getJSONObject(i);


                        for(int j = 0; j < data.size(); j++){
                            data.get(j).setStudent_Id(object.getString("student_Id"));
                            data.get(j).setScore(object.getString("score"));
                            data.get(j).setNumberOfTries(object.getString("number_of_tries"));
                            data.get(j).setTime_used(object.getString("time_used"));
                            data.get(j).toString();
                        }
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


        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Highscore.this, MainMenu.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
