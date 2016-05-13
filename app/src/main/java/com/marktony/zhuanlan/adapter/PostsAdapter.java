package com.marktony.zhuanlan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.bean.PostItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {

    private final Context context;
    private LayoutInflater inflater;
    private List<PostItem> list = new ArrayList<PostItem>();

    public PostsAdapter(Context context,List<PostItem> list){
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_item,parent,false);

        return new PostsViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        PostItem item = list.get(position);

        Glide.with(context).load(item.getImgUrl()).into(holder.ivMain);
        holder.tvAuthor.setText(item.getAuthor());
        holder.tvCommentCount.setText(item.getCommentCount());
        holder.tvTitle.setText(item.getTitle());
        holder.tvBriefContent.setText(item.getBriefContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvAuthor;
        private TextView tvCommentCount;
        private ImageView ivMain;
        private TextView tvTitle;
        private TextView tvBriefContent;

        public PostsViewHolder(View itemView, final Context context) {
            super(itemView);

            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
            ivMain = (ImageView) itemView.findViewById(R.id.iv_main);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvBriefContent = (TextView) itemView.findViewById(R.id.tv_brief_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"lol",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
