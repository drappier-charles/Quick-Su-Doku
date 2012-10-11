package com.drappierc.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.drappierc.R;
import com.drappierc.sqlite.Score;
import com.drappierc.sqlite.ScoresBDD;

import java.util.ArrayList;

public class ScoreActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);


        ScoresBDD scoreBdd = new ScoresBDD(this);
        scoreBdd.open();
        ArrayList<Score> scores = scoreBdd.getAll();

        LinearLayout content = (LinearLayout) findViewById(R.id.content);

        LinearLayout head = new LinearLayout(this);

        TextView timecol = new TextView(this);
        timecol.setText(R.string.score);
        timecol.setTextSize(25);
        timecol.setTextColor(Color.rgb(255, 255, 255));
        timecol.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        timecol.setGravity(Gravity.CENTER);
        head.addView(timecol);

        TextView typecol = new TextView(this);
        typecol.setText(R.string.difficulty);
        typecol.setTextSize(25);
        typecol.setTextColor(Color.rgb(255, 255, 255));
        typecol.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        typecol.setGravity(Gravity.CENTER);
        head.addView(typecol);

        head.setBackgroundColor(Color.rgb(0, 0, 0));
        head.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        content.addView(head);

        if (scores != null) {
            for (int i = 0; i < scores.size(); i++) {
                Score score = scores.get(i);
                LinearLayout item = new LinearLayout(this);

                TextView time = new TextView(this);
                time.setText("" + (score.getScore() / 1000)+" s");
                time.setTextSize(25);
                time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
                time.setGravity(Gravity.CENTER);
                time.setTextColor(Color.rgb(0,0,0));
                item.addView(time);

                TextView type = new TextView(this);
                if(score.getType() == 2) {
                    type.setText(R.string.mini);
                } else if(score.getType() == 3) {
                    type.setText(R.string.classique);
                } else {
                    type.setText(R.string.undefined);
                }
                type.setTextSize(25);
                type.setTextColor(Color.rgb(0,0,0));
                type.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
                type.setGravity(Gravity.CENTER);
                item.addView(type);

                item.setBackgroundColor(Color.rgb(255, 255, 255));
                LinearLayout.LayoutParams paramsItem = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsItem.setMargins(0,0,0,3);
                item.setLayoutParams(paramsItem);
                content.addView(item);
            }
        } else {
            TextView vide = new TextView(this);
            vide.setText(R.string.no_score);
            vide.setTextSize(25);
            vide.setGravity(Gravity.CENTER);
            vide.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            vide.setBackgroundColor(Color.rgb(255, 255, 255));
            content.addView(vide);
        }
        scoreBdd.close();
    }
}