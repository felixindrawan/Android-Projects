package com.example.gpacalculator;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MarksResult extends AppCompatActivity {
  private TextView currentMark, textOptionOne,textOptionTwo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_marks_result);

   try {
     Bundle bundle = getIntent().getExtras();
     Double averageWRTWeight = round(bundle.getDouble("AverageWRTWeight"), 3);
     Double totalWeight = round(bundle.getDouble("TotalWeightOfMarks") * 100, 3);
     Double remainingWeight = 100 - totalWeight;
     Double optionOneUser = bundle.getDouble("OptionalOneUser");
     Double optionTwoUser = bundle.getDouble("OptionalTwoUser");
     Double optionOneVal = round(bundle.getDouble("OptionalOneAns"), 3);
     Double optionTwoVal = round(bundle.getDouble("OptionalTwoAns"), 3);

     currentMark = (TextView) findViewById(R.id.text_current_mark);
     currentMark.setText("CURRENT MARK: " + averageWRTWeight +"\nWEIGHT: " + totalWeight +"%");

     if (optionOneVal != -1){
       textOptionOne = (TextView) findViewById(R.id.textview_optional1);
       textOptionOne.setVisibility(View.VISIBLE);
       textOptionOne.setText("TO FINISH WITH " + optionOneUser + ", YOU NEED " + optionOneVal + ", "
           + "ON THE REMAINING " + remainingWeight + "%");
     }

     if (optionTwoVal != -1){
       textOptionTwo = (TextView) findViewById(R.id.textview_optional2);;
       textOptionTwo.setVisibility(View.VISIBLE);
       textOptionTwo.setText("RECIEVING " + optionTwoUser + " ON THE REMAINING " + remainingWeight +
           "% WILL RESULT IN A FINAL AVERAGE OF " + optionTwoVal);
     }
   } catch (Exception e) {
     currentMark = (TextView) findViewById(R.id.text_current_mark);
     currentMark.setText("PLEASE ENTER VALID VALUES");
     e.printStackTrace();
   }

  }

  private double round(double value, int decimalPoint){
    if (decimalPoint < 0) throw new IllegalArgumentException();

    BigDecimal roundedVal = BigDecimal.valueOf(value);
    roundedVal = roundedVal.setScale(decimalPoint, RoundingMode.HALF_UP);
    return roundedVal.doubleValue();
  }

  public void buttonClick(View v){
    switch (v.getId()){
      case R.id.back_btn:
        textOptionOne = (TextView) findViewById(R.id.textview_optional1);
        textOptionTwo = (TextView) findViewById(R.id.textview_optional2);

        textOptionOne.setVisibility(View.INVISIBLE);
        textOptionTwo.setVisibility(View.INVISIBLE);

        finish();
        break;
    }
  }
}
