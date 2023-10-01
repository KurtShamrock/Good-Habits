package com.example.goodhabits.viewmodel;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.goodhabits.R;
import com.example.goodhabits.Utils;

public class SoundViewModel extends AndroidViewModel {
    public MutableLiveData<String> timerData = new MutableLiveData<>();
    public long timer, timeRemainingInMillis;
    public CountDownTimer countDownTimer;
    public int millisInFuture;
    public MutableLiveData<Boolean> isFinished = new MutableLiveData<>();

    MediaPlayer player_1 = MediaPlayer.create(getApplication(), R.raw.rain);
    MediaPlayer player_2 = MediaPlayer.create(getApplication(), R.raw.flame);
    MediaPlayer player_3 = MediaPlayer.create(getApplication(), R.raw.forrest);

    public SoundViewModel(@NonNull Application application) {
        super(application);
        player_1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });

        player_2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });

        player_3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
    }

    public void onPlay(String sound, String time) {
        switch (sound) {
            case Utils.RAIN:
                player_1.start();
                isFinished.setValue(false);
                startTimer(time);
                break;
            case Utils.FLAME:
                player_2.start();
                isFinished.setValue(false);
                startTimer(time);
                break;
            case Utils.FORREST:
                player_3.start();
                isFinished.setValue(false);
                startTimer(time);
                break;
        }
    }

    public void startTimer(String time) {
        switch (time) {
            case Utils.TIMER_1:
                millisInFuture = 120000;
                break;
            case Utils.TIMER_2:
                millisInFuture = 300000;
                break;
            case Utils.TIMER_3:
                millisInFuture = 600000;
                break;
            case Utils.TIMER_4:
                millisInFuture = 900000;
                break;
            case Utils.TIMER_5:
                millisInFuture = 1800000;
                break;
            case Utils.TIMER_6:
                millisInFuture = 3600000;
                break;
        }
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long l) {
                timeRemainingInMillis = l;
                timer = l / 1000;
                timerData.setValue(DateUtils.formatElapsedTime(timer));
            }

            @Override
            public void onFinish() {
                timerData.setValue("");
                isFinished.setValue(true);
            }
        };
        countDownTimer.start();
    }

    public void onStop(String sound) {
        try {
            countDownTimer.cancel();
            countDownTimer = null;
            countDownTimer = new CountDownTimer(timeRemainingInMillis, 1000) {
                @Override
                public void onTick(long l) {
                    timeRemainingInMillis = l;
                    timer = l / 1000;
                    timerData.setValue(DateUtils.formatElapsedTime(timer));
                }

                @Override
                public void onFinish() {
                    timerData.setValue("");
                    isFinished.setValue(true);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (sound) {
            case Utils.RAIN:
                player_1.stop();
                try {
                    player_1.prepare();
                    player_1.seekTo(0);
                } catch (Throwable t) {
                    Log.d("SoundVM", t.getMessage() + " !");
                }
                break;

            case Utils.FLAME:
                player_2.stop();
                try {
                    player_2.prepare();
                    player_2.seekTo(0);
                } catch (Throwable t) {
                    Log.d("SoundVM", t.getMessage() + " !");
                }
                break;
            case Utils.FORREST:
                player_3.stop();
                try {
                    player_3.prepare();
                    player_3.seekTo(0);
                } catch (Throwable t) {
                    Log.d("SoundVM", t.getMessage() + " !");
                }
                break;
        }
    }

    public void turnOffPlayer(String sound){
        switch (sound) {
            case Utils.RAIN:
                player_1.stop();
                break;
            case Utils.FLAME:
                player_2.stop();
                break;
            case Utils.FORREST:
                player_3.stop();
                break;
        }
    }

    public void continueCountDownTimer(String sound){
        switch (sound) {
            case Utils.RAIN:
                player_1.start();
                countDownTimer.start();
                break;
            case Utils.FLAME:
                player_2.start();
                countDownTimer.start();
                break;
            case Utils.FORREST:
                player_3.start();
                countDownTimer.start();
                break;
        }
    }


    public LiveData<Boolean> getIsFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished.setValue(finished);
    }

    public LiveData<String> getTimerData() {
        return timerData;
    }

    public void setTimerData(String time) {
        timerData.setValue(time);
    }
}
