package com.edulectronics.tinycircuit.Controllers;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Wilmer on 27-1-2017.
 */

public class MediaController {
    Context context;

    public MediaController(Context context){
        this.context = context;
    }

    public void playSound(int id){
        //When a component has no power or resistance it will give 0 as its sound id, so zero means
        //no sound
        if(id != 0) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, id);
            //This listener is used to clean up the memory of the device after playing the sound
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
            mediaPlayer.start();
        }
    }
}
