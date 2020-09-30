package com.example.calkulator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

    String firstNumber = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    private void result(String res) {
        TextView result = (TextView) findViewById(R.id.number);
        result.setText(res);
    }

    public void one(View view) {
        firstNumber += "1";
        result(firstNumber);
    }

    public void two(View view) {
        firstNumber += "2";
        result(firstNumber);
    }

    public void three(View view) {
        firstNumber += "3";
        result(firstNumber);
    }

    public void four(View view) {
        firstNumber += "4";
        result(firstNumber);
    }

    public void five(View view) {
        firstNumber += "5";
        result(firstNumber);
    }

    public void six(View view) {
        firstNumber += "6";
        result(firstNumber);
    }

    public void seven(View view) {
        firstNumber += "7";
        result(firstNumber);
    }

    public void eight(View view) {
        firstNumber += "8";
        result(firstNumber);

    }

    public void nine(View view) {
        firstNumber += "9";
        result(firstNumber);
    }

    public void dot(View view) {
        firstNumber += ".";
        result(firstNumber);
    }

    public void zero(View view) {
        firstNumber += "0";
        result(firstNumber);
    }

    public void minus(View view) {
        firstNumber += "-";
        result(firstNumber);
    }

    public void plus(View view) {
        firstNumber += "+";
        result(firstNumber);
    }

    public void divider(View view) {
        firstNumber += "/";
        result(firstNumber);
    }

    public void multiply(View view) {
        firstNumber += "*";
        result(firstNumber);
    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public void equal(View view) {
        result("" + eval(firstNumber));
    }

    public void clear(View view) {
        firstNumber = "";
        result(firstNumber);
    }


}






