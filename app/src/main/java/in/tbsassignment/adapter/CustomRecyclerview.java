package in.tbsassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import in.tbsassignment.R;
import in.tbsassignment.room.Message;

public class CustomRecyclerview extends RecyclerView.Adapter<CustomRecyclerview.ViewHolder> {

    private Context context;
    private List<Message> arrayList;

    public CustomRecyclerview(Context context, List<Message> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            Message message = arrayList.get(position);

            holder.subject.setText(message.getSubject());
            holder.message.setText(message.getMessage());
            holder.timestamp.setText(message.getTimestamp());
            Glide.with(context)
                    .load(message.getPicture())
                    .placeholder(R.drawable.placeholder)
                    .circleCrop()
                    .into(holder.thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject, message, timestamp;
        public ImageView thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            message = itemView.findViewById(R.id.message);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }
}