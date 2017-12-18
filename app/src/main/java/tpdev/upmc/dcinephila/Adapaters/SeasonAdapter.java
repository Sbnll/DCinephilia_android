package tpdev.upmc.dcinephila.Adapaters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import tpdev.upmc.dcinephila.Beans.Movie;
import tpdev.upmc.dcinephila.Beans.Season;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Wissou on 16/12/2017.
 */

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {

    private Context mContext;
    private List<Season> seasonsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, release_date, episodes_number;
        public ImageView poster;
        public CardView cardview;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            release_date = (TextView) view.findViewById(R.id.first_air_date);
            poster = (ImageView) view.findViewById(R.id.poster);
            episodes_number = (TextView) view.findViewById(R.id.episodes_number);
            cardview = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public SeasonAdapter(Context mContext, List<Season> seasonsList) {
        this.mContext = mContext;
        this.seasonsList = seasonsList;
    }

    @Override
    public SeasonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.season_card, parent, false);

        return new SeasonAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SeasonAdapter.MyViewHolder holder, final int position) {
        Season season = seasonsList.get(position);
        holder.title.setText("Saison " + season.getSeason_number());
        holder.release_date.setText(season.getSeason_date());
        holder.episodes_number.setText(season.getSeason_episodes_number() + " Episodes");

        // loading album cover using Glide library
        Glide.with(mContext).load(season.getPoster()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }

}
