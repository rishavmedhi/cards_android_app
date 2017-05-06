package com.example.rmedhi.chalk_cards_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1000;
    private Context mContext;

    private RecyclerView mRecyclerView;

    private int g_pos;

    TextView welcometext;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<CardList> cardsList = new ArrayList<>();

    @BindView(R.id.rl)
    RelativeLayout mRelativeLayout;

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mymenu = getMenuInflater();
        mymenu.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                    int position = 0;
                    // setting blank template
                    cardsList.add(position, new CardList());
                    Log.d("Add-size",cardsList.size()+"");
                    mAdapter.notifyItemInserted(0);
                    mRecyclerView.scrollToPosition(position);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    welcometext.setVisibility(View.INVISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // Get the application context
        mContext = getApplicationContext();

        // Get the widgets reference from XML layout

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        welcometext = (TextView) findViewById(R.id.welcome_text);

        // Define a layout for RecyclerView
        mLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new ViewAdapter(mContext,cardsList);
        mAdapter.setHasStableIds(false);


        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);


    }

    public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {
        public List<CardList> mDataSet;
        private Context mContext;

        public ViewAdapter(Context context,List<CardList> list){
            mDataSet = list;
            mContext = context;
        }

        /* view holder of the card */
        public class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.tv)
                TextView mTextView;
            @BindView(R.id.gallery_btn)
                Button mGallerybtn;
            @BindView(R.id.imageView)
                ImageView mImageView;
            @BindView(R.id.rl)
                RelativeLayout mRelativeLayout;
            public ViewHolder(View v){
                super(v);
                ButterKnife.bind(this,v);
            }
        }

        @Override
        public ViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            // Create a new View
            View v = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
            ViewAdapter.ViewHolder vh = new ViewAdapter.ViewHolder(v);
            vh.setIsRecyclable(false);
            return vh;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final ViewAdapter.ViewHolder holder, final int position) {

            // checking if set image is not null
            if (mDataSet.get(holder.getAdapterPosition()).getImage() != null) {
                holder.mImageView.setVisibility(View.VISIBLE);
                holder.mGallerybtn.setVisibility(View.INVISIBLE);
                Log.d("AdapterPosition", holder.getAdapterPosition() + "");
                Log.d("Photo", mDataSet.get(holder.getAdapterPosition()).getImage() + "");
                holder.mImageView.setImageBitmap(mDataSet.get(holder.getAdapterPosition()).getImage());
            } else {
                holder.mImageView.setVisibility(View.INVISIBLE);
                holder.mGallerybtn.setVisibility(View.VISIBLE);
            }

            // click action for gallery button
            holder.mGallerybtn.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View v) {
                      g_pos=holder.getAdapterPosition();
                      Log.d("g_pos",g_pos+"");
                      Intent galleryintent = new Intent();
                      galleryintent.setType("image/*");
                      galleryintent.setAction(Intent.ACTION_GET_CONTENT);
                      startActivityForResult(Intent.createChooser(galleryintent, "Select Picture"), PICK_IMAGE_REQUEST);
                  }
            });
        }

        public int getItemCount(){
            return mDataSet.size();
        }

    }

    // for  setting image after activity end
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && data!= null && data.getData()!= null)
        {
            Uri filepath = data.getData();
            // fetching image from gallery
            try {
                Bitmap temp_bmp = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filepath);
                // scaling the image down to optimise app
                int img_width = temp_bmp.getWidth();
                int newHeight=(int) (temp_bmp.getHeight() * (512.0 / temp_bmp.getWidth()));
                Bitmap resized_temp_bmp= Bitmap.createScaledBitmap(temp_bmp,img_width,newHeight,true);
                // setting image to image_view
                cardsList.get(g_pos).setImage(resized_temp_bmp);
                mAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // to check whether an element is null in the image list
    public int checklist(ArrayList<CardList> a)
    {
        int flag=1;
        for (int i=0;i<a.size();i++)
        {
            if(a.get(i).getImage()==null){
                flag=0;
                break;
            }

        }
        return flag;
    }
}

