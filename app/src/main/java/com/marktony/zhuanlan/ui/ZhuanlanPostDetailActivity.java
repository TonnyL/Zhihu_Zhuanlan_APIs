package com.marktony.zhuanlan.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.app.VolleySingleton;
import com.marktony.zhuanlan.bean.ZhuanlanPostDetail;


public class ZhuanlanPostDetailActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private WebView wbMain;
    private ImageView ivHeader;
    private CollapsingToolbarLayout toolbarLayout;

    private MaterialDialog progressDialog;

    private int slug;
    private String title;

    private Gson gson = new Gson();
    private ZhuanlanPostDetail detail;

    private int likesCount;
    private int commentsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        initViews();

        Intent intent = getIntent();

        String imgUrl = intent.getStringExtra("img_url");
        title = intent.getStringExtra("title");
        slug = intent.getIntExtra("slug", 0);

        setCollapsingToolbarLayoutTitle(title);

        if (imgUrl == null || imgUrl.equals("")){
            ivHeader.setImageResource(R.drawable.error_image);
            ivHeader.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            Glide.with(ZhuanlanPostDetailActivity.this).load(imgUrl).centerCrop().into(ivHeader);
        }

        progressDialog = new MaterialDialog.Builder(ZhuanlanPostDetailActivity.this)
                .progress(true,0)
                .content(R.string.loading)
                .build();

        progressDialog.show();

        wbMain.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        wbMain.getSettings().setBuiltInZoomControls(false);
        //缓存
        wbMain.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        wbMain.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        wbMain.getSettings().setAppCacheEnabled(false);
        //不调用第三方浏览器即可进行页面反应
        wbMain.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wbMain.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, "https://zhuanlan.zhihu.com/api/posts/" + slug, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                detail = gson.fromJson(s, ZhuanlanPostDetail.class);

                String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">";
                String html = "<!DOCTYPE html>\n"
                        + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                        + "<head>\n"
                        + "\t<meta charset=\"utf-8\" />\n</head>\n"
                        + css
                        + "\n<body>"
                        + detail.getContent()
                        + "</body>\n</html>";

                wbMain.loadDataWithBaseURL(null,html,"text/html","utf-8",null);

                likesCount = detail.getLikesCount();
                commentsCount = detail.getCommentsCount();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        VolleySingleton.getVolleySingleton(this).addToRequestQueue(request);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
                String shareText = title + " " + "https://zhuanlan.zhihu.com/p/" + slug;
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);
                startActivity(Intent.createChooser(shareIntent,getString(R.string.share_to)));
            }
        });
    }

    private void initViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wbMain = (WebView) findViewById(R.id.wb_main);
        ivHeader = (ImageView) findViewById(R.id.iv_header);
    }

    // to change the title's font size of toolbar layout
    private void setCollapsingToolbarLayoutTitle(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }

        if (id == R.id.action_comments){

            Intent intent = new Intent(ZhuanlanPostDetailActivity.this,CommentActivity.class);
            intent.putExtra("id",slug);
            intent.putExtra("commentsCount",Integer.valueOf(commentsCount));
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String temp = getResources().getString(R.string.likes) + ":" + likesCount;
        menu.findItem(R.id.action_likes).setTitle(temp);
        temp = getResources().getString(R.string.comments) + ":" + commentsCount;
        menu.findItem(R.id.action_comments).setTitle(temp);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}