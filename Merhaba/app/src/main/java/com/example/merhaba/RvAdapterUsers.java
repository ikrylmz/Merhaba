package com.example.merhaba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RvAdapterUsers extends RecyclerView.Adapter<RvAdapterUsers.TypesListHolder> {

    private Context context;
    private LayoutInflater inflater;
    static List<User> listData;
    private ClickListener mListener;

    public RvAdapterUsers(List<User> listData, ClickListener listener, Context context) {
        if (context == null)
            return;

        this.inflater = LayoutInflater.from(context);
        this.listData = listData;
        mListener = listener;
    }

    public interface ClickListener {
        void onItemClicked(User userData);
    }

    @Override
    public RvAdapterUsers.TypesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_user, parent, false);
        return new RvAdapterUsers.TypesListHolder(view);
    }

    @Override
    public void onBindViewHolder(RvAdapterUsers.TypesListHolder holder, final int position) {
        User item = listData.get(position);
        if (item != null) {
            holder.txt_name.setText(item.getAd());
            holder.txt_surname.setText(item.getSoyad());
            holder.txt_uid.setText(item.getUid());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = listData.get(position);
                mListener.onItemClicked(user);

            }
        });
    }

    public void setData(List<User> typeList) {
        listData = typeList;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class TypesListHolder extends RecyclerView.ViewHolder {

        private TextView txt_name;
        private TextView txt_surname;
        private TextView txt_uid;

        public TypesListHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.user_name_txt);
            txt_surname = (TextView) itemView.findViewById(R.id.user_surname_txt);
            txt_uid = (TextView) itemView.findViewById(R.id.uid_txt);
        }
    }
}




