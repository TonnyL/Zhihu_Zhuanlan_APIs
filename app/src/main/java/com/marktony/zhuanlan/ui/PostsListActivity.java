
package com.marktony.zhuanlan.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.PostsAdapter;
import com.marktony.zhuanlan.bean.PostItem;
import com.marktony.zhuanlan.utils.API;
import com.marktony.zhuanlan.utils.OnRecyclerViewOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvPosts;

    private List<PostItem> list = new ArrayList<PostItem>();
    private LinearLayoutManager manager;
    private PostsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        manager = new LinearLayoutManager(PostsListActivity.this);

        initViews();

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

                        String author = object.getJSONObject("author").getString("name");
                        String title = object.getString("title");
                        String commentCount = object.getString("commentsCount");
                        String imgUrl = object.getString("titleImage");
                        String likeCount = object.getString("likesCount");
                        String slug = object.getString("slug");
                        PostItem item = new PostItem(slug,author,commentCount,imgUrl,title,likeCount);

                        list.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new PostsAdapter(PostsListActivity.this,list);
                rvPosts.setAdapter(adapter);
                adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                    @Override
                    public void OnClick(View v, int position) {
                        Intent intent = new Intent(PostsListActivity.this,ReadActivity.class);
                        intent.putExtra("img_url",list.get(position).getImgUrl());
                        intent.putExtra("title",list.get(position).getTitle());
                        intent.putExtra("slug",list.get(position).getSlug());
                        startActivity(intent);
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);

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

        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);
        rvPosts.setLayoutManager(manager);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}