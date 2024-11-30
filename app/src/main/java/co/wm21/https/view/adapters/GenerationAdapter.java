package co.wm21.https.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.wm21.https.R;
import co.wm21.https.view.fragments.member.model.GenerationDataListModel;
import co.wm21.https.presenter.interfaces.GenerationItemClickListner;

public class GenerationAdapter extends RecyclerView.Adapter<GenerationAdapter.MyViewHolder> {

    private List<GenerationDataListModel> gdlModel;
    private Context context;
    GenerationItemClickListner generationItemClickListner;

    public GenerationAdapter(List<GenerationDataListModel> gdlModel, Context context, GenerationItemClickListner generationItemClickListner) {
        this.gdlModel = gdlModel;
        this.context = context;
        this.generationItemClickListner = generationItemClickListner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_row_Generaton_SLNo)
        TextView textViewSLNo;
        @BindView(R.id.txt_row_Generaton_GenerationName)
        TextView textViewGenName;
        @BindView(R.id.txt_row_Generaton_Person)
        TextView textViewPerson;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_generation, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final GenerationDataListModel model = gdlModel.get(position);
        holder.textViewSLNo.setText(model.getSerial());
        holder.textViewGenName.setText(model.getGeneration());
        holder.textViewPerson.setText(model.getMember());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generationItemClickListner.onItemClickListner(model.getSerial());
            }
        });
    }

    @Override
    public int getItemCount() {
        return gdlModel.size();//Math.min(gdlModel.size(), 12

    }
}
