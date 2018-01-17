package tpdev.upmc.dcinephila.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tpdev.upmc.dcinephila.Adapaters.ListsAdapter;
import tpdev.upmc.dcinephila.Adapaters.SearchProfileAdapter;
import tpdev.upmc.dcinephila.Beans.Cinephile;
import tpdev.upmc.dcinephila.Beans.ListCinephile;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Sourour Bnll on 12/01/2018.
 */

public class SearchProfileActivity extends AppCompatActivity {
    private List<String> myLists;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    final String userRecord = FirebaseAuth.getInstance().getCurrentUser().getUid();
    SearchProfileAdapter adapter;
    private TextView text;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);
        // Get Firebase database instance
        mFirebaseInstance = FirebaseDatabase.getInstance();


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        final ImageView btn = (ImageView)findViewById(R.id.search_profile);
        text= (TextView)findViewById(R.id.searching);
        ListView listView = (ListView)findViewById(R.id.listView_profile);
        myLists = new ArrayList<String>();
        adapter = new SearchProfileAdapter(
                this,
                R.layout.list_other_item,
                myLists);
        listView.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProfile();
            }
        });


    }

    public void searchProfile(){
        myLists.clear();
        mFirebaseDatabase = mFirebaseInstance.getReference("cinephiles");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    System.out.println(" la recherche affiche pr les chils ::"+ child.getValue().toString());
                            Cinephile cinp = child.getValue(Cinephile.class);
                            if(cinp.getFirstname().contains(text.getText()) || cinp.getLastname().contains(text.getText())){
                                System.out.println(" la recherche affiche pour le texte  "+ text.getText());
                                System.out.println(" la recherche affiche pour le mail "+cinp.getEmail());
                            adapter.add(cinp.getEmail());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }



}
