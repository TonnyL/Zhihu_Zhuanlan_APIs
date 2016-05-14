package com.marktony.zhuanlan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.bean.PostItem;
import com.marktony.zhuanlan.utils.OnRecyclerViewOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private final Context context;
    private LayoutInflater inflater;
    private List<PostItem> list = new ArrayList<PostItem>();
    private OnRecyclerViewOnClickListener mListener;

    public PostsAdapter(Context context,List<PostItem> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_item,parent,false);

        return new PostsViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        PostItem item = list.get(position);

        if (item.getImgUrl() != null){
            Glide.with(context).load(item.getImgUrl()).centerCrop().into(holder.ivMain);
            Log.d("image",item.getImgUrl());
        } else {
            holder.ivMain.setImageResource(R.drawable.error_image);
            holder.ivMain.setScaleType(ImageView.ScaleType.CENTER_CROP);

        }
        holder.tvAuthor.setText(item.getAuthor());
        String likes = item.getLikeCount() + "赞";
        holder.tvLikesCount.setText(likes);
        String comment = item.getCommentCount() + "条评论";
        holder.tvCommentCount.setText(comment);
        holder.tvTitle.setText(item.getTitle());

    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvAuthor;
        private TextView tvCommentCount;
        private ImageView ivMain;
        private TextView tvTitle;
        private TextView tvLikesCount;

        private OnRecyclerViewOnClickListener listener;

        public PostsViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);

            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
            ivMain = (ImageView) itemView.findViewById(R.id.iv_main);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvLikesCount = (TextView) itemView.findViewById(R.id.tv_like_count);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.OnClick(v,getLayoutPosition());
            }
        }
    }
}
