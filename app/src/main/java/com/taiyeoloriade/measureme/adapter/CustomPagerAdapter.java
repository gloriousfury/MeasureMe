package com.taiyeoloriade.measureme.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taiyeoloriade.measureme.R;


public class CustomPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] titleArray;
    String[] descriptionArray;
    int[] iconsArray;

    LayoutInflater inflater;

    public CustomPagerAdapter(Context context, String[] title, String[] description ) {
        this.context = context;
        this.titleArray = title;
        this.descriptionArray = description;



    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        TextView title;
        TextView description;
        ImageView icon;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
//        icon =  (ImageView) itemView.findViewById(R.id.icon);

        // Capture position and set to the TextViews
        title.setText(titleArray[position]);
        description.setText(descriptionArray[position]);
//        icon.setBackgroundResource(iconsArray[position]);

//
//        // Locate the ImageView in viewpager_item.xml
//        imgflag = (ImageView) itemView.findViewById(R.id.flag);
//        // Capture position and set to the ImageView
//        imgflag.setImageResource(flag[position]);

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}