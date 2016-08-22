package com.example.ja.goldminer;

import android.content.Intent;
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
    private int lvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        page = 0;

        statDAO = new StatDAO(this);

        levelcreator();
        lvl = statDAO.getNoteById(1).getNr();
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        buttonCreator();

    }

    public void buttonCreator(){

        Button button = (Button) findViewById(R.id.level1);
        if (button != null && lvl>=1+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
            button.setText(String.valueOf(1));
        }
        button = (Button) findViewById(R.id.level2);
        if (button != null && lvl>=2+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
            button.setText(String.valueOf(2));
        }
        button = (Button) findViewById(R.id.level3);
        if (button != null && lvl>=3+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
        }
        button = (Button) findViewById(R.id.level4);
        if (button != null && lvl>=4+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
        }
        button = (Button) findViewById(R.id.level5);
        if (button != null && lvl>=5+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
        }
        button = (Button) findViewById(R.id.level6);
        if (button != null && lvl>=6+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
        }
        button = (Button) findViewById(R.id.level7);
        if (button != null && lvl>=7+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
        }
        button = (Button) findViewById(R.id.level8);
        if (button != null && lvl>=8+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
        }
        button = (Button) findViewById(R.id.level9);
        if (button != null && lvl>=9+page) {
            button.setBackground(getResources().getDrawable(R.drawable.level));
        }
    }

    public void levelcreator()
    {
        //(typ/x/y/typ/x/y...
        Stat stat = new Stat(1,"");
        statDAO.insertNote(stat);
        stat = new Stat(10,"6/235/546/" + "0/216/481/" + "1/330/467/" + "1/374/391/" +
                "5/89/390/" + "2/330/230/" + "2/60/222/" + "1/210/207/" + "3/375/164/" + "3/32/158/");
        statDAO.insertNote(stat);
        stat = new Stat(1,"6/235/546/");
        statDAO.insertNote(stat);
    }

    private void startLvl(View view,int nr){
        if(lvl>=nr){
            Stat stat = statDAO.getNoteById(nr+1);
            Intent i = new Intent(this, GameActivity.class);
            i.putExtra("level", stat);
            startActivity(i);
        }
    }

    public void startLevel1(View view)
    {
        startLvl(view,1);
    }
    public void startLevel2(View view)
    {
        startLvl(view,2);
    }
    public void startLevel3(View view)
    {
        startLvl(view,3);
    }
    public void startLevel4(View view)
    {
        startLvl(view,4);
    }
    public void startLevel5(View view)
    {
        startLvl(view,5);
    }
    public void startLevel6(View view)
    {
        startLvl(view,6);
    }
    public void startLevel7(View view)
    {
        startLvl(view,7);
    }
    public void startLevel8(View view)
    {
        startLvl(view,8);
    }
    public void startLevel9(View view)
    {
        startLvl(view,9);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        statDAO.delete();
    }
}
