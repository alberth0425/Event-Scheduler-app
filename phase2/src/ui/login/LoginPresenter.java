package ui.login;

import use_cases.AuthService;

public class LoginPresenter {
    private final LoginView view;

    /**
     * Construct a LoginPresenter with given view.
     *
     * @param view the view using this presenter
     */
    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    /**
     * Handle the login button action.
     */
    public void onLoginButtonPressed() {
        if (AuthService.shared.loginUser(view.getUsername(), view.getPassword())) {
            view.navigateToUserView();
        } else {
            view.setError("Invalid username or password.");
        }
    }

    public void onRegisterButtonPressed() {
        view.navigateToCreateAccount();
    }

    public interface LoginView {
        String getUsername();
        String getPassword();
        void setError(String error);
        void navigateToUserView();
        void navigateToCreateAccount();
    }
}
