package com.mjkonceptz.aerolyfquizapp.helpercontract;

import android.provider.BaseColumns;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/10/18
 * Email: mjkonceptz@gmail.com
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 * CLASS ~ QuizContract Containing The Quiz App Database Tables & Fields.
 */

public final class QuizContract {

    private QuizContract() {} /*end: empty constructor */

    /*CODE DESCRIPTION: Category Table & Fields */
    public static class CategoriesTable implements BaseColumns {

        public static final String TABLE_NAME = "quiz_categories";
        public static final String COLUMN_NAME = "name";

    } /*end: CategoriesTable */

    /*CODE DESCRIPTION: Question Table & Fields */
    public static class QuestionsTable implements BaseColumns {
        public  static  final  String TABLE_NAME = "quiz_questions";
        public  static  final  String COLUMN_QUESTION = "question";
        public  static  final  String COLUMN_OPTION1 = "option1";
        public  static  final  String COLUMN_OPTION2 = "option2";
        public  static  final  String COLUMN_OPTION3 = "option3";
        public  static  final  String COLUMN_OPTION4 = "option4";
        public  static  final  String COLUMN_ANSWER_NUM = "answer_num";
        public  static  final  String COLUMN_DIFFICULTY = "difficulty";
        public  static  final  String COLUMN_CATEGORY_ID = "category_id";

    } /*end: QuestionsTable */


} /*end: CLASS ~ QuizContract */
