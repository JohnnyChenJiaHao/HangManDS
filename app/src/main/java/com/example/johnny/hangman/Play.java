package com.example.johnny.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.ExplodeAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.johnny.hangman.RequestMethod.GET;
import static com.example.johnny.hangman.RequestMethod.POST;

/**
 * Created by Johnny on 23/10/17.
 */

public class Play extends AppCompatActivity implements View.OnClickListener {

    public static Galgelogik spil = new Galgelogik();

    Button guessButton;

    TextView wordView, letters, life, score;

    ImageView image;

    EditText guessLetter;

    public static double totalTime;

    long tStart, tEnd;

    static int totalGames, won, lost;

    static String totalGamesStr, wonStr, lostStr, visiblewWord, lettersUsed;
    String[] wrongLetters;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        tStart = SystemClock.elapsedRealtime();
        spil.setScore(0);
        spil.setStreak(0);

        guessLetter = (EditText) findViewById(R.id.guessLetter);
        guessButton = (Button) findViewById(R.id.guessButton);
        image = (ImageView) findViewById(R.id.image);
        letters = (TextView) findViewById(R.id.letters);
        wordView = (TextView) findViewById(R.id.word);
        life = (TextView) findViewById(R.id.lives);
        score = (TextView) findViewById(R.id.score);
        ;

        guessButton.setOnClickListener(this);

        letters.setText("Used letters: ");
        life.setText("");
        score.setText("");

        class StartUpAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    RestClient Nulstilclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/nulstil");
                    try {
                        Nulstilclient.execute(RequestMethod.GET);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    RestClient client = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/getsynligtord");
                    try {
                        client.execute(RequestMethod.GET);
                        visiblewWord = client.getResponse().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                wordView.setText(visiblewWord);
            }
        }
        new StartUpAsyncTask().execute();


    }


    public void onClick(View v) {
        final String guessLet = guessLetter.getText().toString();

        new ExplodeAnimation(guessButton).setListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                guessButton.setVisibility(View.VISIBLE);
            }
        }).animate();


        class GuessLetterAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    RestClient guessLetterclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/gaetbogstav");
                    try {
                        String inputJsonString = "{" +
                                "    \"bogstav\"          :" + guessLet +
                                "}";

                        guessLetterclient.setJSONString(inputJsonString);
                        guessLetterclient.addHeader("Content-Type", "appication/json");
                        guessLetterclient.execute(RequestMethod.POST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    RestClient visibleWordclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/getsynligtord");
                    try {
                        visibleWordclient.execute(RequestMethod.GET);
                        visiblewWord = visibleWordclient.getResponse().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                RestClient Statusclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/getbrugtebogstaver");
                try {
                    Statusclient.execute(RequestMethod.GET);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lettersUsed = Statusclient.getResponse().replaceAll("\"", "");


                RestClient WrongLetterclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/logstatus/");
                try {
                    WrongLetterclient.execute(RequestMethod.GET);
                    /*
                    MANGLER
                    MANGLER
                    MANGLER
                    MANGLER
                    MANGLER
                    MANGLER
                    Mangler at indsætte forkerte bogstaver, da REST fucker.
                    MANGLER
                    MANGLER
                    MANGLER
                    MANGLER
                     */

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                wordView.setText(visiblewWord);
                letters.setText("Used letters: " + lettersUsed);
                if (!visiblewWord.contains("*")) {
                    tEnd = SystemClock.elapsedRealtime();
                    totalTime();
                    String time = String.valueOf(getTotalTime());
                    Intent i = new Intent(getApplicationContext(), Win.class);
                     i.putExtra("time", time);
                    MediaPlayer win_sound = MediaPlayer.create(getApplicationContext(), R.raw.win);
                    win_sound.start();

                    startActivity(i);
                    finish();
                }
                /*
                if(AntalForkerte = 7 (tror jeg er maks antal før man dør)){
                    Intent i = new Intent(getApplicationContext(), Lose.class);
                    i.putExtra("currentScore", spil.getScore());
                    startActivity(i);
                    finish();
                }
                 */
                /*
                RestClient GameWonclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/erspilletvundet");
                try {
                    GameWonclient.execute(GET);
                    String response = GameWonclient.getResponse();
                    System.out.println(response);
                    if (response.equals("true")) {

                        //MediaPlayer win_sound = MediaPlayer.create(getApplicationContext(), R.raw.win);
                        //win_sound.start();

                        Intent i = new Intent(getApplicationContext(), Win.class);
                        // i.putExtra("currentScore", spil.getScore());
                        startActivity(i);
                        finish();
                    } else {
                        RestClient GameLostclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/erspillettabt");
                        try {
                            GameLostclient.execute(GET);
                            if (GameLostclient.getResponse().equals("true")) {
                                // final MediaPlayer lose_sound = MediaPlayer.create(getApplicationContext(), R.raw.lose);
                                //lose_sound.start();

                                Intent i = new Intent(getApplicationContext(), Lose.class);
                                i.putExtra("currentScore", spil.getScore());
                                startActivity(i);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                */
            }
        }

            /*
            MANGLER
            MANGLER
            MANGLER
            MANGLER
            MANGLER
            BILLEDER NEDENFOR KAN IKKE LAVES FØR REST "LOGSTATUS" VIRKER
            MANGLER
            MANGLER
            MANGLER
            MANGLER

 */
        if (v == guessButton) {
            if (guessLet.length() != 1) {
                guessLetter.setError("Write a letter");
            }
            new GuessLetterAsyncTask().execute();

            switch (spil.getAntalLiv()) {
                case 5:
                    image.setImageResource(R.mipmap.forkert1);
                    break;
                case 4:
                    image.setImageResource(R.mipmap.forkert2);
                    break;
                case 3:
                    image.setImageResource(R.mipmap.forkert3);
                    break;
                case 2:
                    image.setImageResource(R.mipmap.forkert4);
                    break;
                case 1:
                    image.setImageResource(R.mipmap.forkert5);
                    break;
                case 0:
                    image.setImageResource(R.mipmap.forkert6);
                    break;
                default:
                    break;
            }
            guessLetter.setText("");
        }
    }

    /*
                            final SharedPreferences.Editor TotalGame = getSharedPreferences("TotalGames", Context.MODE_PRIVATE).edit();

                        totalGames++;
                        TotalGame.putInt(totalGamesStr, totalGames);
                        TotalGame.apply();

                        won++;
                        final SharedPreferences.Editor win = getSharedPreferences("Wins", Context.MODE_PRIVATE).edit();
                        win.putInt(wonStr, won);
                        win.apply();




totalGames++;
                        TotalGame.putInt(totalGamesStr, totalGames);
                        TotalGame.apply();

                        lost++;
                        final SharedPreferences.Editor lose = getSharedPreferences("Losses", Context.MODE_PRIVATE).edit();
                        lose.putInt(lostStr, lost);
                        lose.apply();

     */
    private void update() {
        life.setText("Lives: " + spil.getAntalLiv());
        score.setText("Score: " + spil.getScore());

    }

    public void totalTime() {
        totalTime = (tEnd - tStart) / 1000;
    }

    public static double getTotalTime() {
        return totalTime;
    }
}
