package com.mjkonceptz.aerolyfquizapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 */

//CLASS: USER Details
public class Constants {

    //MARK: CONSTANTS ~ SharedPreferences (AerolyfQuestionTopicCategoryActivity).
    public static final String SHARED_PREFS = "sharedPrefsConfig";
    public static final String KEY_DISPLAY = "display";
    public static final String KEY_HIGHSCORE = "highScore";
    public static final String KEY_RATINGS = "rating";

    /* MARK:  CONSTANTS ~ OutSate [Orientation] Data Tokens (AerolyfQuizQuestionActivity) */
    public static final String KEY_SCORE = "keyScore";
    public static final String KEY_QUESTION_NUM = "keyQuestionNum";
    public static final String KEY_MILLISECS_LEFT = "keyMillisecsLeft";
    public static final String KEY_ANSWERED = "keyAnswered";
    public static final String KEY_QUESTION_LIST = "keyQuestionList";

    /* FUNCTIONS */
    /* MARK:  CONSTANTS ~ Clear The SharedPreferences For The New User (Activity Context) */
    public static void removeDataFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }//end: removeDataFromPref(Context context)






} // end: class Constants
