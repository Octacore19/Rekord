package com.chegsmania.rekord.ui;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chegsmania.rekord.R;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private EditText playerOneEditText;
    private EditText playerTwoEditText;
    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        playerOneEditText = findViewById(R.id.tv_player_one);
        playerTwoEditText = findViewById(R.id.tv_player_two);
        final Button playButton = findViewById(R.id.btn_play);
        loadingProgressBar = findViewById(R.id.loading);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String errorMsg = getResources().getString(R.string.empty_username_error_msg);
                if (!TextUtils.isEmpty(playerOneEditText.getText()) && !TextUtils.isEmpty(playerTwoEditText.getText())){
                    Intent intent = new Intent(LoginActivity.this, GameActivity.class);
                    intent.putExtra(GameActivity.PLAYER_ONE_INTENT_EXTRA, playerOneEditText.getText().toString().trim());
                    intent.putExtra(GameActivity.PLAYER_TWO_INTENT_EXTRA, playerTwoEditText.getText().toString().trim());
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    startActivity(intent);
                } else if (TextUtils.isEmpty(playerOneEditText.getText()) || TextUtils.isEmpty(playerTwoEditText.getText())){
                    Snackbar snackbar = Snackbar.make(v, errorMsg, Snackbar.LENGTH_LONG).setAction("Action", null);
                            View sbView = snackbar.getView();
                            sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorMint));
                            snackbar.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerOneEditText.setText("");
        playerTwoEditText.setText("");
        loadingProgressBar.setVisibility(View.GONE);
    }
}