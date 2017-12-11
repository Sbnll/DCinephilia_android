package tpdev.upmc.dcinephila.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.Adapaters.MoviesAdapter;
import tpdev.upmc.dcinephila.DesignClasses.AppController;
import tpdev.upmc.dcinephila.Beans.Movie;
import tpdev.upmc.dcinephila.Activities.MainActivity;
import tpdev.upmc.dcinephila.Activities.MovieDetailsActivity;
import tpdev.upmc.dcinephila.R;
import tpdev.upmc.dcinephila.DesignClasses.RecyclerTouchListener;


public class MoviesFragment extends Fragment{

    private TextView now_playong_text, upcoming_text;
    private RecyclerView now_playing_recyclerView, upcoming_movies_recyclerView;
    private MoviesAdapter now_playing_adapter, upcoming_movies_adapter;
    private ArrayList<Movie> now_playing_movies_list, upcoming_movies_list;
    private static String TAG = MainActivity.class.getSimpleName();


    public MoviesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        Typeface face= Typeface.createFromAsset(getContext().getAssets(), "font/Comfortaa-Bold.ttf");

        now_playong_text = (TextView) view.findViewById(R.id.now_playing_text);
        upcoming_text = (TextView) view.findViewById(R.id.upcoming_text);

        now_playong_text.setTypeface(face);
        upcoming_text.setTypeface(face);

        now_playing_recyclerView = (RecyclerView) view.findViewById(R.id.now_playing);
        upcoming_movies_recyclerView = (RecyclerView) view.findViewById(R.id.upcoming_movies);

        now_playing_movies_list = new ArrayList<Movie>();
        upcoming_movies_list = new ArrayList<Movie>();

        now_playing_adapter = new MoviesAdapter(getContext(), now_playing_movies_list);
        upcoming_movies_adapter = new MoviesAdapter(getContext(), upcoming_movies_list);

        RecyclerView.LayoutManager NowPlayingLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        now_playing_recyclerView.setLayoutManager(NowPlayingLayoutManager);
        now_playing_recyclerView.setItemAnimator(new DefaultItemAnimator());
        now_playing_recyclerView.setAdapter(now_playing_adapter);

        RecyclerView.LayoutManager UpcomingLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        upcoming_movies_recyclerView.setLayoutManager(UpcomingLayoutManager);
        upcoming_movies_recyclerView.setItemAnimator(new DefaultItemAnimator());
        upcoming_movies_recyclerView.setAdapter(upcoming_movies_adapter);


        now_playing_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), now_playing_recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = now_playing_movies_list.get(position);
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("movie_id", movie.getMovie_id());
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        upcoming_movies_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), upcoming_movies_recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = upcoming_movies_list.get(position);
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("movie_id", movie.getMovie_id());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



        DisplayMovies();
        return view;
    }

   private void makeJsonObjectRequest(final ArrayList<Movie> moviesList, final MoviesAdapter moviesAdapter, final String urlJsonObj) {

   JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object

                    JSONArray results = response.getJSONArray("results");
                    for (int i=0; i<results.length();i++)
                    {
                        JSONObject movie_json = (JSONObject) results.get(i);

                        int movie_id = movie_json.getInt("id");
                        String title = movie_json.getString("title");
                        String poster = "https://image.tmdb.org/t/p/w500" + movie_json.getString("poster_path");
                        String release_date = movie_json.getString("release_date");

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date datetime = sdf.parse(release_date);
                        Date today = sdf.parse(sdf.format(new Date()));

                        String url = ThemoviedbApiAccess.UPCOMING_MOVIES;
                        if (urlJsonObj.equals(url) || urlJsonObj.equals(url+"&page=2") || urlJsonObj.equals(url+"&page=3") )
                        {
                            if (datetime.after(today))
                            {
                                if(!IsListContainsElement(movie_id,moviesList))
                                {
                                    moviesList.add(new Movie(movie_id,title,release_date,poster));
                                    moviesAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        else
                        {
                            if(!IsListContainsElement(movie_id,moviesList))
                            {
                                moviesList.add(new Movie(movie_id,title,release_date,poster));
                                moviesAdapter.notifyDataSetChanged();
                            }
                        }

                    }

                    Collections.sort(moviesList, new Comparator<Movie>() {
                        public int compare(Movie o1, Movie o2) {
                            if (o1.getDateTime() == null || o2.getDateTime() == null)
                                return 0;
                            return o2.getDateTime().compareTo(o1.getDateTime());
                        }
                    });
                    moviesAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    e.printStackTrace();
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

    private boolean IsListContainsElement(int movie_id, ArrayList<Movie> movie_list)
    {
        int i=0;
        boolean found=false;
        while (i<movie_list.size())
        {
            if (movie_list.get(i).getMovie_id()==movie_id)
                found = true;
            i++;
        }
        return found;
    }



    private void DisplayMovies() {

        makeJsonObjectRequest(now_playing_movies_list, now_playing_adapter, ThemoviedbApiAccess.NOW_PALYING_MOVIES);
        makeJsonObjectRequest(now_playing_movies_list, now_playing_adapter, ThemoviedbApiAccess.NOW_PALYING_MOVIES+"&page=2");

        makeJsonObjectRequest(upcoming_movies_list, upcoming_movies_adapter, ThemoviedbApiAccess.UPCOMING_MOVIES);
        makeJsonObjectRequest(upcoming_movies_list, upcoming_movies_adapter, ThemoviedbApiAccess.UPCOMING_MOVIES+"&page=2");
        makeJsonObjectRequest(upcoming_movies_list, upcoming_movies_adapter, ThemoviedbApiAccess.UPCOMING_MOVIES+"&page=3");


    }

}
