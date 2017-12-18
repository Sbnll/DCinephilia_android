package tpdev.upmc.dcinephila.DesignClasses;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import butterknife.BindView;
import butterknife.ButterKnife;
import tpdev.upmc.dcinephila.R;

/**
 * Created by Wissou on 16/12/2017.
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder {


    public TextView episode_date, episode_title, episode_resume;
    public ImageView episode_picture;
    public TimelineView episode_timeline_view;


    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);


        episode_date= itemView.findViewById(R.id.episode_date);
        episode_title = itemView.findViewById(R.id.episode_title);
        episode_resume = itemView.findViewById(R.id.episode_resume);
        episode_picture = itemView.findViewById(R.id.episode_picture);
        episode_timeline_view = itemView.findViewById(R.id.time_marker);
        episode_timeline_view.initLine(viewType);
    }
}