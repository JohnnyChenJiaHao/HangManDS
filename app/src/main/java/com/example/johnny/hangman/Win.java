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

//import static com.example.johnny.hangman.Play.spil;

/**
 * Created by Johnny on 12/11/17.
 */

public class Win extends AppCompatActivity implements View.OnClickListener {

    int currentScore;

    static String highScoreStr;

    TextView word, nrWrong, time, score;

    Button menu, playAgain;

    String finalWord;


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

        class GetLetterAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {

                RestClient getLetter = new RestClient("http://ubuntu4.saluton.dk:20002/Galgeleg/rest/game/getordet");
                try {
                    getLetter.execute(RequestMethod.GET);
                    finalWord = getLetter.getResponse();
                    System.out.println(finalWord);
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
                if(bundle != null)
                {
                    String timeInSeconds = (String) bundle.get("time");
                    time.setText("Time: " + timeInSeconds);
                    double scoreDouble = (double) bundle.get("score");
                    score.setText("Score: " + String.format("%.3f", scoreDouble));

                }
            }
        }
        new GetLetterAsyncTask().execute();

        menu.setOnClickListener(this);
        playAgain.setOnClickListener(this);

        Intent i = getIntent();
        //currentScore = i.getIntExtra("currentScore",spil.getScore());
    
        //word.setText("You guessed the word: " + spil.getOrdet());
        //nrWrong.setText("Wrong guesses: " + spil.getNrWrong());
        score.setText("Score: " + currentScore);

        final SharedPreferences.Editor highScore = getSharedPreferences("Highscore", Context.MODE_PRIVATE).edit();
        highScore.putInt(highScoreStr,currentScore);
        highScore.apply();
    }

    @Override
    public void onClick(View v) {

        if (v == menu){
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
