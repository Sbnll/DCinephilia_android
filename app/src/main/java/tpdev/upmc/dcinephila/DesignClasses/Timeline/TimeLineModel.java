package tpdev.upmc.dcinephila.Timeline;

import android.os.Parcel;
import android.os.Parcelable;

import tpdev.upmc.dcinephila.Beans.Season;

/**
 * Created by Wissou on 16/12/2017.
 */

public class TimeLineModel implements Parcelable {

    private String episode_title;
    private String episode_date;
    private String episode_picture;
    private String episode_resume;
    private OrderStatus episode_status;

    public TimeLineModel() {
    }

    public TimeLineModel(String episode_title, String episode_date, String episode_picture, String episode_resume, OrderStatus episode_status) {
        this.episode_title = episode_title;
        this.episode_date = episode_date;
        this.episode_picture = episode_picture;
        this.episode_resume = episode_resume;
        this.episode_status = episode_status;
    }

    public String getEpisode_title() {
        return episode_title;
    }

    public void setEpisode_title(String episode_title) {
        this.episode_title = episode_title;
    }

    public String getEpisode_date() {
        return episode_date;
    }

    public void setEpisode_date(String episode_date) {
        this.episode_date = episode_date;
    }

    public String getEpisode_picture() {
        return episode_picture;
    }

    public void setEpisode_picture(String episode_picture) {
        this.episode_picture = episode_picture;
    }

    public String getEpisode_resume() {
        return episode_resume;
    }

    public void setEpisode_resume(String episode_resume) {
        this.episode_resume = episode_resume;
    }

    public OrderStatus getEpisode_status() {
        return episode_status;
    }

    public void setEpisode_status(OrderStatus episode_status) {
        this.episode_status = episode_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.episode_title);
        dest.writeString(this.episode_date);
        dest.writeString(this.episode_resume);
        dest.writeString(this.episode_picture);
        dest.writeInt(this.episode_status == null ? -1 : this.episode_status.ordinal());
    }

    protected TimeLineModel(Parcel in) {
        this.episode_title = in.readString();
        this.episode_date = in.readString();
        this.episode_resume = in.readString();
        this.episode_picture = in.readString();
        int tmpMStatus = in.readInt();
        this.episode_status = tmpMStatus == -1 ? null : OrderStatus.values()[tmpMStatus];
    }

    public static final Parcelable.Creator<TimeLineModel> CREATOR = new Parcelable.Creator<TimeLineModel>() {
        @Override
        public TimeLineModel createFromParcel(Parcel source) {
            return new TimeLineModel(source);
        }

        @Override
        public TimeLineModel[] newArray(int size) {
            return new TimeLineModel[size];
        }
    };
}
