package com.example.singlepagerivew.Cart_CheckOut;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AddressRepository {
    private AddressDao addressDao;
    private LiveData<List<ShippingAddress>> allAddress;
    private ShippingAddress Address;

    public AddressRepository(Application application) {
        AddressDatabase addressDatabase = AddressDatabase.getInstance(application);
        addressDao = addressDatabase.AddressDao();
        allAddress = addressDao.getAllAddress();
    }

    public void insert(ShippingAddress address) {
        new AddressRepository.InsertAddressAsyncTask(addressDao).execute(address);
    }

    public void update(ShippingAddress address,Context ctx) {
        new AddressRepository.UpdateAddressAsyncTask(addressDao,ctx).execute(address);
    }

    public void delete(ShippingAddress address) {
        new AddressRepository.DeleteAddressAsyncTask(addressDao).execute(address);
    }

    public LiveData<List<ShippingAddress>> getAllAddresss() {
        return allAddress;
    }

    private static class InsertAddressAsyncTask extends AsyncTask<ShippingAddress, Void, Void> {
        private AddressDao AddressDao;

        private InsertAddressAsyncTask(AddressDao AddressDao) {
            this.AddressDao = AddressDao;
        }

        @Override
        protected Void doInBackground(ShippingAddress... addresses) {
            AddressDao.insert(addresses[0]);
            return null;
        }
    }


    private static class UpdateAddressAsyncTask extends AsyncTask<ShippingAddress, Void, Void> {
        private AddressDao AddressDao;
        private ProgressDialog dialog;
        private Context ctx;

        private UpdateAddressAsyncTask(AddressDao AddressDao,Context ctx) {
            this.AddressDao = AddressDao;
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(ctx);
            dialog.setMessage("Updating...");
            dialog.setIndeterminate(true);
            dialog.show();
        }


        @Override
        protected Void doInBackground(ShippingAddress... shippingAddresses) {
            AddressDao.update(shippingAddresses[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(ctx, "Updating .. " , Toast.LENGTH_SHORT).show();
        }
    }

    private static class DeleteAddressAsyncTask extends AsyncTask<ShippingAddress, Void, Void> {
        private AddressDao AddressDao;

        private DeleteAddressAsyncTask(AddressDao AddressDao) {
            this.AddressDao = AddressDao;
        }

        @Override
        protected Void doInBackground(ShippingAddress... addresses) {
            AddressDao.delete(addresses[0]);
            return null;
        }
    }

}
