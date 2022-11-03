package com.mjkonceptz.aerolyfquizapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.mjkonceptz.aerolyfquizapp.R;
import com.mjkonceptz.aerolyfquizapp.model.Category;
import com.mjkonceptz.aerolyfquizapp.model.Question;
import com.mjkonceptz.aerolyfquizapp.sqlitedatabase.QuizDbHelper;
import com.mjkonceptz.aerolyfquizapp.utils.Constants;

import java.util.List;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * UI/UX Design: Kemmy MO Jones ~ (Mjkonceptz)
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 */

@SuppressWarnings("ALL")
public class AerolyfQuestionTopicCategoryActivity extends AppCompatActivity{

    /*CONSTANTS: Quiz Result Data. */
    public static final String EXTRA_PLAYER = "extraPlayerName";
    public static final String EXTRA_CATEGORY_ID = "extraCategoryID";
    public static final String EXTRA_CATEGORY_NAME = "extraCategoryName";
    public static final String EXTRA_DIFFICULTY = "extraDifficulty";

    /*VIEWS: Global Variables */
    private Spinner spinnerCategory;
    private Spinner spinnerDifficulty;
    private TextView txtPlayerNameEmail;
    private TextView txtHighScore;
    private TextView txtRatings;

    /*SETTINGS: Variables For Shared Preferences To Save Player's Score & Quiz Rating. */
    private int highScore;
    private float ratings;

    /*VIEW LIFECYCLE ~ onCreate */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* CODE DESCRIPTION: UI Design: Set The Window To Extend Behind The Status Bar. */
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        hideSystemBars();

        setContentView(R.layout.activity_aerolyf_question_topic_category);

        /* Instantiat Aboove Defined Variables */
        txtPlayerNameEmail = findViewById(R.id.txtPlayerNameEmail);

        txtHighScore = findViewById(R.id.txtHighScore);
        txtRatings = findViewById(R.id.txtRatings);

        spinnerCategory = findViewById(R.id.spinner_category);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        /* Record The Player's High Score Here */
        loadPlayerName();
        loadHighScore();
        loadRatings();

        /* Populate The Spinner With Categories & Diffciculty Levels. */
        loadCategories();
        loadDifficultyLevels();

        /* GoTo Quiz Activity Button. */
        Button btnStartQuiz = findViewById(R.id.btnStartQuiz);
        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoQuiz();
            } //end

        }); /*end: btnStartQuiz */

        /* Exit Quiz Start Page */
        Button btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(view -> {
            QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
            dbHelper.close();
            startActivity(new Intent(AerolyfQuestionTopicCategoryActivity.this, AerolyfAppLoginScreen.class));
            finish();

        }); /*end: btnExit */

    } /*end: onCreate */

    /* FUNCTIONS */
    /*SETTINGS: RESULT API FUNCTIONS ~ Retrieve Result Data & Override Activity To Display Player's Results. */
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        /*CODE DESCRIPTION: Retrieving Result Data From The Score Activity Using Intent Extra */
                        Intent intent = result.getData();
                        int percentScore = intent.getIntExtra(ScoreActivity.EXTRA_SCORE, 0);
                        float rvalue = intent.getFloatExtra(ScoreActivity.EXTRA_RATINGS, 0.0F);

                        /*Comment: Assert that intent is  not null and the Activity is accessing the incoming result data. */
                        assert intent != null;

                        /*Comment: Check & Update Player's Score Here. */
                        if (percentScore > highScore) {
                            updateHighScore(percentScore);
                        } /*end: if_score */

                        updateRatings(rvalue);

                    } /*end: if_resultCode */
                } /*end: onActivityResult */
            }); /*end: ActivityResultLauncher */

    /*SETTINGS: Configure The Device Back Button Behaviour.*/
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

        /* Comment: Hide the navigation bar */
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());
    } /*end: hideSystemBars() */

    /*CODE DESCRIPTION: Direct The Player To AerolyfQuizQuestion Activity*/
    private void gotoQuiz(){

        /*CODE DESCRIPTION: Retrieving Player's Name Data From AerolyfAppLoginScreen Using Intent Extra */
        Intent  intentName = getIntent();
        String nameOfPlayer = intentName.getStringExtra(AerolyfAppLoginScreen.EXTRA_NAMES);

        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();
        int categoryID = selectedCategory.getId();
        String categoryName = selectedCategory.getName();
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        /* CODE DESCRIPTION: Sending Data To AerolyQuizQuestion Activity Using Intent Extra */
        Intent intent = new Intent(AerolyfQuestionTopicCategoryActivity.this, AerolyfQuizQuestionActivity.class);
        intent.putExtra(EXTRA_PLAYER, nameOfPlayer);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryID);
        intent.putExtra(EXTRA_CATEGORY_NAME, categoryName);
        intent.putExtra(EXTRA_DIFFICULTY, difficulty);

        /* CODE DESCRIPTION: Result API Call To Request Result Data From AerolyQuizQuestion Activity */
        mStartForResult.launch(intent);

    } /*end: gotoQuiz() */

    /*CODE DESCRIPTION: Loading The Category Spinner With Data From The Database*/
    private void loadCategories(){
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();

        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);

    } /*end: loadCategories() */

    /*CODE DESCRIPTION: Loading The Quiz Difficulty Level Spinner With Data From The Database */
    private void loadDifficultyLevels(){
        String[] difficultyLevels = Question.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    } /*end: loadDifficultyLevels() */

    /*SETTINGS: Shared Preference To Handle The Displaying Player's Details. */
    /*MARK: LOAD PLAYER'S NAME & EMAIL */
    @SuppressLint("DefaultLocale")
    private void loadPlayerName(){

        SharedPreferences prefs  = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);

        String playerNameEmail = prefs.getString(Constants.KEY_DISPLAY, "");

        txtPlayerNameEmail.setText(playerNameEmail);

    } //end: loadPlayerName()

    /*MARK: LOAD PLAYER'S SCORE */
    @SuppressLint("DefaultLocale")
    private void loadHighScore(){
        SharedPreferences prefs  = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);

        highScore = prefs.getInt(Constants.KEY_HIGHSCORE, 0);

        txtHighScore.setText("HighScore: " + highScore + "% " );

    } //end: loadHighScore()

    /*MARK: LOAD PLAYER'S RATINGS */
    @SuppressLint("DefaultLocale")
    private void loadRatings(){
        SharedPreferences prefs  = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);

        ratings = prefs.getFloat(Constants.KEY_RATINGS, 0.0F);

        txtRatings.setText(" Quiz Rating: " + ratings + " ");

    } //end: loadRatings()

    /*SETTINGS: Shared Prefrence to Handle updating player's quiz details. */
    /*MARK: UPDATE PLAYER'S SCORE */
    private void updateHighScore(int highScoreNew){

        highScore = highScoreNew;
        txtHighScore.setText("HighScore: " + highScore + "%" );

        SharedPreferences prefs  = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(Constants.KEY_HIGHSCORE, highScore);
        editor.apply();


    } //end: updateHighScore()

    /*MARK: UPDATE PLAYER'S RATINGS */
    private void updateRatings(float ratingNew){

        ratings = ratingNew;
        txtRatings.setText(" Quiz Rating: " + ratings + "" );

        SharedPreferences prefs  = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putFloat(Constants.KEY_RATINGS, ratings);
        editor.apply();

    } //end: updateRating()

    /*SETTINGS: Configure The Device Back Button Behaviour. */
    @Override
    public void onBackPressed() {

        /* VIEW : Creating An Alert DialogBox To Display An Error Message */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AerolyfQuestionTopicCategoryActivity.this, R.style.customExitAlertDialog);

        /* CODE DESCRIPTION: Set Alert Dialog Title */
        alertDialogBuilder.setTitle(R.string.dialog_title);

        /* CODE DESCRIPTION: Set  Alert Dialog Icon: */
        alertDialogBuilder.setIcon(R.drawable.ic_alert);

        /* CODE DESCRIPTION: Set Alert Dialog Message */
        alertDialogBuilder.setMessage(R.string.dialog_message);

        /* CODE DESCRIPTION: Set Alert Cancel Dialog Button */
        alertDialogBuilder.setCancelable(false);

        /* COMMENT: Positive Alert Button  */
        alertDialogBuilder.setPositiveButton(R.string.dialog_positive_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAndRemoveTask();
            }
        });

        /* COMMENT: Negative Alert Button */
        alertDialogBuilder.setNegativeButton(R.string.dialog_negative_button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AerolyfQuestionTopicCategoryActivity.this, "Click The Start Quiz Button To Begin", Toast.LENGTH_SHORT).show();
            }
        });

        /* CODE DESCRIPTION: Create The Alert Dialog Box */
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        /* CODE DESCRIPTION - UI: Set Alert Dialog Positive Button */
        Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(AerolyfQuestionTopicCategoryActivity.this, R.color.dialogPositiveButtonColor1));
        buttonPositive.setTextSize(16F);

        /* CODE DESCRIPTION - UI: Set Alert Dialog Negative Button */
        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(AerolyfQuestionTopicCategoryActivity.this,R.color.dialogNegativeButtonColor1));
        buttonNegative.setTextSize(16F);


    } /*end: onBackPressed() */

} /*end: CLASS ~ AerolyfQuestionTopicCategoryActivity */

