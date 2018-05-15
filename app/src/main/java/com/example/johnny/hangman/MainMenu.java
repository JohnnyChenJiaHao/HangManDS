package com.example.johnny.hangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button playButton, profileButton, helpButton, resetButton, Highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        playButton = (Button) findViewById(R.id.playButton);
        profileButton = (Button) findViewById(R.id.profileButton);
        helpButton = (Button) findViewById(R.id.helpButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        Highscore = (Button) findViewById(R.id.Highscore);

        playButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        Highscore.setOnClickListener(this);
    }

    public void onClick(View v) {

        if (v == playButton) {
            Intent i = new Intent(this, Play.class);
            startActivity(i);
        }

        else if (v == profileButton) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);
        }

        else if (v == helpButton) {
            Intent i = new Intent(this, Help.class);
            startActivity(i);
        }
        else if(v==Highscore) {
            Intent i = new Intent(this, Highscore.class);
            startActivity(i);
        }

        else if (v == resetButton) {

            AlertDialog.Builder reset = new AlertDialog.Builder(this);
            reset.setTitle("Reset Data");
            reset.setMessage("This will only erase win, loss and amount of games played, not your highscore!");
            reset.setPositiveButton("Reset",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final SharedPreferences.Editor win = getSharedPreferences("Wins", Context.MODE_PRIVATE).edit();
                    final SharedPreferences.Editor lose = getSharedPreferences("Losses", Context.MODE_PRIVATE).edit();
                    final SharedPreferences.Editor totalGame = getSharedPreferences("TotalGames", Context.MODE_PRIVATE).edit();

                    win.clear();
                    lose.clear();
                    totalGame.clear();

                    win.apply();
                    lose.apply();
                    totalGame.apply();

                    Play.totalGames = 0;
                    Play.won = 0;
                    Play.lost = 0;


                    showToast();
                }
            });

            reset.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            reset.create();
            reset.show();

        }
    }

    public void showToast() {
        Toast.makeText(this,"Data erased",Toast.LENGTH_LONG).show();
    }
}
