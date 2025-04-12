package fr.ul.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import fr.ul.myapplication.R;
import fr.ul.myapplication.models.Tontine;

public class TontineAdapter extends RecyclerView.Adapter<TontineAdapter.ViewHolder> {
    private List<Tontine> tontines;

    public TontineAdapter(List<Tontine> tontines) {
        this.tontines = tontines;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tontine, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tontine tontine = tontines.get(position);
        holder.nameTextView.setText(tontine.getName());
        holder.amountTextView.setText(String.format("%.2f FCFA", tontine.getAmount()));
    }

    @Override
    public int getItemCount() {
        return tontines.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView amountTextView;

        public ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.tontineName);
            amountTextView = view.findViewById(R.id.tontineAmount);
        }
    }
}