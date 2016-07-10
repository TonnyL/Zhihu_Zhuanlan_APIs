package com.marktony.zhuanlan.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.db.MyDataBaseHelper;
import com.marktony.zhuanlan.utils.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCategoryActivity extends AppCompatActivity {

    private RequestQueue queue;

    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase db;

    private MaterialDialog progressDialog;

    private static final String TAG = AddCategoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new MaterialDialog.Builder(AddCategoryActivity.this)
                .progress(true,0)
                .build();

        progressDialog.show();

        queue = Volley.newRequestQueue(getApplicationContext());

        dbHelper = new MyDataBaseHelper(AddCategoryActivity.this,"User_defined_IDs.db",null,1);
        db = dbHelper.getWritableDatabase();

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {

            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(AddCategoryActivity.this, R.string.add_zhuanlan_id_error,Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    // Handle text being sent
    private void handleSendText(Intent intent) {

        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {

            // Update UI to reflect text being shared
            // some text http://zhuanlan.zhihu.com/diaosixingge
            Matcher linkMatcher = Pattern.compile("^.*http://zhuanlan.zhihu.com/(.*)$").matcher(sharedText);

            if (linkMatcher.find()){

                final String id = linkMatcher.group(1);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.BASE_URL + id, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            if (jsonObject.getString("slug").equals(id)){

                                Boolean exists = false;

                                Cursor cursor = db.query("Ids",null,null,null,null,null,null);
                                if (cursor.moveToFirst()){
                                    do {
                                        if (id.equals(String.valueOf(cursor.getString(cursor.getColumnIndex("zhuanlanID"))))){
                                            Toast.makeText(AddCategoryActivity.this,R.string.added,Toast.LENGTH_SHORT).show();
                                            exists = true;
                                            break;
                                        }
                                    } while (cursor.moveToNext());
                                }
                                cursor.close();

                                if (!exists){
                                    // 向数据库中插入数据
                                    ContentValues values = new ContentValues();
                                    values.put("zhuanlanID",id);
                                    db.insert("Ids",null,values);

                                    values.clear();

                                    Toast.makeText(AddCategoryActivity.this,R.string.add_zhuanlan_id_success,Toast.LENGTH_SHORT).show();
                                }

                                progressDialog.dismiss();

                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                        Toast.makeText(AddCategoryActivity.this, R.string.add_zhuanlan_id_error,Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                request.setTag(TAG);
                queue.add(request);

            } else {
                progressDialog.dismiss();

                Toast.makeText(AddCategoryActivity.this, R.string.add_zhuanlan_id_error,Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

}
