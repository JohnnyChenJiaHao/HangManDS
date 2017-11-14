package com.example.johnny.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.johnny.hangman.Play.lostStr;
import static com.example.johnny.hangman.Play.totalGamesStr;
import static com.example.johnny.hangman.Play.wonStr;

/**
 * Created by Johnny on 24/10/17.
 */

public class Profile extends AppCompatActivity implements View.OnClickListener {

    static TextView games, won, lost;
    Button menu, highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        final SharedPreferences win = getSharedPreferences("Wins", Context.MODE_PRIVATE);
        final  SharedPreferences lose = getSharedPreferences("Losses", Context.MODE_PRIVATE);
        final  SharedPreferences totalGame = getSharedPreferences("TotalGames", Context.MODE_PRIVATE);

        int wonData = win.getInt(wonStr,0);
        int lostData = lose.getInt(lostStr,0);
        int totalGameData = totalGame.getInt(totalGamesStr, 0);

        games = (TextView) findViewById(R.id.games);
        won = (TextView) findViewById(R.id.won);
        lost = (TextView) findViewById(R.id.lost);

        menu = (Button) findViewById(R.id.menuButton);
        highscore = (Button) findViewById(R.id.highscoreButton);

        menu.setOnClickListener(this);
        highscore.setOnClickListener(this);

        games.setText("Games: \n" + totalGameData);
        won.setText("Won: \n" + wonData);
        lost.setText("Lost: \n" + lostData);
    }

    public void onClick(View v) {
        if (v == menu) {
            onBackPressed();
            finish();
        }

        if (v == highscore) {
            Toast.makeText(this,"Not implemented yet", Toast.LENGTH_SHORT).show();
        }
    }
}
