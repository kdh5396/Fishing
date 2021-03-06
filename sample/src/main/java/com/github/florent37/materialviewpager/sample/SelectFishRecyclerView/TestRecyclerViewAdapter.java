package com.github.florent37.materialviewpager.sample.SelectFishRecyclerView;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialviewpager.sample.R;

import java.util.List;

import butterknife.BindDrawable;

/**
 * Created by florentchampigny on 24/04/15.
 */

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    public TextView mTextView;
    private int image1 = R.drawable.circle;
    private int image2 =  R.drawable.logo_white;
    //image1-9 로 각 grid layout의 cell 한칸마다 다른 그림 넣음

    public TestRecyclerViewAdapter(List<Object> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
//            case 0:
//                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
        //첫번째 네모를 헤더타입이라고 함
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //만들 item은 기본적으로 이 함수를 상속받는다
        // create a new view
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /*  - get element from your dataset at this position
         *  - replace the contents of the view with that element
         *  onCreateViewHolder을 통해서 생성되면, onBindViewHolder에서 해당 holder의 View에 데이터를 노출을 정의하면 됩니다.
         *  RecyclerView는 ViewHolder을 재사용할 수 있도록 설계되어 있으므로, ViewType이 한번 생성된 이후로는 onBindViewHolder만 호출되게 됩니다.
        */

        switch (position) {
            case 1: {
                holder.itemView.findViewById(R.id.card_view_imageView).setBackgroundResource(image1);
                break;
            }
            case 2: {
                holder.itemView.findViewById(R.id.card_view_imageView).setBackgroundResource(image2);
                break;
            }

        }
    }
}