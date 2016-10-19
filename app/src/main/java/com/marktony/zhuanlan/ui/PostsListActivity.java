package com.marktony.zhuanlan.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.PostsAdapter;
import com.marktony.zhuanlan.app.VolleySingleton;
import com.marktony.zhuanlan.bean.ZhuanlanListItem;
import com.marktony.zhuanlan.utils.API;
import com.marktony.zhuanlan.interfaze.OnRecyclerViewOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private List<ZhuanlanListItem> list = new ArrayList<ZhuanlanListItem>();
    private PostsAdapter adapter;

    private Gson gson = new Gson();

    private String slug;
    private int postCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        initViews();

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });

        Intent intent = getIntent();
        slug = intent.getStringExtra("slug");
        postCount = intent.getIntExtra("post_count", 0);
        String title = intent.getStringExtra("title");

        getSupportActionBar().setTitle(title);

        loadData();

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();

                //当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的itemposition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();

                    //判断是否滚动到底部并且是向下滑动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {

                        if (list.size() < postCount){
                            loadData();
                        } else {
                            Snackbar.make(toolbar, R.string.no_more,Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (list.size() != 0) {
                    list.clear();
                }
                loadData();
            }
        });

    }

    private void loadData() {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API.BASE_URL + slug + "/posts?limit=20&offset=" + list.size(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0;i < jsonArray.length();i++){

                    try {
                        JSONObject object = jsonArray.getJSONObject(i);

                        ZhuanlanListItem item = gson.fromJson(object.toString(), ZhuanlanListItem.class);

                        list.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (adapter == null) {

                    adapter = new PostsAdapter(PostsListActivity.this,list);
                    recyclerView.setAdapter(adapter);

                    adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                        @Override
                        public void OnClick(View v, int position) {

                            Intent intent = new Intent(PostsListActivity.this,ZhuanlanPostDetailActivity.class);
                            intent.putExtra("img_url", list.get(position).getTitleImage());
                            intent.putExtra("title",list.get(position).getTitle());
                            intent.putExtra("slug",list.get(position).getSlug());

                            startActivity(intent);

                        }
                    });

                } else {
                    adapter.notifyItemInserted(list.size());
                }

                refreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                });

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
            }
        });

        VolleySingleton.getVolleySingleton(this).addToRequestQueue(request);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);

        recyclerView = (RecyclerView) findViewById(R.id.rv_posts);
        LinearLayoutManager manager = new LinearLayoutManager(PostsListActivity.this);
        recyclerView.setLayoutManager(manager);

        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        refreshLayout.setDistanceToTriggerSync(300);
        //设置下拉刷新按钮的背景颜色
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        refreshLayout.setRefreshing(false);
    }

}