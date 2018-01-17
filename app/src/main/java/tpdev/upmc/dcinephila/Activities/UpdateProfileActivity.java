package tpdev.upmc.dcinephila.Activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tpdev.upmc.dcinephila.Beans.Cinephile;
import tpdev.upmc.dcinephila.R;
import tpdev.upmc.dcinephila.databinding.ActivityUpdateProfileBinding;

/**
 * Created by Sourour Bnll on 19/12/2017.
 */

public class UpdateProfileActivity extends AppCompatActivity {
    Cinephile user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private TextView firstname_update, lastname_update, email_update, password_update,confirmation_update;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ActivityUpdateProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile);
        // display informations of the user
        final String userRecord = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mFirebaseDatabase = mFirebaseInstance.getReference("cinephiles");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for(DataSnapshot c : child.getChildren()){
                        if(c.getValue().equals(userRecord)){
                            Cinephile cinp = child.getValue(Cinephile.class);
                            user = new Cinephile(cinp.getFirstname(), cinp.getLastname(),cinp.getEmail(),"");

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        binding.setUser(user);
    }

    private void updateUser(final String name,final String lastname,final String password,final String email,final String confirmation){
        final String userRecord = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        mFirebaseDatabase = mFirebaseInstance.getReference("cinephiles");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for (DataSnapshot c : child.getChildren()) {
                        if (c.getValue().equals(userRecord)) {
                            // verifier les input check if not null or ! ""
                            if (!name.equals("")) {
                                c.getRef().child("firstname").setValue(name);
                            }
                            if (!lastname.equals("")) {
                                c.getRef().child("lastname").setValue(lastname);
                            }
                            if (!email.equals("")) {
                                c.getRef().child("email").setValue(email);
                            }
                            if (!password.equals("") && verifypassword(password, confirmation)) {
                                c.getRef().child("password").setValue(password);
                            }

                        }
                    }
                }}
        @Override
        public void onCancelled(DatabaseError databaseError) {}
    });
    }
            public boolean verifypassword(String password, String confirmation) {
                if (password.equals(confirmation)) {
                    return true;
                }
                return false;
            }
        }
