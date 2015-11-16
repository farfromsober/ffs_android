package com.farfromsober.ffs.model;

public class DrawerMenuItem {

    private String mTitle;
    private int mIconOffId;
    private int mIconOnId;

    public DrawerMenuItem(String title, int iconOffId, int iconOnId) {
        mTitle = title;
        mIconOffId = iconOffId;
        mIconOnId = iconOnId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getIconOffId() {
        return mIconOffId;
    }

    public void setIconOffId(int iconOffId) {
        mIconOffId = iconOffId;
    }

    public int getIconOnId() {
        return mIconOnId;
    }

    public void setIconOnId(int iconOnId) {
        mIconOnId = iconOnId;
    }
}
