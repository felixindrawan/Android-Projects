package com.example.gpacalculator;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
  private Button addAssignment;
  private TableLayout tableMarks;
  private TableRow tableRows;
  private EditText newMarks, newWeights;
  private List<Double> markEds = new ArrayList<Double>();
  private List<Double> weightEds = new ArrayList<Double>();
  private List<EditText> addMarkEds = new ArrayList<EditText>();
  private List<EditText> addWeightEds = new ArrayList<EditText>();
  private int additionalEd = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    addAssignment = (Button)findViewById(R.id.add_assignment);

    tableMarks = (TableLayout)findViewById(R.id.marks_tablelayout);
    tableMarks.setColumnStretchable(0, true);
    tableMarks.setColumnStretchable(1, true);

  }

  public void buttonClick(View v){
    switch (v.getId()){
      case R.id.add_assignment:
        tableRows = new TableRow(this);
        newMarks = new EditText(this);
        newWeights = new EditText(this);
        addMarkEds.add(newMarks);
        addWeightEds.add(newWeights);

        setEditText(newMarks);
        newMarks.setHint(getResources().getString(R.string.mark));
        newMarks.setId(View.generateViewId());

        setEditText(newWeights);
        newWeights.setHint(getResources().getString(R.string.weight));
        newWeights.setId(View.generateViewId());

        tableRows.addView(newMarks);
        tableRows.addView(newWeights);
        tableMarks.addView(tableRows);
        additionalEd++;
        break;

      case R.id.clear_btn:
        clearButtonPressed();
        break;

      case R.id.compute_btn:
        computeButtonPressed();
        break;

      case R.id.exit:
        android.os.Process.killProcess(android.os.Process.myPid());
        break;
    }
  }

  private void clearButtonPressed(){
    EditText tempEd;

    tempEd = (EditText) findViewById(R.id.user_mark1);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.user_weight1);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.user_mark2);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.user_weight2);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.user_mark3);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.user_weight3);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.user_mark4);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.user_weight4);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.edit_ending_avg);
    tempEd.setText("");
    tempEd = (EditText) findViewById(R.id.edit_remaining_avg);
    tempEd.setText("");

    for (int i = 0 ; i < additionalEd; i++){
      tempEd = (EditText) findViewById(addMarkEds.get(i).getId());
      tempEd.setText("");
      tempEd = (EditText) findViewById(addWeightEds.get(i).getId());
      tempEd.setText("");
    }
  }

  private void computeButtonPressed(){
    EditText tempMarkEd, tempWeightEd;
    double averageTrue = 0, averageWRTweight, totalWeight = 0;
    setDefaultEds();

    for (int j = 0; j < additionalEd; j++){
      tempMarkEd = (EditText) findViewById(addMarkEds.get(j).getId());
      tempWeightEd = (EditText) findViewById(addWeightEds.get(j).getId());
      markEds.add(nonZeroChecker(tempMarkEd));
      weightEds.add(nonZeroChecker(tempWeightEd)/100);
    }

    for (int i = 0; i < markEds.size(); i++){
      totalWeight += weightEds.get(i);
      averageTrue += markEds.get(i) * weightEds.get(i);
    }

    averageWRTweight = averageTrue/totalWeight;

    Intent intent = new Intent(MainActivity.this, MarksResult.class);
    Bundle bundle = new Bundle();
    bundle.putDouble("AverageWRTWeight", averageWRTweight);
    bundle.putDouble("TotalWeightOfMarks", totalWeight);

    tempMarkEd = (EditText) findViewById(R.id.edit_remaining_avg);
    double optionOne = nonZeroChecker(tempMarkEd);
    bundle.putDouble("OptionalOneUser", optionOne);
    System.out.println(optionOne);
    bundle.putDouble("OptionalOneAns", optionalOne(optionOne, averageTrue, 1 - totalWeight));
    tempMarkEd = (EditText) findViewById(R.id.edit_ending_avg);
    double optionTwo = nonZeroChecker(tempMarkEd);
    bundle.putDouble("OptionalTwoUser", optionTwo);
    bundle.putDouble("OptionalTwoAns", optionalTwo(optionTwo, averageTrue, 1-totalWeight));

    intent.putExtras(bundle);
    startActivity(intent);

    markEds.clear();
    weightEds.clear();
  }

  private double optionalOne (double value, double trueAverage, double remainingWeight){
    if (value != 0){
      return (value-trueAverage)/remainingWeight;
    }

    return -1;
  }

  private double optionalTwo (double value, double trueAverage, double remainingWeight){
    if (value != 0){
      return trueAverage + (value*remainingWeight);
    }

    return -1;
  }

  private double nonZeroChecker(EditText field){
    Editable temp = field.getText();

    if (temp == null || temp.toString().equals("")){
      return 0;
    }

    return Double.parseDouble(temp.toString());
  }

  private void setDefaultEds(){
    EditText markEd, weightEd;

    markEd = (EditText) findViewById(R.id.user_mark1);
    weightEd = (EditText) findViewById(R.id.user_weight1);
    markEds.add(nonZeroChecker(markEd));
    weightEds.add(nonZeroChecker(weightEd)/100);

    markEd = (EditText) findViewById(R.id.user_mark2);
    weightEd = (EditText) findViewById(R.id.user_weight2);
    markEds.add(nonZeroChecker(markEd));
    weightEds.add(nonZeroChecker(weightEd)/100);

    markEd = (EditText) findViewById(R.id.user_mark3);
    weightEd = (EditText) findViewById(R.id.user_weight3);
    markEds.add(nonZeroChecker(markEd));
    weightEds.add(nonZeroChecker(weightEd)/100);

    markEd = (EditText) findViewById(R.id.user_mark4);
    weightEd = (EditText) findViewById(R.id.user_weight4);
    markEds.add(nonZeroChecker(markEd));
    weightEds.add(nonZeroChecker(weightEd)/100);
  }

  private void setEditText(EditText field){
    field.setHintTextColor(getResources().getColor(R.color.colorMedEmp));
    field.setTextColor(getResources().getColor(R.color.colorAccent));
    field.setTextSize(20);
    field.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
  }

}
