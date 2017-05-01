package com.example.rmedhi.chalk_cards_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_IMAGE_REQUEST = 1000;
    private Context mContext;

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;

    private int g_pos;
    private int preview=0;

    Button mPreviewBtn;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<CardList> cardsList = new ArrayList<>();

    private com.example.rmedhi.chalk_cards_1.CardList template=new com.example.rmedhi.chalk_cards_1.CardList();

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
                    break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
//        mButtonAdd = (Button) findViewById(R.menu.);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mPreviewBtn = (Button) findViewById(R.id.preview);

        // Define a layout for RecyclerView
        mLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new ViewAdapter(mContext,cardsList);
        mAdapter.setHasStableIds(false);


        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // click listener for preview button
        mPreviewBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // setting preview flag as 1 if preview not active
                if (preview==0) {
                    // if list of images is 0
                    if (cardsList.size()==0)
                        Toast.makeText(mContext,"Add a Card to Preview", Toast.LENGTH_SHORT).show();
                    else {
                        // checking if there is an image missing in a card
                        int check_list=checklist(cardsList);
                        if (check_list==0)
                            Toast.makeText(mContext,"Add a Images to all cards to begin preview", Toast.LENGTH_SHORT).show();
                        else {
                            mRecyclerView.scrollToPosition(0);
                            preview = 1;
                            mRelativeLayout.setBackgroundColor(Color.BLACK);
                            Toast.makeText(mContext,"Preview Mode", Toast.LENGTH_SHORT).show();
                            mPreviewBtn.setText("Back");
                        }
                    }
                }
                else
                {
                    mRelativeLayout.setBackgroundColor(Color.WHITE);
                    preview = 0;
                    Toast.makeText(mContext,"Editing Mode", Toast.LENGTH_SHORT).show();
                    mPreviewBtn.setText("Preview");
                }
            }
        });
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
            public TextView mTextView;
            public Button mCameraButton;
            public Button mRemoveButton;
            public Button mGallerybtn;
            public ImageView mImageView;
            public RelativeLayout mRelativeLayout;
            public ViewHolder(View v){
                super(v);
                mTextView = (TextView) v.findViewById(R.id.tv);
                mRemoveButton = (Button) v.findViewById(R.id.ib_remove);
                mCameraButton = (Button) v.findViewById(R.id.camera_btn);
                mGallerybtn = (Button) v.findViewById(R.id.gallery_btn);
                mRelativeLayout = (RelativeLayout) v.findViewById(R.id.rl);
                mImageView = (ImageView) v.findViewById(R.id.imageView);
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
                holder.mCameraButton.setVisibility(View.INVISIBLE);
                holder.mGallerybtn.setVisibility(View.INVISIBLE);
                Log.d("AdapterPosition", holder.getAdapterPosition() + "");
                Log.d("Photo", mDataSet.get(holder.getAdapterPosition()).getImage() + "");
                holder.mImageView.setImageBitmap(mDataSet.get(holder.getAdapterPosition()).getImage());
            } else {
                holder.mImageView.setVisibility(View.INVISIBLE);
                holder.mCameraButton.setVisibility(View.VISIBLE);
                holder.mGallerybtn.setVisibility(View.VISIBLE);
            }

            // setting click listener for camera launch and fetch clicked image
            holder.mCameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    g_pos=holder.getAdapterPosition();
                    Log.d("g_pos",g_pos+"");
                    Log.d("position",position+"");
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            });

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

            // Set a click listener for item remove button
            holder.mRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // removing only when preview mode is not active
                    if (preview==0)
                    {
                        // Remove the item on remove/button click
                        mDataSet.remove(position);

                        notifyItemRemoved(position);

                        notifyItemRangeChanged(position, mDataSet.size());
                        Log.d("Size:", mDataSet.size() + "");
                        // checking if the list of images is empty
                        if (mDataSet.size()==0) {
                            mRecyclerView.setVisibility(View.INVISIBLE);
                        }
                    }
                    else
                    {
                        Toast.makeText(mContext,"Preview Mode : Tap Back to Remove Cards", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount(){
            return mDataSet.size();
        }

    }

    // for  setting image after activity end
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // camera action
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d("cam_Photo",photo+"---"+g_pos);
            // imageView.setImageBitmap(photo);
            CardList iv = cardsList.get(g_pos);
            iv.setImage(photo);
            mAdapter.notifyItemChanged(g_pos);
            Log.d("Size",cardsList.size()+"");
        }
        // gallery action
        else if (requestCode == PICK_IMAGE_REQUEST && data!= null && data.getData()!= null)
        {
            Uri filepath = data.getData();
            // fetching image from gallery
            try {
                Bitmap temp_bmp = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), filepath);
                cardsList.get(g_pos).setImage(temp_bmp);
                mAdapter.notifyItemChanged(g_pos);
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

