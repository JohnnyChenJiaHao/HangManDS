package com.example.johnny.hangman;

import android.content.Intent;
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

public class Lose extends AppCompatActivity implements View.OnClickListener {

    TextView word, score;
    int currentScore;
    Button menu, playAgain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose);

        word = (TextView) findViewById(R.id.word);
        score = (TextView) findViewById(R.id.score);
        menu = (Button) findViewById(R.id.menuButton);
        playAgain = (Button) findViewById(R.id.playAgainButton);

        menu.setOnClickListener(this);
        playAgain.setOnClickListener(this);

        Intent i = getIntent();
        //currentScore = i.getIntExtra("currentScore",spil.getScore());

        //word.setText("The word was: " + spil.getOrdet());
        score.setText("Score: " + currentScore);
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
