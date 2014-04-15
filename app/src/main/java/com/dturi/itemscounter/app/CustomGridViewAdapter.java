package com.dturi.itemscounter.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 * @author manish.s
 *
 */
public class CustomGridViewAdapter extends ArrayAdapter<InterestingItems> {
    Context context;
    int layoutResourceId;
    ArrayList<InterestingItems> data = new ArrayList<InterestingItems>();
    private InterestingItemsDataSource datasource;

    public CustomGridViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<InterestingItems> data,
                                 InterestingItemsDataSource datasource) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.datasource = datasource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final RecordHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtName = (EditText) row.findViewById(R.id.TextViewName);
            holder.txtCounter = (TextView) row.findViewById(R.id.TextViewCounter);
            holder.btnAddOne = (Button) row.findViewById(R.id.ButtonAddOne);
            holder.btnRemoveOne = (Button) row.findViewById(R.id.ButtonRemoveOne);
            holder.btnRemove = (Button) row.findViewById(R.id.RemoveItem);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        final InterestingItems item = data.get(position);
        holder.txtName.setText(item.GetName());
        String counterValue = String.valueOf(item.GetScore());
        holder.txtCounter.setText(counterValue);

        holder.txtName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    item.SetName(holder.txtName.getText().toString());
                    datasource.updateItemName(item);

                    return true;
                }
                return false;
            }
        });

        holder.txtName.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!item.GetName().equals(holder.txtName.getText().toString())) {
                        item.SetName(holder.txtName.getText().toString());
                        datasource.updateItemName(item);
                    }
                }
            }
        });

        holder.btnAddOne.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                item.AddOne();
                String counterValue = String.valueOf(item.GetScore());
                holder.txtCounter.setText(counterValue);
                datasource.updateItemCounter(item);
            }
        });

        holder.btnRemoveOne.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                item.RemoveOne();
                String counterValue = String.valueOf(item.GetScore());
                holder.txtCounter.setText(counterValue);
                datasource.updateItemCounter(item);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                data.remove(item);
                datasource.deleteItem(item);
                notifyDataSetChanged();
            }
        });

        return row;

    }

    static class RecordHolder {
        EditText txtName;
        TextView txtCounter;
        Button btnAddOne;
        Button btnRemoveOne;
        Button btnRemove;
    }
}