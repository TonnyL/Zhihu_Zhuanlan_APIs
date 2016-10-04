package com.marktony.zhuanlan.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.bean.Zhuanlan;
import com.marktony.zhuanlan.db.MyDataBaseHelper;
import com.marktony.zhuanlan.interfaze.OnRecyclerViewOnClickListener;
import com.marktony.zhuanlan.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class ZhuanlanAdapter extends RecyclerView.Adapter<ZhuanlanAdapter.ZhuanlanItemViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private List<Zhuanlan> list = new ArrayList<>();
    private OnRecyclerViewOnClickListener mListener;

    public ZhuanlanAdapter(Context context,List<Zhuanlan> list){
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
        Zhuanlan item = list.get(position);
        if (item.getAvatar() != null){
            String url = item.getAvatar().getTemplate()
                    .replace("{id}", item.getAvatar().getId())
                    .replace("{size}", "m");
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(holder.ciAvatar);
        }
        holder.tvName.setText(item.getName());
        String text = item.getFollowersCount() + "人关注TA";
        holder.tvFansCount.setText(text);
        text = item.getPostsCount() + "篇文章";
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

    /**
     * 配合ZhuanlanItenTouchHelper使用，暴露方法
     * 对于数据库中数据的操作在此完成,helper中主要完成view的删除
     * @param position position
     */
    public void remove(int position) {

        Zhuanlan item = list.get(position);
        SQLiteDatabase db = new MyDataBaseHelper(context,"User_defined_IDs.db",null,1).getWritableDatabase();
        db.delete("Ids","zhuanlanID=?", new String[] {item.getSlug()});
        list.remove(position);
        notifyItemRemoved(position);

    }

}
