package sg.edu.np.mad.madpractical5;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView description;
    ImageView smallImage;
    ImageView bigImage;

    public UserViewHolder(View itemView){
        super(itemView);
        name = itemView.findViewById(R.id.tvName);
        description = itemView.findViewById(R.id.tvDescription);
        smallImage = itemView.findViewById(R.id.ivSmallImage);
        bigImage = itemView.findViewById(R.id.ivBigImage);
    }
}
