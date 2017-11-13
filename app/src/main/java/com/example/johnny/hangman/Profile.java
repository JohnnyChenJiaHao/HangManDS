package com.example.johnny.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Johnny on 24/10/17.
 */

public class Profile extends AppCompatActivity implements View.OnClickListener {

    TextView game, won, lost;
    Button menu, highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        game = (TextView) findViewById(R.id.games);
        won = (TextView) findViewById(R.id.won);
        lost = (TextView) findViewById(R.id.lost);

        menu = (Button) findViewById(R.id.menuButton);
        highscore = (Button) findViewById(R.id.highscoreButton);

        menu.setOnClickListener(this);
        highscore.setOnClickListener(this);

        game.setText("Games: " + Play.games);
        won.setText("Won: " + Play.won);
        lost.setText("Lost: " + Play.lost);
    }

    public void onClick(View v) {
        if (v == menu) {
            onBackPressed();
        }

        if (v == highscore) {

        }
    }

}
