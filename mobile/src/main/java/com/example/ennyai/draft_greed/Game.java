package com.example.ennyai.draft_greed;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Game extends AppCompatActivity {

    RelativeLayout mRelativeLayout;
    ImageView center_deck,p1_hand,p1_best_card;
    TextView cpu_score, p1_score, card_count;
    Button greed_button,play_button,reset_button;

    int total_cards = 52;
    int max_rand = total_cards - 1;
    int card_count_num = 0;
    int p1_points = 0;
    int cpu_points = 0;
    int p1_high_pair;
    int p1_high_card;
    int cpu_high_pair;
    int cpu_high_card;

    private Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        center_deck = (ImageView) findViewById(R.id.center_deck);
        p1_hand = (ImageView) findViewById(R.id.p1_hand);
        p1_best_card = (ImageView) findViewById(R.id.p1_best_card);
        cpu_score = (TextView) findViewById(R.id.cpu_score);
        p1_score = (TextView) findViewById(R.id.p1_score);
        card_count = (TextView) findViewById(R.id.card_count);
        greed_button = (Button) findViewById(R.id.greed_button);
        play_button = (Button) findViewById(R.id.play_button);
        reset_button = (Button) findViewById(R.id.reset_button);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.game);

        final String[] freeCards = new String[52];
        String[] suitType = new String[4];
        suitType[0] = "d";
        suitType[1] = "s";
        suitType[2] = "c";
        suitType[3] = "h";
        int i;
        int j = 0;
        int c = 12;
        for(i=0;i<13;i++) {
            freeCards[j]= ("card_"+ c + suitType[0]);
            j++;
            freeCards[j]= ("card_"+ c + suitType[1]);
            j++;
            freeCards[j]= ("card_"+ c + suitType[2]);
            j++;
            freeCards[j]= ("card_"+ c + suitType[3]);
            j++;
            c++;
        }

        final String[] cloneArray = freeCards.clone();

        final ArrayList p1_singles = new ArrayList();
        final ArrayList p1_pairs = new ArrayList();
        final ArrayList cpu_singles = new ArrayList();
        final ArrayList cpu_pairs = new ArrayList();


        greed_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total_cards==52) {
                    int i;
                    for(i=0;i<52;i++){
                        cloneArray[i] = freeCards[i];
                    }
                }
                if(total_cards>0){

                    //P1 PICK
                    int pick = r.nextInt(total_cards);
                    String p1_pick = cloneArray[pick];
                    String cardNumString = cloneArray[pick].substring(5,7);
                    int cardNum = Integer.valueOf(cardNumString) - 10;

                    //COLLECT CARD
                    if(p1_singles.contains(cardNum)){
                        int i;
                        for (i=0;i<p1_singles.size();i++) {

                            if (p1_singles.get(i).equals(cardNum)) {
                                p1_pairs.add(cardNum);
                                p1_singles.remove(i);
                                Collections.sort(p1_pairs, Collections.reverseOrder());
                                p1_high_pair = p1_pairs.get(0).hashCode();
                                break;
                            }
                        }
                    }

                    else {
                        p1_singles.add(cardNum);
                    }

                    //FINISH UP
                    cloneArray[pick] = cloneArray[max_rand];
                    total_cards--;
                    max_rand--;
                    int p1_card = getResources().getIdentifier(p1_pick,"drawable",getPackageName());
                    String high_card_string = "card_".concat(Integer.toString(p1_high_pair+10)).concat("s");
                    int p1_best = getResources().getIdentifier(high_card_string,"drawable",getPackageName());

                    //CPU_PICK
                    pick = r.nextInt(total_cards);
                    cardNumString = cloneArray[pick].substring(5,7);
                    cardNum = Integer.valueOf(cardNumString) - 10;

                    //COLLECT CARD
                    if(cpu_singles.contains(cardNum)){
                        int i;
                        for (i=0;i<cpu_singles.size();i++) {

                            if (cpu_singles.get(i).equals(cardNum)) {
                                cpu_pairs.add(cardNum);
                                cpu_singles.remove(i);
                                Collections.sort(cpu_pairs, Collections.reverseOrder());
                                cpu_high_pair = cpu_pairs.get(0).hashCode();
                                break;
                            }
                        }
                    }

                    else {
                        cpu_singles.add(cardNum);
                    }

                    //FINISH UP
                    cloneArray[pick] = cloneArray[max_rand];
                    total_cards--;
                    max_rand--;

                    card_count_num = 52 - total_cards;

                    card_count.setText(String.valueOf(card_count_num));
                    p1_hand.setImageResource(p1_card);
                    p1_best_card.setImageResource(p1_best);
                }
                else {
                    Toast.makeText(Game.this,"NO MORE CARDS",Toast.LENGTH_SHORT).show();
                }
            }
        });

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p1_pairs.size() < 1){
//                    Toast.makeText(Game.this,"MUST HAVE AT LEAST ONE PAIR",Toast.LENGTH_SHORT).show();
                    Snackbar.make(mRelativeLayout,"Welcome",Snackbar.LENGTH_LONG).show();
                }
                else {
                    String hand_score = String.valueOf(p1_high_pair).concat(" - ").concat(String.valueOf(cpu_high_pair));
                    if(p1_high_pair>cpu_high_pair){
                        Toast.makeText(Game.this,hand_score.concat(" | ROUND WIN"),Toast.LENGTH_SHORT).show();
                        total_cards = 52;
                        max_rand = total_cards - 1;
                        p1_points = p1_points + card_count_num;
                        card_count_num = 0;
                        p1_score.setText(String.valueOf(p1_points));
                        card_count.setText(String.valueOf(card_count_num));
                        p1_pairs.clear();
                        p1_singles.clear();
                        cpu_pairs.clear();
                        cpu_singles.clear();
                        p1_high_pair = 0;
                        p1_hand.setImageResource(0);
                        p1_best_card.setImageResource(0);
                    }
                    else {
                        Toast.makeText(Game.this,hand_score.concat(" | ROUND LOSS"),Toast.LENGTH_SHORT).show();
                        total_cards = 52;
                        max_rand = total_cards - 1;
                        cpu_points = cpu_points + card_count_num;
                        card_count_num = 0;
                        cpu_score.setText(String.valueOf(cpu_points));
                        card_count.setText(String.valueOf(card_count_num));
                        p1_pairs.clear();
                        p1_singles.clear();
                        cpu_pairs.clear();
                        cpu_singles.clear();
                        p1_high_pair = 0;
                        p1_hand.setImageResource(0);
                        p1_best_card.setImageResource(0);
                    }
                }
            }

        });

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_cards = 52;
                max_rand = total_cards - 1;
                p1_points = 0;
                cpu_points = 0;
                card_count_num = 0;
                p1_score.setText("0");
                cpu_score.setText("0");
                card_count.setText("0");
                p1_pairs.clear();
                p1_singles.clear();
                cpu_pairs.clear();
                cpu_singles.clear();
                p1_high_pair = 0;
                p1_hand.setImageResource(0);
                p1_best_card.setImageResource(0);
                Toast.makeText(Game.this,"BOARD RESET",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
