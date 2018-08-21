package info.kingpes.recyclerviewandloadmore;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Chau Huynh on 03/12/02016.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Object> SpecList;

    //Loaad More
    private RecyclerView recyclerView;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    ////* Interface LoadMore/
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private OnLoadMoreListener onLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    ////* Interface LoadMore/

    public RecyclerViewAdapter(Context mContext, List<Object> specList, RecyclerView recyclerView) {
        this.mContext = mContext;
        SpecList = specList;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void setLoaded() {        //*******//
        loading = false;
    }

    @Override
    public int getItemViewType(int position) {
        return SpecList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false));
        } else {
            return new MyProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            final Object o = SpecList.get(position);
            ((MyViewHolder) holder).tvTitle.setText(o.getName());
        }else{
            ((MyProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return SpecList.size();
    }

    //* Interface OnClick/
    //Click
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);  // du lieu truyen ra ngoai
    }

    private static OnItemClickListener listener;       //khai bao contructor
    //khai bao them du lieu nhan vao sau do khai bao contructor giong nhu Postdata app Onehealth

    public void setOnItemClickListener(OnItemClickListener listener) { //khai bao contructor
        this.listener = listener;
    }
    //* Interface OnClick/


    //Create ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        private ImageView imvImage;
        private CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.textView_spec_title);
            imvImage = (ImageView) view.findViewById(R.id.imageView_spec_image);
            cardView = (CardView) view.findViewById(R.id.card_view);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

    //Create Progrss
    public static class MyProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public MyProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }
}

