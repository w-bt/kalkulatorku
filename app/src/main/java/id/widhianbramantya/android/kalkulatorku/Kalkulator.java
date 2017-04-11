package id.widhianbramantya.android.kalkulatorku;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Kalkulator extends AppCompatActivity {
    private TextView screenTop;
    private TextView screenBottom;
    private String displayTop = "";
    private String displayBottom = "";
    private String currentOperator = "";
    private int minStat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);
        screenTop = (TextView)findViewById(R.id.textTop);
        screenTop.setText(displayTop);
        screenBottom = (TextView)findViewById(R.id.textBottom);
        screenBottom.setText(displayBottom);
    }

    private void updateTopScreen(){
        screenTop.setText(displayTop);
    }

    private void updateBottomScreen(){
        screenBottom.setText(displayBottom);
    }

    public void onClickNumber(View v){
        Button b = (Button) v;
        displayBottom += b.getText();
        updateBottomScreen();
    }

    private void clear(){
        displayTop = "";
        displayBottom = "";
        currentOperator = "";
    }

    public void onClickClear(View v){
        clear();
        updateTopScreen();
        updateBottomScreen();
    }

    public void onClickOperator(View v){
        if(Character.isDigit(displayBottom.charAt(displayBottom.length()-1))){
            Button b = (Button) v;
            if(!currentOperator.equals("")){
                String[] operation = displayBottom.split(Pattern.quote(currentOperator));
                int flag = !operation[0].equals("") ? 1 : displayBottom.startsWith("-") ? 1 : 0;
                if((operation.length >= 2)&&(flag == 1)){
                    Double result = 0.;
                    if(operation.length == 4){
                        result = operate("-" + operation[1],"-" + operation[3],currentOperator);
                    } else if(operation.length == 3){
                        if(operation[0].equals("")){
                            result = operate("-" + operation[1],operation[2],currentOperator);
                        } else {
                            result = operate(operation[0],"-" + operation[2],currentOperator);
                        }
                    } else if(operation.length == 2){
                        result = operate(operation[0],operation[1],currentOperator);
                    }

                    DecimalFormat format = new DecimalFormat("0.###");
                    displayTop = displayBottom;
                    displayBottom = String.valueOf(format.format(result)).replace(",",".") + b.getText();
                    currentOperator = b.getText().toString();
                    minStat = 0;
                    updateTopScreen();
                    updateBottomScreen();
                }
            } else {
                displayBottom += b.getText();
                currentOperator = b.getText().toString();
                minStat = 0;
                updateBottomScreen();
            }
        }
    }

    private double operate(String a, String b, String op){
        switch(op){
            case "+":
                try{
                    return (Double.valueOf(a)+Double.valueOf(b));
                } catch (Exception e){
                    Log.d("Kalkulator", e.getMessage());
                }
            case "-":
                try{
                    return (Double.valueOf(a)-Double.valueOf(b));
                } catch (Exception e){
                    Log.d("Kalkulator", e.getMessage());
                }
            case "x":
                try{
                    return (Double.valueOf(a)*Double.valueOf(b));
                } catch (Exception e){
                    Log.d("Kalkulator", e.getMessage());
                }
            case "/":
                try{
                    return (Double.valueOf(a)/Double.valueOf(b));
                } catch (Exception e){
                    Log.d("Kalkulator", e.getMessage());
                }
            default: return -1;
        }
    }

    public void onClickEqual(View v){
        if(Character.isDigit(displayBottom.charAt(displayBottom.length()-1))){
            String[] operation = displayBottom.split(Pattern.quote(currentOperator));
            int flag = !operation[0].equals("") ? 1 : displayBottom.startsWith("-") ? 1 : 0;
            if((operation.length >= 2)&&(flag == 1)&&(!currentOperator.equals(""))){
                Double result = 0.;
                if(operation.length == 4){
                    result = operate("-" + operation[1],"-" + operation[3],currentOperator);
                } else if(operation.length == 3){
                    if(operation[0].equals("")){
                        result = operate("-" + operation[1],operation[2],currentOperator);
                    } else {
                        result = operate(operation[0],"-" + operation[2],currentOperator);
                    }
                } else if(operation.length == 2){
                    result = operate(operation[0],operation[1],currentOperator);
                }

                DecimalFormat format = new DecimalFormat("0.###");
                displayTop = displayBottom;
                displayBottom = String.valueOf(format.format(result)).replace(",",".");
                currentOperator = "";
                updateTopScreen();
                updateBottomScreen();
            }
        }
    }

    public void onClickBack(View v){
        if(displayBottom.endsWith("-")){
            if(Character.isDigit(displayBottom.charAt(displayBottom.length()-2))){
                currentOperator = "";
                minStat = 0;
            }
        } else if(displayBottom.endsWith("+") || displayBottom.endsWith("x") || displayBottom.endsWith("/")){
            currentOperator = "";
            minStat = 0;
        }
        if(!displayBottom.equals("")) {
            displayBottom = displayBottom.substring(0, displayBottom.length() - 1);
            updateBottomScreen();
        }
    }

    public void onClickDot(View v){
        if(!currentOperator.equals("")){
            String[] operation = displayBottom.split(Pattern.quote(currentOperator));
            String last = operation[1];
            if(!last.contains(".")){
                displayBottom += ".";
                updateBottomScreen();
            }
        } else {
            if(!displayBottom.contains(".")){
                displayBottom += ".";
                updateBottomScreen();
            }
        }
    }

    public void onClickPM(View v){
        if(minStat == 0){
            if(displayBottom.equals("") || !currentOperator.equals("")){
                displayBottom += "-";
            }
            minStat = 1;
        } else {
            if(!Character.isDigit(displayBottom.charAt(displayBottom.length()-1))){
                displayBottom = displayBottom.substring(0,displayBottom.length()-1);
                minStat = 0;
            }
        }
        updateBottomScreen();
    }
}
