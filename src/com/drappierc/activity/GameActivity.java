package com.drappierc.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.TabStopSpan;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.drappierc.R;
import com.drappierc.models.Grille;
import com.drappierc.sqlite.Score;
import com.drappierc.sqlite.ScoresBDD;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {
    private Grille grille;
    private ScoresBDD scoreBdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.scoreBdd = new ScoresBDD(this);
        scoreBdd.open();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // On désactive le clavier android.
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // On récupere le LinearLayout prévu pour y glisser le contenu
        LinearLayout content = (LinearLayout) findViewById(R.id.content);

        // On récupère la taille souhaité du jeu
        Bundle b = getIntent().getExtras();
        int size = b.getInt("size");

        LinearLayout keyboard = (LinearLayout) findViewById(R.id.keyboard);
        keyboard.removeAllViews();
        this.initKeyboard(keyboard, size);

        //On creer le jeu et on le balance dans le contenu (en supprimant ce qu'il y avais avant) on oublie pas le clavier
        this.grille = new Grille(this, size);
        content.removeAllViews();
        content.addView(this.grille.getGrilleLayout());


    }

    // Ici on gere un menu (avec la touche menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.gamemenu, menu);
        menu.getItem(0).getSubMenu().setHeaderIcon(R.drawable.ic_menu_play_clip);
        return true;
    }

    //Ici on gere les actions lorsqu'on presse un bouton du menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mini:
                startGame(2);
                return true;
            case R.id.classique:
                startGame(3);
                return true;
            case R.id.quitter:
                finish();
                return true;
            case R.id.newGame:
                return true;
        }
        return false;
    }

    // Initialise le keyboard
    private void initKeyboard(LinearLayout keyboardView, int size) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        // Load du keyboard
        if (size == 2) {
            View keyboard = inflater.inflate(R.layout.keyboard2, (ViewGroup) findViewById(R.layout.keyboard2));
            keyboardView.addView(keyboard);
        }
        if (size == 3) {
            View keyboard = inflater.inflate(R.layout.keyboard3, (ViewGroup) findViewById(R.layout.keyboard3));
            keyboardView.addView(keyboard);
        }

        // Action quand un bouton chiffre est presser
        for (int i = 1; i <= size * size; i++) {
            final int number = i;
            final Button button = (Button) findViewById(getResources().getIdentifier("B" + i, "id", getPackageName()));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    changeFocusCaseContent("" + number);
                }
            });
        }

        // Action quand le bouton reset est pressé
        final Button reset = (Button) findViewById(R.id.BR);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeFocusCaseContent("");
            }
        });
        final long startTime = System.currentTimeMillis();

        final int sizeGame = size;
        // Action quand on veux verifier
        final Button buttonVerif = (Button) findViewById(R.id.verifButton);
        buttonVerif.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (grille.verifGrille()) {
                    long stopTime = System.currentTimeMillis();
                    long time = stopTime - startTime;
                    Score score = new Score(time,sizeGame);
                    scoreBdd.insertScore(score);
                    Toast finish = Toast.makeText(getApplicationContext(), getString(R.string.victoire) + " "+ (time/1000) + " s", Toast.LENGTH_LONG);
                    finish.show();
                    /** Stoker le temps en base **/
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    },5000);
                } else {
                    Toast notFinish = Toast.makeText(getApplicationContext(), R.string.notcomplete, Toast.LENGTH_LONG);
                    notFinish.show();
                }
            }
        });
    }

    // Sert a modifier le contenu d'une case
    private void changeFocusCaseContent(String number) {
        LinearLayout content = (LinearLayout) findViewById(R.id.content);
        // Va récupérer l'edit text en verifiant qu'il n'y a pas une erreur dans le focus
        LinearLayout s1 = (LinearLayout) content.getFocusedChild();
        if (s1 == null) return;
        LinearLayout s2 = (LinearLayout) s1.getFocusedChild();
        if (s2 == null) return;
        LinearLayout s3 = (LinearLayout) s2.getFocusedChild();
        if (s3 == null) return;
        LinearLayout s4 = (LinearLayout) s3.getFocusedChild();
        if (s4 == null) return;
        EditText edit = (EditText) s4.getFocusedChild();
        // Modifie la valeur si c'est possible
        String tag = (String) edit.getTag();
        String tags[] = tag.split(",");
        int blockNumber = Integer.parseInt(tags[0]);
        int caseNumber = Integer.parseInt(tags[1]);

        if (number != "") {
            if (this.grille.verifNumber(blockNumber, caseNumber, Integer.parseInt(number))) {
                edit.setText(number);
                this.grille.setGrilleNumbers(blockNumber, caseNumber, Integer.parseInt(number));
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), R.string.not_here, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            edit.setText(number);
            this.grille.setGrilleNumbers(blockNumber, caseNumber, 0);
        }


    }

    // Redemarre une nouvelle partie (Quitte la premiere)
    private void startGame(int size) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("size", size);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

}
