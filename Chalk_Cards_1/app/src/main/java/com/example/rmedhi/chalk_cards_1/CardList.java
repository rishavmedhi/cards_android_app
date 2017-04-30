package com.example.rmedhi.chalk_cards_1;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by R Medhi on 30-04-2017.
 */

// class to store images
public class CardList implements java.io.Serializable{
    Bitmap image;

    // getter for getting image element
    public Bitmap getImage(){
        return image;
    }

    // setter for setting image element
    public void setImage(Bitmap image){
        this.image = image;
    }

}
