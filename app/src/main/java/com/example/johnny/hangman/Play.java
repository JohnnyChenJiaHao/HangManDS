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

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.ExplodeAnimation;


import java.util.logging.Level;
import java.util.logging.Logger;



public class Play extends AppCompatActivity implements View.OnClickListener {

    Button guessButton;

    TextView wordView, letters, tries, score;

    ImageView image;

    EditText guessLetter;

    public static double totalTime;

    static int totalGames, won, lost;
    static Thread sc;


    static String totalGamesStr, wonStr, lostStr, visiblewWord, lettersUsed, numOfTries, tmp, lostword;
    String[] wrongLetters;

    static long tStart, tEnd, tDelta;
    static double elapsedSeconds, temp1, temp2, temp3;
    int numtries;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        // spil.setScore(0);
        // spil.setStreak(0);
        tStart = SystemClock.elapsedRealtime();
        sc = new Thread(new score());
        sc.start();

        guessLetter = (EditText) findViewById(R.id.guessLetter);
        guessButton = (Button) findViewById(R.id.guessButton);
        image = (ImageView) findViewById(R.id.image);
        letters = (TextView) findViewById(R.id.letters);
        wordView = (TextView) findViewById(R.id.word);
        tries = (TextView) findViewById(R.id.lives);
        score = (TextView) findViewById(R.id.score);



        guessButton.setOnClickListener(this);

        letters.setText("Used letters: ");
        tries.setText("");
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
                        if(client.getResponseCode() == 200){
                            visiblewWord = client.getResponse().toString();
                        }else
                            visiblewWord = "test";

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
                    //JSONObject status = new JSONObject(WrongLetterclient.getResponse());
                    String WrongLetters = WrongLetterclient.getResponse();

                    String tmp[] = WrongLetters.split(":");
                    String tmp1[] = tmp[2].split("B");
                    String wrongs[] = tmp1[0].split(" ");
                    numOfTries = wrongs[1];
                    numOfTries.replace("\n", "");
                    numOfTries.replace(" ", "");
                    numOfTries.replace("\t", "");
                    numtries = Integer.parseInt(numOfTries.trim());
                    if (numtries == 6) {
                        RestClient lostclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/getordet");
                        try {
                            lostclient.execute(RequestMethod.GET);
                            lostword = lostclient.getResponse();
                            System.out.println(lostword);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                wordView.setText(visiblewWord);
                letters.setText("Used letters: " + lettersUsed);
                tries.setText("Number of tries: " + numOfTries);
                final SharedPreferences.Editor TotalGame = getSharedPreferences("TotalGames", Context.MODE_PRIVATE).edit();
                final SharedPreferences.Editor lose = getSharedPreferences("Losses", Context.MODE_PRIVATE).edit();
                if (!visiblewWord.contains("*")) {
                    //tEnd = SystemClock.elapsedRealtime();
                    temp3 = getScore();
                    totalTime();
                    Double time = getTotalTime();
                    Intent i = new Intent(getApplicationContext(), Win.class);
                    i.putExtra("time", time);
                    i.putExtra("score", temp3);
                    i.putExtra("tries", numtries);


                    totalGames++;
                    TotalGame.putInt(totalGamesStr, totalGames);
                    TotalGame.apply();

                    won++;
                    final SharedPreferences.Editor win = getSharedPreferences("Wins", Context.MODE_PRIVATE).edit();
                    win.putInt(wonStr, won);
                    win.apply();
                    MediaPlayer win_sound = MediaPlayer.create(getApplicationContext(), R.raw.win);
                    win_sound.start();

                    startActivity(i);
                    finish();
                } else {
                    if (numtries == 6) {
                        final MediaPlayer lose_sound = MediaPlayer.create(getApplicationContext(), R.raw.lose);
                        lose_sound.start();
                        temp3 = getScore();
                        totalGames++;
                        TotalGame.putInt(totalGamesStr, totalGames);
                        TotalGame.apply();

                        lost++;
                        lose.putInt(lostStr, lost);
                        lose.apply();
                        Intent i = new Intent(getApplicationContext(), Lose.class);
                        i.putExtra("currentScore", temp3);
                        i.putExtra("word", lostword);
                        startActivity(i);
                        finish();
                    }
                }
            }
        }

        if (v == guessButton) {
            if (guessLet.length() != 1) {
                guessLetter.setError("Write a letter");
            }
            new GuessLetterAsyncTask().execute();

            switch (numtries) {
                case 0:
                    image.setImageResource(R.mipmap.forkert1);
                    break;
                case 1:
                    image.setImageResource(R.mipmap.forkert2);
                    break;
                case 2:
                    image.setImageResource(R.mipmap.forkert3);
                    break;
                case 3:
                    image.setImageResource(R.mipmap.forkert4);
                    break;
                case 4:
                    image.setImageResource(R.mipmap.forkert5);
                    break;
                case 5:
                    image.setImageResource(R.mipmap.forkert6);
                    break;
                default:
                    break;
            }
            guessLetter.setText("");
        }
    }


    public void totalTime() {
        totalTime = (tEnd - tStart) / 1000;
    }

    public static double getTotalTime() {
        return totalTime;
    }


    public static double getScore() {
        //Grabbing time and converting to seconds.
        RestClient Statusclient = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/getbrugtebogstaver");
        try {
            Statusclient.execute(RequestMethod.GET);
            lettersUsed = Statusclient.getResponse().replaceAll("\"", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        tEnd = SystemClock.elapsedRealtime();
        tDelta = tEnd - tStart;
        elapsedSeconds = tDelta / 1000.0;

        temp1 = elapsedSeconds * (lettersUsed.length() + 1);
        temp2 = temp1 / (visiblewWord.length() + 1);
        temp3 = (100 / temp2) * 100;
        return temp3;
    }

    //Score Thread for calculating and updating score
    public static class score implements Runnable {
        public void run() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
            }

            getScore();
        }
    }
}
