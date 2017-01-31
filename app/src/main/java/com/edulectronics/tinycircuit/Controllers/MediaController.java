package com.edulectronics.tinycircuit.Controllers;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Wilmer on 27-1-2017.
 */

public class MediaController {
    Context context;
    private boolean makingSound = false;

    public MediaController(Context context){
        this.context = context;
    }

    public void playSound(int id){
        //When a component has no power or resistance it will give 0 as its sound id, so zero means
        //no sound
        if(id != 0 && !makingSound) {
            makingSound = true;
            MediaPlayer mediaPlayer = MediaPlayer.create(context, id);
            //This listener is used to clean up the memory of the device after playing the sound
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    makingSound = false;
                    mp.release();
                }
            });
            mediaPlayer.start();
        }
    }
}
