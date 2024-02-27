package com.example.blackjackFirebase;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

// btw to bomo delali na login activityju - ta activity je samo za testiranje, ga bomo kasneje izbrisali
// najprej chckamo, če je uporabnik vpisan - FirebaseAuth.getInstance().getCurrentUser() != null
// če je izpišemo ime in damo možnost, da gre v igro ali pa da se izpiše

// če ni vpisan damo možnost da se vpiše / ustvari nov račun
// gumb za odjavo je v tem primeru skrit

// disabled email enumeration protection
// https://cloud.google.com/identity-platform/docs/admin/email-enumeration-protection
// sign in v existin account s firebaseUI sicer ne dela

// za zdaj bo brez testiranja emaila - bomo pol to dodali, če bo čas

// TODO: Send verification email
// TODO: reset password
// TODO: add realtime database

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn, btnLogOut;
    private TextView txtSignInTitle;
    private ImageView imageViewSignInAce, imageViewSignInKing;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        imageViewSignInAce = findViewById(R.id.imageViewSignInAce);
        imageViewSignInKing = findViewById(R.id.imageViewSignInKing);

        Glide.with(this).asBitmap().load("https://deckofcardsapi.com/static/img/AH.png").into(imageViewSignInAce);
        Glide.with(this).asBitmap().load("https://deckofcardsapi.com/static/img/KH.png").into(imageViewSignInKing);

        txtSignInTitle = findViewById(R.id.txtSignInTitle);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnLogOut = findViewById(R.id.btnLogOut);
        setParameters();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSignIn.getTag() == "SignIn"){
                    // Create and Launch Sign in intent
                    Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder().
                            setAvailableProviders(providers).build();
                    signInLauncher.launch(signInIntent);
                }
                else {
                    // pošljemo Uid
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Intent intent = new Intent(SignInActivity.this, GameActivity.class);
                    intent.putExtra("Uid", user.getUid());
                    startActivity(intent);
                }
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(SignInActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setParameters();
                    }
                });
            }
        });
    }

    // See: https://developer.android.com/training/basics/intents/result

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result){
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Toast.makeText(this, "Sign in successfully", Toast.LENGTH_SHORT).show();
            setParameters();
        }
        else{
            //javi napako
            Toast.makeText(this, "Error signing-in", Toast.LENGTH_SHORT).show();
        }
    }

    private void setParameters(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            txtSignInTitle.setText("Signed in as " + user.getEmail());
            btnSignIn.setText("Play");
            btnSignIn.setTag("");
            btnLogOut.setVisibility(View.VISIBLE);
        }
        else {
            txtSignInTitle.setText("Not signed in");
            btnSignIn.setText("Sign In");
            btnSignIn.setTag("SignIn");
            btnLogOut.setVisibility(View.INVISIBLE);
        }
    }
}