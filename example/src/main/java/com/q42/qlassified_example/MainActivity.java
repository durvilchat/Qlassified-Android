package com.q42.qlassified_example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.q42.qlassified.Qlassified;
import com.q42.qlassified.Storage.QlassifiedSharedPreferencesService;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText putKeyText;
    private EditText putValueText;

    private EditText getValueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button putButton = (Button) findViewById(R.id.put_classified_button);
        final Button getButton = (Button) findViewById(R.id.get_classified_button);

        putKeyText = (EditText) findViewById(R.id.put_alias_classified_edittext);
        putValueText = (EditText) findViewById(R.id.put_value_classified_edittext);

        getValueText = (EditText) findViewById(R.id.get_value_classified_edittext);

        putButton.setOnClickListener(this);
        getButton.setOnClickListener(this);

        // We can add our own storage service like so;
        Qlassified.Service.start(this);
        Qlassified.Service.setStorageService(new QlassifiedSharedPreferencesService(this, getString(R.string.storage_name)));
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.put_classified_button:
                Qlassified.Service.put(
                        putKeyText.getText().toString(),
                        putValueText.getText().toString()
                );
                putKeyText.setText(null);
                putValueText.setText(null);
                closeKeyobard();
                break;
            case R.id.get_classified_button:
                String string = Qlassified.Service.getString(getValueText.getText().toString());
                Snackbar snackbar = Snackbar.make(
                        findViewById(android.R.id.content),
                        (string != null)
                                ? String.format(getString(R.string.result_found), string)
                                : getString(R.string.no_result),
                        Snackbar.LENGTH_LONG);
                snackbar.show();
                break;
        }
    }

    /**
     * Quick copy from the interwebs;
     * http://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
     */
    private void closeKeyobard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
