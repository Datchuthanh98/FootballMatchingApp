package com.example.myclub.auth;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myclub.auth.facebookauthentication.FaceBookLoginActivity;
import com.example.myclub.databinding.ActivityLoginBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.Arrays;

public class ActivityLogin extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private SharedPreferences sharedPref;
    private Dialog loginLoading;
    private ActivityLoginBinding binding;
    //google
    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private MKLoader loader;
    //facebook
    private LinearLayout fb;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        if (FirebaseAuth.getInstance().getCurrentUser() != null){
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//        }

//
//        sharedPref = getApplicationContext().getSharedPreferences("sessionUser", Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPref.edit();
        mAuth = FirebaseAuth.getInstance();


        //Login withEmail
        binding.btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.txtEmail.getText().toString())){
                    Toast.makeText(ActivityLogin.this, "Pleas Enter Email Address", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.getText().toString()).matches()){
                    Toast.makeText(ActivityLogin.this, "Pleas Enter valid Email Address", Toast.LENGTH_SHORT).show();
                }
                else  if(TextUtils.isEmpty(binding.txtPassword.getText().toString())){
                    Toast.makeText(ActivityLogin.this, "Pleas Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(binding.txtPassword.getText().toString().length()<6){
                    Toast.makeText(ActivityLogin.this, "Pleas Enter 6 or more than digit password", Toast.LENGTH_SHORT).show();
                }
                else {

                    loginLoading = new Dialog(ActivityLogin.this);
                    loginLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    loginLoading.setContentView(R.layout.custom_loading_layout);
                    loginLoading.setCancelable(false);
                    loginLoading.show();

                   // firebaseAuthWithEmail();
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser user = authResult.getUser();
                                    Toast.makeText(ActivityLogin.this, "Login Successful....", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
//                                    editor.putString("session",user.getEmail());
//                                    editor.apply();
                                    loginLoading.cancel();
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ActivityLogin.this, "Failed :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    loginLoading.cancel();
                                }
                            });
                }
            }
        });


        //Register Email
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, EmailRegistrationActivity.class));
            }
        });


        //Login With Phone
        binding.btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, PhoneLoginActivity.class));
            }
        });


        //Login With Google
        mAuth = FirebaseAuth.getInstance();
        loader = binding.loader;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loader.setVisibility(View.VISIBLE);
                signIn();
            }
        });



        //Login Facebook
        mCallbackManager = CallbackManager.Factory.create();
        binding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(ActivityLogin.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("djvbdfjbv", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("djvbdfjbv", "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("djvbdfjbv", "facebook:onError", error);
                        // ...
                    }
                });
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("hjdsvhjcdb", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loader.setVisibility(View.GONE);
                            Log.d("hjdsvhjcdb", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
                            finish();

                        } else {
                            loader.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w("hjdsvhjcdb", "signInWithCredential:failure", task.getException());
                            Toast.makeText(ActivityLogin.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }



    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("smdbcdbsc", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("smdbcdbsc", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("smdbcdbsc", "signInWithCredential:failure", task.getException());
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("hjdsvhjcdb", "Google sign in failed", e);
                // ...
            }
        }else {
            //Facebook
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}
