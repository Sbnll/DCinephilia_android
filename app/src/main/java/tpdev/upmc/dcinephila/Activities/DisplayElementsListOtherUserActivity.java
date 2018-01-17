package tpdev.upmc.dcinephila.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import tpdev.upmc.dcinephila.Adapaters.ElementListAdapter;
import tpdev.upmc.dcinephila.Adapaters.ElementListOtherUserAdapter;
import tpdev.upmc.dcinephila.Beans.ElementList;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Sourour Bnll on 14/01/2018.
 */

public class DisplayElementsListOtherUserActivity extends AppCompatActivity {
    private List<ElementList> myLists;
    private String title,emailOtherUser;
    private TextView content_list;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ElementListOtherUserAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_elements_lists);
        Typeface face_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Bold.ttf");

        // Get Firebase database instance
        mFirebaseInstance = FirebaseDatabase.getInstance();
        Bundle i = getIntent().getExtras();
        emailOtherUser=i.getString("emailOtherUser");
        title=i.getString("title");
        System.out.println(" le finaal email "+emailOtherUser);
        System.out.println(" le finaal title "+title);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ListView listView = (ListView)findViewById(R.id.listView);
        myLists = new ArrayList<ElementList>();
        HashSet<ElementList> hashSet = new HashSet<ElementList>();
        hashSet.addAll(myLists);
        myLists.clear();
        myLists.addAll(hashSet);
        adapter = new ElementListOtherUserAdapter( this,
                R.layout.element_movie_item,
                myLists);

        listView.setAdapter(adapter);
        display();
        content_list=(TextView)findViewById(R.id.content_list);
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        content_list.setTypeface(boldTypeface);
        content_list.setTypeface(face_bold);
        content_list.setText(title);

    }

    public void display(){

        mFirebaseDatabase = mFirebaseInstance.getReference("elements_lists");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for (DataSnapshot c : child.getChildren()) {;
                        for (DataSnapshot d : c.getChildren()) {
                            System.out.println("email other" + emailOtherUser);
                            System.out.println("cc d " + d.toString());
                            if(d.getValue().equals(emailOtherUser)){
                                System.out.println("ça entre ");
                                ElementList element = c.getValue(ElementList.class);
                                if(element.getId_list().equals(title)){
                                    adapter.add(element);
                                }
                            }
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
