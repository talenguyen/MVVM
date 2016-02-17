package vn.tiki.mvvmbestpractice.event;

/**
 * Created by tale on 2/17/16.
 */
public class SignInEvent {
    private final boolean success;
    private final String email;

    public SignInEvent(boolean success, String email) {
        this.success = success;
        this.email = email;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getEmail() {
        return email;
    }
}
