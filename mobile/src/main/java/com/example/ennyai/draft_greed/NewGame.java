package com.example.ennyai.draft_greed;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class NewGame extends AppCompatActivity {

    RelativeLayout mRelativeLayout;
    LinearLayout p1_cards;
    ImageView center_deck,p1_hand,p1_best_card;
    TextView p2_score, p1_score, card_count, deck_count;
    Button play_button,reset_button;

    int total_cards = 52;
    int play_master = 1;
    int p1_points = 0;
    int cpu_points = 0;

    private Random r = new Random();
    private ArrayList<Cards> mCards;

    private static final String TAG = "NewGame";

    private ImageView old_cardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgame);

        center_deck = (ImageView) findViewById(R.id.center_deck);
        p1_hand = (ImageView) findViewById(R.id.p1_hand);
        p1_best_card = (ImageView) findViewById(R.id.p1_best_card);
        p2_score = (TextView) findViewById(R.id.p2_score);
        p1_score = (TextView) findViewById(R.id.p1_score);
        card_count = (TextView) findViewById(R.id.card_count);
        deck_count = (TextView) findViewById(R.id.deck_count);
        play_button = (Button) findViewById(R.id.play_button);
        reset_button = (Button) findViewById(R.id.reset_button);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.newgame);
        p1_cards = (LinearLayout) findViewById(R.id.p1_cards);

        mCards = new ArrayList<>();

        for (int i = 0; i < 13; i++) {

            int card_value = i + 2;
            String card_file_name = "card_".concat(Integer.toString(card_value + 10));

            mCards.add(new Cards("diamonds", card_value, card_file_name.concat("d"), 0,"none"));
            mCards.add(new Cards("clubs", card_value, card_file_name.concat("c"), 0,"none"));
            mCards.add(new Cards("hearts", card_value, card_file_name.concat("h"), 0,"none"));
            mCards.add(new Cards("spades", card_value, card_file_name.concat("s"), 0,"none"));

        }//mCards for loop

        center_deck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//P1
                    if(total_cards>0) {
                        int owner1 = 100;
                        int owner2 = 100;

                        int pick1 = 0;
                        int pick2 = 0;

                        while(owner1 > 0){
                            pick1 = r.nextInt(52);
                            owner1 = mCards.get(pick1).getOwner();
                        }

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(320,LinearLayout.LayoutParams.MATCH_PARENT);
                        lp.setMargins(50,50,50,50);

                        LinearLayout.LayoutParams lp_after = new LinearLayout.LayoutParams(320,LinearLayout.LayoutParams.MATCH_PARENT);
                        lp_after.setMargins(50,50,-320,50);

                        mCards.get(pick1).setOwner(1);
                        ImageView cardImage = new ImageView(NewGame.this);
                        cardImage.setTag(mCards.get(pick1).getFilename());
                        cardImage.setLayoutParams(lp);
                        if(mCards.get(pick1).getValue()>10){ cardImage.setColorFilter(Color.argb(50, 200, 120, 0));}
                        p1_cards.addView(cardImage);
                        int p1_current_card = getResources().getIdentifier(mCards.get(pick1).getFilename(),"drawable",getPackageName());
                        if(old_cardImage != null) { old_cardImage.setLayoutParams(lp_after); }
                        old_cardImage = cardImage;
                        cardImage.setImageResource(p1_current_card);
                        total_cards--;

                        while(owner2 > 0){
                            pick2 = r.nextInt(52);
                            owner2 = mCards.get(pick2).getOwner();
                        }

                        mCards.get(pick2).setOwner(2);
                        total_cards--;

                        card_count.setText(String.valueOf(52-total_cards));
                        deck_count.setText(String.valueOf(total_cards));
                    }

                    else
                    {
                        Snackbar.make(mRelativeLayout,"NO MORE CARDS",Snackbar.LENGTH_LONG).show();
                    }//if total cards > 0
                }
            });//greed_button

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_cards = 52;
                p1_score.setText("0");
                p2_score.setText("0");
                card_count.setText("0");
                p1_cards.removeAllViews();

                for (int i = 0; i < 13; i++) {

                    int card_value = i + 2;
                    String card_file_name = "card_".concat(Integer.toString(card_value + 10));

                    mCards.add(new Cards("diamonds", card_value, card_file_name.concat("d"), 0,"none"));
                    mCards.add(new Cards("clubs", card_value, card_file_name.concat("c"), 0,"none"));
                    mCards.add(new Cards("hearts", card_value, card_file_name.concat("h"), 0,"none"));
                    mCards.add(new Cards("spades", card_value, card_file_name.concat("s"), 0,"none"));

                }//mCards for loop


                Snackbar.make(mRelativeLayout,"BOARD RESET",Snackbar.LENGTH_LONG).show();

            }
        });


    }
}
