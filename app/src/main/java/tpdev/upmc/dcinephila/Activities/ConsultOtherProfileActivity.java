package tpdev.upmc.dcinephila.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tpdev.upmc.dcinephila.Beans.Cinephile;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Sourour Bnll on 13/01/2018.
 */

public class ConsultOtherProfileActivity extends AppCompatActivity{
    private FirebaseAuth auth;
    private TextView firstname_other_profile, lastname_other_profile, email_other_profile;
    private ImageView lists_other,like_other,chat;
    private TextView lists_other_profile, like_other_profile,chat_profile;
    private String emailOtherUser;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        Bundle i = getIntent().getExtras();
        emailOtherUser=i.getString("consultProfile");
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        Typeface face_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Bold.ttf");
        firstname_other_profile = (TextView) findViewById(R.id.firstname_other_profile);
        lastname_other_profile = (TextView) findViewById(R.id.lastname_other_profile);
        email_other_profile = (TextView) findViewById(R.id.email_other_profile);
        lists_other=(ImageView)findViewById(R.id.lists_other);
        like_other=(ImageView)findViewById(R.id.like_other);
        chat=(ImageView) findViewById(R.id.chat);
        lists_other_profile =(TextView) findViewById(R.id.lists_other_profile);
        like_other_profile=(TextView) findViewById(R.id.like_other_profile);
        chat_profile=(TextView)findViewById(R.id.chat_profile) ;
        lists_other_profile.setTypeface(face_bold);
        like_other_profile.setTypeface(face_bold);
        chat_profile.setTypeface(face_bold);
        firstname_other_profile.setTypeface(face_bold);
        lastname_other_profile.setTypeface(face_bold);
        email_other_profile.setTypeface(face_bold);
        GetUserInformations();



        lists_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(ConsultOtherProfileActivity.this, DisplayListsOtherUserActivity.class).putExtra("emailOtherUser",emailOtherUser));
                System.out.println(" le mail ds cnsult "+emailOtherUser);
                finish();
            }
        });

        lists_other_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(ConsultOtherProfileActivity.this, DisplayListsOtherUserActivity.class).putExtra("emailOtherUser",emailOtherUser));
                 System.out.println(" le mail ds cnsult "+emailOtherUser);
                finish();
            }
        });

        like_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultOtherProfileActivity.this, DisplayLikeOtherUserActivity.class).putExtra("emailOtherUser",emailOtherUser));
                finish();
            }
        });

        like_other_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultOtherProfileActivity.this, DisplayLikeOtherUserActivity.class).putExtra("emailOtherUser",emailOtherUser));
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultOtherProfileActivity.this, DisplayListsOtherUserActivity.class).putExtra("emailOtherUser",emailOtherUser));
                finish();
            }
        });

        chat_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConsultOtherProfileActivity.this, DisplayListsOtherUserActivity.class).putExtra("emailOtherUser",emailOtherUser));
                finish();
            }
        });

    }


    private void GetUserInformations() {
        mFirebaseDatabase = mFirebaseInstance.getReference("cinephiles");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for(DataSnapshot c : child.getChildren()){
                        if(c.getValue().equals(emailOtherUser)){
                            Cinephile cinp = child.getValue(Cinephile.class);
                            firstname_other_profile.setText(cinp.getFirstname());
                            lastname_other_profile.setText(cinp.getLastname());
                            email_other_profile.setText(cinp.getEmail());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

}
