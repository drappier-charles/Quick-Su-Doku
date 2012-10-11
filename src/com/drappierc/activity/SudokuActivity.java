package com.drappierc.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import com.drappierc.R;
import com.drappierc.sqlite.ScoresBDD;

public class SudokuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        // Demarre une partie classique (3x3)
        final Button newGameButton2 = (Button) findViewById(R.id.newGameButton2);
        newGameButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame(2);
            }
        });
        final Button newGameButton3 = (Button) findViewById(R.id.newGameButton3);
        newGameButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startGame(3);
            }
        });

        // Scores
        final Button scoreButton = (Button) findViewById(R.id.scoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                displayScore();
            }
        });

        // Ferme l'aply
        final Button closeGameButton = (Button) findViewById(R.id.closeGameButton);
        closeGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startGame(int size) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("size", size);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void displayScore() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

}
