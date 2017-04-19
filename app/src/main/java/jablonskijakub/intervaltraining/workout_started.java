package jablonskijakub.intervaltraining;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class workout_started extends AppCompatActivity {
    MediaPlayer mp;
    CountDownTimer countDownTimer;
    int i;
    int warmUpTime=0;
    int restTime=0;
    int coolDownTime=0;
    int workTime=0;
    int roundsNumber=0;
    int max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_started);
        countDownTimer=null;
        mp= MediaPlayer.create(this, R.raw.beep07);
        final ImageView bg=(ImageView)findViewById(R.id.bg);
        final TextView number=(TextView)findViewById(R.id.number);
        bg.setAlpha((float)0.6);
        new CountDownTimer(3000, 50) {
            int textSize=300;
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished%1000<50)
                {
                    textSize=300;

                }

                number.setText(Integer.toString((int)((Math.ceil(millisUntilFinished / 1000))%60+1)));
                textSize-=(int)((Math.pow((millisUntilFinished%1000)-300,2)+200)/10000+25);
                if (textSize>0)
                {
                    number.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

                }
            }
            public void onFinish() {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                bg.setAlpha((float)0.0);
                number.setAlpha(0.0f);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    warmUpTime=extras.getInt("warmUpTime",warmUpTime);
                    restTime=extras.getInt("restTime",restTime);
                    coolDownTime=extras.getInt("coolDownTime",coolDownTime);
                    workTime=extras.getInt("workTime",workTime);
                    roundsNumber=extras.getInt("roundsNumber",roundsNumber);
                    //The key argument here must match that used in the other activity
                }
                int[] times= new int[4];
                times[0]=warmUpTime;
                times[1]=workTime;
                times[2]=restTime;
                times[3]=coolDownTime;
                max=1+roundsNumber*2;
                goTimer(times,1+roundsNumber*2);
                activateButtons();
                this.cancel();
            }
        }.start();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer!=null)
            countDownTimer.cancel();
    }
    private void activateButtons()
    {
        final Button skip=(Button)findViewById(R.id.skip);
        final Button end=(Button)findViewById(R.id.end);
        skip.setEnabled(true);
        end.setEnabled(true);
    }
    public void onSkip(View v)
    {
        i--;
        countDownTimer.onFinish();
    }

    public void onEnd(View v)
    {
        countDownTimer.cancel();
       finish();
    }

    protected void goTimer(final int[] times, final int step) {
        int time=5;
        final TextView timerText;
        timerText = (TextView) findViewById(R.id.display);

        if(step%2==0 && step!=max && step!=0) // check if work time
            time=times[1];
        else if(step%2==1&& step!=max && step!=0) // check if rest time
            time=times[2];
        else if(step==max)// check if it is warmuptime, then set it :D
            time=times[0];
        else if(step==0)//cooldown time
            time=times[3];
        countDownTimer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText(millisUntilFinished/60000+":"+String.format("%02d", (millisUntilFinished / 1000)%60));//best visual effects
                if(millisUntilFinished/1000<6)
                {
                   mp.start();//if it's finish
                }
            }

            public void onFinish() {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);
                if(step>0)
                {
                    goTimer(times, step - 1); // start next interval
                }
                this.cancel();
            }
        }.start();
    }
}
