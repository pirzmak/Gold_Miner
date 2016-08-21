package com.example.ja.goldminer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private int page;
    private StatDAO statDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        page=0;

        statDAO = new StatDAO(this);
        levelcreator();

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        Button button = (Button)findViewById(R.id.level1);
        if (button != null) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
            button.setText("1");
        }


        System.out.print("a");
        System.out.print(Stat.getId());

    }

    public void levelcreator()
    {
        //(typ/x/y/typ/x/y...
        Stat stat = new Stat(10,"6/235/546/" + "0/216/481/" + "1/330/467/" + "1/374/391/" +
                "5/89/390/" + "2/330/230/" + "2/60/222/" + "1/210/207/" + "3/375/164/" + "3/32/158/");
        statDAO.insertNote(stat);
    }

    public void startLevel1(View view)
    {
        Stat stat = statDAO.getNoteById(1);
        setContentView(new GamePanel(this,stat));
    }
}
