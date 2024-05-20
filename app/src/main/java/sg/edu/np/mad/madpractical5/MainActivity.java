package sg.edu.np.mad.madpractical5;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Start from Here!
        // Initialize a new User object
        DatabaseHandler dbHandler = new DatabaseHandler(this,null, null, 1);
        Intent myRecvIntent = getIntent();
        // get id
        int id = myRecvIntent.getIntExtra("id", 1);
        // get user record from database
        User user = dbHandler.getUser(id);
        // Get the TextViews and Button from the layout
        TextView tvName = findViewById(R.id.tvName);
        TextView tvDescription = findViewById(R.id.tvDescription);
        Button btnFollow = findViewById(R.id.btnFollow);
        Button btnMessage = findViewById(R.id.btnMessage);

        // Set the TextViews with the User's name, description and default button message
        tvName.setText(user.getName());
        tvDescription.setText(user.getDescription());

        if (user.getFollowed()) {
            btnFollow.setText("Unfollow");
        }
        else {
            btnFollow.setText("Follow");
        }

        // Set the OnClickListener for the follow button
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the followed state
                boolean followed = user.getFollowed();
                followed = !followed;
                user.setFollowed(followed);
                dbHandler.updateUser(user);

                // Update the button text based on the new state
                if (user.getFollowed()) {
                    btnFollow.setText("Unfollow");
                    Toast.makeText(getApplicationContext(),"Followed",Toast.LENGTH_SHORT).show();
                }
                else {
                    btnFollow.setText("Follow");
                    Toast.makeText(getApplicationContext(),"Unfollowed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}