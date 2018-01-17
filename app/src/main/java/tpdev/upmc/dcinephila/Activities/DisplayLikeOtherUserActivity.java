package tpdev.upmc.dcinephila.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.Adapaters.ElementLikeAdapter;
import tpdev.upmc.dcinephila.Adapaters.ElementListOtherUserAdapter;
import tpdev.upmc.dcinephila.Beans.ElementList;
import tpdev.upmc.dcinephila.DesignClasses.AppController;
import tpdev.upmc.dcinephila.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Sourour Bnll on 14/01/2018.
 */

public class DisplayLikeOtherUserActivity extends AppCompatActivity {
    private List<String> myLists;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ElementLikeAdapter adapter;
    private String emailOtherUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_likes);
        Typeface face_bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Bold.ttf");

        // Get Firebase database instance
        mFirebaseInstance = FirebaseDatabase.getInstance();
        Bundle i = getIntent().getExtras();
        emailOtherUser=i.getString("emailOtherUser");
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        ListView listView = (ListView)findViewById(R.id.listView);
        myLists = new ArrayList<String>();
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(myLists);
        myLists.clear();
        myLists.addAll(hashSet);
        //creer un autre ElementLike niania
        adapter = new ElementLikeAdapter( this,
                R.layout.element_movie_item,
                myLists);

        listView.setAdapter(adapter);
        display();

    }


    public void display(){
        //ici il faut iterer sur les childs pr voir si emailOtherUser y est
        //puis aprés si c le cas on prend elementid et on le passe à l'adapter pr avoir une arraylist d'id movie
        //ensuite ds l'autre classe adapter pour chaque id movie on appelle GetMovieDetails(ThemoviedbApiAccess.AllMovieDetailsURL(movie_id)); qui
        //se trouve à la ligne 387 et on get l url et le nom et date de l'element
        mFirebaseDatabase = mFirebaseInstance.getReference("cinephiles_movies_likes");
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    for(DataSnapshot c : child.getChildren()){
                        for(DataSnapshot d : c.getChildren()) {
                            if (d.getValue().equals(emailOtherUser)) {
                                 adapter.add(c.child("element_id").getValue().toString());

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
