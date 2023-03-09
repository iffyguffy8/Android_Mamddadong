package gnu.cs.mamddadong.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowInsetsController;
import android.widget.ImageButton;

import androidx.core.splashscreen.SplashScreen;

import com.kakao.sdk.auth.AuthApiClient;
import com.kakao.sdk.user.UserApiClient;

import gnu.cs.mamddadong.main.MapActivity;
import gnu.cs.mamddadong.R;

public class LoginActivity extends AppCompatActivity {

    private boolean keep = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setStatusBarColor(Color.rgb(255,255,255));
        getWindow().getInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> keep);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            keep = false;
            if (AuthApiClient.getInstance().hasToken()) {
                Intent intent = new Intent(this, MapActivity.class);
                startActivity(intent);
            }
        }, 1500);

        ImageButton btn_kakao_login = findViewById(R.id.btn_kakao_login);
        btn_kakao_login.setOnClickListener(v -> {
            if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                login();
            } else {
                accountLogin();
            }
        });
    }

    public void login() {
        UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e("DEBUG - login()", "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i("DEBUG - login()", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
            }
            return null;
        });
    }

    public void accountLogin() {
        UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this,(oAuthToken, error) -> {
            if (error != null) {
                Log.e("DEBUG - accountLogin()", "로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i("DEBUG - accountLogin()", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
            }
            return null;
        });
    }
}