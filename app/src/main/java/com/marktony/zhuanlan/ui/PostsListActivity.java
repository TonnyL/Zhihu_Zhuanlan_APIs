package com.marktony.zhuanlan.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.PostsAdapter;
import com.marktony.zhuanlan.bean.PostItem;

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

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_list);

        manager = new LinearLayoutManager(PostsListActivity.this);

        initViews();

        queue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://zhuanlan.zhihu.com/api/columns/wooyun/posts?limit=10&offset=0", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                for (int i = 0;i < jsonArray.length();i++){
                    try {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String author = object.getJSONObject("author").getString("name");
                        String title = object.getString("title");
                        String commentCount = object.getString("commentsCount");
                        String imgUrl = object.getString("titleImage");
                        String content = object.getString("summary");

                        PostItem item = new PostItem(author,commentCount,imgUrl,title,content);

                        list.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter = new PostsAdapter(PostsListActivity.this,list);
                rvPosts.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);

    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);
        rvPosts.setLayoutManager(manager);
    }

}
