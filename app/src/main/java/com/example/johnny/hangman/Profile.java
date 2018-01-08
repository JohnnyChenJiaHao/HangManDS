package com.example.johnny.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.example.johnny.hangman.Play.lostStr;
import static com.example.johnny.hangman.Play.totalGamesStr;
import static com.example.johnny.hangman.Play.wonStr;
import static com.example.johnny.hangman.Win.highScoreStr;

/**
 * Created by Johnny on 24/10/17.
 */

public class Profile extends AppCompatActivity {

    static TextView games, won, lost, highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        final SharedPreferences win = getSharedPreferences("Wins", Context.MODE_PRIVATE);
        final SharedPreferences lose = getSharedPreferences("Losses", Context.MODE_PRIVATE);
        final SharedPreferences totalGame = getSharedPreferences("TotalGames", Context.MODE_PRIVATE);
        final SharedPreferences highscores = getSharedPreferences("Highscore", Context.MODE_PRIVATE);


        int wonData = win.getInt(wonStr,0);
        int lostData = lose.getInt(lostStr,0);
        int totalGameData = totalGame.getInt(totalGamesStr, 0);
        int highscoreData = highscores.getInt(highScoreStr,0);

        games = (TextView) findViewById(R.id.games);
        won = (TextView) findViewById(R.id.won);
        lost = (TextView) findViewById(R.id.lost);
        highscore = (TextView) findViewById(R.id.highscore);


        games.setText("Games: \n" + totalGameData);
        won.setText("Won: \n" + wonData);
        lost.setText("Lost: \n" + lostData);
        highscore.setText("Highscore: \n" + highscoreData);
    }

}
