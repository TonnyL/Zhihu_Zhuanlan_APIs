package com.marktony.zhuanlan.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
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
    private RecyclerView rvPosts;
    private SwipeRefreshLayout refreshLayout;

    private List<ZhuanlanListItem> list = new ArrayList<ZhuanlanListItem>();
    private LinearLayoutManager manager;
    private PostsAdapter adapter;

    private Gson gson = new Gson();

    private static final String TAG = PostsListActivity.class.getSimpleName();

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
        String slug = intent.getStringExtra("slug");
        String title = intent.getStringExtra("title");

        getSupportActionBar().setTitle(title);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API.BASE_URL + slug + "/posts?limit=20&offset=0", new Response.Listener<JSONArray>() {
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
                    rvPosts.setAdapter(adapter);

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

                    refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(false);
                        }
                    });

                    refreshLayout.setEnabled(false);

                } else {
                    adapter.notifyDataSetChanged();
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

        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);
        manager = new LinearLayoutManager(PostsListActivity.this);
        rvPosts.setLayoutManager(manager);

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
    }

}