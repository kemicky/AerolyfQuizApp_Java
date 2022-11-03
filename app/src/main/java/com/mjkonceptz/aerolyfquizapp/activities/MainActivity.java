package com.mjkonceptz.aerolyfquizapp.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.mjkonceptz.aerolyfquizapp.R;
import com.mjkonceptz.aerolyfquizapp.sqlitedatabase.QuizDbHelper;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * UI/UX Design: Kemmy MO Jones ~ (Mjkonceptz)
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 */

@SuppressWarnings("FieldCanBeLocal")
@SuppressLint("CustomSplashScreen")
public class MainActivity extends AppCompatActivity {

    /* VIEWS: SplashScreen Timer */
    private Timer splashTimer;

    /* VARIABLES */
    private static final long DELAY = 10000;
    boolean scheduled = false;

    /* VIEW LIFECYCLE ~ onCreate */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* CODE DESCRIPTION: UI Design: Set The Window To Extend Behind The Status Bar. */
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false );
        hideSystemBars();

        setContentView(R.layout.activity_main);

        /* View: Initializing Splash Screen Timer. */
        splashTimer = new Timer();

        /*SETTINGS: Splash Screen Timer Configuration To Navigate To Aerolyf Quiz App Login Screen. */
        splashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, AerolyfAppLoginScreen.class);
                startActivity(intent);
                finish();
            }
        },DELAY);
        scheduled = true;

        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        try {
            dbHelper.createDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbHelper.openDatabase();

    } /* end: onCreate */


    /* SETTINGS: Configure The Device Back Button Behaviour. */
    @SuppressWarnings("deprecation")
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

    } /*end: hideSystemBars */


} /*end: CLASS: end_MainActivity  */


