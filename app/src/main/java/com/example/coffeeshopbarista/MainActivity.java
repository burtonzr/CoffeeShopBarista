package com.example.coffeeshopbarista;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static RecyclerView.Adapter sAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView sRecyclerView;
    private static ArrayList<DataModel> data;
    SQLiteDatabase db;

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.coffeeapp.Provider/Orders");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        sRecyclerView = findViewById(R.id.recycler_view_id);
        mLayoutManager = new LinearLayoutManager(this);
        sRecyclerView.setLayoutManager(mLayoutManager);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        String[] projections = new String[] {"CoffeeName", "CoffeePrice", "MilkType", "Size", "Quantity", "OrderID", "Status", "UserID"};
        CursorLoader cursorLoader = new CursorLoader(this, CONTENT_URI, projections, "Status = 1", null, "ID DESC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        data = new ArrayList<>();

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            DataModel item = new DataModel(cursor.getString(cursor.getColumnIndex("CoffeeName")),
                    cursor.getString(cursor.getColumnIndex("CoffeePrice")),
                    cursor.getString(cursor.getColumnIndex("MilkType")),
                    cursor.getString(cursor.getColumnIndex("Size")),
                    cursor.getInt(cursor.getColumnIndex("Quantity")),
                    cursor.getInt(cursor.getColumnIndex("OrderID")),
                    cursor.getString(cursor.getColumnIndex("Status")),
                    cursor.getString(cursor.getColumnIndex("UserID")));
            data.add(item);
            cursor.moveToNext();
        }

        sAdapter = new Adapter(data, this);
        sRecyclerView.setAdapter(sAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class Holder extends RecyclerView.ViewHolder {

        private DataModel mDataModel;
        private TextView CartItemName;
        private TextView CartItemPrice;
        private TextView CartItemMilk;
        private TextView CartItemSize;
        private TextView CartOrderID;
        private TextView CartUserName;
        private ImageView CartItemCount;


        public void bind(DataModel data) {
            mDataModel = data;
            CartOrderID.setText("Order #: " + mDataModel.getOrderID());
            CartItemName.setText(mDataModel.getCoffeeName());
            CartItemPrice.setText(mDataModel.getCoffeePrice());
            CartItemMilk.setText(mDataModel.getMilkType());
            CartItemSize.setText(mDataModel.getSize());
            CartUserName.setText(mDataModel.getUserID());
        }

        public Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.activity_main, parent, false));
            CartOrderID    = itemView.findViewById(R.id.cart_order_id);
            CartItemName   = itemView.findViewById(R.id.cart_item_name);
            CartItemPrice  = itemView.findViewById(R.id.cart_item_price);
            CartItemMilk   = itemView.findViewById(R.id.cart_item_milk);
            CartItemSize   = itemView.findViewById(R.id.cart_item_size);
            CartItemCount  = itemView.findViewById(R.id.cart_item_quantity);
            CartUserName   = itemView.findViewById(R.id.user_name);
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {

        public List<DataModel> mDataModel;
        public Context mContext;

        public Adapter(List<DataModel> data, Context context) {
            mDataModel = data;
            this.mContext = context;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getBaseContext());

            return new Holder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            DataModel cartData = data.get(position);
            TextDrawable drawable = TextDrawable.builder().buildRound("" + mDataModel.get(position).getQuantity(), Color.RED);
            holder.CartItemCount.setImageDrawable(drawable);
            holder.bind(cartData);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
