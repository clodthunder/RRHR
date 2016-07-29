package com.lskj.gx.rrhr.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lskj.gx.rrhr.R;
import com.lskj.gx.rrhr.main.bean.SubjectsBean;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Home on 16/7/26.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    private List<SubjectsBean> data;

    public List<SubjectsBean> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    private Context mContext;


    public MovieAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<SubjectsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.rv_movie_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        SubjectsBean current = data.get(position);
        if (current != null) {
            holder.mTitleView.setText(current.getTitle());
            holder.mDName.setText(current.getDirectors().get(0).getName());
            holder.mAverage.setText(current.getRating().getAverage() + "");
            Glide.with(mContext)
                    .load(current.getImages().getSmall())
                    .placeholder(R.drawable.ic_refresh_blue_12dp)
                    .error(R.drawable.ic_warning_red_12dp)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
//                    .override(60 * 3, 120 * 3)
                    .into(holder.mIcon);
            holder.mTagGroup.setTags(current.getGenres());
            holder.mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                @Override
                public void onTagClick(String tag) {
                    Toast.makeText(mContext, "click-->" + tag, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    /**
     * 添加data
     *
     * @param beanList
     */
    public void addData(List<SubjectsBean> beanList) {
        if (beanList == null) {
            return;
        }
        getData().addAll(beanList);
        this.notifyItemRangeInserted(data == null ? 0 : data.size(), beanList.size());
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        TextView mTitleView;
        TextView mDName;//dao导演的名称
        ImageView mIcon;
        TagGroup mTagGroup;
        TextView mAverage;

        public MovieHolder(View itemView) {
            super(itemView);
            mTitleView = (TextView) itemView.findViewById(R.id.tv_item_movie_title);
            mIcon = (ImageView) itemView.findViewById(R.id.iv_item_movie_icon);
            mTagGroup = (TagGroup) itemView.findViewById(R.id.tg_item_movie);
            mDName = (TextView) itemView.findViewById(R.id.tv_item_movie_dname);
            mAverage = (TextView) itemView.findViewById(R.id.tv_item_movie_average);
        }
    }
}
