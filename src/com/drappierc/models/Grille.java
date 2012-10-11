package com.drappierc.models;

import android.content.Context;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Random;

public class Grille {

    private int[][] grilleNumbers;
    private LinearLayout grilleLayout;
    private int size;


    // Constructeur
    public Grille(Context context, int size) {
        this.size = size;
        this.grilleNumbers = this.getGrille();
        this.grilleLayout = this.createLinearGrille(context);
    }

    // Retourne la grille a afficher
    public LinearLayout getGrilleLayout() {
        return this.grilleLayout;
    }

    public boolean verifNumber(int blockNumber, int caseNumber, int value) {
        // Verif block
        for (int i = 0; i < this.size * this.size; i++) {
            if (value == grilleNumbers[blockNumber][i] && i != caseNumber) return false;
        }

        int bNumber; int cNumber;

        // Verif col
        bNumber = blockNumber;
        for (int i = 0; i < this.size; i++) {
            if (bNumber >= this.size * this.size) bNumber = bNumber - this.size * this.size;
            cNumber = caseNumber;
            for (int j = 0; j < this.size; j++) {
                if (cNumber >= this.size * this.size) cNumber = cNumber - this.size * this.size;
                if (value == grilleNumbers[bNumber][cNumber] && (blockNumber != bNumber || caseNumber != cNumber))
                    return false;
                cNumber = cNumber + this.size;
            }
            bNumber = bNumber + this.size;
        }

        // Verif ligne
        bNumber = blockNumber;
        for (int i = 0; i < this.size; i++) {
            if (i != 0 && (bNumber == this.size || bNumber == 2*this.size || bNumber == 3*this.size)) bNumber = bNumber - this.size;
            cNumber = caseNumber;
            for (int j = 0; j < this.size; j++) {
                if (j != 0 && (cNumber == this.size || cNumber == 2*this.size || cNumber == 3*this.size)) cNumber = cNumber - this.size;
                if (value == grilleNumbers[bNumber][cNumber] && (blockNumber != bNumber || caseNumber != cNumber))
                    return false;
                cNumber++;
            }
            bNumber++;
        }

        return true;
    }

    public boolean verifGrille() {
        for(int blockN = 0; blockN<this.size*this.size ;blockN++){
            for(int caseN = 0; caseN<this.size*this.size ; caseN++) {
                if(this.grilleNumbers[blockN][caseN] == 0) return false;
            }
        }

        return true;
    }

    public void setGrilleNumbers(int blockNumber, int caseNumber, int value) {
        grilleNumbers[blockNumber][caseNumber] = value;
    }

    // Recupère en random une grille selon la taille choisi
    private int[][] getGrille() {
        int numberGrilleDispo;
        Random random = new Random();
        if (this.size == 2) {
            numberGrilleDispo = grilles2x2.length;
            int randomInt = random.nextInt(numberGrilleDispo);
            return grilles2x2[randomInt];
        }
        if (this.size == 3) {
            numberGrilleDispo = grilles3x3.length;
            int randomInt = random.nextInt(numberGrilleDispo);
            return grilles3x3[randomInt];
        }
        return null;
    }

    // Creer une grille de sudoku (le layout)
    private LinearLayout createLinearGrille(Context context) {
        LinearLayout grille = new LinearLayout(context);
        grille.setOrientation(LinearLayout.VERTICAL);
        grille.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for (int tr = 0; tr < this.size; tr++) {
            LinearLayout row = new LinearLayout(grille.getContext());
            for (int td = 0; td < this.size; td++) {
                row.addView(createLinearBlock(row.getContext(), tr * this.size + td));
            }
            grille.addView(row);
        }

        return grille;
    }


    // Creer a un bloc de sudoku (layout)
    private LinearLayout createLinearBlock(Context context, int blockNumber) {
        LinearLayout block = new LinearLayout(context);
        block.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.MarginLayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT,1);
        params.setMargins(3, 3, 3, 3);
        block.setLayoutParams(params);

        for (int tr = 0; tr < this.size; tr++) {
            LinearLayout row = new LinearLayout(block.getContext());
            for (int td = 0; td < this.size; td++) {
                row.addView(createCase(row.getContext(), blockNumber, tr * this.size + td));
            }
            block.addView(row);
        }

        return block;
    }

    // Correspond a une case de chiffre (prérempli ou non) (editText)
    private EditText createCase(Context context, int blockNumber, int caseNumber) {
        InputFilter[] maxLength = new InputFilter[1];
        maxLength[0] = new InputFilter.LengthFilter(1);

        EditText editText = new EditText(context);
        editText.setFilters(maxLength);

        editText.setTextColor(Color.rgb(0, 0, 0));
        editText.setGravity(Gravity.CENTER);
        editText.setInputType(InputType.TYPE_NULL);
        if (this.grilleNumbers[blockNumber][caseNumber] != 0) {
            editText.setText("" + this.grilleNumbers[blockNumber][caseNumber]);
            editText.setFocusable(false);
            editText.setTextColor(Color.rgb(0, 0, 255));
        }
        editText.setTag(blockNumber + "," + caseNumber);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT,1);
        params.setMargins(1,1,1,1);
        editText.setLayoutParams(params);
        editText.setBackgroundColor(Color.rgb(255,255,255));
        if(this.size==3)
            editText.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP,11);
        if(this.size==2)
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);

        return editText;
    }

    // Stocker en base prochainement
    private int grilles2x2[][][] = {
            {
                    {0, 4, 3, 0},
                    {3, 0, 0, 0},
                    {0, 0, 0, 1},
                    {0, 1, 2, 0}
            },
            {
                    {2, 0, 0, 0},
                    {0, 0, 1, 0},
                    {0, 2, 0, 0},
                    {0, 0, 0, 4}
            },
            {
                    {0, 0, 3, 1},
                    {0, 0, 4, 2},
                    {2, 4, 0, 0},
                    {3, 1, 0, 0}
            },
            {
                    {0, 0, 0, 0},
                    {2, 1, 0, 3},
                    {4, 0, 1, 2},
                    {0, 0, 0, 0}
            }
    };
    private int grilles3x3[][][] = {
            {
                    {0, 4, 0, 0, 0, 0, 3, 1, 0},
                    {0, 0, 2, 3, 5, 1, 0, 9, 4},
                    {0, 1, 9, 0, 8, 6, 7, 0, 0},
                    {0, 9, 4, 0, 0, 0, 2, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 7, 0, 0, 0, 8, 9, 0},
                    {0, 0, 9, 4, 2, 0, 1, 6, 0},
                    {5, 2, 0, 1, 6, 9, 8, 0, 0},
                    {0, 4, 1, 0, 0, 0, 0, 7, 0}
            },
            {
                    {0, 3, 0, 0, 0, 7, 5, 0, 0},
                    {0, 6, 0, 0, 0, 3, 0, 0, 0},
                    {5, 0, 0, 0, 0, 8, 4, 0, 0},
                    {0, 4, 0, 0, 5, 0, 0, 0, 0},
                    {0, 0, 2, 1, 9, 0, 0, 0, 0},
                    {0, 0, 0, 8, 2, 0, 0, 0, 0},
                    {0, 0, 0, 7, 0, 8, 0, 0, 6},
                    {8, 0, 0, 0, 4, 9, 0, 0, 0},
                    {0, 3, 9, 0, 0, 6, 1, 0, 0}
            },
            {
                    {0, 0, 1, 0, 9, 4, 0, 0, 0},
                    {0, 0, 0, 0, 3, 7, 4, 9, 0},
                    {0, 3, 0, 5, 8, 0, 0, 0, 0},
                    {0, 3, 6, 0, 8, 0, 9, 0, 0},
                    {0, 5, 0, 0, 0, 2, 0, 8, 0},
                    {0, 0, 4, 0, 0, 0, 3, 7, 0},
                    {0, 6, 0, 8, 0, 3, 2, 1, 0},
                    {0, 7, 0, 0, 0, 0, 0, 0, 4},
                    {0, 1, 0, 0, 4, 0, 0, 0, 0}
            },
            {
                    {8, 0, 4, 0, 0, 9, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 3, 0, 2},
                    {2, 0, 9, 1, 0, 0, 0, 0, 7},
                    {0, 5, 0, 0, 0, 0, 0, 1, 0},
                    {1, 0, 4, 0, 3, 0, 7, 0, 9},
                    {0, 8, 0, 0, 0, 0, 0, 2, 0},
                    {5, 0, 0, 0, 0, 3, 4, 0, 6},
                    {4, 0, 3, 0, 0, 0, 0, 0, 0},
                    {0, 0, 8, 4, 0, 0, 3, 0, 1}
            }
    };
}
