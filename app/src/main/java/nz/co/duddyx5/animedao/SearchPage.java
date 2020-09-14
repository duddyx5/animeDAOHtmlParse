package nz.co.duddyx5.animedao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public class SearchPage extends FragmentActivity {

    TextView back;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.search_page);
            back = findViewById(R.id.back_to_ondemand_search);
        }catch(Exception e){
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        SearchKeyBoard keyboard = findViewById(R.id.reg_frame);
        Log.d("Keycode Search: ", keyCode + "");
        if (keyCode == KeyEvent.KEYCODE_DEL){
            keyboard.onClick(findViewById(R.id.button_delete));
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        Fragment menuFrag = getSupportFragmentManager().findFragmentByTag("Menu");

        if (menuFrag == null) {
            if (back.hasFocus()) {
                super.onBackPressed();
            } else {
                LinearLayout menuItems = findViewById(R.id.linear_1);
                menuItems.setVisibility(View.VISIBLE);
                back.requestFocus();
                back.setFocusable(true);
                back.requestFocus();
            }
        } else
        {
            super.onBackPressed();
            back.setFocusable(true);
            back.requestFocus();
        }
    }
}
