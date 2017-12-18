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
import tpdev.upmc.dcinephila.Beans.TVshow;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Wissou on 13/12/2017.
 */

public class TvShowsAdapter  extends RecyclerView.Adapter<TvShowsAdapter.MyViewHolder> {

    private Context mContext;
    private List<TVshow> showsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, airing_date;
        public ImageView poster;
        public CardView cardview;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            airing_date = (TextView) view.findViewById(R.id.release_date);
            poster = (ImageView) view.findViewById(R.id.poster);
            cardview = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public TvShowsAdapter(Context mContext, List<TVshow> showsList) {
        this.mContext = mContext;
        this.showsList = showsList;
    }

    @Override
    public TvShowsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card, parent, false);

        return new TvShowsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TvShowsAdapter.MyViewHolder holder, final int position) {
        TVshow show = showsList.get(position);
        holder.title.setText(show.getShow_title());
        holder.airing_date.setText(show.getAiring_date());

        // loading album cover using Glide library
        Glide.with(mContext).load(show.getPoster()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

}
