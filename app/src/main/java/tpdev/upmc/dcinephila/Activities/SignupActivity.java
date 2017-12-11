package tpdev.upmc.dcinephila.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tpdev.upmc.dcinephila.Beans.Cinephile;
import tpdev.upmc.dcinephila.R;


public class SignupActivity extends AppCompatActivity {

    private TextView textview;
    private EditText inputFirstname, inputLastname, inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Get Firebase database instance
        mFirebaseInstance = FirebaseDatabase.getInstance();


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        textview = (TextView) findViewById(R.id.dcinephilia);
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputFirstname = (EditText) findViewById(R.id.firstname);
        inputLastname = (EditText) findViewById(R.id.lastname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        Typeface face= Typeface.createFromAsset(getApplicationContext().getAssets(), "font/CollegeMovie.ttf");
        textview.setTypeface(face);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstname = inputFirstname.getText().toString().trim();
                final String lastname = inputLastname.getText().toString().trim();
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(lastname)) {
                    Toast.makeText(getApplicationContext(), "Saisissez votre nom !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(firstname)) {
                    Toast.makeText(getApplicationContext(), "Saisissez votre prénom !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Entrez votre adresse mail !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Saisissez votre motre passe !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Vous vous êtes déjà inscrit avec cette adresse mail ! ",
                                            Toast.LENGTH_LONG).show();
                                } else {

                                    // get reference to "cinephiles" node
                                    mFirebaseDatabase = mFirebaseInstance.getReference("cinephiles");

                                    String cinephileId =  mFirebaseDatabase.push().getKey();

                                    // creating cinephile object
                                    Cinephile cinephile = new Cinephile(firstname,lastname,email,password);

                                    // pushing cinephile to 'cinephiles' node using the cinephileId
                                    mFirebaseDatabase.child(cinephileId).setValue(cinephile);
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
