package tpdev.upmc.dcinephila.Adapaters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.Activities.MovieDetailsActivity;
import tpdev.upmc.dcinephila.Beans.Actor;
import tpdev.upmc.dcinephila.Beans.ElementList;
import tpdev.upmc.dcinephila.DesignClasses.AppController;
import tpdev.upmc.dcinephila.R;

import static android.content.ContentValues.TAG;

/**
 * Created by Sourour Bnll on 15/01/2018.
 */

public class ElementLikeAdapter extends ArrayAdapter<String> {
    final int layoutResource;
    private final List<String> myLists;
    private Context context;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ArrayList<String> a = new ArrayList<String>();
    private String movie,movie_id;
    private String release_date,url_movie;
    ElementLikeAdapter.ViewHolder holder;
    // Get Firebase database instance

    private static class ViewHolder {
        TextView textView;
        TextView date;
        ImageView image;
    }


    public ElementLikeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        layoutResource = resource;
        myLists = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = createView();
            notifyDataSetChanged();
        }

        holder = (ElementLikeAdapter.ViewHolder) convertView.getTag();
        final String todo = getItem(position);
        System.out.println("item reçu"+todo);
        GetMovieDetails(ThemoviedbApiAccess.AllMovieDetailsURL(Integer.valueOf(todo)));
        System.out.println("movie reçu"+movie);
        // on se charge ici de get tt les trucs de name date et ect...
        holder.textView.setText(movie);
        holder.date.setText("Sortie le :"+release_date);

       // Glide.with(getContext()).load(url_movie).into(holder.image);

        return convertView;
    }


    private View createView() {
        // Create item
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(layoutResource, null);
        ElementLikeAdapter.ViewHolder holder = new ElementLikeAdapter.ViewHolder();
        holder.textView = view.findViewById(R.id.movie_title);
        holder.date=view.findViewById(R.id.date_of);
        holder.image=view.findViewById(R.id.poster);
        view.setTag(holder);
        return view;
    }

    private void GetMovieDetails(final String urlJsonObj) {
        System.out.println("iciii");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {

                    movie=response.getString("title");
                    holder.textView.setText(movie);
                    System.out.println("le titre"+response.getString("title"));
                    try {
                        if (response.getString("release_date")!=null)
                            release_date = response.getString("release_date");
                            System.out.println("la data"+response.getString("release_date"));
                    }
                    catch (Exception e){
                        release_date="";
                    }

                    holder.date.setText("Sortie le :"+release_date);
                    // Getting movie runtime
                    System.out.println("la url  "+response.getString("poster_path"));
                    url_movie="https://image.tmdb.org/t/p/w500" + response.getString("poster_path");

                    Glide.with(getContext()).load("https://image.tmdb.org/t/p/w500" + response.getString("poster_path")).into(holder.image);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
