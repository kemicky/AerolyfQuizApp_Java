package com.mjkonceptz.aerolyfquizapp.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.android.material.snackbar.Snackbar;
import com.mjkonceptz.aerolyfquizapp.R;
import com.mjkonceptz.aerolyfquizapp.utils.Constants;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * UI/UX Design: Kemmy MO Jones ~ (Mjkonceptz)
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 */

/*VIEW LIFECYCLE ~ onCreate */
@SuppressLint("CustomSplashScreen")
public class AerolyfAppLoginScreen extends AppCompatActivity {

    /* CONSTANTS: Intent Extra */
    public static final String EXTRA_NAMES = "extraPlayerName";

    /* UI - Views (TextInput Layout). */
    private EditText txtName;
    private EditText txtPassword;

    /* VIEW LIFECYCLE ~ onCreate */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* CODE DESCRIPTION: UI Design: Set the Window to extend behind the status bar */
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false );
        hideSystemBars();

        setContentView(R.layout.activity_aerolyf_app_login_screen);

        /* CODE DESCRIPTION: VIEWS- Initializing the TextInput Layout Edit Text */
        txtName = findViewById(R.id.txtName);
        txtPassword = findViewById(R.id.txtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        /*VIEWS: Login & Register Buttons */
        //noinspection Convert2Lambda
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                /*NAVIGATION To AerolyfQuizQuestion Dashboard */
                gotoCategory();
            }

        }); /*end: btnLogin */

        //noinspection Convert2Lambda
        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRegister();
            }

        }); /*end: btnRegister */

    } /*end: onCreate */

    /* FUNCTIONS */
    /* SETTINGS: Configure The Device Back Button Behaviour.*/
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
    }/*end: hideSystemBars() */

    /*CODE DESCRIPTION: Direct The Player To The Quiz Dashboard If Login Is Successful .*/
    private void gotoCategory(){

        /* CODE DESCRIPTION: Name Input Validation & Button To Display A Message In DialogBox. */
        if(TextUtils.isEmpty(txtName.getText().toString()) || (TextUtils.isEmpty(txtPassword.getText().toString()))){

            /* DONE: VIEW - Insert A DialogBox Here: */
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AerolyfAppLoginScreen.this, R.style.customLoginErrorAlertDialog);

            /* CODE DESCRIPTION: Set Alert Dialog Title: */
            alertDialogBuilder.setTitle(R.string.login_dialog_title);

            /* CODE DESCRIPTION: Set  Alert Dialog Icon: */
            alertDialogBuilder.setIcon(R.drawable.ic_info_exit);

            /* CODE DESCRIPTION: Set Alert Dialog Message & Settings: */
            alertDialogBuilder.setMessage(R.string.login_dialog_message);

            /* CODE DESCRIPTION: Set Alert Cancel Dialog Button Settings: */
            alertDialogBuilder.setCancelable(false);

            /* COMMENT: Positive Alert Button:  */
            alertDialogBuilder.setPositiveButton(R.string.dialog_ok_button_text, null);

            /* CODE DESCRIPTION:  Create The Alert Dialog Box: */
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            /* CODE DESCRIPTION - UI: Set Alert Dialog Button Settings */
            Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            buttonPositive.setTextColor(ContextCompat.getColor(AerolyfAppLoginScreen.this, R.color.dialog_ok_btn));
            buttonPositive.setTextSize(16F);

        } else {

            /* SETTINGS: Shared Preference To Handle The Displaying Player's Details. */
            String playerName = "Name: " + txtName.getText().toString();
            String playerPassword = "Password: " + txtPassword.getText().toString();

            SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);
            String userDetails = prefs.getString(playerName + playerPassword + "data", "Name Or Password Is Invalid");
            SharedPreferences.Editor editor = prefs.edit();

            if(prefs.contains(playerName + playerPassword + "data")){
                editor.putString(Constants.KEY_DISPLAY, userDetails);
                editor.apply();

                Intent intentPlayer = new Intent(AerolyfAppLoginScreen.this, AerolyfQuestionTopicCategoryActivity.class);
                intentPlayer.putExtra(EXTRA_NAMES, playerName);
                intentPlayer.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intentPlayer);
                finish();
            }
            else {
                txtName.setText("");
                txtPassword.setText("");
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Details Does Not Exist !" + "\n" + "New User ?, Proceed To Register", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }//end: if-else

    } //end: gotoCategory()

    /*CODE DESCRIPTION: Re-Direct New Player To Register Details Before To Get Access To Log-In To The App.*/
    private void gotoRegister(){
        Constants.removeDataFromPref(AerolyfAppLoginScreen.this);
        Intent intentRegisterPlayer = new Intent(AerolyfAppLoginScreen.this, AerolyfRegisterActivity.class);
        startActivity(intentRegisterPlayer);
        finish();
    }//end: gotoRegister()

    /* SETTINGS: Configure The Device Back Button Behaviour. */
    @Override
    public void onBackPressed() {

        /*CODE DESCRIPTION: VIEW ~ Creating An Alert DialogBox */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AerolyfAppLoginScreen.this, R.style.customExitAlertDialog);

        /* CODE DESCRIPTION: Set Alert Dialog Title */
        alertDialogBuilder.setTitle(R.string.dialog_title);

        /* CODE DESCRIPTION: Set  Alert Dialog Icon */
        alertDialogBuilder.setIcon(R.drawable.ic_alert);

        /* CODE DESCRIPTION: Set Alert Dialog Message */
        alertDialogBuilder.setMessage(R.string.dialog_message);

        /* CODE DESCRIPTION: Set Alert Cancel Dialog Button */
        alertDialogBuilder.setCancelable(false);

        /* COMMENT: Positive Alert Button:  */
        alertDialogBuilder.setPositiveButton(R.string.dialog_positive_button_text, (DialogInterface dialogInterface, int i) -> finishAndRemoveTask());

        /* COMMENT: Negative Alert Button: */
        alertDialogBuilder.setNegativeButton(R.string.dialog_negative_button_text, (dialogInterface, i) -> Toast.makeText(AerolyfAppLoginScreen.this, "Login To Quiz Dashboard", Toast.LENGTH_SHORT).show());

        /* CODE DESCRIPTION:  Create The Alert Dialog Box: */
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        /* CODE DESCRIPTION - UI: Set Alert Dialog Positive Button */
        Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(AerolyfAppLoginScreen.this,R.color.dialogPositiveButtonColor1));
        buttonPositive.setTextSize(16F);

        /* CODE DESCRIPTION - UI: Set Alert Dialog Negative Button */
        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(AerolyfAppLoginScreen.this,R.color.teal_700));
        buttonNegative.setTextSize(16F);

    }//end:- onBackPressed()

} //end: Class ~ AerolyfQuizAppLaunchScreen


