package sg.edu.np.mad.madpractical5;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{
    private ArrayList<User> list_objects;
    private ListActivity activity;
    public UserAdapter(ArrayList<User> list_objects, ListActivity activity){
        this.list_objects = list_objects;
        this.activity = activity;
    }
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }
    public void onBindViewHolder(UserViewHolder holder, int position){
        User list_items = list_objects.get(position);
        holder.name.setText(list_items.getName());
        holder.description.setText(list_items.getDescription());

        holder.bigImage.setVisibility(list_items.getName().endsWith("7") ? View.VISIBLE : View.GONE);
        holder.smallImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Profile");
                builder.setMessage(String.valueOf(holder.name.getText()));
                builder.setPositiveButton("View", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //send only id to main activity
                        Intent profile = new Intent(activity, MainActivity.class);
                        profile.putExtra("id", list_items.getId());
                        activity.startActivity(profile);
                    }
                });
                builder.setNegativeButton("Close", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public int getItemCount(){
        return list_objects.size();
    }
}
