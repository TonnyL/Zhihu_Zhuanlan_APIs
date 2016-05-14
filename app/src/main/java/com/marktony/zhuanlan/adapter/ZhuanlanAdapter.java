package com.marktony.zhuanlan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.bean.ZhuanlanItem;
import com.marktony.zhuanlan.ui.PostsListActivity;
import com.marktony.zhuanlan.utils.OnRecyclerViewOnClickListener;
import com.marktony.zhuanlan.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class ZhuanlanAdapter extends RecyclerView.Adapter<ZhuanlanAdapter.ZhuanlanItemViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<ZhuanlanItem> list = new ArrayList<ZhuanlanItem>();
    private OnRecyclerViewOnClickListener mListener;

    public ZhuanlanAdapter(Context context,List<ZhuanlanItem> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ZhuanlanItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.zhuanlan_item,parent,false);

        return new ZhuanlanItemViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(ZhuanlanItemViewHolder holder, int position) {
        ZhuanlanItem item = list.get(position);
        if (item.getAvatarUrl() != null){
            Glide.with(context).load(item.getAvatarUrl()).into(holder.ciAvatar);
        }
        holder.tvName.setText(item.getName());
        String text = item.getFocusCount() + "人关注TA";
        holder.tvFansCount.setText(text);
        text = item.getArticleCount() + "篇文章";
        holder.tvArticleCount.setText(text);
        holder.tvIntro.setText(item.getIntro());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;
    }

    public class ZhuanlanItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView ciAvatar;
        private TextView tvName;
        private TextView tvIntro;
        private TextView tvArticleCount;
        private TextView tvFansCount;
        private OnRecyclerViewOnClickListener listener;

        public ZhuanlanItemViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);

            ciAvatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvIntro = (TextView) itemView.findViewById(R.id.intro);
            tvFansCount = (TextView) itemView.findViewById(R.id.tv_fans_count);
            tvArticleCount = (TextView) itemView.findViewById(R.id.tv_article_count);

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
