package gnu.cs.mamddadong.login;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class KakaoGlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this, "a8273465176660cb2cfb868c97daca21");
    }
}
