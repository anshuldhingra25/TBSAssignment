package in.tbsassignment.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.tbsassignment.R;
import in.tbsassignment.retrofit.ApiClient;
import in.tbsassignment.retrofit.ApiInterface;
import in.tbsassignment.adapter.CustomRecyclerview;
import in.tbsassignment.room.DatabaseClient;
import in.tbsassignment.room.MessageTable;
import in.tbsassignment.room.Message;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    List<Message> messages;
    private RecyclerView recyclerview;
    private ArrayList<Message> arrayList;
    private CustomRecyclerview adapter;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if (isNetworkAvailable(MainActivity.this) && arrayList != null) {
            fetchFromServer();
        } else {
            fetchfromRoom();
        }
    }

    private void initViews() {
        pb = findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        recyclerview = findViewById(R.id.recyclerview);
        arrayList = new ArrayList<>();
        adapter = new CustomRecyclerview(this, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(adapter);
    }


    private void fetchfromRoom() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<MessageTable> recipeList = DatabaseClient.getInstance(MainActivity.this).getAppDatabase().messageDao().getAll();
                    arrayList.clear();
                    for (MessageTable messageTable : recipeList) {
                        Message message = new Message(messageTable.getId(), messageTable.getSubject(),
                                messageTable.getMessage(),
                                messageTable.getPicture(),
                                messageTable.getTimestamp());
                        arrayList.add(message);
                    }
                    // refreshing recycler view
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            });
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchFromServer() {
        try {
            pb.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<List<Message>> call = apiService.getInboxList();
            call.enqueue(new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, retrofit2.Response<List<Message>> response) {
                    if (response == null) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    arrayList.clear();
                    messages = response.body();
                    arrayList.addAll(messages);

                    // refreshing recycler view
                    adapter.notifyDataSetChanged();
                    pb.setVisibility(View.GONE);

                    saveTask();
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void saveTask() {
        try {
            class SaveTask extends AsyncTask<Void, Void, Void> {

                @Override
                protected Void doInBackground(Void... voids) {
                    //creating a task
                    for (int i = 0; i < messages.size(); i++) {
                        MessageTable messageTable = new MessageTable();
                        messageTable.setSubject(messages.get(i).getSubject());
                        messageTable.setMessage(messages.get(i).getMessage());
                        messageTable.setPicture(messages.get(i).getPicture());
                        messageTable.setTimestamp(messages.get(i).getTimestamp());
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().messageDao().insert(messageTable);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                }
            }
            SaveTask st = new SaveTask();
            st.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}