package jablonskijakub.intervaltraining.exceptions;

/**
 * Created by Japko on 2017-04-17.
 */
public class WrongTimeSetEx extends RuntimeException {
    private String message;
    public WrongTimeSetEx(String message)
    {
        this.message=message;
    }
    public String getMessageValue()
    {
        return message;
    }
}
