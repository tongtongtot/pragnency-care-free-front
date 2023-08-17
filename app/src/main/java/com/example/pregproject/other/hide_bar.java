package com.example.pregproject.other;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class hide_bar {

    private  Activity activity;

    public hide_bar(Activity activity) {
        this.activity=activity;
    }

    public void hide(){

        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        ActionBar actionBar = activity.getActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }

    }

}
