package com.example.singlepagerivew.Cart_CheckOut;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.singlepagerivew.R;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditAddress.OnAddEditAddressFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditAddress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditAddress extends Fragment implements SubAddressAdapter.OnSubAddressClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText name, email, phone, dist, address, place, division;
    private CheckBox defaultAddress, createAccount;
    private Button saveAddress;
    List<String> subEntries;
    Dialog recycleDialog;
    int type=1;

    private OnAddEditAddressFragmentInteractionListener mListener;

    public AddEditAddress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEditAddress.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditAddress newInstance(String param1, String param2) {
        AddEditAddress fragment = new AddEditAddress();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_edit_address, container, false);
        name = view.findViewById(R.id.editName);
        email = view.findViewById(R.id.editEmail);
        phone = view.findViewById(R.id.editContact);

        dist = view.findViewById(R.id.distEt);
        division = view.findViewById(R.id.diviEt);
        place = view.findViewById(R.id.placeEt);
        address = view.findViewById(R.id.addressEt);
        defaultAddress = view.findViewById(R.id.defaultAddressChckBx);
        createAccount = view.findViewById(R.id.createAccountChckBx);

        saveAddress = view.findViewById(R.id.saveAddress);

        division.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                subEntries = Arrays.asList(new String[]{"Dhaka", "Chittagong", "Khulna", "Rajshahi", "Sylhet", "Barishal", "Mymensingh", "Rangpur"});
                type = 1;
                if (division.getText().toString().length() > 0) {
                    showDialogBox(subEntries, division.getText().toString());
                } else {
                    showDialogBox(subEntries, "none");
                }

            }

        });


        //if(division.getText().toString().length()>0) {

        dist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = 2;

                if (division.getText().toString().length() > 0) {
                    Toast.makeText(getActivity(), division.getText().toString(), Toast.LENGTH_SHORT).show();

                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Pleas Wait..");
                    dialog.setIndeterminate(true);
                    dialog.show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://accsectiondemo.site11.com/null/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    if (division.getText().toString().equals("Dhaka")) {
                        retrofit = new Retrofit.Builder()
                                .baseUrl("http://accsectiondemo.site11.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                    }

                    if (division.getText().toString().equals("Barishal")) {
                        retrofit = new Retrofit.Builder()
                                .baseUrl("http://accsectiondemo.site11.com/barishal.json/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                    }


                    AddressPlaceHolderAPI jsonPlaceHolderApi = retrofit.create(AddressPlaceHolderAPI.class);

                    Call<SubAddress> call = jsonPlaceHolderApi.getSubAddress();

                    call.enqueue(new Callback<SubAddress>() {

                        @Override
                        public void onResponse(Call<SubAddress> call, Response<SubAddress> response) {

                            if (!response.isSuccessful()) {

                            }

                            else {
                                SubAddress subAddress = response.body();
                                if (subAddress.getParent().size() > 0) {

                                    dialog.dismiss();

                                    if (dist.getText().toString().length() > 0) {
                                        showDialogBox(subAddress.getParent(), dist.getText().toString());
                                    } else {
                                        showDialogBox(subAddress.getParent(), "none");
                                    }

                                }
                            }




                        }

                        @Override
                        public void onFailure(Call<SubAddress> call, Throwable t) {

                        }
                    });
                }
            }

        });


        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dist.getText().toString().length() > 0) {

                    type = 3;

                    Toast.makeText(getActivity(), dist.getText().toString(), Toast.LENGTH_SHORT).show();

                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Pleas Wait..");
                    dialog.setIndeterminate(true);
                    dialog.show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://accsectiondemo.site11.com/null/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    if (dist.getText().toString().equals("Dhaka")) {
                        retrofit = new Retrofit.Builder()
                                .baseUrl("http://accsectiondemo.site11.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                    }


                    AddressPlaceHolderAPIV2 jsonPlaceHolderApi = retrofit.create(AddressPlaceHolderAPIV2.class);

                    Call<SubAddress> call = jsonPlaceHolderApi.getSubAddress();


                    call.enqueue(new Callback<SubAddress>() {
                        @Override
                        public void onResponse(Call<SubAddress> call, Response<SubAddress> response) {

                            if (!response.isSuccessful()) {
                                dialog.dismiss();
                            }

                            else {
                                SubAddress subAddress = response.body();
                                if (subAddress.getParent().size() > 0) {

                                    dialog.dismiss();

                                    if (place.getText().toString().length() > 0) {
                                        showDialogBox(subAddress.getParent(), place.getText().toString());
                                    } else {
                                        showDialogBox(subAddress.getParent(), "none");
                                    }

                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<SubAddress> call, Throwable t) {

                        }
                    });
                }
            }
        });


        if (!getArguments().getString("userName").equals("Blank")) {
            name.setText(getArguments().getString("userName"));
            division.setText(getArguments().getString("userDiv"));
            email.setText(getArguments().getString("userEmail"));
            phone.setText(getArguments().getString("userContact"));
            dist.setText(getArguments().getString("userDist"));
            place.setText(getArguments().getString("userPlace"));
            address.setText(getArguments().getString("userAddress"));
        }

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() > 0 && phone.getText().toString().length() > 0 && dist.getText().toString().length() > 0
                        && place.getText().toString().length() > 0 && address.getText().toString().length() > 0) {


                    ShippingAddress shippingAddress = new ShippingAddress(name.getText().toString(), phone.getText().toString(),
                            email.getText().toString(), division.getText().toString(), dist.getText().toString(), place.getText().toString(), address.getText().toString());

                    ((ManageAddressLists) getActivity()).addAddress(shippingAddress);

                } else {
                    Toast.makeText(getActivity(), "Please fill up required Field", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void showDialogBox(List<String> list, String checked) {
        subEntries = list;
        recycleDialog = new Dialog(getActivity());
        recycleDialog.setContentView(R.layout.subaddress_listview);
        RecyclerView subAddressRecyclerView = recycleDialog.findViewById(R.id.subaddressRecycler);
        SubAddressAdapter subAddressAdapter = new SubAddressAdapter(list, this, checked);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        subAddressRecyclerView.setLayoutManager(mLayoutManager);
        subAddressRecyclerView.setItemAnimator(new DefaultItemAnimator());
        subAddressRecyclerView.setAdapter(subAddressAdapter);
        recycleDialog.show();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAddEditAddressFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddEditAddressFragmentInteractionListener) {
            mListener = (OnAddEditAddressFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddEditAddressFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAddressClick(int positon) {
        recycleDialog.dismiss();
        if (type==1)
            division.setText(subEntries.get(positon));


        else if (type==2)
            dist.setText(subEntries.get(positon));


        else if (type==3)
            place.setText(subEntries.get(positon));

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddEditAddressFragmentInteractionListener {
        // TODO: Update argument type and name
        void onAddEditAddressFragmentInteraction(Uri uri);
    }
}
