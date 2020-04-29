package themion.choiceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    LinkedList<LinearLayout> lay = new LinkedList<>();
    int count = 0;
    String text = "선택사항의 수가 2개 이하입니다.";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addChoice();
        addChoice();
    }

    public void onClick(View view)
    {
        if(view.getId() == findViewById(R.id.add).getId()) { addChoice(); }
        else if(view.getId() == findViewById(R.id.cf).getId())
        {
            if(count >= 2) getRes();
            else Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
    }

    private void addChoice()
    {
        TextView check = findViewById(R.id.check);
        check.setText("");

        LinearLayout choice = new LinearLayout(this);

        choice.setGravity(Gravity.CENTER);
        choice.setOrientation(LinearLayout.HORIZONTAL);

        EditText newED = new EditText(this);
        newED.setWidth(px(260));
        newED.setHint("선택사항 입력");
        newED.setTag("ED");
        newED.setSingleLine();

        Button newBT = new Button(this);
        newBT.setWidth(px(30));
        newBT.setText("삭제");
        newBT.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(count > 2)
                {
                    TextView check = findViewById(R.id.check);
                    check.setText("");
                    Remove((LinearLayout)view.getParent());
                }

                else Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });

        count += 1;

        choice.addView(newED);
        choice.addView(newBT);

        LinearLayout plate = findViewById(R.id.plate);
        plate.addView(choice);

        lay.add(choice);
    }

    private void getRes()
    {
        LinkedList<String> lst = new LinkedList<>();
        TextView check = findViewById(R.id.check);
        Random random = new Random();
        int rand = 0;

        for(int i = count; i > 0; i--)
        {
            LinearLayout it = lay.get(i - 1);
            EditText ed = it.findViewWithTag("ED");

            if(ed.getText().toString().length() != 0)
            {
                lst.add(ed.getText().toString());
                rand += 1;
            }

            else Remove(it);
        }

        while (count < 2) addChoice();

        if(rand >= 2) check.setText(lst.get(random.nextInt(rand)));
        else Toast.makeText(getApplicationContext(), "입력된 " + text, Toast.LENGTH_LONG).show();
    }

    private void Remove(LinearLayout it)
    {
        for(int i = 0; i < count; i++)
        {
            if(lay.get(i) == it)
            {
                it.removeAllViews();
                ViewGroup par = (ViewGroup)it.getParent();
                par.removeView(it);

                lay.remove(i);

                count -= 1;

                break;
            }
        }
    }

    private int px(int dp)
    {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
