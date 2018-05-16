package com.example.johnny.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import static com.example.johnny.hangman.Play.spil;

/**
 * Created by Johnny on 12/11/17.
 */

public class Win extends AppCompatActivity implements View.OnClickListener {

    int currentScore;

    static String highScoreStr;

    TextView word, nrWrong, time, score;

    Button menu, playAgain;

    String finalWord, auth, username;
    int numOfTries;
    double scoreDouble, timeInSeconds;

    SharedPreferences pref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);

        word = (TextView) findViewById(R.id.word);
        nrWrong = (TextView) findViewById(R.id.nrWrong);
        time = (TextView) findViewById(R.id.time);
        score = (TextView) findViewById(R.id.score);


        menu = (Button) findViewById(R.id.menuButton);
        playAgain = (Button) findViewById(R.id.playAgainButton);
        pref = getSharedPreferences("PREFS", 0);
        auth = pref.getString("auth", "failed");
        username = pref.getString("username", "failed");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        timeInSeconds = (Double) bundle.get("time");
        time.setText("Time: " + timeInSeconds);
        scoreDouble = (double) bundle.get("score");
        score.setText("Score: " + String.format("%.3f", scoreDouble));
        numOfTries = (int) bundle.get("tries");
        nrWrong.setText("Number of wrong guesses: " + numOfTries);
        class GetLetterAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {

                RestClient getLetter = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/getordet");
                try {
                    getLetter.execute(RequestMethod.GET);
                    finalWord = getLetter.getResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                word.setText("" + finalWord.toString());
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    time.setText("Time: " + timeInSeconds);
                    score.setText("Score: " + String.format("%.3f", scoreDouble));
                    nrWrong.setText("Number of wrong guesses: " + numOfTries);
                    System.out.println("TEST1");
                    System.out.println(scoreDouble);
                    System.out.println(numOfTries);
                    System.out.println(timeInSeconds);
                }
            }
        }

        new GetLetterAsyncTask().execute();


        class PostScoreAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                    System.out.println("HEJ");
                    System.out.println(scoreDouble);
                    System.out.println(numOfTries);
                    System.out.println(timeInSeconds);


                RestClient postclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/postscore/");
                String inputJsonString =
                        "{" +
                                "    \"jwt\"          :" + auth +
                                "," +
                                "    \"username\"          : " + username +
                                "," +
                                "    \"score\"          : " + scoreDouble +
                                "," +
                                "    \"numtries\"          : " + numOfTries +
                                "," +
                                "    \"time\"          : " + timeInSeconds +
                                "}";
                postclient.setJSONString(inputJsonString);
                postclient.addHeader("Content-Type", "appication/json");
                try {
                    postclient.execute(RequestMethod.POST);
                    System.out.println(postclient.getResponseCode());
                    System.out.println(postclient.getResponse());

                } catch (Exception e) {
                    System.out.println("Missing connection");
                    Toast.makeText(getApplicationContext(), "Missing connection, please try again later", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
            }
        }
        new PostScoreAsyncTask().execute();


        menu.setOnClickListener(this);
        playAgain.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == menu) {
            onBackPressed();
            finish();
        }

        if (v == playAgain) {
            Intent i = new Intent(this, Play.class);
            startActivity(i);
            finish();
        }
    }
}
