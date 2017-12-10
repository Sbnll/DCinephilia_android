package tpdev.upmc.dcinephila;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.Adapaters.ActorAdapter;
import tpdev.upmc.dcinephila.Adapaters.ImageAdapter;
import tpdev.upmc.dcinephila.Adapaters.MoviesAdapter;
import tpdev.upmc.dcinephila.Adapaters.SearchResultsAdapter;
import tpdev.upmc.dcinephila.Beans.Actor;
import tpdev.upmc.dcinephila.Beans.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView movie_title, movie_overview, movie_release_date, movie_director, movie_genres,
            movie_runtime, images_text, casting_text, actors_number, images_number, rating_value, seeImageBtn,
            similar_movies_text, similar_movies_number;
    private RatingBar ratingBar;
    private ImageView movie_poster;
    private RecyclerView castingRecyclerView, imageRecyclerView, similarMoviesRecyclerViewer;
    private ActorAdapter actorAdapter;
    private ImageAdapter imageAdapter;
    private MoviesAdapter moviesAdapter;
    private ArrayList<Actor> actorsList;
    private ArrayList<String> imagesList;
    private ArrayList<Movie> similarMoviesList;
    private static String TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        final int movie_id = intent.getIntExtra("movie_id",0);
        Typeface face= Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Light.ttf");
        Typeface face_bold= Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Bold.ttf");

        movie_title = (TextView) findViewById(R.id.movie_title);
        movie_overview = (TextView) findViewById(R.id.movie_overview);
        movie_release_date = (TextView) findViewById(R.id.date_release);
        movie_director = (TextView) findViewById(R.id.director);
        movie_genres = (TextView) findViewById(R.id.movie_genres);
        movie_runtime = (TextView) findViewById(R.id.movie_runtime);
        movie_poster = (ImageView) findViewById(R.id.poster);
        casting_text = (TextView) findViewById(R.id.casting_text);
        images_text = (TextView) findViewById(R.id.images_text);
        similar_movies_text = (TextView) findViewById(R.id.similar_movies_text);
        actors_number = (TextView) findViewById(R.id.actors_number);
        images_number = (TextView) findViewById(R.id.images_number);
        similar_movies_number = (TextView) findViewById(R.id.similar_movies_number);
        rating_value = (TextView) findViewById(R.id.rating_value);
        castingRecyclerView = (RecyclerView) findViewById(R.id.casting_recyclerview);
        imageRecyclerView = (RecyclerView) findViewById(R.id.images_recyclerview);
        similarMoviesRecyclerViewer = (RecyclerView) findViewById(R.id.similar_movies_recyclerview);
        ratingBar = (RatingBar) findViewById(R.id.RatingBar);
        seeImageBtn = (TextView) findViewById(R.id.seeImages);

        movie_title.setTypeface(face_bold);
        movie_overview.setTypeface(face);
        movie_release_date.setTypeface(face);
        movie_release_date.setTypeface(face);
        movie_director.setTypeface(face);
        movie_genres.setTypeface(face);
        movie_runtime.setTypeface(face);
        actors_number.setTypeface(face);
        images_number.setTypeface(face);
        similar_movies_number.setTypeface(face);
        rating_value.setTypeface(face);
        seeImageBtn.setTypeface(face_bold);
        casting_text.setTypeface(face_bold);
        images_text.setTypeface(face_bold);
        similar_movies_text.setTypeface(face_bold);

        actorsList = new ArrayList<Actor>();
        imagesList = new ArrayList<String>();
        similarMoviesList = new ArrayList<Movie>();
        actorAdapter = new ActorAdapter(this, actorsList);
        imageAdapter = new ImageAdapter(this, imagesList);
        moviesAdapter = new MoviesAdapter(this, similarMoviesList);

        RecyclerView.LayoutManager CastLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        castingRecyclerView.setLayoutManager(CastLayoutManager);
        castingRecyclerView.setItemAnimator(new DefaultItemAnimator());
        castingRecyclerView.setAdapter(actorAdapter);

        RecyclerView.LayoutManager ImagesLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(ImagesLayoutManager);
        imageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        imageRecyclerView.setAdapter(imageAdapter);

        RecyclerView.LayoutManager SimilarMoviesLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        similarMoviesRecyclerViewer.setLayoutManager(SimilarMoviesLayoutManager);
        similarMoviesRecyclerViewer.setItemAnimator(new DefaultItemAnimator());
        similarMoviesRecyclerViewer.setAdapter(moviesAdapter);
        similarMoviesRecyclerViewer.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), similarMoviesRecyclerViewer, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = similarMoviesList.get(position);
                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                intent.putExtra("movie_id", movie.getMovie_id());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        seeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieDetailsActivity.this, ImagesSlider.class);
                intent.putExtra("movie_id", movie_id);
                intent.putExtra("movie_title",movie_title.getText().toString());
                startActivity(intent);
            }
        });

        GetMovieDetails(ThemoviedbApiAccess.AllMovieDetailsURL(movie_id));
        GetMovieImages(ThemoviedbApiAccess.MovieBackdrops(movie_id));
        GetSimilarMovies(ThemoviedbApiAccess.MovieRecommendationsURl(movie_id));
    }

    private void GetMovieDetails(final String urlJsonObj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    movie_title.setText(response.getString("title"));
                    movie_release_date.setText("Sortie : "+ response.getString("release_date"));
                    rating_value.setText(response.getString("vote_average"));
                    ratingBar.setRating(Float.valueOf(response.getString("vote_average")));
                    // Getting movie runtime
                    if (response.getString("runtime").equals("0") || response.getString("runtime").equals("null")  )
                    {
                        movie_runtime.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        int runtime = Integer.valueOf(response.getString("runtime"));
                        int hours = runtime / 60;
                        int minutes = runtime % 60;
                        String minutes_text = String.valueOf(minutes);
                        if (minutes<10) minutes_text = "0"+ String.valueOf(minutes);
                        String runtime_text = String.valueOf(hours)+"h"+minutes_text;
                        movie_runtime.setText("Durée : " + runtime_text);
                    }
                    movie_overview.setText(response.getString("overview"));
                    Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + response.getString("poster_path")).into(movie_poster);

                    // Getting movie genres
                    String genres_text = "";
                    JSONArray genres = response.getJSONArray("genres");
                    for (int i=0; i<genres.length();i++) {
                        JSONObject result_json = (JSONObject) genres.get(i);
                        String genre_name = result_json.getString("name");
                        genres_text+= result_json.getString("name");
                        if (i+1<genres.length()) genres_text+=" | ";
                    }
                    movie_genres.setText("Genres : " + genres_text);

                    JSONObject movie_credits = (JSONObject) response.getJSONObject("credits");
                    JSONArray movie_cast = (JSONArray) movie_credits.getJSONArray("cast");
                    JSONArray movie_crew = (JSONArray) movie_credits.getJSONArray("crew");

                    movie_director.setText("De : " + movie_crew.getJSONObject(0).getString("name"));

                    for (int i=0; i<movie_cast.length(); i++)
                    {
                        JSONObject actor_json = (JSONObject) movie_cast.get(i);

                        int actor_id = actor_json.getInt("id");
                        String name = actor_json.getString("name");
                        String profile_picture = "https://image.tmdb.org/t/p/w500" + actor_json.getString("profile_path");
                        String character = actor_json.getString("character");

                        if (!actor_json.getString("profile_path").equals("null"))
                        {
                            actorsList.add(new Actor(actor_id,name,character,profile_picture));
                            actorAdapter.notifyDataSetChanged();
                        }
                    }
                    actors_number.setText("•  " +String.valueOf(actorsList.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
            }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void GetMovieImages(final String urlJsonObj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    JSONArray movie_backdrops = (JSONArray) response.getJSONArray("backdrops");


                    for (int i=0; i<movie_backdrops.length(); i++)
                    {
                        JSONObject image_json = (JSONObject) movie_backdrops.get(i);
                        String image_url = "https://image.tmdb.org/t/p/w780" + image_json.getString("file_path");

                        imagesList.add(image_url);
                        imageAdapter.notifyDataSetChanged();
                    }
                    images_number.setText("•  " +String.valueOf(imagesList.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void GetSimilarMovies(final String urlJsonObj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    JSONArray results = response.getJSONArray("results");
                    for (int i=0; i<results.length();i++)
                    {
                        JSONObject movie_json = (JSONObject) results.get(i);

                        int movie_id = movie_json.getInt("id");
                        String title = movie_json.getString("title");
                        String poster = "https://image.tmdb.org/t/p/w500" + movie_json.getString("poster_path");
                        String release_date = movie_json.getString("release_date");

                        if(!movie_json.getString("poster_path").equals("null"))
                        {
                            similarMoviesList.add(new Movie(movie_id,title,release_date,poster));
                            moviesAdapter.notifyDataSetChanged();
                        }

                    }
                    similar_movies_number.setText("•  " +String.valueOf(similarMoviesList.size()));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
