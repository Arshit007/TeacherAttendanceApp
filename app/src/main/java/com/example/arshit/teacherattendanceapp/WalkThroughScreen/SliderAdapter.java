package com.example.arshit.teacherattendanceapp.WalkThroughScreen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arshit.teacherattendanceapp.R;


public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;



    public SliderAdapter(Context context) {

        this.context = context;
    }

    // img Array
    public int[] image_slide ={
            R.drawable.bg1,
            R.drawable.bg2,
            R.drawable.bg3
    };

    // heading Array
    public int[] heading_slide ={

            R.color.pink,
            R.color.btnred,
            R.color.blue,
    };
//
//    // description Array
//    public String[] description_slide ={
//            "Splash Screen like professional with Animation in Android Studio",
//            "Splash Screen with Transition Animation",
//            " simple way to create Splash Scree"
//    };




    @Override
    public int getCount() {

        return image_slide.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container,false);
        container.addView(view);

        ImageView slide_imageView = view.findViewById(R.id.imageView1);
//        TextView slideHeading = view.findViewById(R.id.tvHeading);
//        TextView  slideDescription = view.findViewById(R.id.tvDescription);

        RelativeLayout relativeLayout = view.findViewById(R.id.slide_relative);

        slide_imageView.setImageResource(image_slide[position]);
        relativeLayout.setBackgroundColor(heading_slide[position]);
//        slideDescription.setText(description_slide[position]);

        return view;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }


}
