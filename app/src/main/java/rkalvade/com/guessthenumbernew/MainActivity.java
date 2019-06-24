package rkalvade.com.guessthenumbernew;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //MEMBER VARIABLES
    TextView mClues;
    TextView mGuessCounter;
    EditText mNumberBox;
    int NumberEntered;
    Button CheckButton;
    Random mRandom;
    int NumberGenerated;
    int NoOfGuess = 0;
    int maxAttempts = 5;
    MediaPlayer backgroundMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClues = (TextView) findViewById(R.id.Clues);
        mGuessCounter = (TextView) findViewById(R.id.GuessCounter);
        mNumberBox =(EditText) findViewById(R.id.PlayerGuess);
        CheckButton = (Button) findViewById(R.id.Check);
        mRandom = new Random();
        backgroundMusic =MediaPlayer.create(getApplicationContext(),R.raw.background);
        backgroundMusic.start();
            NumberGenerated = mRandom.nextInt(100);
        Log.d("Guess","Number = "+NumberGenerated);
        CheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Guess The Number","Button pressed");
                NumberEntered = Integer.parseInt(mNumberBox.getText().toString());
                if (NoOfGuess >= maxAttempts) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Game Over");
                    alert.setMessage("Do you want to play again?");
                    alert.setCancelable(false);
                    alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            backgroundMusic.stop();
                            finish();
                        }
                    });
                    alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            PackageManager packageManager = getApplicationContext().getPackageManager();
                            Intent intent = packageManager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
                            ComponentName componentName = intent.getComponent();
                            Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                            getApplicationContext().startActivity(mainIntent);

                        }
                    });
                    alert.show();
                }
                else if(NumberEntered==NumberGenerated)
                {
                    final AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("You won");
                    alert.setMessage("Do you want to play again?");
                    alert.setCancelable(false);
                    alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            backgroundMusic.stop();
                            finish();
                        }});
                    alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            PackageManager packageManager = getApplicationContext().getPackageManager();
                            Intent intent = packageManager.getLaunchIntentForPackage(getApplicationContext().getPackageName());
                            ComponentName componentName = intent.getComponent();
                            Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                            getApplicationContext().startActivity(mainIntent);
                        }});
                    alert.show();

                }
                else if (NumberEntered>NumberGenerated) {

                    mClues.setText("Number is less than " + NumberEntered);
                    NoOfGuess++;
                }
                else if(NumberEntered < NumberGenerated){

                    mClues.setText("Number is greater than "+NumberEntered);
                    NoOfGuess++;
                };

                mGuessCounter.setText("GUESS COUNTER -"+NoOfGuess+"/5");
            }}
        );

    }
}

