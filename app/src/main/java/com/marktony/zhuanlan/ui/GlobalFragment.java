package com.marktony.zhuanlan.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.ZhuanlanAdapter;
import com.marktony.zhuanlan.app.VolleySingleton;
import com.marktony.zhuanlan.bean.Zhuanlan;
import com.marktony.zhuanlan.interfaze.OnRecyclerViewOnClickListener;
import com.marktony.zhuanlan.utils.API;

import java.util.ArrayList;

/**
 * Created by Lizhaotailang on 2016/10/3.
 */

public class GlobalFragment extends Fragment {

    private int type;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RequestQueue queue;
    private String[] ids;
    private ZhuanlanAdapter adapter;
    private ArrayList<Zhuanlan> list = new ArrayList<>();

    private Gson gson = new Gson();

    public static final int TYPE_PRODUCT = 0;
    public static final int TYPE_MUSIC = 1;
    public static final int TYPE_LIFE = 2;
    public static final int TYPE_EMOTION = 3;
    public static final int TYPE_FINANCE = 4;
    public static final int TYPE_ZHIHU = 5;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public GlobalFragment() {

    }

    public static GlobalFragment newInstance() {
        return new GlobalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_universal,container,false);

        initViews(view);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });

        switch (type){
            default:
            case TYPE_PRODUCT:
                ids = getActivity().getResources().getStringArray(R.array.product);
                break;
            case TYPE_MUSIC:
                ids = getActivity().getResources().getStringArray(R.array.music);
                break;
            case TYPE_LIFE:
                ids = getActivity().getResources().getStringArray(R.array.life);
                break;
            case TYPE_EMOTION:
                ids = getActivity().getResources().getStringArray(R.array.emotion);
                break;
            case TYPE_FINANCE:
                ids = getActivity().getResources().getStringArray(R.array.profession);
                break;
            case TYPE_ZHIHU:
                ids = getActivity().getResources().getStringArray(R.array.zhihu);
                break;

        }

        for (int i = 0;i < ids.length; i++) {

            final int finalI = i;

            StringRequest request = new StringRequest(Request.Method.GET, API.BASE_URL + ids[i], new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Zhuanlan z = gson.fromJson(s, Zhuanlan.class);
                    list.add(z);

                    if (adapter == null) {
                        adapter = new ZhuanlanAdapter(getActivity(),list);
                        recyclerView.setAdapter(adapter);
                        adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                            @Override
                            public void OnClick(View v, int position) {
                                Intent intent = new Intent(getContext(),PostsListActivity.class);
                                intent.putExtra("slug",list.get(position).getSlug());
                                intent.putExtra("title",list.get(position).getName());
                                startActivity(intent);
                            }
                        });
                    } else {
                        adapter.notifyItemInserted(list.size() - 1);
                    }

                    if (finalI == (ids.length - 1)){

                        refreshLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        });

                        refreshLayout.setEnabled(false);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            VolleySingleton.getVolleySingleton(getActivity()).addToRequestQueue(request);

        }

        return view;
    }

    private void initViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);

    }

}
