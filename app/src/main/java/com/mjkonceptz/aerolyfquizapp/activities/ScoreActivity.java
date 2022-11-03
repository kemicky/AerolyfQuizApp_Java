package com.mjkonceptz.aerolyfquizapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.mjkonceptz.aerolyfquizapp.R;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * UI/UX Design: Kemmy MO Jones ~ (Mjkonceptz)
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 */

@SuppressWarnings("ALL")
public class ScoreActivity extends AppCompatActivity {

    /* CONSTANTS: Quiz Result Data. */
    public static final String EXTRA_SCORE = "extraScore";
    public static final String EXTRA_RATINGS = "extraRating";

    /* VARIABLES: Global Variables */
    private int percentScore;
    private float rvalue;

    /* VIEW LIFECYCLE ~ onCreate */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*CODE DESCRIPTION: UI Design: Set The Window To Extend Behind The Status Bar. */
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        hideSystemBars();

        setContentView(R.layout.activity_score);

        /* CODE DESCRIPTION: Retrieving Data From The Quiz Category Dashboard Using Intent Extra */
        Intent intent = getIntent();
        String player = intent.getStringExtra(AerolyfQuizQuestionActivity.EXTRA_PLAYERS_NAME);
        int questionCountTotal = intent.getIntExtra(AerolyfQuizQuestionActivity.EXTRA_TOTAL_QUESTION, 0);
        int score = intent.getIntExtra(AerolyfQuizQuestionActivity.EXTRA_TOTAL_SCORE, 0);
        percentScore =  score * 100 / questionCountTotal;


        /*VIEWS: Initializing The Layout Views */
        TextView txtNames = findViewById(R.id.txtNames);
        TextView txtMtScore = findViewById(R.id.txtMtScore);
        TextView txtScoreDisplay = findViewById(R.id.txtScoreDisplay);
        RatingBar ratingStar = findViewById(R.id.ratingStar);
        TextView txtRating = findViewById(R.id.txtRating);

        /* COMMENT: Displaying TextView Labels &  Quiz Result Details */
        txtNames.setText(player + " ");
        txtScoreDisplay.setText(" Score: " + score + " out of " + questionCountTotal + " Questions ");
        txtMtScore.setText(" Percentage Score: " + percentScore + "% ") ;


        rvalue = ratingStar.getRating();

        /* FUNCTIONS */
        /* VIEW: Rating Bar Displaying Quiz Ratings */
        ratingStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                txtRating.setText(" Rating: " + String.valueOf(rating) + " ");

            }
        }); /* end: OnRatingchangeListener() */


        /* VIEWS: Home Button */
        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*CODE DESCRIPTION: Sending Requested Quiz Results To The AerolyfQuestionTopicCategory Activity To Display Result & Close ScoreActivity.*/
                rvalue = ratingStar.getRating();
                Intent resultIntent = new Intent(ScoreActivity.this, AerolyfQuestionTopicCategoryActivity.class);
                resultIntent.putExtra(EXTRA_SCORE, percentScore);
                resultIntent.putExtra(EXTRA_RATINGS, rvalue);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }); /* end: btnHome() */

    } /* end: OnCreate */

    /* FUNCTIONS */
    /* SETTINGS: Configure The Device Back Button Behaviour.*/
    private void hideSystemBars() {

        /*Comment: Calling The Device Systems Bars Configuration From The WindowInsetController Library */
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }

        /*Comment: Configure the behavior of the hidden system bars */
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );

        /*Comment: Hide the navigation bar */
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
    } /*end: hideSystemBars() */

    /* SETTINGS: Configure The Device Back Button Behaviour. */
    @Override
    public void onBackPressed() {

        /* VIEW : Creating An Alert DialogBox To Display An Error Message */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScoreActivity.this, R.style.customScoreAlertDialog);

        /* CODE DESCRIPTION: Set Alert Dialog Title*/
        alertDialogBuilder.setTitle(R.string.score_dialog_title);

        /* CODE DESCRIPTION: Set  Alert Dialog Icon: */
        alertDialogBuilder.setIcon(R.drawable.ic_info_warning);

        /* CODE DESCRIPTION: Set Alert Dialog Message */
        alertDialogBuilder.setMessage(R.string.score_dialog_message);

        /* CODE DESCRIPTION: Set Alert Cancel Dialog Button */
        alertDialogBuilder.setCancelable(false);

        /* COMMENT: Positive Alert Button */
        alertDialogBuilder.setPositiveButton(R.string.dialog_ok_button_text, null);

        /* CODE DESCRIPTION: Create The Alert Dialog Box */
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        /* CODE DESCRIPTION - UI: Set Alert Dialog Positive Button */
        Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(ScoreActivity.this, R.color.my_color_stroke3));
        buttonPositive.setTextSize(16F);

    } /*end: onBackPressed() */


} /*end: CLASS ~ ScoreActivity */


