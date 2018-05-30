package com.example.johnny.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.johnny.hangman.Play.lostStr;
import static com.example.johnny.hangman.Play.totalGamesStr;
import static com.example.johnny.hangman.Play.wonStr;
import static com.example.johnny.hangman.RequestMethod.GET;
import static com.example.johnny.hangman.Win.highScoreStr;

/**
 * Created by Johnny on 24/10/17.
 */

public class Profile extends AppCompatActivity {

    static TextView games, won, lost, highscore;
    double HS = 0.0;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        final SharedPreferences win = getSharedPreferences("Wins", Context.MODE_PRIVATE);
        final SharedPreferences lose = getSharedPreferences("Losses", Context.MODE_PRIVATE);
        final SharedPreferences totalGame = getSharedPreferences("TotalGames", Context.MODE_PRIVATE);
        int wonData = win.getInt(wonStr,0);
        int lostData = lose.getInt(lostStr,0);
        int totalGameData = totalGame.getInt(totalGamesStr, 0);

        games = (TextView) findViewById(R.id.games);
        won = (TextView) findViewById(R.id.won);
        lost = (TextView) findViewById(R.id.lost);
        highscore = (TextView) findViewById(R.id.highscore);
        pref = getSharedPreferences("PREFS", 0);
        final String usname = pref.getString("user","failed");
        System.out.println(usname);
        games.setText("Games: \n" + totalGameData);
        won.setText("Won: \n" + wonData);
        lost.setText("Lost: \n" + lostData);
        class AsyncTaskURL extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    RestClient client = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/score");

                    try {
                        client.execute(GET);
                    } catch (Exception e) {
                        System.out.println("Missing connection");
                        Toast.makeText(getApplicationContext(), "Missing connection, please try again later", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    String response = client.getResponse();
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        if(object.getString("student_Id").equals(usname)){
                            double highscore = Double.parseDouble(object.getString("score"));
                           if(highscore > HS){
                               HS = highscore;
                           }
                        }
                    }
                }
                catch (Exception e) {
                    System.out.println("Missing connection");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                highscore.setText("Highscore: \n" + HS);

            }
        }
        new AsyncTaskURL().execute();
    }

}
