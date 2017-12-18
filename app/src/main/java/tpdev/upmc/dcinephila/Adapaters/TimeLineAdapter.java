package tpdev.upmc.dcinephila.Adapaters;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import tpdev.upmc.dcinephila.DesignClasses.TimeLineViewHolder;
import tpdev.upmc.dcinephila.R;
import tpdev.upmc.dcinephila.Timeline.DateTimeUtils;
import tpdev.upmc.dcinephila.Timeline.OrderStatus;
import tpdev.upmc.dcinephila.Timeline.Orientation;
import tpdev.upmc.dcinephila.Timeline.TimeLineModel;
import tpdev.upmc.dcinephila.Timeline.VectorDrawableUtils;

/**
 * Created by Wissou on 16/12/2017.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private Orientation mOrientation;
    private boolean mWithLinePadding;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<TimeLineModel> feedList, Orientation orientation, boolean withLinePadding) {
        mFeedList = feedList;
        mOrientation = orientation;
        mWithLinePadding = withLinePadding;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view;

        if(mOrientation == Orientation.HORIZONTAL) {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_horizontal_line_padding : R.layout.item_timeline_horizontal, parent, false);
        } else {
            view = mLayoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_line_padding : R.layout.item_timeline, parent, false);
        }

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {

        TimeLineModel timeLineModel = mFeedList.get(position);
        Typeface face= Typeface.createFromAsset(mContext.getAssets(), "font/Comfortaa-Light.ttf");
        Typeface face_bold= Typeface.createFromAsset(mContext.getAssets(), "font/Comfortaa-Bold.ttf");
        holder.episode_title.setTypeface(face_bold);
        holder.episode_resume.setTypeface(face);
        holder.episode_date.setTypeface(face);

        if(timeLineModel.getEpisode_status() == OrderStatus.INACTIVE) {
            holder.episode_timeline_view.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
        } else if(timeLineModel.getEpisode_status() == OrderStatus.ACTIVE) {
            holder.episode_timeline_view.setMarker(VectorDrawableUtils.getDrawable(mContext, R.drawable.ic_marker_active, R.color.colorPrimary));
        } else {
            holder.episode_timeline_view.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
        }

        if(!timeLineModel.getEpisode_date().isEmpty()) {
            holder.episode_date.setVisibility(View.VISIBLE);
            holder.episode_date.setText(DateTimeUtils.parseDateTime(timeLineModel.getEpisode_date(), "yyyy-MM-dd", "dd-MMM-yyyy"));
        }
        else
            holder.episode_date.setVisibility(View.GONE);



        holder.episode_title.setText(timeLineModel.getEpisode_title());
        holder.episode_resume.setText(timeLineModel.getEpisode_resume());
        Glide.with(mContext).load(timeLineModel.getEpisode_picture()).into(holder.episode_picture);
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

}