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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.ZhuanlanAdapter;
import com.marktony.zhuanlan.bean.ZhuanlanItem;
import com.marktony.zhuanlan.utils.API;
import com.marktony.zhuanlan.utils.OnRecyclerViewOnClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class InternetFragment extends Fragment {

    private RecyclerView rvMain;
    private SwipeRefreshLayout refreshLayout;
    private RequestQueue queue;
    private ZhuanlanAdapter adapter;
    private List<ZhuanlanItem> list = new ArrayList<ZhuanlanItem>();
    private LinearLayoutManager layoutManager;

    private String[] ids;

    private static final String TAG = "TAG";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        layoutManager = new LinearLayoutManager(getActivity());

        ids = getActivity().getResources().getStringArray(R.array.tech_ids);
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

        for (int i = 0;i < ids.length; i++) {

            final int finalI = i;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.BASE_URL + ids[i], new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String followersCount = jsonObject.getString("followersCount");
                        String description = jsonObject.getString("description");
                        String avatar = "https://pic2.zhimg.com/" + jsonObject.getJSONObject("avatar").getString("id") + "_l.jpg";
                        String slug = jsonObject.getString("slug");
                        String name = jsonObject.getString("name");
                        String postCount = jsonObject.getString("postsCount");
                        ZhuanlanItem item = new ZhuanlanItem(name,slug,avatar,followersCount,postCount,description);

                        list.add(item);

                        if (finalI == (ids.length - 1)){
                            adapter = new ZhuanlanAdapter(getActivity(),list);
                            rvMain.setAdapter(adapter);
                            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                                @Override
                                public void OnClick(View v, int position) {
                                    Intent intent = new Intent(getContext(),PostsListActivity.class);
                                    intent.putExtra("slug",list.get(position).getSlug());
                                    intent.putExtra("title",list.get(position).getName());
                                    startActivity(intent);
                                }
                            });

                            refreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    refreshLayout.setRefreshing(false);
                                }
                            });

                            refreshLayout.setEnabled(false);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    });

                    refreshLayout.setEnabled(false);
                }
            });

            request.setTag(TAG);
            queue.add(request);

        }

        return view;
    }

    private void initViews(View view) {

        rvMain = (RecyclerView) view.findViewById(R.id.rv_main);
        rvMain.setLayoutManager(layoutManager);
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

    @Override
    public void onStop() {
        super.onStop();

        if (queue != null){
            queue.cancelAll(TAG);
        }
    }
}
