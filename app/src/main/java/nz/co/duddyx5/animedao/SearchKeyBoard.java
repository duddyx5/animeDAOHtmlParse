package nz.co.duddyx5.animedao;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class SearchKeyBoard extends LinearLayout implements View.OnClickListener {
    Boolean isPressed = false;
    private Button buttona,buttonb,buttonc,buttond,buttone,buttonf,buttong,buttonh,buttoni,buttonj,buttonk,buttonl,buttonm,buttonn,buttono,buttonp,buttonq,buttonr,buttons,buttont,buttonu,buttonv,buttonw,buttonx,buttony,buttonz, button1, button2, button3, button4,
            button5, button6, button7, button8,
            button9, button0, buttonDot,buttonCom,buttonat,buttonunder,buttonclear,buttonSpace,buttonNZ,buttonHotmail,buttonGmail,buttonYahoo,buttonXtra;
    private ImageButton buttonshift, buttonDelete;
    private SparseArray<String> keyValues = new SparseArray<>();
    private SparseArray<String> keyValuesCaps = new SparseArray<>();
    private InputConnection inputConnection;

    public SearchKeyBoard(Context context) {
        this(context, null, 0);
    }

    public SearchKeyBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchKeyBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        buttona = (Button) findViewById(R.id.button_a);
        buttona.setOnClickListener(this);
        buttonb = (Button) findViewById(R.id.button_b);
        buttonb.setOnClickListener(this);
        buttonc = (Button) findViewById(R.id.button_c);
        buttonc.setOnClickListener(this);
        buttond = (Button) findViewById(R.id.button_d);
        buttond.setOnClickListener(this);
        buttone = (Button) findViewById(R.id.button_e);
        buttone.setOnClickListener(this);
        buttonf = (Button) findViewById(R.id.button_f);
        buttonf.setOnClickListener(this);
        buttong = (Button) findViewById(R.id.button_g);
        buttong.setOnClickListener(this);
        buttonh = (Button) findViewById(R.id.button_h);
        buttonh.setOnClickListener(this);
        buttoni = (Button) findViewById(R.id.button_i);
        buttoni.setOnClickListener(this);
        buttonj = (Button) findViewById(R.id.button_j);
        buttonj.setOnClickListener(this);
        buttonk = (Button) findViewById(R.id.button_k);
        buttonk.setOnClickListener(this);
        buttonl = (Button) findViewById(R.id.button_l);
        buttonl.setOnClickListener(this);
        buttonm = (Button) findViewById(R.id.button_m);
        buttonm.setOnClickListener(this);
        buttonn = (Button) findViewById(R.id.button_n);
        buttonn.setOnClickListener(this);
        buttono = (Button) findViewById(R.id.button_o);
        buttono.setOnClickListener(this);
        buttonp = (Button) findViewById(R.id.button_p);
        buttonp.setOnClickListener(this);
        buttonq = (Button) findViewById(R.id.button_q);
        buttonq.setOnClickListener(this);
        buttonr = (Button) findViewById(R.id.button_r);
        buttonr.setOnClickListener(this);
        buttons = (Button) findViewById(R.id.button_s);
        buttons.setOnClickListener(this);
        buttont = (Button) findViewById(R.id.button_t);
        buttont.setOnClickListener(this);
        buttonu = (Button) findViewById(R.id.button_u);
        buttonu.setOnClickListener(this);
        buttonv = (Button) findViewById(R.id.button_v);
        buttonv.setOnClickListener(this);
        buttonw = (Button) findViewById(R.id.button_w);
        buttonw.setOnClickListener(this);
        buttonx = (Button) findViewById(R.id.button_x);
        buttonx.setOnClickListener(this);
        buttony = (Button) findViewById(R.id.button_y);
        buttony.setOnClickListener(this);
        buttonz = (Button) findViewById(R.id.button_z);
        buttonz.setOnClickListener(this);
        button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button_4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button_5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button_6);
        button6.setOnClickListener(this);
        button7 = (Button) findViewById(R.id.button_7);
        button7.setOnClickListener(this);
        button8 = (Button) findViewById(R.id.button_8);
        button8.setOnClickListener(this);
        button9 = (Button) findViewById(R.id.button_9);
        button9.setOnClickListener(this);
        button0 = (Button) findViewById(R.id.button_0);
        button0.setOnClickListener(this);
        buttonclear = (Button) findViewById(R.id.button_clear);
        buttonclear.setOnClickListener(this);
        buttonSpace = (Button) findViewById(R.id.space_button);
        buttonSpace.setOnClickListener(this);
        buttonDelete = (ImageButton) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(this);
        buttonDelete.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v.hasFocus()){
                    buttonDelete.setImageResource(R.drawable.delete_selected);
                }else{
                    buttonDelete.setImageResource(R.drawable.delete_key);
                }
            }
        });
//buttonDone = findViewById(R.id.button_done) ;
//buttonDone.setOnClickListener(this);
        keyValues.put(R.id.space_button," ");
        keyValues.put(R.id.button_a, "a");
        keyValues.put(R.id.button_b, "b");
        keyValues.put(R.id.button_c, "c");
        keyValues.put(R.id.button_d, "d");
        keyValues.put(R.id.button_e, "e");
        keyValues.put(R.id.button_f, "f");
        keyValues.put(R.id.button_g, "g");
        keyValues.put(R.id.button_h, "h");
        keyValues.put(R.id.button_i, "i");
        keyValues.put(R.id.button_j, "j");
        keyValues.put(R.id.button_k, "k");
        keyValues.put(R.id.button_l, "l");
        keyValues.put(R.id.button_m, "m");
        keyValues.put(R.id.button_n, "n");
        keyValues.put(R.id.button_o, "o");
        keyValues.put(R.id.button_p, "p");
        keyValues.put(R.id.button_q, "q");
        keyValues.put(R.id.button_r, "r");
        keyValues.put(R.id.button_s, "s");
        keyValues.put(R.id.button_t, "t");
        keyValues.put(R.id.button_u, "u");
        keyValues.put(R.id.button_v, "v");
        keyValues.put(R.id.button_w, "w");
        keyValues.put(R.id.button_x, "x");
        keyValues.put(R.id.button_y, "y");
        keyValues.put(R.id.button_z, "z");
        keyValues.put(R.id.button_1, "1");
        keyValues.put(R.id.button_2, "2");
        keyValues.put(R.id.button_3, "3");
        keyValues.put(R.id.button_4, "4");
        keyValues.put(R.id.button_5, "5");
        keyValues.put(R.id.button_6, "6");
        keyValues.put(R.id.button_7, "7");
        keyValues.put(R.id.button_8, "8");
        keyValues.put(R.id.button_9, "9");
        keyValues.put(R.id.button_0, "0");
    }
    @Override
    public void onClick(View view) {

        if (inputConnection == null)
            return;

        if (view.getId() == R.id.button_delete) {

            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                inputConnection.commitText("", 1);

            }
        } else if (view.getId() == R.id.button_clear){
            inputConnection.deleteSurroundingText(50,0);
        }
        else {
                String value = keyValues.get(view.getId());
                inputConnection.commitText(value, 1);
        }

    }



    public void setInputConnection(InputConnection ic) {
        inputConnection = ic;
    }
}
