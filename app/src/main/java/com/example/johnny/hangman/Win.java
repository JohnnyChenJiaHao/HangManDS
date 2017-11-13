package com.example.johnny.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.johnny.hangman.Play.spil;

/**
 * Created by Johnny on 12/11/17.
 */

public class Win extends AppCompatActivity implements View.OnClickListener {

    TextView word, nrWrong, time;

    Button menu, playAgain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win);

        word = (TextView) findViewById(R.id.word);
        nrWrong = (TextView) findViewById(R.id.nrWrong);
        time = (TextView) findViewById(R.id.time);

        menu = (Button) findViewById(R.id.menuButton);
        playAgain = (Button) findViewById(R.id.playAgainButton);

        menu.setOnClickListener(this);
        playAgain.setOnClickListener(this);

        word.setText("You guessed the word: " + spil.getOrdet());
        nrWrong.setText("Wrong guesses: " + spil.getNrWrong());
        time.setText("Time: " + Play.getTotalTime() + "s");
    }

    @Override
    public void onClick(View v) {

        if (v == menu){
            onBackPressed();
        }

        if (v == playAgain) {
            Intent i = new Intent(this, Play.class);
            startActivity(i);
            finish();
        }
    }
}
