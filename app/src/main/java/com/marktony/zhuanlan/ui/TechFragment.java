package com.marktony.zhuanlan.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.marktony.zhuanlan.R;
import com.marktony.zhuanlan.adapter.ZhuanlanAdapter;
import com.marktony.zhuanlan.bean.ZhuanlanItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class TechFragment extends Fragment {

    private RecyclerView rvMain;
    private RequestQueue queue;
    private ZhuanlanAdapter adapter;
    private List<ZhuanlanItem> list = new ArrayList<ZhuanlanItem>();
    private LinearLayoutManager layoutManager;

    private String[] ids;
    private String baseUrl = "https://zhuanlan.zhihu.com/api/columns/";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        layoutManager = new LinearLayoutManager(getActivity());

        ids = getActivity().getResources().getStringArray(R.array.tech_ids);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tech,container,false);

        initViews(view);

        for (String id:ids) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, baseUrl + id, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        String followersCount = jsonObject.getString("followersCount");
                        String description = jsonObject.getString("description");
                        String avatar = "https://pic2.zhimg.com/" + jsonObject.getJSONObject("avatar").getString("id") + "_m.jpg";
                        String id = "wooyun";
                        String name = jsonObject.getString("name");
                        String postCount = jsonObject.getString("postsCount");
                        ZhuanlanItem item = new ZhuanlanItem(name,id,avatar,followersCount,postCount,description);

                        list.add(item);

                        adapter = new ZhuanlanAdapter(getActivity(),list);
                        rvMain.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });

            queue.add(request);
        }


        return view;
    }

    private void initViews(View view) {

        rvMain = (RecyclerView) view.findViewById(R.id.rv_main);
        rvMain.setLayoutManager(layoutManager);
    }

}
