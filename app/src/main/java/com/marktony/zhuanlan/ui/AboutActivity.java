package com.marktony.zhuanlan.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
                try {
                    Uri uri = Uri.parse("market://details?id="+getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex){
                    Snackbar.make(layoutRate, R.string.no_market_app, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        layoutBugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Uri uri = Uri.parse(getString(R.string.mail_account));
                    Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                    intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.mail_subject));
                    intent.putExtra(Intent.EXTRA_TEXT,
                            getString(R.string.device_model) + Build.MODEL
                            + "\n" + getString(R.string.sdk_version)
                            + Build.VERSION.RELEASE + "\n");
                    startActivity(intent);
                }catch (android.content.ActivityNotFoundException ex){
                    Snackbar.make(layoutBugs, R.string.no_mail_app,Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        layoutSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog dialog = new MaterialDialog.Builder(AboutActivity.this)
                        .title(R.string.donate)
                        .content(R.string.donate_content)
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.ok)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //将指定账号添加到剪切板
                                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("text", getString(R.string.donate_account));
                                manager.setPrimaryClip(clipData);

                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        }).build();
                dialog.show();
            }
        });

        tvGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.github_url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        tvZhihu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getString(R.string.zhihu_url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
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
