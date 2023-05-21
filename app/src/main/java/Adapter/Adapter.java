package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doubt_hub.QuestionMember;
import com.example.doubt_hub.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    ArrayList<QuestionMember> arrayList;
    Context context;

    public Adapter(ArrayList<QuestionMember> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionMember questionMember = arrayList.get(position);
        holder.name_result.setText(questionMember.getName());
        holder.question_result.setText(questionMember.getQuestion());
        holder.time_result.setText(questionMember.getDate());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_view;
        TextView name_result, question_result, time_result, comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_result = itemView.findViewById(R.id.username);
            question_result = itemView.findViewById(R.id.questions);
            time_result = itemView.findViewById(R.id.date);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context,CommentActivity.class);
//                    int position = getAbsoluteAdapterPosition();
//                    intent.putExtra("name",arrayList.get(position).getName());
//                    intent.putExtra("userid", arrayList.get(position).getUserid());
//                    context.startActivity(intent);
//                }
//            });
//            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/doubt-hub-fcb62.appspot.com/o/placeholder.png?alt=media&token=7444bc68-d648-431f-bd20-3e4c8de91015").into(image_view);
        }
    }
}
