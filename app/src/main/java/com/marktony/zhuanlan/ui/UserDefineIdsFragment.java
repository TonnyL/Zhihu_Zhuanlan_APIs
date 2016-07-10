package com.marktony.zhuanlan.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.ZhuanlanAdapter;
import com.marktony.zhuanlan.bean.ZhuanlanItem;
import com.marktony.zhuanlan.db.MyDataBaseHelper;
import com.marktony.zhuanlan.utils.API;
import com.marktony.zhuanlan.utils.OnRecyclerViewOnClickListener;
import com.marktony.zhuanlan.utils.ZhuanlanItemTouchHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/5/26.
 */
public class UserDefineIdsFragment extends Fragment{

    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private ArrayList<String> list = new ArrayList<String>();

    private TextView tvUserDefine;
    private FloatingActionButton fab;
    private SwipeRefreshLayout refreshLayout;

    private ZhuanlanAdapter adapter;
    private List<ZhuanlanItem> zhuanlanItemList = new ArrayList<ZhuanlanItem>();
    private RecyclerView rvMain;
    private LinearLayoutManager layoutManager;

    private RequestQueue queue;

    private ItemTouchHelper.Callback callback;

    private static final String TAG = "TAG";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new MyDataBaseHelper(getActivity(),"User_defined_IDs.db",null,1);
        db = dbHelper.getWritableDatabase();

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        layoutManager = new LinearLayoutManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_define,container,false);

        initViews(view);

        Cursor cursor = db.query("Ids",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(cursor.getColumnIndex("zhuanlanID")));
            } while (cursor.moveToNext());

        }
        cursor.close();

        if (list.size() == 0){
            tvUserDefine.setVisibility(View.VISIBLE);
        } else {

            tvUserDefine.setVisibility(View.GONE);

            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });

            for (int i = 0; i < list.size(); i++){

                final int finalI = i;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.BASE_URL + list.get(i), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            String followersCount = jsonObject.getString("followersCount");
                            String description = jsonObject.getString("description");
                            String avatar = "https://pic2.zhimg.com/" + jsonObject.getJSONObject("avatar").getString("id") + "_l.jpg";
                            String slug = jsonObject.getString("slug");
                            String name = jsonObject.getString("name");
                            String postCount = jsonObject.getString("postsCount");
                            ZhuanlanItem item = new ZhuanlanItem(name,slug,avatar,followersCount,postCount,description);

                            zhuanlanItemList.add(item);

                            if (finalI == (list.size() - 1)) {
                                adapter = new ZhuanlanAdapter(getActivity(), zhuanlanItemList);
                                rvMain.setAdapter(adapter);
                                adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                                    @Override
                                    public void OnClick(View v, int position) {

                                        Intent intent = new Intent(getContext(), PostsListActivity.class);
                                        intent.putExtra("slug", zhuanlanItemList.get(position).getSlug());
                                        intent.putExtra("title", zhuanlanItemList.get(position).getName());
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

                            }

                            // 具体的删除操作在touch helper中完成
                            callback = new ZhuanlanItemTouchHelper(getActivity(),adapter);
                            ItemTouchHelper helper = new ItemTouchHelper(callback);
                            helper.attachToRecyclerView(rvMain);

                        } catch (JSONException e) {
                            e.printStackTrace();
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

                        refreshLayout.setEnabled(false);
                    }

                });

                request.setTag(TAG);
                queue.add(request);
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                        .title(R.string.add_zhuanlan_id)
                        .content(R.string.add_zhuanlan_id_description)
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                            }
                        }).build();

                dialog.setActionButton(DialogAction.NEGATIVE,R.string.cancel);
                dialog.setActionButton(DialogAction.POSITIVE,R.string.ok);
                dialog.setActionButton(DialogAction.NEUTRAL, R.string.zhuanlan_id_help);

                dialog.getActionButton(DialogAction.NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 监听输入面板的情况，如果激活则隐藏
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm.isActive()) {
                            imm.hideSoftInputFromWindow(fab.getWindowToken(), 0);
                        }

                        String url = getString(R.string.add_zhuanlan_id_help);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);

                    }
                });

                dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String input = dialog.getInputEditText().getText().toString();

                        if (!input.isEmpty()){

                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API.BASE_URL + input, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {

                                    try {
                                        String followersCount = jsonObject.getString("followersCount");
                                        String description = jsonObject.getString("description");
                                        String avatar = "https://pic2.zhimg.com/" + jsonObject.getJSONObject("avatar").getString("id") + "_l.jpg";
                                        String slug = jsonObject.getString("slug");
                                        String name = jsonObject.getString("name");
                                        String postCount = jsonObject.getString("postsCount");

                                        ZhuanlanItem item = new ZhuanlanItem(name,slug,avatar,followersCount,postCount,description);

                                        zhuanlanItemList.add(item);

                                        if (zhuanlanItemList.size() == 1){

                                            adapter = new ZhuanlanAdapter(getActivity(),zhuanlanItemList);
                                            rvMain.setAdapter(adapter);
                                            adapter.setItemClickListener(new OnRecyclerViewOnClickListener() {
                                                @Override
                                                public void OnClick(View v, int position) {
                                                    Intent intent = new Intent(getContext(),PostsListActivity.class);
                                                    intent.putExtra("slug",zhuanlanItemList.get(position).getSlug());
                                                    intent.putExtra("title",zhuanlanItemList.get(position).getName());
                                                    startActivity(intent);

                                                }

                                            });

                                            tvUserDefine.setVisibility(View.GONE);
                                        }
                                        adapter.notifyItemInserted(zhuanlanItemList.size() - 1);
                                        // 具体的删除操作在touch helper中完成
                                        callback = new ZhuanlanItemTouchHelper(getActivity(),adapter);
                                        ItemTouchHelper helper = new ItemTouchHelper(callback);
                                        helper.attachToRecyclerView(rvMain);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    // 向数据库中插入数据
                                    ContentValues values = new ContentValues();
                                    values.put("zhuanlanID",input);
                                    db.insert("Ids",null,values);

                                    values.clear();

                                    // 监听输入面板的情况，如果激活则隐藏
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (imm.isActive()) {
                                        imm.hideSoftInputFromWindow(fab.getWindowToken(), 0);
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    Snackbar.make(fab, R.string.add_zhuanlan_id_error,Snackbar.LENGTH_SHORT).show();

                                }
                            });

                            request.setTag(TAG);
                            queue.add(request);

                        }

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    private void initViews(View view) {

        tvUserDefine = (TextView) view.findViewById(R.id.tv_user_define);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        rvMain = (RecyclerView) view.findViewById(R.id.recycler_view);
        rvMain.setLayoutManager(layoutManager);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);

    }

    @Override
    public void onStop() {
        super.onStop();

        if (queue != null){
            queue.cancelAll(TAG);
        }

        if (refreshLayout.isRefreshing()){
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(false);
                }
            });
        }
    }
}