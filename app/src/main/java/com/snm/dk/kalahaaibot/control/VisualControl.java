package com.snm.dk.kalahaaibot.control;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class VisualControl {

    public static void showDialog(FragmentActivity activity, Fragment newFragment, int fragmentContainer, View[] views) {

        fadeInAnimation(views);

        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(fragmentContainer, newFragment)
                .addToBackStack(null)
                .commit();
    }

    public static void hideDialog(FragmentActivity activity, View[] views) {
        fadeOutAnimation(views);

        activity.getSupportFragmentManager().popBackStack();
    }

    private static void fadeInAnimation(View[] views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeIn).duration(400).playOn(v);
        }
    }

    private static void fadeOutAnimation(final View[] views) {
        for (View v : views) {
            YoYo.with(Techniques.FadeOut).duration(400).playOn(v);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable () {
            public void run() {
                for (View v : views) {
                    v.setVisibility(View.GONE);
                }
            }
        }, 400);
    }
}
