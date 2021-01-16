package com.example.myclub.auth;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.R;
import com.example.myclub.data.datasource.PlayerDataSource;
import com.example.myclub.model.Field;
import com.example.myclub.model.Image;
import com.example.myclub.session.SessionUser;
import com.example.myclub.databinding.ActivityLoginBinding;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Player;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private PlayerDataSource playerDataSource = PlayerDataSource.getInstance();
    private ActivityLoginBinding binding;
    //google
    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private MKLoader loader;
    //facebook
    private LinearLayout fb;
    private CallbackManager mCallbackManager;
    private Dialog loadingDialog;
    private LoadingLayoutBinding loadingLayoutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initLoadingDialog(ActivityLogin.this);


        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Log.d("auto login",FirebaseAuth.getInstance().getCurrentUser().getEmail());
            loginWithEmail();
        }


        mAuth = FirebaseAuth.getInstance();
        //Login withEmail
        binding.btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     loginWithEmail();

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
                Toast.makeText(getApplicationContext(),"Chức năng đăng nhập qua điện thoại đang bảo trì !",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(ActivityLogin.this, PhoneLoginActivity.class));
            }
        });


        //Login With Google
        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Chức năng đăng nhập qua gmail đang bảo trì !",Toast.LENGTH_SHORT).show();
//                signIn();
            }
        });



        //Login Facebook
        mCallbackManager = CallbackManager.Factory.create();
        binding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Chức năng đăng nhập qua facebook đang bảo trì !",Toast.LENGTH_SHORT).show();
//                LoginManager.getInstance().logInWithReadPermissions(ActivityLogin.this, Arrays.asList("email", "public_profile"));
//                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        handleFacebookAccessToken(loginResult.getAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Log.d("djvbdfjbv", "facebook:onCancel");
//                        // ...
//                    }
//
//                    @Override
//                    public void onError(FacebookException error) {
//                        Log.d("djvbdfjbv", "facebook:onError", error);
//                        // ...
//                    }
//                });
            }
        });

    }

    private  void loginWithEmail(){
        loadingDialog.show();
        playerDataSource.login(binding.txtEmail.getText().toString(), binding.txtPassword.getText().toString(), new CallBack<Player,String>() {
            @Override
            public void onSuccess(Player player) {
                SessionUser.getInstance().onUserChange(player);
                loadingDialog.dismiss();
                Intent intent = new Intent(ActivityLogin.this,ActivityHome.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error "+message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                                Toast.makeText(getApplicationContext(),FirebaseAuth.getInstance().getUid(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
                            finish();

                        } else {
                            loader.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ActivityLogin.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }



    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),FirebaseAuth.getInstance().getUid(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
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
                // ...
            }
        }else {
            //Facebook
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingLayoutBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(loadingLayoutBinding.getRoot());
        loadingDialog.setCancelable(false);
    }


}
