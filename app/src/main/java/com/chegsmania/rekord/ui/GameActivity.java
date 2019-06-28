package com.chegsmania.rekord.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chegsmania.rekord.R;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private static final String LOG_TAG = GameActivity.class.getSimpleName();

    public static final String PLAYER_ONE_INTENT_EXTRA = LOG_TAG + "PLAYER_ONE";
    public static final String PLAYER_TWO_INTENT_EXTRA = LOG_TAG + "PLAYER_TWO";

    private String mPlayerOneDisplayName;
    private String mPlayerTwoDisplayName;
    private int playerOnePointScored;
    private int playerTwoPointScored ;
    private int playerOneSetPoint = 0;
    private int playerTwoSetPoint = 0;
    private int playerOneServePoint = 0;
    private int playerTwoServePoint = 0;

    private TextView mPlayerOneDisplayTextView;
    private TextView mPlayerOnePointScoredTextView;
    private TextView mPlayerOneServeScoreTextView;
    private TextView mPlayerOneSetScoreTextView;
    private TextView mPlayerOnePointLabelTextView;
    private CardView mPlayerOneCardView;

    private TextView mPlayerTwoDisplayTextView;
    private TextView mPlayerTwoPointScoredTextView;
    private TextView mPlayerTwoServeScoreTextView;
    private TextView mPlayerTwoSetScoreTextView;
    private TextView mPlayerTwoPointLabelTextView;
    private CardView mPlayerTwoCardView;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_tennis_ball);
        getSupportActionBar().setTitle("  Rekord");

        final Intent intent = getIntent();
        mPlayerOneDisplayName = intent.getExtras().getString(PLAYER_ONE_INTENT_EXTRA);
        mPlayerTwoDisplayName = intent.getExtras().getString(PLAYER_TWO_INTENT_EXTRA);

        mPlayerOneDisplayTextView = findViewById(R.id.playerone_display_name_textview);
        mPlayerOnePointScoredTextView = findViewById(R.id.playerone_point_score_textview);
        mPlayerOneServeScoreTextView = findViewById(R.id.playerone_serve_score_textview);
        mPlayerOneSetScoreTextView = findViewById(R.id.playerone_set_score_textview);
        mPlayerOnePointLabelTextView = findViewById(R.id.playerone_point_label_textview);
        mPlayerTwoPointLabelTextView = findViewById(R.id.playertwo_point_label_textview);
        mPlayerOneCardView = findViewById(R.id.playerone_cardview);

        mPlayerTwoDisplayTextView = findViewById(R.id.playertwo_display_name_textview);
        mPlayerTwoPointScoredTextView = findViewById(R.id.playertwo_point_score_textview);
        mPlayerTwoServeScoreTextView = findViewById(R.id.playertwo_serve_score_textview);
        mPlayerTwoSetScoreTextView = findViewById(R.id.playertwo_set_score_textview);
        mPlayerTwoCardView = findViewById(R.id.playertwo_cardview);

        mPlayerOneDisplayTextView.setText(mPlayerOneDisplayName);
        mPlayerTwoDisplayTextView.setText(mPlayerTwoDisplayName);

        if (playerOnePointScored == 0 && playerTwoPointScored == 0){
            mPlayerOnePointLabelTextView.setVisibility(View.GONE);
            mPlayerTwoPointLabelTextView.setVisibility(View.GONE);
        }

        mPlayerOneCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerOnePointScored < 60) {
                    if (playerOnePointScored < 30) {
                        playerOnePointScored = playerOnePointScored + 15;
                    } else {
                        playerOnePointScored = playerOnePointScored + 10;
                    }
                    mPlayerOnePointScoredTextView.setText(String.valueOf(playerOnePointScored));
                    mPlayerOnePointLabelTextView.setVisibility(View.VISIBLE);
                }
                if (playerOnePointScored == 40 && playerTwoPointScored == 40 || playerOnePointScored == 50 && playerTwoPointScored == 50){
//                    mPlayerTwoPointScoredTextView.setText(String.valueOf(playerTwoPointScored));
                    displayDeuceDialogPopup(getString(R.string.deuce));
                }
                if (playerOnePointScored == 50 && playerTwoPointScored == 40){
                    mPlayerOnePointScoredTextView.setText(getString(R.string.advantage));
                    mPlayerOnePointLabelTextView.setVisibility(View.GONE);
                    mPlayerTwoPointScoredTextView.setText(String.valueOf(playerTwoPointScored));
                }else if (playerOnePointScored == 60){
                    mPlayerOnePointScoredTextView.setText(getString(R.string.game_point));
                    mPlayerOnePointLabelTextView.setVisibility(View.GONE);
                    handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            increasePlayerOneServePoint();
                        }
                    }, 2000);
                }else
                if (playerOnePointScored == 50 && playerOnePointScored > playerTwoPointScored){
                    mPlayerOnePointScoredTextView.setText(getString(R.string.game_point));
                    mPlayerOnePointLabelTextView.setVisibility(View.GONE);
                    handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            increasePlayerOneServePoint();
                        }
                    }, 2000);
                }
            }
        });

        mPlayerTwoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerTwoPointScored < 60){
                    if (playerTwoPointScored < 30){
                        playerTwoPointScored = playerTwoPointScored + 15;
                    } else {
                        playerTwoPointScored = playerTwoPointScored + 10;
                    }
                    mPlayerTwoPointScoredTextView.setText(String.valueOf(playerTwoPointScored));
                    mPlayerTwoPointLabelTextView.setVisibility(View.VISIBLE);
                }
                if (playerOnePointScored == 40 && playerTwoPointScored == 40 || playerOnePointScored == 50 && playerTwoPointScored == 50){
//                    mPlayerOnePointScoredTextView.setText(String.valueOf(playerOnePointScored));
                    displayDeuceDialogPopup(getString(R.string.deuce));
                }
                if (playerTwoPointScored == 50 && playerOnePointScored == 40){
                    mPlayerTwoPointScoredTextView.setText(getString(R.string.advantage));
                    mPlayerTwoPointLabelTextView.setVisibility(View.GONE);
                    mPlayerOnePointScoredTextView.setText(String.valueOf(playerOnePointScored));
                }else if (playerTwoPointScored == 60){
                    mPlayerTwoPointScoredTextView.setText(getString(R.string.game_point));
                    mPlayerTwoPointLabelTextView.setVisibility(View.GONE);
                    handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            increasePlayerTwoServePoint();
                        }
                    }, 2000);
                }
                else if (playerTwoPointScored == 50 && playerTwoPointScored > playerOnePointScored){
                    mPlayerTwoPointScoredTextView.setText(getString(R.string.game_point));
                    mPlayerTwoPointLabelTextView.setVisibility(View.GONE);
                    handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            increasePlayerTwoServePoint();
                        }
                    }, 2000);
                }
            }
        });
    }

    private void increasePlayerOneServePoint() {
        playerOneServePoint++;
        mPlayerOneServeScoreTextView.setText(String.valueOf(playerOneServePoint));
        playerOnePointScored = 0;
        playerTwoPointScored = 0;
        mPlayerOnePointScoredTextView.setText(getString(R.string.love_string));
        mPlayerTwoPointScoredTextView.setText(getString(R.string.love_string));
        mPlayerOnePointLabelTextView.setVisibility(View.GONE);
        mPlayerTwoPointLabelTextView.setVisibility(View.GONE);

        if (playerOneServePoint >= 6 && (playerOneServePoint - playerTwoServePoint) >= 2){
            playerOneSetPoint++;
            mPlayerOneSetScoreTextView.setText(String.valueOf(playerOneSetPoint));
            playerOneServePoint = 0;
            playerTwoServePoint = 0;
            mPlayerOneServeScoreTextView.setText(String.valueOf(playerOneServePoint));
            mPlayerTwoServeScoreTextView.setText(String.valueOf(playerTwoServePoint));
        }
    }

    private void increasePlayerTwoServePoint() {
        playerTwoServePoint++;
        mPlayerTwoServeScoreTextView.setText(String.valueOf(playerTwoServePoint));
        playerOnePointScored = 0;
        playerTwoPointScored = 0;
        mPlayerOnePointScoredTextView.setText(getString(R.string.love_string));
        mPlayerTwoPointScoredTextView.setText(getString(R.string.love_string));
        mPlayerOnePointLabelTextView.setVisibility(View.GONE);
        mPlayerTwoPointLabelTextView.setVisibility(View.GONE);

        if (playerTwoServePoint >= 6 && (playerTwoServePoint - playerOneServePoint) >= 2){
            playerTwoSetPoint++;
            mPlayerTwoSetScoreTextView.setText(String.valueOf(playerTwoSetPoint));
            playerOneServePoint = 0;
            playerTwoServePoint = 0;
            mPlayerOneServeScoreTextView.setText(String.valueOf(playerOneServePoint));
            mPlayerTwoServeScoreTextView.setText(String.valueOf(playerTwoServePoint));
        }
    }

    private void displayDeuceDialogPopup(final String msg){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.activity_game_popup, null);
        TextView text = dialogView.findViewById(R.id.deuce_popup_textview);
        text.setText(msg);
        playerOnePointScored = 40;
        playerTwoPointScored = 40;
        final AlertDialog popupDialog = new AlertDialog.Builder(this).create();
        popupDialog.setView(dialogView);
        popupDialog.setCancelable(true);
        popupDialog.show();

        handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                mPlayerOnePointScoredTextView.setText(String.valueOf(playerOnePointScored));
                mPlayerTwoPointScoredTextView.setText(String.valueOf(playerTwoPointScored));
                mPlayerOnePointLabelTextView.setVisibility(View.VISIBLE);
                mPlayerTwoPointLabelTextView.setVisibility(View.VISIBLE);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                popupDialog.dismiss();
            }
        }, 2000);

        /*final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                popupDialog.dismiss();
                timer.cancel();
            }
        }, 2000);*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}