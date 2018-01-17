package tpdev.upmc.dcinephila.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tpdev.upmc.dcinephila.Adapaters.ListsAdapter;
import tpdev.upmc.dcinephila.Beans.ListCinephile;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Sourour Bnll on 18/12/2017.
 */

public class DisplayListsActivity extends AppCompatActivity {
    // List de mes favoris
    private List<String> myLists;
    private TextView favoris,list_name;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    final String userRecord = FirebaseAuth.getInstance().getCurrentUser().getUid();
    final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    ListsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lists);

        // Get Firebase database instance
        mFirebaseInstance = FirebaseDatabase.getInstance();

        Typeface face_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Bold.ttf");
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        favoris=(TextView)findViewById(R.id.favoris);
        list_name=(TextView)findViewById(R.id.list_name);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        favoris.setTypeface(boldTypeface);
        list_name.setTypeface(face_bold);

        final EditText et = (EditText) findViewById(R.id.list_name);
        //Boutton add list
        final ImageView btn = (ImageView)findViewById(R.id.create_list);
        ListView listView = (ListView)findViewById(R.id.listView);
        myLists = new ArrayList<>();
        adapter = new ListsAdapter(
                this,
                R.layout.list_item,
                myLists);
        listView.setAdapter(adapter);
        display();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.clear();
                adapter.notifyDataSetChanged();
                // creation de listes

                mFirebaseDatabase = mFirebaseInstance.getReference("lists_cinephile");

                // creating cinephile object
               final ListCinephile list_cinephile = new ListCinephile(userRecord,et.getText().toString(),email);
                mFirebaseDatabase.child(et.getText().toString()).setValue(list_cinephile);
                // pushing cinephile to 'cinephiles' node using the cinephileId
                // mFirebaseDatabase.child(email ).setValue(cinephile);


            }
        });


    }

    public void display(){
        mFirebaseDatabase = mFirebaseInstance.getReference("lists_cinephile");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for(DataSnapshot c : child.getChildren()){
                        if(c.getValue().equals(userRecord)){
                            ListCinephile cinp = child.getValue(ListCinephile.class);
                            adapter.add(cinp.getTitle());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }



    }
