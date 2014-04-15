package com.dturi.itemscounter.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    GridView gridView;
    ArrayList<InterestingItems> gridArray = new ArrayList<InterestingItems>();
    CustomGridViewAdapter customGridAdapter;
    private InterestingItemsDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        datasource = new InterestingItemsDataSource(this);
        datasource.open();
        gridView = (GridView) findViewById(R.id.MyGridView);
        customGridAdapter = new CustomGridViewAdapter(MainActivity.this, R.layout.row_grid, gridArray, datasource);
        gridView.setAdapter(customGridAdapter);

        gridArray = datasource.getAllItems();

        for (InterestingItems i : gridArray)
        {
            customGridAdapter.add(i);
        }

		Button AddButton = (Button) findViewById(R.id.AddItem);
		AddButton.setOnClickListener(new View.OnClickListener() 
		{
	        public void onClick(View v) 
	        {
	        	AlertDialog.Builder windowChoice = new AlertDialog.Builder(MainActivity.this);
	        	final EditText text = new EditText(MainActivity.this);
	        	windowChoice.setView(text);          
	        	windowChoice.setMessage("Enter name");  
	        	
	        	windowChoice.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
	        	public void onClick(DialogInterface dialog, int which) {
	        	     String entered_name = text.getText().toString();   
	        	     if (entered_name == "")
	        	     {
	        	    	 return;  
	        	     }
	        	     else
	        	     {
                         InterestingItems S1 = new InterestingItems();
                         S1.SetName(entered_name);
                         gridArray.add(S1);
                         InterestingItems item = datasource.createItem(entered_name);
                         customGridAdapter.add(item);
	        	     }
	        	    }   
	        	 });  
	        	
	        	windowChoice.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {  
	        	     public void onClick(DialogInterface dialog, int which) { 
	        	     return;  
	        	     } 
	        	  }); 
	        	
	        	windowChoice.show();        
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}