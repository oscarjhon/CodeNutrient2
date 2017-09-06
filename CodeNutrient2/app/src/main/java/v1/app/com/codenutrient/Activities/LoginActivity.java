package v1.app.com.codenutrient.Activities;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.IOException;
import java.sql.SQLException;

import v1.app.com.codenutrient.HTTP.HttpManager;
import v1.app.com.codenutrient.Helpers.DataBaseHelper;
import v1.app.com.codenutrient.POJO.AppUser;
import v1.app.com.codenutrient.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
   public TextView prueba;
    private int RC_SIGN_IN;
    public static GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;
    public ImageView image;
    public ProgressBar progressBar;
    public SignInButton signInButton;
    public TextView text;

    public void onCreate(Bundle saveState) {
        super.onCreate(saveState);
        setContentView(R.layout.activity_login);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        signInButton = (SignInButton) findViewById(R.id.singIn);
        image = (ImageView) findViewById(R.id.logo);
        text = (TextView) findViewById(R.id.text_singin);
        signInButton.setSize(0);
        signInButton.setOnClickListener(listener);
        progressBar = (ProgressBar) findViewById(R.id.login_bar);
        DataBaseHelper d = new DataBaseHelper(getApplicationContext());
        try {
            d.createDataBase();
            d.openDataBaseReadWrite();
            if (d.checkMeasure()) {
                d.executeMeasure();
            }
            d.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void onStart() {
        super.onStart();
        googleApiClient.connect();
        OptionalPendingResult<GoogleSignInResult> optional = Auth.GoogleSignInApi.silentSignIn(this.googleApiClient);
        if (optional.isDone()) {
            try {
                handleSignInResult(optional.get());
            } catch (SQLException e) {
                showError();
            }
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            image.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.VISIBLE);
        }
    }

    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.singIn) {
                signInButton.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        }
    };

    public LoginActivity() {
        RC_SIGN_IN = 10;
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        signInButton.setEnabled(false);
        Snackbar.make(findViewById(R.id.loginCoordinator), "No se ha podido iniciar sesión", Snackbar.LENGTH_SHORT).show();
    }

    private AppUser FirstOrCreate(String provider, String uid, String name, String token, String email) throws SQLException {
        AppUser appUser = getDatabaseUser(provider, uid);
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        helper.openDataBaseReadWrite();
        if (appUser != null) {
            helper.updateUsers(token, uid, "Google", email, name);
            helper.close();
            return getDatabaseUser(provider, uid);
        }
        long id = helper.insertUsers(email, "Google", token, uid, name);
        if (id  == -1) {
            return null;
        }
        helper.insertMETS(id);
        helper.close();
        return getDatabaseUser(provider, uid);
    }

    private AppUser PassCursorAppUser(Cursor cursor) {
        AppUser appUser = new AppUser();
        appUser.setUid(cursor.getString(cursor.getColumnIndex("uid")));
        appUser.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
        appUser.setName(cursor.getString(cursor.getColumnIndex("name")));
        appUser.setToken(cursor.getString(cursor.getColumnIndex("token")));
        appUser.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        return appUser;
    }


    private void disconect() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        try {
            helper.openDataBaseReadWrite();
            helper.updateUsersStatus();
            helper.close();
        } catch (SQLException ignored) {}

    }

    private AppUser getDatabaseUser(String provider, String uid) throws SQLException {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        dataBaseHelper.openDataBaseRead();
        Cursor cursor = dataBaseHelper.fetchUser(uid, provider);
        if (cursor == null) {
            dataBaseHelper.close();
            return null;
        } else if (cursor.moveToFirst()) {
            dataBaseHelper.close();
            return PassCursorAppUser(cursor);
        } else {
            dataBaseHelper.close();
            return null;
        }
    }

    private void handleSignInResult(GoogleSignInResult result) throws SQLException {
         if (result.isSuccess()) {
             GoogleSignInAccount account = result.getSignInAccount();
             AppUser appUser;
             HttpManager manager = new HttpManager();
            if (manager.isOnLine(getApplicationContext())) {
                appUser = new AppUser();
                appUser.setName(account.getDisplayName());
                appUser.setEmail(account.getEmail());
                appUser.setUid(account.getId());
                appUser.setPhoto(account.getPhotoUrl());
                appUser.setProvider("Google");
                new myTask().execute(appUser);
                return;
            }
            appUser = getDatabaseUser("Google", account.getId());
            if (appUser == null) {
                showError();
                return;
            }
            appUser.setPhoto(account.getPhotoUrl());
            moveToMain(appUser);
        } else {
             Log.e("ERROR", result.getStatus().toString());

             progressBar.setVisibility(View.INVISIBLE);
             image.setVisibility(View.VISIBLE);
             text.setVisibility(View.VISIBLE);
             signInButton.setVisibility(View.VISIBLE);
             signInButton.setEnabled(true);
             Snackbar.make(findViewById(R.id.loginCoordinator),  "No se ha podido iniciar sesión", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void moveToMain(AppUser appUser) {
        Intent intent = new Intent().setClass(LoginActivity.this, MainActivity.class);
        MainActivity.appUser = appUser;
        startActivity(intent);
        finish();
    }

    private void showError() {
        signInButton.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
        Snackbar.make(findViewById(R.id.loginCoordinator), "No se ha podido iniciar sesión", Snackbar.LENGTH_SHORT).show();
        disconect();
        progressBar.setVisibility(View.INVISIBLE);
        image.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        signInButton.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
        Snackbar.make(findViewById(R.id.loginCoordinator),  message, Snackbar.LENGTH_SHORT).show();
        disconect();
        progressBar.setVisibility(View.INVISIBLE);
        image.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.VISIBLE);
    }

    private void signIn() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(googleApiClient), this.RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            try {
                handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void postExecute(AppUser appUser) {
        if (appUser.getCode() == 200) {
            try {
                appUser = FirstOrCreate(appUser.getProvider(), appUser.getUid(), appUser.getName(), appUser.getToken(), appUser.getEmail());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (appUser != null) {
                appUser.setPhoto(appUser.getPhoto());
                moveToMain(appUser);
                return;
            }
            showError();
        } else if (appUser.getCode() == 0) {
            showError("No se ha podido conectar con el servidor");
        } else {
            showError();
        }
    }

    private class myTask extends AsyncTask<AppUser, String, AppUser> {

        public AppUser doInBackground(AppUser... appUsers) {
            v1.app.com.codenutrient.Requests.AppUser request = new v1.app.com.codenutrient.Requests.AppUser();
            AppUser appUser = request.ExecutePost("Google", appUsers[0].getEmail(), appUsers[0].getUid(), appUsers[0].getName());
            appUser.setPhoto(appUsers[0].getPhoto());
            return appUser;
        }

        public void onPostExecute(AppUser appUser) {
            postExecute(appUser);
        }
    }
}
