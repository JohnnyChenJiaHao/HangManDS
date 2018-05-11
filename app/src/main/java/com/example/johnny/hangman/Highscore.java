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

import static com.example.johnny.hangman.RequestMethod.GET;

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
                    RestClient client = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/score");

                    try {
                        client.execute(GET);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String response = client.getResponse();
                    JSONArray jsonArray = new JSONArray(response);
                    data = new ArrayList<User>();
                    User user1 = new User("placeholder","1231","2","30");
                    User user2 = new User("placeholder","1212","2","30");
                    User user3 = new User("placeholder","12323","231","232");
                    User user4 = new User("Placeholder","2312","12","231");
                    User user5 = new User("Placeholder","Score: 1232","Tries: 132","Time: 22");
                    data.add(user1);
                    data.add(user2);
                    data.add(user3);
                    data.add(user4);
                    data.add(user5);



                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        data.get(i).setScore(object.getString("score"));
                        data.get(i).setStudent_Id(object.getString("student_Id"));
                        data.get(i).setTime_used(object.getString("time_used"));
                        data.get(i).setNumberOfTries(object.getString("number_of_tries"));

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
