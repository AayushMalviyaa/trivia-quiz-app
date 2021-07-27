package com.example.trivia;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView triviatext;
    int score=0;
    int highscore;
    int x;
    boolean b;
    TextView questioncountertext;
    TextView cardtext;
    Button truebutton;
    Button falsebutton;
    ImageButton prevbutton;
    ImageButton nextButton;
    CardView card;
    GifImageView gif;
    int queCounter=0;
     List<Question> question;
    Button highscorebutton;
    TextView scoretext;
    ArrayList<Integer> l=new ArrayList<>();
    TextView highscoretext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questioncountertext=findViewById(R.id.quecountertext);


         cardtext=findViewById(R.id.cardtext);
         truebutton=findViewById(R.id.truebutton);
         falsebutton=findViewById(R.id.falsebutton);
         prevbutton=findViewById(R.id.prevbutton);
         nextButton=findViewById(R.id.nextbutton);
         highscoretext=findViewById((R.id.highscoretext));
         card=findViewById(R.id.cardView);
        highscorebutton=findViewById(R.id.highscorebutton);
        scoretext=findViewById(R.id.scoretext);
        TextView scoretext;
         question=new QuestionBank().getQuestions(new AnswerListAsyncResponse(){
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                Question question1=new Question();
               cardtext.setText(questionArrayList.get(queCounter).getAnswer());
               questioncountertext.setText(queCounter+"/"+questionArrayList.size());
                Log.d("aayush", "processFinished: "+questionArrayList.size());


            }
        } );
        truebutton.setOnClickListener(this);
        falsebutton.setOnClickListener(this);
        prevbutton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        highscorebutton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view)
    {switch (view.getId())
    {
        case R.id.falsebutton:
            checkanswer(false);


            break;
        case R.id.truebutton:
            checkanswer(true);

            break;
        case R.id.nextbutton:
            queCounter=(queCounter+1)%question.size();

            updateque();
            break;
        case R.id.prevbutton:

            if(queCounter!=0) {
                queCounter = (queCounter - 1);
                updateque();
            }
            else {

                queCounter=question.size()-1;
                updateque();
            break;
            }
        case R.id.highscorebutton:
            gethighscore();
    }
    }
   public void updateque()
   {
       cardtext.setText(question.get(queCounter).getAnswer());
       questioncountertext.setText((queCounter+1)+"/"+question.size());
   }
    public void checkanswer(boolean userschoice)
    {
        boolean realanswer=question.get(queCounter).isAnswerTrue();
        if(userschoice==realanswer)
        {  fade();
            Toast.makeText(this,"CORRECT",Toast.LENGTH_SHORT).show();
            if(score>highscore&&score>x)
            {
                SharedPreferences sharedPreferences=getSharedPreferences("highscore",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("highscore",score);
                editor.apply();
            }
            Collections.sort(l);
            int i=Collections.binarySearch(l,queCounter);
            if(i>=0)
            {
                b=false;
            }
            else b=true;
            if(b==true)
            {
             l.add(queCounter);
             score=score+100;
             scoretext.setText("score"+":"+score);
            }

            updateque();
        }
        else
            {shakeAnimation();
//              new Handler().postDelayed(new Runnable() {
//                  @Override
//                  public void run() {
//                      startActivity(new Intent(MainActivity.this,gif.class));
//                  }
//              }, 1000);
                Toast.makeText(this,"INCORRECT",Toast.LENGTH_SHORT).show();
                Collections.sort(l);

                int i=Collections.binarySearch(l,queCounter);
                if(i>=0)
                {
                    b=false;
                }
                else b=true;
                if(b==true) {
                    l.add(queCounter);
                    if(score>0)
                    score = score-100;
                    scoretext.setText("score" + ":" + score);
                }
                    updateque();
        }
    }
    public  void shakeAnimation()
    {
        Animation shake= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        card=findViewById((R.id.cardView));
        card.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                card.setCardBackgroundColor(Color.parseColor("#FF0909"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    public void fade()
    {
        AlphaAnimation alphaAnimation=new AlphaAnimation(1f,0f);
        alphaAnimation.setDuration(600);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        card.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                card.setCardBackgroundColor(Color.parseColor("#1DFB26"));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    public void gethighscore()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("highscore",MODE_PRIVATE);
         x=sharedPreferences.getInt("highscore",0)+100;
        highscoretext.setText(x+"");

    }
    public void sharescore(View view)
    {
        Intent intent =new Intent(Intent.ACTION_SEND);
        intent.setType("image");
        intent.putExtra(Intent.EXTRA_SUBJECT,"I AM PLAYING TRIVIA");
        intent.putExtra(Intent.EXTRA_TEXT,"my current score is:"+scoretext.getText()+"\n"+"MY HIGHSCORE IS:" +highscoretext.getText());
        startActivity(intent);
    }



}