package com.marktony.zhuanlan.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.CommentsAdapter;
import com.marktony.zhuanlan.bean.CommentItem;
import com.marktony.zhuanlan.utils.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvComments;

    private String id;

    private List<CommentItem> list = new ArrayList<CommentItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initViews();

        id = getIntent().getStringExtra("id");

        Log.d("id",id);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API.POST_URL + id + "/comments", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                if (jsonArray.length() != 0){
                    for(int i = 0; i < jsonArray.length(); i++){
                        try {
                            JSONObject o = jsonArray.getJSONObject(i);
                            String temp = "https://pic4.zhimg.com/" + o.getJSONObject("author").getJSONObject("avatar").getString("id") + "_l.jpg";
                            CommentItem item = new CommentItem(temp,
                                    o.getJSONObject("author").getString("name"),
                                    o.getString("content"),
                                    o.getString("createdTime"),
                                    o.getString("likesCount"));

                            list.add(item);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    CommentsAdapter adapter = new CommentsAdapter(CommentActivity.this,list);
                    rvComments.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(request);

    }

    private void initViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvComments = (RecyclerView) findViewById(R.id.rv_comments);
        rvComments.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
    }

}
