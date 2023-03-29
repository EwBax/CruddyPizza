// This adapter and the way I set up my recycler view was inspired by these videos https://www.youtube.com/watch?v=1ssYYy8Li48 https://www.youtube.com/watch?v=7GPUpvcU1FE&t=9s

package utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ewbax.cruddypizza.R;

import java.util.ArrayList;

import models.OrderModel;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<OrderModel> orderModels;


    public OrderHistoryAdapter(Context context, ArrayList<OrderModel> orderModels, RecyclerViewInterface recyclerViewInterface) {

        this.context = context;
        this.orderModels = orderModels;
        this.recyclerViewInterface = recyclerViewInterface;

    }


    @NonNull
    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_history_row, parent, false);

        return new OrderHistoryAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.MyViewHolder holder, int position) {

        String orderDetails = "#" + orderModels.get(position).getOrderNum() +
                " " + orderModels.get(position).getDate();

        holder.orderDetailsTV.setText(orderDetails);

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderDetailsTV;
        ImageView rightArrowIV;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            orderDetailsTV = itemView.findViewById(R.id.orderDetailsTV);
            rightArrowIV = itemView.findViewById(R.id.rightArrowIV);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }

    } // end inner class

} // end outer class
