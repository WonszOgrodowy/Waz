package com.example.dominik.aplikacjawaz;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;


public class facebook extends Activity{

    private static final String APP_ID = "373326432874563";
    private static final String[] PERMISSIONS = new String[] {"publish_stream"};

    private static final String TOKEN = "access_token";
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "facebook-credentials";

    private Facebook facebook;
    private String messageToPost;

    public boolean saveCredentials(Facebook facebook) {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, facebook.getAccessToken());
        editor.putLong(EXPIRES, facebook.getAccessExpires());
        return editor.commit();
    }

    public boolean restoreCredentials(Facebook facebook) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(KEY, Context.MODE_PRIVATE);
        facebook.setAccessToken(sharedPreferences.getString(TOKEN, null));
        facebook.setAccessExpires(sharedPreferences.getLong(EXPIRES, 0));
        return facebook.isSessionValid();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        facebook = new Facebook(APP_ID);
        restoreCredentials(facebook);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_facebook2);
        changeFont();

        String facebookMessage = getIntent().getStringExtra("facebookMessage");
        if (facebookMessage == null){
            facebookMessage = "Test wall post";
        }
        messageToPost = facebookMessage;
    }

    public void doNotShare(View button){
        finish();
    }
    public void share(View button){
        if (! facebook.isSessionValid()) {
            loginAndPostToWall();
        }
        else {
            postToWall(messageToPost);
        }
    }

    public void loginAndPostToWall(){
        facebook.authorize(this, PERMISSIONS, Facebook.FORCE_DIALOG_AUTH, new LoginDialogListener());
    }

    public void postToWall(String message){
        Bundle parameters = new Bundle();
        parameters.putString("message", message);
        parameters.putString("description", "topic share");
        try {
            facebook.request("me");
            String response = facebook.request("me/feed", parameters, "POST");
            Log.d("Tests", "got response: " + response);
            if (response == null || response.equals("") ||
                    response.equals("false")) {
                showToast("Blank response.");
            }
            else {
                showToast("Message posted to your facebook wall!");
            }
            finish();
        } catch (Exception e) {
            showToast("Failed to post to wall!");
            e.printStackTrace();
            finish();
        }
    }
    public void changeFont(){
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/XD.TTF");
        TextView myTexview = (TextView) findViewById(R.id.waarnn);
        Button ButtonText = (Button) findViewById(R.id.FacebookShareButton);
        Button ButtonText2 = (Button) findViewById(R.id.FacebookShareNotButton);
        myTexview.setTypeface(myTypeface);
        ButtonText.setTypeface(myTypeface);
        ButtonText2.setTypeface(myTypeface);
    }

    class LoginDialogListener implements Facebook.DialogListener {
        public void onComplete(Bundle values) {
            saveCredentials(facebook);
            if (messageToPost != null){
                postToWall(messageToPost);
            }
        }
        public void onFacebookError(FacebookError error) {
            showToast("Authentication with Facebook failed!");
            finish();
        }
        public void onError(DialogError error) {
            showToast("Authentication with Facebook failed!");
            finish();
        }
        public void onCancel() {
            showToast("Authentication with Facebook cancelled!");
            finish();
        }
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
