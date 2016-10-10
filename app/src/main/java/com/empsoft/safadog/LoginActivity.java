package com.empsoft.safadog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class LoginActivity extends Activity {

    private EditText loginEditText;
    private EditText senhaEditText;
    private Button loginWithFacebookButton;
    private Installation pInst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        loginEditText = (EditText) findViewById(R.id.editText_login);
        senhaEditText = (EditText) findViewById(R.id.editText_senha);
        senhaEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == R.id.edittext_action_login || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
                    login();
                    return true;
                }
                return false;
            }
        });

        loginWithFacebookButton = (Button) findViewById(R.id.login_facebook_button);
        loginWithFacebookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginWithFB();
            }

        });

        Button actionButton = (Button) findViewById(R.id.button_entrar);
        actionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                login();
            }
        });
    }

    /**
     * Login with Facebook
     */
    private void loginWithFB() {
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, null, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                    Installation pInst = new Installation();
                    pInst.install();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                    Installation pInst = new Installation();
                    pInst.install();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * It performs the login in the system.
     * Checking and making the user aware of any errors at login.
     */
    private void login() {
        String login = loginEditText.getText().toString().trim();
        String senha = senhaEditText.getText().toString().trim();

        // Validate the log in data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (login.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_email));
        }
        if (senha.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        validationErrorMessage.append(getString(R.string.error_end));


        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage(getString(R.string.progress_login));
        dialog.show();
        // Call the Parse login method
        ParseUser.logInInBackground(login, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(LoginActivity.this, R.string.invalid_login_credentials, Toast.LENGTH_LONG).show();
                }else {
                    boolean verified = user.getBoolean("emailVerified");
                    if(!verified){
                        Toast.makeText(LoginActivity.this, R.string.email_not_verified, Toast.LENGTH_LONG).show();
                    }else {
                        Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                        pInst = new Installation();
                        pInst.install();

                        startActivity(intent);
                    }
                }
            }
        });
    }
}
