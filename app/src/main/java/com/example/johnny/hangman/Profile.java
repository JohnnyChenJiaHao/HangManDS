package com.example.johnny.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Johnny on 24/10/17.
 */

public class Profile extends AppCompatActivity implements View.OnClickListener {

    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == menu) {
            onBackPressed();
        }
    }

}
