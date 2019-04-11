package go.id.kominfo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;



public class RegisterActivity extends AppCompatActivity {

    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (Build.VERSION.SDK_INT >= 21) { //ganti warna status bar diatas OS lolipop
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorDarkPurple));
        }
    }
}
