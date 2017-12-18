package tpdev.upmc.dcinephila.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.FloatProperty;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.Activities.MainActivity;
import tpdev.upmc.dcinephila.Activities.MovieDetailsActivity;
import tpdev.upmc.dcinephila.Activities.TvShowsDetailsActivity;
import tpdev.upmc.dcinephila.Adapaters.MoviesAdapter;
import tpdev.upmc.dcinephila.Adapaters.TvShowsAdapter;
import tpdev.upmc.dcinephila.Beans.Movie;
import tpdev.upmc.dcinephila.Beans.TVshow;
import tpdev.upmc.dcinephila.DesignClasses.AppController;
import tpdev.upmc.dcinephila.DesignClasses.RecyclerTouchListener;
import tpdev.upmc.dcinephila.R;


public class TvshowsFragment extends Fragment {


    private TextView now_playing_text, top_rated_text;
    private RecyclerView now_playing_shows_recyclerView, top_rated_shows_recyclerView;
    private TvShowsAdapter now_playing_shows_adapter, top_rated_shows_adapter;
    private ArrayList<TVshow> now_playing_shows_list, top_rated_shows_list;
    private static String TAG = MainActivity.class.getSimpleName();

    public TvshowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tvshows, container, false);
        Typeface face= Typeface.createFromAsset(getContext().getAssets(), "font/Comfortaa-Bold.ttf");

        now_playing_text = (TextView) view.findViewById(R.id.now_playing_text);
        top_rated_text = (TextView) view.findViewById(R.id.top_rated_text);

        now_playing_text.setTypeface(face);
        top_rated_text.setTypeface(face);

        now_playing_shows_recyclerView = (RecyclerView) view.findViewById(R.id.now_playing);
        top_rated_shows_recyclerView = (RecyclerView) view.findViewById(R.id.top_rated_shows);

        now_playing_shows_list = new ArrayList<TVshow>();
        top_rated_shows_list = new ArrayList<TVshow>();

        now_playing_shows_adapter = new TvShowsAdapter(getContext(), now_playing_shows_list);
        top_rated_shows_adapter = new TvShowsAdapter(getContext(), top_rated_shows_list);

        RecyclerView.LayoutManager NowPlayingLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        now_playing_shows_recyclerView.setLayoutManager(NowPlayingLayoutManager);
        now_playing_shows_recyclerView.setAdapter(now_playing_shows_adapter);

        RecyclerView.LayoutManager TopRatedLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        top_rated_shows_recyclerView.setLayoutManager(TopRatedLayoutManager);
        top_rated_shows_recyclerView.setItemAnimator(new DefaultItemAnimator());
        top_rated_shows_recyclerView.setAdapter(top_rated_shows_adapter);

        now_playing_shows_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), now_playing_shows_recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TVshow show = now_playing_shows_list.get(position);
                Intent intent = new Intent(getContext(), TvShowsDetailsActivity.class);
                intent.putExtra("show_id", show.getShow_id());
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        top_rated_shows_recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), top_rated_shows_recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TVshow show = top_rated_shows_list.get(position);
                Intent intent = new Intent(getContext(), TvShowsDetailsActivity.class);
                intent.putExtra("show_id", show.getShow_id());
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        DisplayTVShows();
        return view;

    }


    private void GetTvShows(final ArrayList<TVshow> showsList, final TvShowsAdapter tvShowsAdapter, final String urlJsonObj) {

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
                        JSONObject show_json = (JSONObject) results.get(i);

                        int show_id = show_json.getInt("id");
                        String title = show_json.getString("name");
                        String poster = "https://image.tmdb.org/t/p/w500" + show_json.getString("poster_path");
                        String airing_date = show_json.getString("first_air_date");
                        Float vote_average = Float.parseFloat(show_json.getString("vote_average"));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date datetime = sdf.parse(airing_date);
                        Calendar c = Calendar.getInstance();
                        c.setTime(datetime);
                        {
                            if(!IsListContainsElement(show_id,showsList))
                            {
                                if (show_json.getString("original_language").equals("en") && c.get(Calendar.YEAR)>=2005)
                                {
                                    showsList.add(new TVshow(show_id, title,airing_date,poster,vote_average));
                                    tvShowsAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                        String url = ThemoviedbApiAccess.MOST_POPULAR_TVSHOWS;
                        if (urlJsonObj.equals(url) || urlJsonObj.equals(url+"&page=2") || urlJsonObj.equals(url+"&page=3") )
                            Collections.sort(showsList, new Comparator<TVshow>() {
                                public int compare(TVshow o1, TVshow o2) {
                                    if (o1.getVote_average() == 0 || o2.getVote_average() == 0)
                                        return 0;
                                    return Float.valueOf(o2.getVote_average()).compareTo(Float.valueOf(o1.getVote_average()));
                                }
                            });
                        tvShowsAdapter.notifyDataSetChanged();


                    }


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

    private boolean IsListContainsElement(int show_id, ArrayList<TVshow> shows_list)
    {
        int i=0;
        boolean found=false;
        while (i<shows_list.size())
        {
            if (shows_list.get(i).getShow_id()==show_id)
                found = true;
            i++;
        }
        return found;
    }

    private void DisplayTVShows() {

        GetTvShows(now_playing_shows_list, now_playing_shows_adapter, ThemoviedbApiAccess.ON_THE_AIR_TVSHOWS);
        GetTvShows(now_playing_shows_list, now_playing_shows_adapter, ThemoviedbApiAccess.ON_THE_AIR_TVSHOWS+"&page=2");
        GetTvShows(now_playing_shows_list, now_playing_shows_adapter, ThemoviedbApiAccess.ON_THE_AIR_TVSHOWS+"&page=3");

        GetTvShows(top_rated_shows_list, top_rated_shows_adapter, ThemoviedbApiAccess.MOST_POPULAR_TVSHOWS);
        GetTvShows(top_rated_shows_list, top_rated_shows_adapter, ThemoviedbApiAccess.MOST_POPULAR_TVSHOWS+"&page=2");
    }

}
