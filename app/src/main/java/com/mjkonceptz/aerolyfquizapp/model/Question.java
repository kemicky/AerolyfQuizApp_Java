package com.mjkonceptz.aerolyfquizapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 * CLASS ~ Question Data Fields
 */

@SuppressWarnings("unused")
public class Question implements Parcelable {

    /* Constants: Quiz Difficulty Levels */
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    /*VARIABLES: Global Variables */
    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNum;
    private String difficulty;
    private int categoryID;

    public Question() {} /*end: empty constructor */

    public Question(String question, String option1, String option2, String option3,
                    String option4, int answerNum, String difficulty, int categoryID) {

        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNum = answerNum;
        this.difficulty = difficulty;
        this.categoryID = categoryID;


    }  /*end: Question() ~ constructor with parameter */


    protected Question(Parcel in) {

        id = in.readInt();
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        option4 = in.readString();
        answerNum = in.readInt();
        difficulty = in.readString();
        categoryID = in.readInt();

    } /*end: Question(Parcel in) */

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeString(option4);
        dest.writeInt(answerNum);
        dest.writeString(difficulty);
        dest.writeInt(categoryID);

    } /*end: Question(WriteToParcel) */

    @Override
    public int describeContents() {
        return 0;
    } /*end: describeContents */

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        } /*end: createFromParcel */

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        } /*end: Question[] */
    }; /*end: Creator<Question> */

    /*CODE DESCRIPTION: Getter & Setter */
    public int getId() { return id; }

    public void setId(int id) { this.id = id; } /*end: id */

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    } /*end: question */

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    } /*end: option1 */

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    } /*end: option2 */

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    } /*end: option3 */

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    } /*end: option4 */

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    } /*end: answerNum */

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    } /*end: difficulty */

    public int getCategoryID() { return categoryID; }

    public void setCategoryID(int categoryID) { this.categoryID = categoryID; } /*end: categoryID */

    public static String[] getAllDifficultyLevels(){
        return new String[]{
                DIFFICULTY_EASY,
                DIFFICULTY_MEDIUM,
                DIFFICULTY_HARD
        };

    } /*end: getAllDifficultyLevels() */

} /* end: CLASS ~ Question */
