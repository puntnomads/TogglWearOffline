package com.puntnomads.togglwearoffline;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class KeyboardActivity extends Activity implements View.OnClickListener {
    /**
     * Wear-Keyboard, Activity-Based Keyboard for Android Wear.
     * Built by Ido Ideas, 2014.
     * This code is Open Source and free to use.
     */
    private TextView mTextView;
    static EditText editText;
    static Button del,space, num, cap, OK;
           RelativeLayout Scroll;
    static String letters = "abcdefghijklmnopqrstuvwxyz";
    static String capitalletters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static String numbers = "1234567890";
    ArrayList<Button> buttons = new ArrayList<Button>();
    private boolean Capital = false;
    static String TextInput;
    List<String> projects = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        Scroll = (RelativeLayout) findViewById(R.id.scroll);
        editText = (EditText) findViewById(R.id.textedit);
        OK = (Button) findViewById(R.id.send);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInput = editText.getText().toString();
                /*****
                 * Here is where you use the text inserted.
                 * TextInput is the variable with the text that the user put inside.
                 * Its a static.
                 */

                String list = getIntent().getStringExtra("list");

                //this is my preferences variable
                SharedPreferences prefs = getSharedPreferences(list, 0);
                if(prefs.getString(list, "").length()>0){
                    String projectsString = prefs.getString(list, "");
                    String[] itemsProjects = projectsString.split(",");
                    if(projects.size()==0){
                        for (int i = 0; i < itemsProjects.length; i++){
                            projects.add(itemsProjects[i]);
                        }
                    }
                }

                projects.add(TextInput);

                StringBuilder stringBuilder = new StringBuilder();
                for (String s : projects){
                    stringBuilder.append(s);
                    stringBuilder.append(",");
                }
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(list, stringBuilder.toString());
                editor.apply();

            }
        });
setKeyboardCharacters(letters);
del = (Button) findViewById(R.id.backspace);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().length() > 0) {
                    editText.setText(editText.getText().toString().substring(0, editText.getText().length() - 1));
                }
            }
        });
        space = (Button) findViewById(R.id.space);
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(editText.getText()+" ");
            }
        });
        num = (Button) findViewById(R.id.numbers);
        num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scroll.removeAllViews();
                if (num.getText().toString().equals("123")){
                num.setText("Eng");
                setKeyboardCharacters(numbers);}
                else{
                    num.setText("123");
                    setKeyboardCharacters(letters);
                }
            }
        });
        cap = (Button) findViewById(R.id.capital);
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num.getText().toString().equals("123")){
                    Scroll.removeAllViews();
                    if (!Capital){
                        Capital = true;
                    setKeyboardCharacters(capitalletters); }
                    else{
                        Capital = false;

                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        Vibrator vi = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
        vi.vibrate(50);
        Button button = (Button) v;
        editText.setText(editText.getText()+""+button.getText());
    }
public void setKeyboardCharacters(String Characters){
    for (int i = 0; i<Characters.length();i++){
        Button b = new Button(getApplicationContext());
        b.setId(i);
        buttons.add(b);
        Button currentButton = buttons.get(i);
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) px,(int) px);
        if (i>0){
            if (i%4!=0) {
                if (i<4){
                    lp.addRule(RelativeLayout.BELOW,R.id.textedit);}
                else{
                    lp.addRule(RelativeLayout.BELOW,buttons.get(i-4).getId());
                }
                if (i==1){
                    lp.setMargins((int)  px,lp.topMargin,lp.rightMargin,lp.bottomMargin);}
                else{
                    lp.addRule(RelativeLayout.RIGHT_OF,buttons.get(i-1).getId());}
            }
            else{
                lp.addRule(RelativeLayout.BELOW,buttons.get(i-3).getId());
            }
        }
        else{
            lp.addRule(RelativeLayout.BELOW,R.id.textedit);
        }
        currentButton.setLayoutParams(lp);
        currentButton.setText(Characters.charAt(i)+"");
        currentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vi = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
                vi.vibrate(50);
                Button button = (Button) v;
                editText.setText(editText.getText()+""+button.getText());

            }
        });
            Scroll.addView(currentButton);
    }
}
}
