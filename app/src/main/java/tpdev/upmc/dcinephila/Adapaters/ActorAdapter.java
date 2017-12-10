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

import tpdev.upmc.dcinephila.Beans.Actor;
import tpdev.upmc.dcinephila.Beans.Movie;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Wissou on 09/12/2017.
 */

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.MyViewHolder> {

    private Context mContext;
    private List<Actor> actorsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, character;
        public ImageView profile_picture;
        public CardView cardview;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            character = (TextView) view.findViewById(R.id.character);
            profile_picture = (ImageView) view.findViewById(R.id.poster);
            cardview = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public ActorAdapter(Context mContext, List<Actor> actorsList) {
        this.mContext = mContext;
        this.actorsList = actorsList;
    }

    @Override
    public ActorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actor_card, parent, false);

        return new ActorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ActorAdapter.MyViewHolder holder, final int position) {
        Actor actor = actorsList.get(position);
        holder.name.setText(actor.getActor_name());
        holder.character.setText(actor.getCharacter());
        Glide.with(mContext).load(actor.getProfile_picture()).into(holder.profile_picture);
    }

    @Override
    public int getItemCount() {
        return actorsList.size();
    }

}
