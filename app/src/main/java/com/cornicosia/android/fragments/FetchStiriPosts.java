package com.cornicosia.android.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cornicosia.android.PostDetail;
import com.cornicosia.android.R;
import com.cornicosia.android.utils.PostContentAdapter;
import com.cornicosia.android.utils.PostContentModel;
import com.cornicosia.android.utils.RecyclerItemClickListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.LinkedList;
import java.util.List;


public class FetchStiriPosts extends Fragment {

    private PostContentAdapter postContentAdapter;
    private List<PostContentModel> postContentList;
    private JsonArray jsonArray;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView postContent;

    private View view;
    public FetchStiriPosts() {
        // Required empty public constructor
    }

    private void fetchPostContent() {
        Log.d("fetchpost", "yes fetching");
        if (haveNetworkConnection()) {
            postContentList = new LinkedList<>();
            Log.d("debugging_error", "yes have internet connection");
            Ion.with(getActivity()).load("https://cor-nicosia.com/fetch-stiri-posts/")
                    .asJsonArray().setCallback(new FutureCallback<JsonArray>() {
                @Override
                public void onCompleted(Exception e, JsonArray result) {
                    Log.d("responseis", result.toString());
                    swipeRefreshLayout.setRefreshing(false);
                    if (result != null) {
                        Log.d("debugging_error", "received result from server");
                        Log.d("resultis", "result is " + result.toString());
                        jsonArray = result.getAsJsonArray();

                        //here we will show first child in large size
                        final JsonObject firstJSONObject = jsonArray.get(0).getAsJsonObject();
                        TextView firstChildTitle=view.findViewById(R.id.first_child_title);
                        TextView firstChildDescription=view.findViewById(R.id.first_child_description);
                        TextView firstChildDate=view.findViewById(R.id.first_child_date);
                        ImageView firstChildImage=view.findViewById(R.id.first_child_image);

                        firstChildTitle.setText(firstJSONObject.get("post_title").getAsString());
                        firstChildDescription.setText(firstJSONObject.get("post_description").getAsString());
                        firstChildDate.setText(firstJSONObject.get("post_publish_date").getAsString()+"  "+" 10:10PM");

                        //load first child image into imageview using Glide library
                        Glide.with(getActivity())
                                .load(firstJSONObject.get("post_image_large").getAsString())
                                .into(firstChildImage);

                        RelativeLayout firstChildData=view.findViewById(R.id.firstChildData);
                        firstChildData.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent mIntent = new Intent(getActivity(), PostDetail.class);
                                mIntent.putExtra("post_title", firstJSONObject.get("post_title").getAsString());
                                mIntent.putExtra("post_description", firstJSONObject.get("post_description").getAsString());
                                mIntent.putExtra("post_featured_image", firstJSONObject.get("post_image_large").getAsString());
                                mIntent.putExtra("post_publish_date", firstJSONObject.get("post_publish_date").getAsString());
                                mIntent.putExtra("post_publish_time", "10:00 PM");
                                mIntent.putExtra("post_author", firstJSONObject.get("post_author").getAsString());
                                mIntent.putExtra("post_permalink", firstJSONObject.get("post_permalink").getAsString());
                                getActivity().overridePendingTransition(R.anim.signin_incoming_screen_right_to_mean_position, R.anim.signin_current_screen_move_mean_to_left);
                                startActivity(mIntent);
                            }
                        });
                        //remaining json objects's data will show into recyclerview
                        for (int i = 1; i < jsonArray.size(); i++) {
                            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                            String postTitle = jsonObject.get("post_title").getAsString();
                            String postDescription = jsonObject.get("post_description").getAsString();
                            String postPublishDate = jsonObject.get("post_publish_date").getAsString();
                            String postPublishTime = "10:10 PM";
                            String postImageLarge = jsonObject.get("post_image_large").getAsString();
                            String postImageSmall = jsonObject.get("post_image_small").getAsString();
                            String postAuthor = jsonObject.get("post_author").getAsString();
                            String postPermalink = "Link";
                            postContentList.add(new PostContentModel(postTitle, postDescription, postPublishDate,postPublishTime, postAuthor, postImageLarge, postImageSmall,postPermalink));
                            // Log.d("objsize", "size is " + distance);
                            setAdapter();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            connectionLossDialog();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fetch_stiri_posts, container, false);
        postContent = view.findViewById(R.id.post_content_list);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchPostContent();
                    }
                }
        );
        fetchPostContent();
        return view;
    }

    private void setAdapter() {
        Log.d("debugging_error", "Setting adapter");
        postContentAdapter = new PostContentAdapter(postContentList, getActivity());
        postContent.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        postContent.setItemAnimator(new DefaultItemAnimator());
        postContent.setAdapter(postContentAdapter);

        //click listner
        postContent.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), postContent, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "Position " + position, Toast.LENGTH_SHORT).show();
//                        Log.d("urlsis", postContentList.get(position).getUrl());
                        Intent mIntent = new Intent(getActivity(), PostDetail.class);
                        mIntent.putExtra("post_title", postContentList.get(position).getPostTitle());
                        mIntent.putExtra("post_description", postContentList.get(position).getPostDescription());
                        mIntent.putExtra("post_featured_image", postContentList.get(position).getPostImageLarge());
                        mIntent.putExtra("post_publish_date", postContentList.get(position).getPostPublishDate());
                        mIntent.putExtra("post_publish_time", postContentList.get(position).getPostPublishTime());
                        mIntent.putExtra("post_author", postContentList.get(position).getPostAuthor());
                        mIntent.putExtra("post_permalink", "Link");
                        getActivity().overridePendingTransition(R.anim.signin_incoming_screen_right_to_mean_position, R.anim.signin_current_screen_move_mean_to_left);
                        startActivity(mIntent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void connectionLossDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_connection, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog findMeDialog = dialogBuilder.create();
        findMeDialog.show();
        LinearLayout reset_btn = dialogView.findViewById(R.id.ok);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMeDialog.dismiss();
                getActivity().finish();
            }
        });
    }
}