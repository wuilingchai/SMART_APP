package com.bignerdranch.android.smart;

import java.util.TimeZone;
import java.util.Date;
import java.util.UUID;

public class Smart {

    private UUID mId;
    private String mTitle;
    private String mSpecific;
    private String mMeasurable;
    private String mAttainable;
    private String mRelevant;
    private Date mDate;
    private Date mTime;
    private boolean mCompleted;
    private String mGallery;

    public Smart(){
        this (UUID.randomUUID());
    }

    public Smart(UUID id){
        mId = id;
        mDate = new Date();
        mTime = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSpecific() {
        return mSpecific;
    }

    public void setSpecific(String specific) {
        mSpecific = specific;
    }

    public String getMeasurable() {
        return mMeasurable;
    }

    public void setMeasurable(String measurable) {
        mMeasurable = measurable;
    }

    public String getAttainable() {
        return mAttainable;
    }

    public void setAttainable(String attainable) {
        mAttainable = attainable;
    }

    public String getRelevant() {
        return mRelevant;
    }

    public void setRelevant(String relevant) {
        mRelevant = relevant;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public String getGallery(){
        return mGallery;
    }

    public void setGallery(String gallery){
        mGallery = gallery;
    }

    public String getPhotoFilename(){
        return "IMG_" + getId().toString() + ".jpg";
    }

    public void remove(int removing_item) {
        return ;
    }
}
