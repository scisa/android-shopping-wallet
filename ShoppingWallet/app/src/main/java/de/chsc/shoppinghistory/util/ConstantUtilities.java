package de.chsc.shoppinghistory.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ConstantUtilities {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String setDoublePrecision(double number, int precision){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        return decimalFormat.format(number);
    }
}
