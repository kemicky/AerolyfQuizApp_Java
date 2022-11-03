package com.mjkonceptz.aerolyfquizapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AerolyfRegisterActivity extends AppCompatActivity {

    /* Views: Global Variable Definition */
    private EditText txtNameReg;
    private EditText txtEmailReg;
    private EditText txtPasswordReg;

    /* VIEW LifeCycle ~ onCreate */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* CODE DESCRIPTION: UI Design: Set the Window to extend behind the status bar */
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false );
        hideSystemBars();

        setContentView(R.layout.activity_aerolyf_register);

        /*VIEWS- Initializing The Views (TextInputs) */
        txtNameReg = findViewById(R.id.txtNameReg);
        txtEmailReg = findViewById(R.id.txtEmailReg);
        txtPasswordReg = findViewById(R.id.txtPasswordReg);

        /* VIEWS: Register Button */
        Button btnRegisterReg = findViewById(R.id.btnRegisterReg);
        btnRegisterReg.setOnClickListener(view -> registerPlayer()); /* end: btnRegisterReg */


    } /* end: onCreate */

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
    }/* end: hideSystemBars() */

    /* FUNCTIONS */

    /*CODE DESCRIPTION: Register New Player Details & Re-Direct To Quiz App Login Screen*/
    private void registerPlayer(){

        /* CODE DESCRIPTION: TextFields Input Validation & Button To Display A Message In a DialogBox. */
        if( TextUtils.isEmpty(txtNameReg.getText().toString()) || (TextUtils.isEmpty(txtEmailReg.getText().toString())) ||
                (TextUtils.isEmpty(txtPasswordReg.getText().toString()))){

            /* VIEW : Creating An Alert DialogBox To Display An Error Message */
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AerolyfRegisterActivity.this, R.style.customLoginErrorAlertDialog);

            /* CODE DESCRIPTION: Set Alert Dialog Title */
            alertDialogBuilder.setTitle(R.string.register_dialog_title);

            /* CODE DESCRIPTION: Set  Alert Dialog Icon */
            alertDialogBuilder.setIcon(R.drawable.ic_alert);

            /* CODE DESCRIPTION: Set Alert Dialog Message */
            alertDialogBuilder.setMessage(R.string.register_dialog_message);

            /* CODE DESCRIPTION: Set Alert Cancel Dialog Button */
            alertDialogBuilder.setCancelable(false);

            /* COMMENT: Positive Alert Button:  */
            alertDialogBuilder.setPositiveButton(R.string.dialog_ok_button_text, null);

            /* CODE DESCRIPTION:  Create The Alert Dialog Box: */
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            /* CODE DESCRIPTION - UI: Set Alert Dialog Positive Button */
            Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            buttonPositive.setTextColor(ContextCompat.getColor(AerolyfRegisterActivity.this, R.color.dialogPositiveButtonColor1));
            buttonPositive.setTextSize(16F);

        } else {

            /* SETTINGS: SHARED PREFERENCES ~ Saving User Details In Shared Preferences For Log-In. */
            SharedPreferences prefs = getSharedPreferences(Constants.SHARED_PREFS, MODE_PRIVATE);

            /* CONSTANTS : SharedPreferences Data And Keys */
            String newPlayerName = "Name: " + txtNameReg.getText().toString();
            String newPlayerEmail = "Email: " + txtEmailReg.getText().toString();
            String newPlayerPassword = "Password: " + txtPasswordReg.getText().toString();

            /* CODE DESCRIPTION: Writing The User Details Into SharedPreferences Editor. */
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(newPlayerName + newPlayerPassword + "data",  newPlayerName + "\n" + newPlayerEmail);
            editor.apply();

            /* COMMENT: Send User BackTo Login Page After Registration . */
            Intent intentRegisterPlayer = new Intent(AerolyfRegisterActivity.this, AerolyfAppLoginScreen.class);

            /*VIEW - DialogBox To Display User Details Registration Was Successful  */
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AerolyfRegisterActivity.this, R.style.customSuccessDialog);

            /* CODE DESCRIPTION: Set Alert Dialog Title */
            alertDialogBuilder.setTitle(R.string.success_dialog_title);

            /* CODE DESCRIPTION: Set  Alert Dialog Icon */
            alertDialogBuilder.setIcon(R.drawable.ic_info_success);

            /* CODE DESCRIPTION: Set Alert Dialog Message */
            alertDialogBuilder.setMessage(R.string.success_dialog_message);

            /* CODE DESCRIPTION: Set Alert Cancel Dialog Button */
            alertDialogBuilder.setCancelable(false);

            /* CODE DESCRIPTION: If Registration Was Successful & OK Button Was Tapped, Re-Direct Player To Login Screen */
            alertDialogBuilder.setPositiveButton(R.string.dialog_ok_button_text, (dialogInterface, i) -> {
                startActivity(intentRegisterPlayer);
                finish();
            });

            /*CODE DESCRIPTION:  Create The Alert Dialog Box */
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            /* CODE DESCRIPTION - UI: Set Alert Dialog Positive Button */
            Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            buttonPositive.setTextColor(ContextCompat.getColor(AerolyfRegisterActivity.this, R.color.dialog_ok_btn));
            buttonPositive.setTextSize(16F);

        }/*end: if_else */


    } /*end: registerPlayer() */

    /* SETTINGS: Disable The Device System Back Button */
    @Override
    public void onBackPressed() {

        /* DONE: VIEW - Insert A DialogBox Here: */
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AerolyfRegisterActivity.this, R.style.customExitAlertDialog);

        /* CODE DESCRIPTION: Set Alert Dialog Title: */
        alertDialogBuilder.setTitle(R.string.dialog_title);

        /* CODE DESCRIPTION: Set  Alert Dialog Icon: */
        alertDialogBuilder.setIcon(R.drawable.ic_info_exit);

        /* CODE DESCRIPTION: Set Alert Dialog Message & Settings: */
        alertDialogBuilder.setMessage(R.string.dialog_message);


        /* CODE DESCRIPTION: Set Alert Cancel Dialog Button Settings: */
        alertDialogBuilder.setCancelable(false);

        /* COMMENT: Positive Alert Button:  */
        alertDialogBuilder.setPositiveButton(R.string.dialog_positive_button_text, (dialogInterface, i) -> finishAndRemoveTask());

        /* COMMENT: Negative Alert Button: */
        alertDialogBuilder.setNegativeButton(R.string.dialog_negative_button_text, (dialogInterface, i) -> Toast.makeText(AerolyfRegisterActivity.this, "Register Your Details & Login To Quiz Dashboard", Toast.LENGTH_SHORT).show());

        /* CODE DESCRIPTION:  Create The Alert Dialog Box: */
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        /* CODE DESCRIPTION - UI: Set Alert Dialog Button Settings */
        Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(AerolyfRegisterActivity.this, R.color.dialogPositiveButtonColor1));
        buttonPositive.setTextSize(16F);

        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(AerolyfRegisterActivity.this,R.color.dialogNegativeButtonColor1));
        buttonNegative.setTextSize(16F);


    }/* end: onBackPressed() */

} /*end: CLASS ~ AerolyfRegisterActivity */