package com.example.singlepagerivew.Cart_CheckOut;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Cart.class}, version = 1, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    private static CartDatabase instance;

    public abstract CartDao cartDao();

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    CartDatabase.class, "cart_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CartDao cartDao;

        private PopulateDbAsyncTask(CartDatabase db) {
            cartDao = db.cartDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cartDao.insert(new Cart(1,"Floor Mat",  "JRPCMWDGGX-M", "https://www.jadroo.com/uploads/media/old/images/products/products-4014774912_1753305594.jpg", 1, 300, 2, "size : 50-80cm-2",true));
            cartDao.insert(new Cart(10,"Taco Holder Wave Shape Kitchen Food Pizza Shell Restaurant Baking Tool Stand Rack Display Pastry Stainless Steel Mexican Pancake(Silver)", "0123","https://www.jadroo.com/uploads/media/2019/06/TIoVh2ojUV/zhutu-medium.jpg",2 , 400, 5, null,true));
            cartDao.insert(new Cart(25,"Casual Non-Slip Males Slippers", "XMA012-R-44", "https://www.jadroo.com/uploads/media/old/2018/12/%E4%B8%BB%E5%9B%BE-%E5%B0%8F%E8%B4%9D%E9%BB%91%E8%89%B2.jpg", 2, 580, 2, "color: red-2,size: 44", true ));
            cartDao.insert(new Cart(2,"32\" JadRoo Smart LED TV",  "JRT-LEDS32", "https://www.jadroo.com/uploads/media/2019/07/7nokVRHVH1/32%20Inch%20LED%20Smart%20TV%20Front%20Side-medium.jpg", 1, 35000, 15, null,true));
            cartDao.insert(new Cart(11,"Colorful Traveling Rechargeable Mini Fan", "JRJM-LD209-P","https://www.jadroo.com/uploads/media/old/images/products/products-D_3_c9abb8be-b21a-4941-99a3-729b7e175bef.jpg",2 , 770, 7, "color : purple-3",true));
            return null;
        }
    }
}