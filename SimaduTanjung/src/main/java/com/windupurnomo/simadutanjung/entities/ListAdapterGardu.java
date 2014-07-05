package com.windupurnomo.simadutanjung.entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.windupurnomo.simadutanjung.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windu Purnomo on 7/1/14.
 */
public class ListAdapterGardu extends BaseAdapter implements Filterable {
    private Context context;
    private List<Gardu> gardus, filtered;

    public ListAdapterGardu(Context context, List<Gardu> gardus) {
        this.context = context;
        this.gardus = gardus;
        this.filtered = this.gardus;
    }

    @Override
    public Filter getFilter() {
        return new GarduFilter();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.context);
            convertView = inflater.inflate(R.layout.list_row, null);
        }
        Gardu mhs = filtered.get(position);
        TextView textNomor = (TextView) convertView.findViewById(R.id.text_nomor);
        textNomor.setText(mhs.getNomor());

        TextView textDaya = (TextView) convertView.findViewById(R.id.text_daya);
        String daya = Float.toString(mhs.getDaya()) + " kVA";
        textDaya.setText(daya);

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return filtered.get(i);
    }

    @Override
    public int getCount() {
        return filtered.size();
    }


    /** Class filter untuk melakukan filter (pencarian) */
    private class GarduFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Gardu> filteredData = new ArrayList<Gardu>();
            FilterResults result = new FilterResults();
            String filterString = constraint.toString().toLowerCase();
            for(Gardu gardu: gardus){
                if(gardu.getNomor().toLowerCase().contains(filterString)){
                    filteredData.add(gardu);
                }
            }
            result.count = filteredData.size();
            result.values =  filteredData;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered = (List<Gardu>) results.values;
            notifyDataSetChanged();
        }

    }
}
