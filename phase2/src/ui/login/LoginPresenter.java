package ui.login;

import use_cases.AuthService;

public class LoginPresenter {
    private final LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void onLoginButtonPressed() {
        if (AuthService.shared.loginUser(view.getUsername(), view.getPassword())) {
            view.navigateToUserView();
        } else {
            view.setError("Invalid username or password.");
        }
    }

    public interface LoginView {
        String getUsername();
        String getPassword();
        void setError(String error);
        void navigateToUserView();
    }
}
