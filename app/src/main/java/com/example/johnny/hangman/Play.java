package com.example.johnny.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Johnny on 23/10/17.
 */

public class Play extends AppCompatActivity implements View.OnClickListener {

    public static Galgelogik spil = new Galgelogik();

    Button guessButton, menu;

    TextView wordView, miss;

    ImageView image;

    EditText guessLetter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        spil.nulstil();

        wordView = (TextView) findViewById(R.id.word);
        guessLetter = (EditText) findViewById(R.id.guessLetter);
        guessButton = (Button) findViewById(R.id.guessButton);

        image = (ImageView) findViewById(R.id.image);
        miss = (TextView) findViewById(R.id.miss);
        menu = (Button) findViewById(R.id.menu);

        guessButton.setOnClickListener(this);
        menu.setOnClickListener(this);

        wordView.setText(spil.getSynligtOrd());
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

            switch (spil.getAntalForkerteBogstaver()) {
                case 1:
                    image.setImageResource(R.mipmap.forkert1);
                    break;
                case 2:
                    image.setImageResource(R.mipmap.forkert2);
                    break;
                case 3:
                    image.setImageResource(R.mipmap.forkert3);
                    break;
                case 4:
                    image.setImageResource(R.mipmap.forkert4);
                    break;
                case 5:
                    image.setImageResource(R.mipmap.forkert5);
                    break;
                case 6:
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
        miss.setText(spil.getAntalForkerteBogstaver() + " Miss(es): " + spil.getBrugteBogstaver());

        if (spil.erSpilletVundet()) {
            Toast.makeText(this, "Congratulations, you won!", Toast.LENGTH_SHORT).show();
            spil.nulstil();
            miss.setText("");
        }
        if (spil.erSpilletTabt()) {
            Toast.makeText(this, "You lost.. the word was: " + spil.getOrdet(), Toast.LENGTH_SHORT).show();
            spil.nulstil();
        }

    }

}
