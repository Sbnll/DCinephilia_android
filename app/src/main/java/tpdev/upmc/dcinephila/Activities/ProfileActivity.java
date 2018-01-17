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

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView firtsname_profile_text, lastname_profile_text, email_profile_text, adress_value;
    private ImageView event_view,lists_view,edit_view;
    private TextView event_text, lists_text,edit_text;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view now
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        Typeface face_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Bold.ttf");
        firtsname_profile_text = (TextView) findViewById(R.id.firstname_profile);
        lastname_profile_text = (TextView) findViewById(R.id.lastname_profile);
        email_profile_text = (TextView) findViewById(R.id.email_profile);
        adress_value = (TextView) findViewById(R.id.adress_value);
        event_view=(ImageView)findViewById(R.id.event);
        lists_view=(ImageView)findViewById(R.id.lists);
        edit_view=(ImageView)findViewById(R.id.update);
        lists_text =(TextView) findViewById(R.id.create_lists);
        event_text=(TextView) findViewById(R.id.create_event);
        edit_text=(TextView)findViewById(R.id.update_lists) ;
        lists_text.setTypeface(face_bold);
        event_text.setTypeface(face_bold);
        edit_text.setTypeface(face_bold);
        firtsname_profile_text.setTypeface(face_bold);
        lastname_profile_text.setTypeface(face_bold);
        email_profile_text.setTypeface(face_bold);
        adress_value.setTypeface(face_bold);
        GetUserInformations();



        event_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(ProfileActivity.this, DisplayEventsActivity.class));
                //finish();
            }
        });

        event_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivity(new Intent(ProfileActivity.this, DisplayEventsActivity.class));
                //finish();
            }
        });

        lists_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, DisplayListsActivity.class));
                finish();
            }
        });

        lists_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, DisplayListsActivity.class));
                finish();
            }
        });

        edit_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
                finish();
            }
        });

        edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
                finish();
            }
        });

    }


    private void GetUserInformations() {
        adress_value.setText("son adresse ");
        final String userRecord = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        System.out.println("current user  :  "+ FirebaseAuth.getInstance().getCurrentUser());
        mFirebaseDatabase = mFirebaseInstance.getReference("cinephiles");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for(DataSnapshot c : child.getChildren()){
                        if(c.getValue().equals(userRecord)){
                            Cinephile cinp = child.getValue(Cinephile.class);
                            firtsname_profile_text.setText(cinp.getFirstname());
                            lastname_profile_text.setText(cinp.getLastname());
                            email_profile_text.setText(cinp.getEmail());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}