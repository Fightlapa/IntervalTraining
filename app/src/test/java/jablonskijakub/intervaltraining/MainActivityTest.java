package jablonskijakub.intervaltraining;

import android.view.View;
import android.widget.NumberPicker;

import org.junit.Test;
import jablonskijakub.intervaltraining.exceptions.WrongTimeSetEx;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by Japko on 2017-04-17.
 */
//@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {
    NumberPicker warmUpSeconds;
    NumberPicker warmUpMinutes;
    NumberPicker restSeconds;
    NumberPicker restMinutes;
    NumberPicker coolDownSeconds;
    NumberPicker coolDownMinutes;
    NumberPicker workMinutes;
    NumberPicker workSeconds;
    NumberPicker rounds;
    @Test(expected = WrongTimeSetEx.class)
    public void testIfWrongTimeExceptionIsThrownWhenInputMakesNoSense() throws WrongTimeSetEx {
        MainActivity tested=new MainActivity();
        tested.setTimes(0,0,0,0,0,0,0,0,0);
    }
    @Test
    public void testIfTimesInSecondsAreCalculatedCorrectly() {
        MainActivity tested=new MainActivity();
        tested.setTimes(10,1,20,2,30,3,40,4,5);
        assertEquals(70,tested.warmUpTime);
        assertEquals(140,tested.restTime);
        assertEquals(210,tested.coolDownTime);
        assertEquals(280,tested.workTime);
    }
}