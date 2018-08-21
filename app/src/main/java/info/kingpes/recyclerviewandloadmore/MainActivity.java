package info.kingpes.recyclerviewandloadmore;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Object> List;
    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_Refresh);
        swipeRefresh.setOnRefreshListener(this);
        CreateSpec();
        LoadItem(10);
        LoadMore();
    }

    //Create News
    private void CreateSpec() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        List = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new RecyclerViewAdapter(MainActivity.this, List, recyclerView);
        recyclerView.setAdapter(adapter);


//        adapter = new DataAdapter(List, recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerView.setAdapter(adapter);
    }

    //Load More
    private void LoadMore(){
        adapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                List.add(null);
                adapter.notifyItemInserted(List.size() - 1);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List.remove(List.size() - 1);
                        adapter.notifyItemRemoved(List.size());
                        int start = List.size();
                        int end = start + 5;

                        for (int i = start + 1; i <= end; i++) {
                            List.add(new Object(1, "AndroidStudent" + i + "@gmail.com", 2));
                            //adapter.notifyItemInserted(List.size());
                        }
                        adapter.setLoaded();   //*******//
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void LoadItem(int item) {
        List.clear();
        for (int i = 0; i < item; i++) {
            List.add(new Object(1, "CardView + " + i, 2));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        Log.d("MainActivity_","onLoadMore");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(false);
                LoadItem(10);

            }
        },2000);
    }
}
