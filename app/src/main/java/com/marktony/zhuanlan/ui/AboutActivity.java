package com.marktony.zhuanlan.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marktony.zhuanlan.R;

public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout layoutRate;
    private TextView tvGitHub;
    private TextView tvZhihu;
    private LinearLayout layoutBugs;
    private LinearLayout layoutSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initViews();

        layoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layoutBugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layoutSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvZhihu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutRate = (LinearLayout) findViewById(R.id.LL_rate);
        layoutBugs = (LinearLayout) findViewById(R.id.LL_bugs);
        layoutSupport = (LinearLayout) findViewById(R.id.LL_support);
        tvGitHub = (TextView) findViewById(R.id.tv_github);
        tvZhihu = (TextView) findViewById(R.id.tv_zhihu);
    }
}
