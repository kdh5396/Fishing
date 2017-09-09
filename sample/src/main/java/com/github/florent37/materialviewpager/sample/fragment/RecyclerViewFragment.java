package com.github.florent37.materialviewpager.sample.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.SelectFishRecyclerView.TestRecyclerViewAdapter;
import com.github.florent37.materialviewpager.sample.SelectFishRecyclerView.TestRecyclerViewAdapter2;
import com.github.florent37.materialviewpager.sample.SelectFishRecyclerView.TestRecyclerViewAdapter3;
import com.github.florent37.materialviewpager.sample.SelectFishRecyclerView.TestRecyclerViewAdapter4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {
    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;
    private static final boolean GRID_LAYOUT = true;
    //Grid로 바꿈
    private static final int ITEM_COUNT = 12;
    private int fragOffset;
    public static int count = 0;

    //RecyclerView의 아이템 갯수

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    public interface OnLogoSelectedLisnter {
        public void onLogoSelected(int personalInfo,int offset);
    }

    OnLogoSelectedLisnter mListener;

    @Override
    public void onAttach(Context context) {
        fragOffset = count++;
    //    count ++;
        //프래그먼트를 액티비티에 추가할 때 시스템이 호출하는 메서드
        //가 OnLogoSelectedLisnter의 인스턴스를 생성해야 합니다. 이때 onAttach()로 전달되는 Activity를(Context로바뀜) 형변환하는 방법을 씁니다.
        super.onAttach(context);
        try {
            mListener = (OnLogoSelectedLisnter) context;
            //성공 시, mListener 멤버가 액티비티의 OnLogoSelectedLisnter 구현에 대한 참조를 보유하므로, 프래그먼트 A가 액티비티와 이벤트를 공유할 수 있습니다
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLogoSelectedLisnter");
            //액티비티가 인터페이스를 구현하지 않은 경우, 프래그먼트가 ClassCastException을 발생
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final List<Object> items = new ArrayList<>();
        final List<Object> overlapChild = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new Object());
        }


        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        switch(fragOffset){
            case 0:{
                mRecyclerView.setAdapter(new TestRecyclerViewAdapter(items));
                break;
            }
            case 1:{
                mRecyclerView.setAdapter(new TestRecyclerViewAdapter2(items));
                break;
            }
            case 2:{
                mRecyclerView.setAdapter(new TestRecyclerViewAdapter3(items));
                break;
            }
            case 3:{
                mRecyclerView.setAdapter(new TestRecyclerViewAdapter4(items));
                break;
            }
        }

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //item 터치시 listener

                switch (e.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if(clickDuration < MAX_CLICK_DURATION) {
                            View child = rv.findChildViewUnder(e.getX(), e.getY());
                            boolean hasChild = overlapChild.contains(child);
                            if(hasChild){
                                child.findViewById(R.id.card_view).setBackgroundColor(Color.WHITE);
                                overlapChild.remove(child);
                                mListener.onLogoSelected(rv.getChildAdapterPosition(child),fragOffset);
                            }else {
                                overlapChild.add(child);
                                if (child != null) {
                                    child.findViewById(R.id.card_view).setBackgroundColor(Color.BLACK);
                                    mListener.onLogoSelected(rv.getChildAdapterPosition(child),fragOffset);
                                }
                                //click event has occurred
                            }
                        }
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
        });
    }
}
