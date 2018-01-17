package tpdev.upmc.dcinephila.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tpdev.upmc.dcinephila.Adapaters.ListOtherAdapter;
import tpdev.upmc.dcinephila.Adapaters.ListsAdapter;
import tpdev.upmc.dcinephila.Beans.ElementList;
import tpdev.upmc.dcinephila.Beans.ListCinephile;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Sourour Bnll on 13/01/2018.
 */

public class DisplayListsOtherUserActivity extends AppCompatActivity {
    // List de mes favoris
    private List<ElementList> myLists;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ListOtherAdapter adapter;
    private String emailOtherUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lists_other);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        Bundle b = getIntent().getExtras();
        emailOtherUser=b.getString("emailOtherUser");
        Intent intent = new Intent(DisplayListsOtherUserActivity.this, DisplayElementsListOtherUserActivity.class).putExtra("emailOtherUser",b.getString("emailOtherUser"));
        //getApplicationContext().startActivity(intent);
        ListView listView = (ListView)findViewById(R.id.listView);
        myLists = new ArrayList<>();
        adapter = new ListOtherAdapter(
                this,
                R.layout.list_other_item,
                myLists);
        listView.setAdapter(adapter);
        display();


    }

    public void display(){
        mFirebaseDatabase = mFirebaseInstance.getReference("lists_cinephile");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for(DataSnapshot c : child.getChildren()){
                        if(c.getValue().equals(emailOtherUser)){
                            ListCinephile cinp = child.getValue(ListCinephile.class);
                            ElementList el= new ElementList(cinp.getTitle(),emailOtherUser);
                            adapter.add(el);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }



}
