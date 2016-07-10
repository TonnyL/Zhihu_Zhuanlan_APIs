package com.marktony.zhuanlan.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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

    private int commentCount;

    private List<CommentItem> list = new ArrayList<CommentItem>();

    private  CommentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initViews();

        id = getIntent().getStringExtra("id");
        commentCount = getIntent().getIntExtra("commentsCount",0);

        loadData(true);

        rvComments.setOnScrollListener(new RecyclerView.OnScrollListener() {

            boolean isSlidingToLast = false;

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

                        if (list.size() < commentCount){
                            loadData(false);
                        } else {
                            Snackbar.make(rvComments, R.string.no_more,Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                isSlidingToLast = dy > 0;
            }
        });


    }

    private void initViews(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvComments = (RecyclerView) findViewById(R.id.rv_comments);
        rvComments.setLayoutManager(new LinearLayoutManager(CommentActivity.this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 通过网络请求加载评论数据
     * @param firstLoad 是否为初始化加载，如果为true，那么给recycler view设置适配器，否则通知数据变化
     */
    private void loadData(Boolean firstLoad){

        if (firstLoad){

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API.POST_URL + id + "/comments" + "?limit=20&offset=" + list.size(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {

                    if (jsonArray.length() != 0){
                        for(int i = 0; i < jsonArray.length(); i++){
                            try {
                                JSONObject o = jsonArray.getJSONObject(i);
                                CommentItem item = new CommentItem("https://pic4.zhimg.com/" + o.getJSONObject("author").getJSONObject("avatar").getString("id") + "_l.jpg",
                                        o.getJSONObject("author").getString("name"),
                                        o.getString("content"),
                                        o.getString("createdTime").substring(0,10),
                                        o.getString("likesCount"));

                                list.add(item);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter = new CommentsAdapter(CommentActivity.this,list);
                        rvComments.setAdapter(adapter);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(request);

        } else {

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, API.POST_URL + id + "/comments" + "?limit=20&offset=" + list.size(), new Response.Listener<JSONArray>() {
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

                        adapter.notifyDataSetChanged();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            Volley.newRequestQueue(getApplicationContext()).add(request);
        }

    }

}
