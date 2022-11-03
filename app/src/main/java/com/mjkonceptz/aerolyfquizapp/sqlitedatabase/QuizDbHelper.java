package com.mjkonceptz.aerolyfquizapp.sqlitedatabase;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mjkonceptz.aerolyfquizapp.helpercontract.QuizContract.CategoriesTable;
import com.mjkonceptz.aerolyfquizapp.helpercontract.QuizContract.QuestionsTable;
import com.mjkonceptz.aerolyfquizapp.model.Category;
import com.mjkonceptz.aerolyfquizapp.model.Question;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 * CLASS ~ SQLite Database Helper.
 */

public class QuizDbHelper extends SQLiteOpenHelper {

    /*VARIABLES: Global Variables */
    private final Context mContext;
    private  static  String DB_PATH = "";
    private  static  final String DATABASE_NAME = "AerolyfQuiz.db";
    private  static  final int DATABASE_VERSION = 1;

    @SuppressLint("StaticFieldLeak")
    private  static QuizDbHelper instance;
    private  SQLiteDatabase db;


    /* FUNCTIONS: SQLite Database */
    /* SETTINGS: Database Name Configuration & Integration To The Quiz App Application Interface Via Context Library. */
    private QuizDbHelper(Context context) {

        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        DB_PATH = context.getApplicationInfo().dataDir+"/databases/";

        this.mContext = context;
    }/*end: QuizDbHelper */

    /* SETTINGS: Database Configuration: Create A Database File In The App. */
    public void createDatabase()  {

        boolean isDBExist = checkDatabase();

        //noinspection StatementWithEmptyBody
        if(isDBExist){
            //COMMENT: Do Nothing
        } else{
            this.getReadableDatabase();
            try{
                copyDatabase();

            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        } //end: if_else
    } //end: createDatabase()

    /* SETTINGS: Database Configuration: Create An Instance Of The Database & Synchronize With The Quiz App Interface. */
    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    } /* end: getInstance(context) */

    /* SETTINGS: Database Configuration: Checking if the Database Exists. */
    private Boolean checkDatabase(){

        SQLiteDatabase tempDB = null;
        try{
            String dbPath = DB_PATH + DATABASE_NAME;
             tempDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e ){

            try {
                mContext.deleteDatabase("AerolyfQuiz.db");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } //end: try_catch

        if (tempDB != null){
            tempDB.close();
        }

        return tempDB != null;

    } //end: checkDatabase()

    /* SETTINGS: Database Configuration: If The Database Doesn't Exist Then Copy It Into The App. */
    public void copyDatabase(){

        try {

            AssetManager assetManager = mContext.getAssets();
            InputStream dbInputStream = assetManager.open(DATABASE_NAME);
            String outputFileName = DB_PATH+DATABASE_NAME;
            OutputStream dbOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInputStream.read(buffer)) > 0) {
                dbOutput.write(buffer,0,length);
            }
            dbOutput.flush();
            dbOutput.close();
            dbInputStream.close();

        } catch(IOException e) {
            e.printStackTrace();
        } //end: try_catch

    } //end: CopyDatabase()

    /* SETTINGS: Database Configuration: Open The Database When Its Needed. */
   public void openDatabase() throws SQLException {
        String dbPath = DB_PATH + DATABASE_NAME;
        SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.CREATE_IF_NECESSARY);
    } //end: openDatabase()

    /* SETTINGS: Close Database Configuration ~ Database Must Be Closed After Use (Cost Effective). */
    @Override
    public synchronized void close() {
        if(db != null){
            db.close();
        }
        super.close();
    } //end: close()

    /* SETTINGS: Creating The Database Tables, Configuration & Populating of The
                Database Table's Fields With Data From The App To SQLite Studio. */
    @Override
    public void onCreate(SQLiteDatabase db) {

    } /* end: onCreate(SQLiteDatabase db) */

    /* SETTINGS: Updating The Database Tables In Case New Data(s) Are Removed Or Added To The
                         Database From The App From The App To SQLite Studio. */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    } /*end onUpgrade */

    /* SETTINGS: Performing SQL Query on Category Table To Extract All Quiz Topic Categories In The Table To Create A List. */
    @SuppressLint("Range")
    public List<Category> getAllCategories(){
        List<Category> categoryList = new ArrayList<>();

        db = getReadableDatabase();

        try{
            Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

            if(c.moveToFirst()){
                do{
                    Category category = new Category();
                    category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                    category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                    categoryList.add(category);
                } while (c.moveToNext());
            } //end: if

            c.close();

        } catch(Exception er){
            er.printStackTrace();
        }//end: try_catch

        db.close();
        return categoryList;

    } /*end: getAllCategory*/

    /* SETTINGS: Performing SQL Query on Question Tables To Extract All Quiz Questions In The Table To Create An Array Of Questions List. */
    @SuppressLint("Range")
    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        try{
        Cursor c = db.rawQuery("SELECT * FROM "
                + QuestionsTable.TABLE_NAME, null);

            if(c.moveToFirst()) {
                do {

                    Question question = new Question();
                    question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                    question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                    question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                    question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                    question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                    question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                    question.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUM)));
                    question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                    question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                    questionList.add(question);

                } while (c.moveToNext());
            } //end: if
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
        } //end: try_catch

        db.close();
        return questionList;

    } /*end: List<getAllQuestion> */

    /* SETTINGS: Performing SQL Query on Question Tables To Extract Each Quiz Questions In The Table To Create An Array Of Questions With Difficulty Levels & Topic Category List. */
    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(int categoryID, String difficulty){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID  + " = ? "
                + " AND " + QuestionsTable.COLUMN_DIFFICULTY  + " = ? ";

        String[] selectionArgs = new String[] {String.valueOf(categoryID), difficulty};

        try {
            Cursor c = db.query(
                    QuestionsTable.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            if ( c.moveToFirst() ) {
                do {

                    Question question = new Question();
                    question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                    question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                    question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                    question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                    question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                    question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                    question.setAnswerNum(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUM)));
                    question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                    question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                    questionList.add(question);

                } while (c.moveToNext());
            } //end: if
            c.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }//end: try_catch

        db.close();
        return questionList;


    } /*end: ArrayList<getQuestions> */


}//end: class_QuizHelperDB



