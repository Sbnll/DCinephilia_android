package tpdev.upmc.dcinephila.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import tpdev.upmc.dcinephila.APIs.ThemoviedbApiAccess;
import tpdev.upmc.dcinephila.DesignClasses.AppController;
import tpdev.upmc.dcinephila.R;

public class ImagesSliderActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener{

    private SliderLayout sliderLayout ;
    private static String TAG = ImagesSliderActivity.class.getSimpleName();
    private ArrayList<String> imagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_slider);

        Intent intent = getIntent();
        int movie_id = intent.getIntExtra("element_id",0);
        String movie_title = intent.getStringExtra("element_title").toString();
        sliderLayout = (SliderLayout)findViewById(R.id.slider);
        imagesList= new ArrayList<String>();

        GetMovieImages(ThemoviedbApiAccess.MovieBackdrops(movie_id), movie_title);

    }


    private void GetMovieImages(final String urlJsonObj, final String movie_title) {

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
                        if (!image_json.getString("file_path").equals("null"))
                            imagesList.add(image_url);
                    }
                    sliderLayout.stopAutoCycle();
                    for(int i=0; i<imagesList.size(); i++){

                        TextSliderView textSliderView = new TextSliderView(ImagesSliderActivity.this);
                        textSliderView
                                .description(movie_title + " - "+ (i+1))
                                .image(imagesList.get(i))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(ImagesSliderActivity.this);

                        sliderLayout.addSlider(textSliderView);
                    }

                    sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);

                    sliderLayout.addOnPageChangeListener(ImagesSliderActivity.this);

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


    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        //  Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
