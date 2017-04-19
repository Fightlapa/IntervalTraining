package jablonskijakub.intervaltraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;

import jablonskijakub.intervaltraining.exceptions.WrongTimeSetEx;


public class MainActivity extends AppCompatActivity {
    int warmUpTime=0;
    int restTime=0;
    int coolDownTime=0;
    int workTime=0;
    int roundsNumber=0;
    NumberPicker warmUpSeconds;
    NumberPicker warmUpMinutes;
    NumberPicker restSeconds;
    NumberPicker restMinutes;
    NumberPicker coolDownSeconds;
    NumberPicker coolDownMinutes;
    NumberPicker workMinutes;
    NumberPicker workSeconds;
    NumberPicker rounds;
    public void buttonOnClick(View v) {

        //calculate time in seconds
        try
        {
            setTimes(warmUpSeconds.getValue(),warmUpMinutes.getValue(),restSeconds.getValue(),restMinutes.getValue(),coolDownSeconds.getValue(),coolDownMinutes.getValue(),workSeconds.getValue(),workMinutes.getValue(),rounds.getValue());
            //pack data in order to start new activity
            Intent startWorkout = new Intent(this, workout_started.class);
            startWorkout.putExtra("warmUpTime",warmUpTime);
            startWorkout.putExtra("restTime",restTime);
            startWorkout.putExtra("coolDownTime",coolDownTime);
            startWorkout.putExtra("workTime",workTime);
            startWorkout.putExtra("roundsNumber",roundsNumber);
            startActivity(startWorkout);
        }
        catch(WrongTimeSetEx e)
        {
            popUpWindow(e.getMessageValue());
        }
    }
    private void popUpWindow(String text)//pop-up messages, like errors etc.
    {
        final ImageView bg=(ImageView)findViewById(R.id.bg);
        bg.setAlpha((float)0.4);
        Intent popUp = new Intent(MainActivity.this,Pop.class);
        popUp.putExtra("text",text);
        popUp.putExtra("width",.7);
        popUp.putExtra("height",.2);
        startActivity(popUp);
    }
    public void setTimes(int warmUpSeconds,int warmUpMinutes,int restSeconds,int restMinutes, int coolDownSeconds, int coolDownMinutes,int workSeconds,int workMinutes, int rounds) throws WrongTimeSetEx
    {
        warmUpTime = warmUpSeconds;
        warmUpTime += 60 * warmUpMinutes;
        restTime = restSeconds;
        restTime += 60 * restMinutes;
        coolDownTime = coolDownSeconds;
        coolDownTime += 60 * coolDownMinutes;
        workTime = workSeconds;
        workTime += 60 * workMinutes;
        roundsNumber = rounds;
        String communicate="";
        if(restTime==0) //check if times are correct and make sense
            communicate+="No time set for Rest Time!<br>\n";
        if(workTime==0)
            communicate+="No time set for Work Time!<br>\n";
        if(roundsNumber==0)
            communicate+="No rounds number is set!<br>\n";
        if(!communicate.isEmpty())
            throw new WrongTimeSetEx(communicate);

    }



    public void onAbout(View v) //display info in popup window
    {
        Intent popUp = new Intent(MainActivity.this,Pop.class);
        String text="About:<br>\n" +
                "Image source - unsplash.com<br>\n" +
                "License - Creative Commons Zero<br>\n" +
                "How to:<br><br>\n\n" +
                "<b>Warm up</b> - is first interval of the training, leave it as 0:00 to skip it<br>\n" +
                "<b>Work time</b> - is interval in which you should do high effort exercise, it launches alternetely with rest time, work is right after warm up.<br>\n" +
                "<b>Rest time</b> - is interval in which you should calm down a bit, let oxygen flow to your muscles and get ready to next work interval<br>\n" +
                "<b>Work time</b> and rest time are repeated as many times as the rounds rumber is set<br>\n" +
                "<b>Made by Jakub Jabłoński</b><br>\n" +
                "<b>Contact:Jakub.T.Jablonski@wp.pl</b>";
        popUp.putExtra("text",text);
        startActivity(popUp);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get content in order to get times and rounds number
        warmUpSeconds = (NumberPicker) findViewById(R.id.warmUpSeconds);
        warmUpMinutes = (NumberPicker) findViewById(R.id.warmUpMinutes);
        restSeconds = (NumberPicker) findViewById(R.id.restSeconds);
        restMinutes = (NumberPicker) findViewById(R.id.restMinutes);
        coolDownSeconds = (NumberPicker) findViewById(R.id.coolDownSeconds);
        coolDownMinutes = (NumberPicker) findViewById(R.id.coolDownMinutes);
        workSeconds = (NumberPicker) findViewById(R.id.workSeconds);
        workMinutes = (NumberPicker) findViewById(R.id.workMinutes);
        rounds = (NumberPicker) findViewById(R.id.rounds);

        NumberPicker warmUpSeconds = (NumberPicker) findViewById(R.id.warmUpSeconds);
        final NumberPicker warmUpMinutes = (NumberPicker) findViewById(R.id.warmUpMinutes);
        final NumberPicker restSeconds = (NumberPicker) findViewById(R.id.restSeconds);
        final NumberPicker restMinutes = (NumberPicker) findViewById(R.id.restMinutes);
        final NumberPicker coolDownSeconds = (NumberPicker) findViewById(R.id.coolDownSeconds);
        final NumberPicker coolDownMinutes = (NumberPicker) findViewById(R.id.coolDownMinutes);
        final NumberPicker workSeconds = (NumberPicker) findViewById(R.id.workSeconds);
        final NumberPicker workMinutes = (NumberPicker) findViewById(R.id.workMinutes);
        final NumberPicker rounds = (NumberPicker) findViewById(R.id.rounds);

        warmUpSeconds.setMaxValue(60);
        restSeconds.setMaxValue(60);
        workSeconds.setMaxValue(60);
        coolDownSeconds.setMaxValue(60);
        warmUpMinutes.setMaxValue(60);
        restMinutes.setMaxValue(20);
        workMinutes.setMaxValue(20);
        coolDownMinutes.setMaxValue(60);
        rounds.setMaxValue(30);

    }
    @Override protected void onPause()
    {
        super.onPause();
        ImageView bg= (ImageView) findViewById(R.id.bg);
        bg.setAlpha((float)0.4);
    }
    @Override protected void onResume()
    {
        super.onResume();
        ImageView bg= (ImageView) findViewById(R.id.bg);
        bg.setAlpha((float)0.0);
    }

    static int countLines(String str){
        String[] lines = str.split("\r\n|\r|\n");
        return  lines.length;
    }
}