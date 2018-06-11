package com.cangmaomao.advertising;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JZVideoPlayerStandard;

public class ABSJZVideoPlayerStandard extends JZVideoPlayerStandard {


    public ABSJZVideoPlayerStandard(Context context) {
        super(context);
    }

    public ABSJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setProgressAndText(int progress, long position, long duration) {
        super.setProgressAndText(progress, position, duration);
        if (progressListener != null) {
            progressListener.progress(progress);
        }

    }


    public interface ProgressListener {
        void progress(int progress);
    }


    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public ProgressListener progressListener;


}
