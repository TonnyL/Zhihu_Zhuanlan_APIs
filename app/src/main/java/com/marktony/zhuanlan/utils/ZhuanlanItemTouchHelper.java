package com.marktony.zhuanlan.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.marktony.zhuanlan.adapter.ZhuanlanAdapter;

/**
 * Created by lizhaotailang on 2016/5/27.
 * 对recycler view的侧滑进行监听
 */
public class ZhuanlanItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private ZhuanlanAdapter adapter;
    private Context context;

    public ZhuanlanItemTouchHelper(Context context , ZhuanlanAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.remove(context,viewHolder.getAdapterPosition());
    }
}
