package com.github.florent37.materialviewpager.sample.SelectFishRecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.florent37.materialviewpager.sample.MainActivity;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.fragment.RecyclerViewFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectFishActivity extends DrawerActivity implements RecyclerViewFragment.OnLogoSelectedLisnter {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    final Integer numberOfTab = 4;
    private ArrayList<Integer>[] group = new ArrayList[numberOfTab];

    @Override
    public void onLogoSelected(int personalInfo,int offset) {
        //물고기 모양(logo) customizing 눌릴때마다 바뀌게할것인지...
        final boolean isOverlapped = group[offset].contains(personalInfo);
            if (isOverlapped) {
                group[offset].remove((Integer) personalInfo);
                /*
                * switch(offset){
                *   case 0 :{
                *     findViewById(R.id.logo_white).setBackgroundColor(Color.BLUE);
                * }
                *   선택한 personalInfo대로 바로 바뀜? 조합대로 바뀌게 하면 너무많은 조합이 나옴
                * } 여기 구현은 아직 미정.
                * */
            } else {
                group[offset].add(personalInfo);

                switch (personalInfo) {
                    case 1: {
                        findViewById(R.id.fish_top).setBackgroundResource(R.drawable.circle);
                        break;
                    }
                    case 2: {
                        findViewById(R.id.fish_eye).setBackgroundResource(R.drawable.circle);
                        break;
                    }
                    default: {
                        findViewById(R.id.fish_body).setBackgroundColor(Color.GRAY);
                        break;
                    }
                }
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfish);
        final View logo = findViewById(R.id.fish_body);
        setTitle("");
        ButterKnife.bind(this);
        for(int i = 0; i < numberOfTab; i++){
            group[i] = new ArrayList<Integer>();
        }
        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {return RecyclerViewFragment.newInstance();}

            @Override
            public int getCount() {
                return numberOfTab;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % numberOfTab) {
                    case 0:
                        return "당신의 성격은?";
                    case 1:
                        return "당신의 취미는?";
                    case 2:
                        return "당신의 이상형은?";
                    case 3:
                        return "기타";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.green,
                            "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.blue,
                            "http://www.hdiphonewallpapers.us/phone-wallpapers/540x960-1/540x960-mobile-wallpapers-hd-2218x5ox3.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.cyan,
                            "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                            R.color.red,
                            "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //물고기 모양(logo)를 클릭했을때, 모든 항목(성격,이상형,기타,,,,)을 선택 했으면 main activity로 넘어간다
                    mViewPager.notifyHeaderChanged();
                    isSelectedAll();
//                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                   // startActivity(new Intent(SelectFishActivity.this, MainActivity.class));
//                                for(int i = 0; i < numberOfTab; i++){
//                                    for(int j = 0; j< group[i].size(); j++){
//                                        Log.d("","page number : "+i+", selected : "+group[i].get(j));
//                                    }
//                                }
                }
            });
        }
    }

    private void isSelectedAll() {
        boolean selectedAll = true;
        for(int i = 0; i < numberOfTab; i++) {
            if(group[i].size() == 0){
                Toast.makeText(getApplicationContext(), "You should select at least one for each section", Toast.LENGTH_SHORT).show();
                selectedAll = false;
                break;
            }
        }
        if(selectedAll){
            startActivity(new Intent(SelectFishActivity.this, MainActivity.class)); }
    }
}
