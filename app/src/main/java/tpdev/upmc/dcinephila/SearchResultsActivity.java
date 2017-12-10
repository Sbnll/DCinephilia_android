package tpdev.upmc.dcinephila;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
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
import java.util.Date;

import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.Adapaters.MoviesAdapter;
import tpdev.upmc.dcinephila.Adapaters.SearchResultsAdapter;
import tpdev.upmc.dcinephila.Beans.Movie;

public class SearchResultsActivity extends AppCompatActivity {

    private TextView search_text;
    private RecyclerView searchResultsRecyclerView;
    private SearchResultsAdapter searchResultsAdapter;
    private ArrayList<Movie> resultList;
    private static String TAG = SearchResultsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query").toString();

        Typeface face= Typeface.createFromAsset(this.getAssets(), "font/Comfortaa-Bold.ttf");

        search_text = (TextView) findViewById(R.id.search_text);
        searchResultsRecyclerView = (RecyclerView) findViewById(R.id.search_results_recyclerview);
        resultList = new ArrayList<Movie>();
        searchResultsAdapter = new SearchResultsAdapter(this, resultList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        searchResultsRecyclerView.setLayoutManager(mLayoutManager);
        searchResultsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        searchResultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);

        searchResultsRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), searchResultsRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Movie movie = resultList.get(position);
                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                intent.putExtra("movie_id", movie.getMovie_id());
                startActivity(intent);
                //Toast.makeText(getContext(), movie.getMovie_title() + " iiiiiiiiiiis   selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        makeJsonObjectRequest(resultList,searchResultsAdapter,ThemoviedbApiAccess.MULTIPLE_SEARCH+query);
    }

    private void makeJsonObjectRequest(final ArrayList<Movie> moviesList, final SearchResultsAdapter searchResultsAdapter, final String urlJsonObj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Parsing json object response
                    // response will be a json object

                    JSONArray results = response.getJSONArray("results");
                    for (int i=0; i<results.length();i++)
                    {
                        JSONObject result_json = (JSONObject) results.get(i);

                        String media_type = result_json.getString("media_type");

                        if (media_type.equals("movie"))
                        {
                            int movie_id = result_json.getInt("id");
                            String title = result_json.getString("title");
                            String poster = "https://image.tmdb.org/t/p/w500" + result_json.getString("poster_path");
                            String release_date = result_json.getString("release_date");
                            resultList.add(new Movie(movie_id,title,release_date,poster));
                            searchResultsAdapter.notifyDataSetChanged();

                        }
                    }

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


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
