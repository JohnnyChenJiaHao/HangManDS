package com.example.johnny.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Johnny on 23/10/17.
 */

public class Play extends AppCompatActivity implements View.OnClickListener {

    public static Galgelogik spil = new Galgelogik();

    Button guessButton, menu;

    TextView wordView, letters, life;

    ImageView image;

    EditText guessLetter;

    public static double totalTime;

    long tStart, tEnd;

    static int totalGames, won, lost;

    static String totalGamesStr, wonStr, lostStr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        tStart = SystemClock.elapsedRealtime();

        class AsyncTaskURL extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    spil.hentOrdFraURL();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                spil.nulstil();
                update();
                wordView.setText(spil.getSynligtOrd());
            }
        }

        new AsyncTaskURL().execute();

        guessLetter = (EditText) findViewById(R.id.guessLetter);

        guessButton = (Button) findViewById(R.id.guessButton);
        menu = (Button) findViewById(R.id.menuButton);

        image = (ImageView) findViewById(R.id.image);

        letters = (TextView) findViewById(R.id.letters);
        wordView = (TextView) findViewById(R.id.word);
        life = (TextView) findViewById(R.id.lives);

        guessButton.setOnClickListener(this);
        menu.setOnClickListener(this);

        letters.setText("Used letters: " + spil.getBrugteBogstaver());
        life.setText("Lives: " + spil.getAntalLiv());
    }

    public void onClick(View v) {
        String guessLet = guessLetter.getText().toString();

        if (v == guessButton) {
            if (guessLet.length() != 1) {
                guessLetter.setError("Write a letter");
                return;
            }

            spil.gætBogstav(guessLet);

            guessLetter.setError(null);

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

            update();
            guessLetter.setText("");
        }

        else if (v == menu) {
            onBackPressed();
        }
    }

    private void update() {
        wordView.setText(spil.getSynligtOrd());
        letters.setText("Used letters: " + spil.getBrugteBogstaver());
        life.setText("Lives: " + spil.getAntalLiv());

        final SharedPreferences.Editor TotalGame = getSharedPreferences("TotalGames", Context.MODE_PRIVATE).edit();

        if (spil.erSpilletVundet()) {
            tEnd = SystemClock.elapsedRealtime();
            totalTime();

            totalGames++;
            TotalGame.putInt(totalGamesStr,totalGames);
            TotalGame.apply();

            won++;
            final SharedPreferences.Editor win = getSharedPreferences("Wins", Context.MODE_PRIVATE).edit();
            win.putInt(wonStr,won);
            win.apply();

            Intent i = new Intent(this, Win.class);
            startActivity(i);
            finish();
        }
        else if (spil.erSpilletTabt()) {
            totalGames++;
            TotalGame.putInt(totalGamesStr,totalGames);
            TotalGame.apply();

            lost++;
            final SharedPreferences.Editor lose = getSharedPreferences("Losses", Context.MODE_PRIVATE).edit();
            lose.putInt(lostStr,lost);
            lose.apply();

            Intent i = new Intent(this, Lose.class);
            startActivity(i);
            finish();
        }
    }

    public void totalTime() {
        totalTime = (tEnd - tStart) / 1000;
    }

    public static double getTotalTime() {
        return totalTime;
    }
}
