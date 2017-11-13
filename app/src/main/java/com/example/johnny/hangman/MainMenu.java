package com.example.johnny.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button playButton, profileButton, helpButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        playButton = (Button) findViewById(R.id.playButton);
        profileButton = (Button) findViewById(R.id.profileButton);
        helpButton = (Button) findViewById(R.id.helpButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        playButton.setOnClickListener(this);
        profileButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
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

        else if (v == resetButton) {
            Toast.makeText(this, "Not implemented yet", Toast.LENGTH_LONG).show();
        }

    }
}
