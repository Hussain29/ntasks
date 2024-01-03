package com.example.ntasks;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonAddTask;
    private Button buttonViewTasks;
    private Button buttonAssignedTasks;
    private Button buttonLogout; // Add this line
    private Button buttonAddClient;
    private ImageView imagelogo;
    private Button buttonPersonal;// Add this line
    LinearLayout card1;
    private Button buttonAllTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();

        }
        auth = FirebaseAuth.getInstance();
        // Get the ActionBar

        buttonLogin = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
        buttonAddTask = findViewById(R.id.button_addtask);
        buttonViewTasks = findViewById(R.id.button_viewtasks);
        buttonLogout = findViewById(R.id.button_logout); // Initialize the button
        buttonAssignedTasks = findViewById(R.id.button_asndby);
        buttonAddClient = findViewById(R.id.button_addclient); // Initialize the button
        buttonAllTasks = findViewById(R.id.buttonShowAllTasks);
        buttonPersonal = findViewById(R.id.buttonPersonalTasks);
        card1 = findViewById(R.id.card);
        imagelogo=findViewById(R.id.imageView_logo);



        imagelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent8=new Intent(MainActivity.this,Master.class);
                startActivity(intent8);
            }
        });


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "1234567",    // Choose a unique channel ID
                    "Task Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Channel for task notifications");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Register.class);
                startActivity(intent1);
            }
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is logged in before allowing access
                if (isUserLoggedIn()) {
                    Intent intent3 = new Intent(MainActivity.this, AddTaskActivity.class);
                    startActivity(intent3);
                } else {
                    showToast("Please log in to add a task");
                }
            }
        });

        buttonViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is logged in before allowing access
                if (isUserLoggedIn()) {
                    Intent intent4 = new Intent(MainActivity.this, taskviewlist.class);
                    startActivity(intent4);
                } else {
                    showToast("Please log in to view tasks");
                }
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log out the user
                auth.signOut();
                // Update UI to show login and register buttons
                updateUI(null);
                showToast("Logged out successfully");
            }
        });
        buttonAssignedTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyAssignedTasksActivity.class);
                startActivity(intent);
            }
        });

        buttonAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the AddClientActivity when the "Add Client" button is clicked
                Intent intent = new Intent(MainActivity.this, AddClientActivity.class);
                startActivity(intent);
            }
        });

        buttonAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the AddClientActivity when the "Add Client" button is clicked
                Intent intent = new Intent(MainActivity.this, AllTasksActivity.class);
                startActivity(intent);
            }
        });

        buttonPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the AddClientActivity when the "Add Client" button is clicked
                Intent intent = new Intent(MainActivity.this, PersonalTasksActivity.class);
                startActivity(intent);
            }
        });

        // Hide buttons if the user is logged in
        updateUI(auth.getCurrentUser());
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Update UI when the activity is resumed
        updateUI(auth.getCurrentUser());
    }

    private boolean isUserLoggedIn() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user) {
        // If the user is logged in, hide the login and register buttons
        if (user != null) {
            buttonLogin.setVisibility(View.GONE);
            buttonRegister.setVisibility(View.GONE);
            buttonAddTask.setVisibility(View.VISIBLE);
            buttonViewTasks.setVisibility(View.VISIBLE);
            card1.setVisibility(View.VISIBLE);

            buttonAddClient.setVisibility(View.VISIBLE);
            buttonLogout.setVisibility(View.VISIBLE);
            buttonAssignedTasks.setVisibility(View.VISIBLE);
            buttonAllTasks.setVisibility(View.VISIBLE);// Show the logout button
            buttonPersonal.setVisibility(View.VISIBLE);// Show the logout button
        } else {
            buttonLogin.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);
            card1.setVisibility(View.GONE);
            buttonAddTask.setVisibility(View.GONE);
            buttonViewTasks.setVisibility(View.GONE);
            buttonAddClient.setVisibility(View.GONE);
            buttonLogout.setVisibility(View.GONE);
            buttonAllTasks.setVisibility(View.GONE);
            buttonAssignedTasks.setVisibility(View.GONE);
            buttonPersonal.setVisibility(View.GONE);// Hide the logout button
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle the home button click
                Intent intent = new Intent(this, Master.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


/*
package com.example.ntasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonAddTask;
    private Button buttonViewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        buttonLogin = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
        buttonAddTask = findViewById(R.id.button_addtask);
        buttonViewTasks = findViewById(R.id.button_viewtasks);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Register.class);
                startActivity(intent1);
            }
        });

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is logged in before allowing access
                if (isUserLoggedIn()) {
                    Intent intent3 = new Intent(MainActivity.this, AddTaskActivity.class);
                    startActivity(intent3);
                } else {
                    showToast("Please log in to add a task");
                }
            }
        });

        buttonViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is logged in before allowing access
                if (isUserLoggedIn()) {
                    Intent intent4 = new Intent(MainActivity.this, taskviewlist.class);
                    startActivity(intent4);
                } else {
                    showToast("Please log in to view tasks");
                }
            }
        });

        // Hide buttons if the user is logged in
        updateUI(auth.getCurrentUser());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update UI when the activity is resumed
        updateUI(auth.getCurrentUser());
    }

    private boolean isUserLoggedIn() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateUI(FirebaseUser user) {
        // If the user is logged in, hide the login and register buttons
        if (user != null) {
            buttonLogin.setVisibility(View.GONE);
            buttonRegister.setVisibility(View.GONE);
        } else {
            buttonLogin.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.VISIBLE);
        }
    }
}
*/


/*
package com.example.ntasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
*/
/*
       getSupportActionBar().setTitle("Ntasks App");
*//*


        Button buttonlogin=findViewById(R.id.button_login);
        Button buttonregister=findViewById(R.id.button_register);
        Button buttonaddtask=findViewById(R.id.button_addtask);
        Button buttonviewtask=findViewById(R.id.button_viewtasks);



     buttonlogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(MainActivity.this, LoginActivity.class);
             startActivity(intent);
         }
     });

     buttonregister.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent1=new Intent(MainActivity.this, Register.class);
             startActivity(intent1);
         }
     });
           buttonaddtask.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent3 =new Intent(MainActivity.this, AddTaskActivity.class);
                   startActivity(intent3);
               }
           });
           buttonviewtask.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent4 =new Intent(MainActivity.this, taskviewlist.class);
                   startActivity(intent4);

               }
           });
    }
}*/
