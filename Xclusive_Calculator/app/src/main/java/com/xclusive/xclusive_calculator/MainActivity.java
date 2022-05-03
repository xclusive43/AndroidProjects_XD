package com.xclusive.xclusive_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button one,two,three,four,five,six,seven,eight,nine,zero,pie,
                   equal,add,subs,multiply,divide,sin,cos,log,ln,tan,ac,c,bracket1,bracket2
                   ,factorial,square,squareroot,dot,bmi;
    private TextView top,current;
    private static StringBuffer equation1 = new StringBuffer();
    private String pievalue = "3.14159265";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        current.setText("0");
        numberbuttonclick();

    }



    private void numberbuttonclick() {


        bmi.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, bmi_activity.class );
            startActivity(intent);

        });
        ac.setOnClickListener(v->{
            current.setText("");
            top.setText("");
            equation1.delete(0,equation1.length());
        });
        c.setOnClickListener(v->{
            if (!current.getText().toString().isEmpty()) {
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring);
            }
        });
        zero.setOnClickListener(v->{
            current.setText(current.getText()+"0");
        });
        one.setOnClickListener(v->{
            current.setText(current.getText()+"1");
        });
        two.setOnClickListener(v->{
            current.setText(current.getText()+"2");
        });
        three.setOnClickListener(v->{
            current.setText(current.getText()+"3");
        });
        four.setOnClickListener(v->{
            current.setText(current.getText()+"4");
        });
        five.setOnClickListener(v->{
            current.setText(current.getText()+"5");
        });
        six.setOnClickListener(v->{
            current.setText(current.getText()+"6");
        });
        seven.setOnClickListener(v->{
            current.setText(current.getText()+"7");
        });
        eight.setOnClickListener(v->{
            current.setText(current.getText()+"8");
        });
        nine.setOnClickListener(v->{
            current.setText(current.getText()+"9");
        });
        dot.setOnClickListener(v->{
            String equa=current.getText().toString();

            if (equa.isEmpty()){
                current.setText("0.");
                return;
            }
            char last = equa.charAt(equa.length()-1);
            if(last == '+' || last == '-'  || last == '÷' ||  last == 'x' || last == '!' || last=='.'){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+".");
            }
            else {
                current.setText(current.getText().toString()+".");
            }

        });
        bracket1.setOnClickListener(v->{
            String equa=current.getText().toString();

            if (equa.isEmpty()){
                current.setText(current.getText().toString()+"(");
                return;
            }
            char last = equa.charAt(equa.length()-1);
            if(last == '+' || last == '-'  || last == '÷' ||  last == 'x' || last == '!' || last=='.' || last=='('){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+"(");
            }
            else {
                current.setText(current.getText().toString()+"(");
            }
        });
        bracket2.setOnClickListener(v->{
            String equa=current.getText().toString();
            if (equa.isEmpty()){
                current.setText(current.getText().toString()+")");
                return;
            }
            char last = equa.charAt(equa.length()-1);
            if(last == '+' || last == '-'  || last == '÷' ||  last == 'x' || last == '!' || last=='.' || last==')'){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+")");
            }
            else {
                current.setText(current.getText().toString()+")");
            }

        });
        add.setOnClickListener(v->{
            String equa=current.getText().toString();

            if (equa.isEmpty()){
                current.setText("+");
                return;
            }
            char last = equa.charAt(equa.length()-1);
            if(last == '!' || last == '-'  || last == '÷' ||  last == 'x' || last=='+'){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+"+");
            }

            else {
                current.setText(current.getText().toString()+"+");
            }

        });
        subs.setOnClickListener(v->{

            String equa=current.getText().toString();

            if (equa.isEmpty()){
                current.setText("-");
                return;
            }
            char last = equa.charAt(equa.length()-1);
            if(last == '!' || last == '+'  || last == '÷' ||  last == 'x' || last=='-'){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+"-");
            }
            else {
                current.setText(current.getText().toString()+"-");
            }


        });
        divide.setOnClickListener(v->{
            String equa=current.getText().toString();

            if (equa.isEmpty()){
                current.setText("÷");
                return;
            }
            char last = equa.charAt(equa.length()-1);
            if(last == '!' || last == '-'  || last == '+' ||  last == 'x' || last =='÷'){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+"÷");
            }
            else {
                current.setText(current.getText().toString()+"÷");
            }

        });
        multiply.setOnClickListener(v->{
            String equa=current.getText().toString();

            if (equa.isEmpty()){
                current.setText("x");
                return;
            }char last = equa.charAt(equa.length()-1);
            if(last == '!' || last == '-'  || last == '+' ||  last == '÷' || last=='x'){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+"x");
            }
            else {
                current.setText(current.getText().toString()+"x");
            }

        });
        squareroot.setOnClickListener(v->{

            String value = current.getText().toString();
            if (!value.isEmpty()){
                current.setText("√"+current.getText().toString());
                double res = Math.sqrt(Double.parseDouble(value));
                top.setText(String.valueOf(res));
            }


        });
        square.setOnClickListener(v->{
            String value = current.getText().toString();
            current.setText(current.getText().toString());
            if (value.isEmpty()){
                return;
            }

            double res = Double.parseDouble(value)*Double.parseDouble(value);

            top.setText(String.valueOf(value+"² = "+res));
        });
        pie.setOnClickListener(v->{

                top.setText(pie.getText());
                current.setText(current.getText()+pievalue);

        });
        sin.setOnClickListener(v->{
            if (current.getText().toString().contains("sin")){
                return;
            }

            if (current.getText().toString().contains("cos")||current.getText().toString().contains("tan") || current.getText().toString().contains("log"))
            {
                String r = current.getText().toString().replace("cos", "sin").replace("tan","sin").replace("log","sin");
                current.setText(r);
            }
           else {
                current.setText("sin" + current.getText());
            }

        });
        cos.setOnClickListener(v->{

            if (current.getText().toString().contains("cos")){
                return;
            }

            if (current.getText().toString().contains("sin")||current.getText().toString().contains("tan") || current.getText().toString().contains("log"))
            {
                String r = current.getText().toString().replace("sin", "cos").replace("tan","cos").replace("log","cos");
                current.setText(r);
            }
            else {
                current.setText("cos" + current.getText());
            }


        });
        tan.setOnClickListener(v->{
            if (current.getText().toString().contains("tan")){
                return;
            }

            if (current.getText().toString().contains("sin")||current.getText().toString().contains("cos") || current.getText().toString().contains("log"))
            {
                String r = current.getText().toString().replace("sin", "tan").replace("cos","tan").replace("log","tan");
                current.setText(r);
            }
            else {
                current.setText("tan" + current.getText());
            }

        });
        log.setOnClickListener(v->{
            if (current.getText().toString().contains("log")){
                return;
            }

            if (current.getText().toString().contains("sin")||current.getText().toString().contains("tan") || current.getText().toString().contains("cos"))
            {
                String r = current.getText().toString().replace("sin", "log").replace("tan","log").replace("cos","log");
                current.setText(r);
            }
            else {
                current.setText("log" + current.getText());
            }

        });
        ln.setOnClickListener(v->{
            if (!current.getText().toString().isEmpty()){
                current.setText(current.getText().toString()+"^"+"(-1)");
            }
        });
        equal.setOnClickListener(v->{
            String equa=current.getText().toString();

            if (equa.isEmpty()){
                top.setText("0");
                return;
            }
            char last = equa.charAt(equa.length()-1);
            if(last == '÷' || last == '-'  || last == '+' ||  last == 'x'){
                String currentstring = current.getText().toString();
                currentstring = currentstring.substring(0, currentstring.length() - 1);
                current.setText(currentstring+"");
            }
            String val = current.getText().toString();
            String val2 =  val.replace('x','*').replace('÷','/').trim();
            double res = eval(val2);
            top.setText(String.valueOf(res));
        });
        factorial.setOnClickListener(v-> {
            String a = current.getText().toString();

            if (a.isEmpty()){
                top.setText("0");
                return;
            }
            if (a.contains(".")){
                top.setText(String.valueOf("0"));
                return;
            }
            else {
                double fact = Double.parseDouble(current.getText().toString());
                current.setText(current.getText().toString() + "!");
                top.setText(String.valueOf(factorialfun(fact)));
            }
        });


    }

    private double factorialfun(double parseDouble) {
        return (parseDouble==1 || parseDouble==0)? 1:parseDouble*factorialfun(parseDouble-1);
    }



    private void init() {
        bmi = findViewById(R.id.bmibtn);
        top = findViewById(R.id.toptextview);
        current= findViewById(R.id.currenttextview);
        zero= findViewById(R.id.zerobtn);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        pie = findViewById(R.id.piebtn);
        equal= findViewById(R.id.equalbtn);
        add = findViewById(R.id.addbtn);
        subs = findViewById(R.id.substract);
        multiply = findViewById(R.id.multiplybtn);
        divide = findViewById(R.id.divide);
        sin = findViewById(R.id.sin);
        cos = findViewById(R.id.cos);
        tan = findViewById(R.id.tan);
        log = findViewById(R.id.log);
        ln = findViewById(R.id.ln);
        dot = findViewById(R.id.dotbtn);
        ac = findViewById(R.id.ac);
        c = findViewById(R.id.c);
        bracket1 = findViewById(R.id.bracket1);
        bracket2 = findViewById(R.id.bracket2);
        factorial = findViewById(R.id.factorial);
        square = findViewById(R.id.xsquare);
        squareroot = findViewById(R.id.squareroot);



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
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}