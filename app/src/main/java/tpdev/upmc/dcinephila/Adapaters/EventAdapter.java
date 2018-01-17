package tpdev.upmc.dcinephila.Adapaters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tpdev.upmc.dcinephila.Beans.Seance;
import tpdev.upmc.dcinephila.DesignClasses.Utils;
import tpdev.upmc.dcinephila.R;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

import static tpdev.upmc.dcinephila.DesignClasses.Utils.setupItem;

/**
 * Created by Wissou on 25/12/2017.
 */

public class HorizontalPagerAdapter extends PagerAdapter {

    private List<Utils.EventItem> eventsList;

    /*private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.ic_strategy,
                    "Strategy"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_design,
                    "Design"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_development,
                    "Development"
            ),
            new Utils.LibraryObject(
                    R.drawable.ic_qa,
                    "Quality Assurance"
            )
    };*/

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public HorizontalPagerAdapter(final Context context, final ArrayList<Utils.EventItem> eventsList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.eventsList = eventsList;
    }

    @Override
    public int getCount() {
        return eventsList.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, eventsList.get(position),mContext);


        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

}
