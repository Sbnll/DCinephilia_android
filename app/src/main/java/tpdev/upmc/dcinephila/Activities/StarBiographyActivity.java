package tpdev.upmc.dcinephila.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;
import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.Adapaters.MoviesAdapter;
import tpdev.upmc.dcinephila.Beans.Actor;
import tpdev.upmc.dcinephila.Beans.Movie;
import tpdev.upmc.dcinephila.DesignClasses.AppController;
import tpdev.upmc.dcinephila.DesignClasses.MySpannable;
import tpdev.upmc.dcinephila.DesignClasses.RecyclerTouchListener;
import tpdev.upmc.dcinephila.R;

public class StarBiographyActivity extends AppCompatActivity {

    private TextView actor_name, birthday_text, birthday_place_text, birthday_value, birthday_place_value,
            biopgraphy, movies_text, movies_number;
    private RecyclerView moviesRecycerView;
    private CircleImageView actor_avatar;
    private KenBurnsView background;
    private ArrayList<Movie> moviesList;
    private MoviesAdapter moviesAdapter;
    private static String TAG = StarBiographyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_biography);

        Intent intent = getIntent();
        final int person_id = intent.getIntExtra("person_id",0);
        Typeface face= Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Light.ttf");
        Typeface face_bold= Typeface.createFromAsset(getApplicationContext().getAssets(), "font/Comfortaa-Bold.ttf");

        actor_name = (TextView) findViewById(R.id.actor_name);
        birthday_text = (TextView) findViewById(R.id.birthday_text);
        birthday_value = (TextView) findViewById(R.id.birthday_value);
        birthday_place_text = (TextView) findViewById(R.id.birthday_place_text);
        birthday_place_value = (TextView) findViewById(R.id.birthday_place_value);
        biopgraphy = (TextView) findViewById(R.id.biography);
        actor_avatar = (CircleImageView) findViewById(R.id.avatar);
        background = (KenBurnsView) findViewById(R.id.background);
        movies_text = (TextView) findViewById(R.id.movies_text);
        movies_number = (TextView) findViewById(R.id.movies_number);
        moviesRecycerView = (RecyclerView) findViewById(R.id.movies_recyclerview);

        actor_name.setTypeface(face_bold);
        birthday_text.setTypeface(face_bold);
        birthday_place_text.setTypeface(face_bold);
        movies_text.setTypeface(face_bold);
        birthday_value.setTypeface(face);
        birthday_place_value.setTypeface(face);
        biopgraphy.setTypeface(face);
        movies_number.setTypeface(face);

        moviesList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, moviesList);
        RecyclerView.LayoutManager MoviesLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        moviesRecycerView.setLayoutManager(MoviesLayoutManager);
        moviesRecycerView.setItemAnimator(new DefaultItemAnimator());
        moviesRecycerView.setAdapter(moviesAdapter);
        moviesRecycerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), moviesRecycerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = moviesList.get(position);
                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                intent.putExtra("movie_id", movie.getMovie_id());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        GetStarDetails(ThemoviedbApiAccess.AllPersonDetailsURL(person_id));
        GetActorMovies(ThemoviedbApiAccess.PersonMovies(person_id));

    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText().toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText().toString(), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(tv.getText().toString(), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final String strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(true){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 6, "View More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    private void GetStarDetails(final String urlJsonObj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    actor_name.setText(response.getString("name"));
                    if (!response.getString("birthday").equals("null"))birthday_value.setText(response.getString("birthday"));
                    if (!response.getString("place_of_birth").equals("null")) birthday_place_value.setText(response.getString("place_of_birth"));
                    biopgraphy.setText(response.getString("biography"));
                    Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + response.getString("profile_path")).into(actor_avatar);
                    makeTextViewResizable(biopgraphy, 6, "View More", true);

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

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void GetActorMovies(final String urlJsonObj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    JSONArray movies_array = (JSONArray) response.getJSONArray("cast");
                    for (int i=0; i<movies_array.length(); i++)
                    {
                        JSONObject movie_json = (JSONObject) movies_array.get(i);
                        int movie_id = movie_json.getInt("id");
                        String title = movie_json.getString("title");
                        String poster = "https://image.tmdb.org/t/p/w500" + movie_json.getString("poster_path");
                        String release_date="";
                        try {
                            if (movie_json.getString("release_date")!=null)
                                release_date = movie_json.getString("release_date");
                        }
                        catch (Exception e){
                            release_date="";
                        }

                        if(!movie_json.getString("poster_path").equals("null"))
                        {
                            moviesList.add(new Movie(movie_id,title,release_date,poster));
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
                    movies_number.setText("•  " +String.valueOf(moviesList.size()));

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

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
