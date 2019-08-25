package com.example.singlepagerivew.Cart_CheckOut;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.singlepagerivew.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Manage_Adress.onManageAddressFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Manage_Adress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Manage_Adress extends Fragment implements AddressAdapter.OnAddressClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout ll;
    private List<ShippingAddress> addressesList;

    private onManageAddressFragmentInteractionListener mListener;

    public Manage_Adress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Manage_Adress.
     */
    // TODO: Rename and change types and number of parameters
    public static Manage_Adress newInstance(String param1, String param2) {
        Manage_Adress fragment = new Manage_Adress();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.address_lists_xml, container, false);
        RecyclerView addRecyclerView = view.findViewById(R.id.addressListRc);
        Button btn = view.findViewById(R.id.addAddressBtn);

        TextView tv = view.findViewById(R.id.toolbarHeader);
        tv.setText("Manage Address");

        ll = view.findViewById(R.id.addressListPlaceholder);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ManageAddressLists)getActivity()).changeFragment(false,null);
            }
        });

        final AddressAdapter addressAdapter = new AddressAdapter(getActivity(),this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        addRecyclerView.setLayoutManager(mLayoutManager);
        addRecyclerView.setItemAnimator(new DefaultItemAnimator());
        addRecyclerView.setHasFixedSize(true);
        addRecyclerView.setAdapter(addressAdapter);
        ((ManageAddressLists)getActivity()).getView(addressAdapter,ll);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onManageAddressFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onManageAddressFragmentInteractionListener) {
            mListener = (onManageAddressFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onManageAddressFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMainAddressClick() {
//        SharedPreferences addressPrefs = getActivity().getSharedPreferences("AddressPrefs", getActivity().MODE_PRIVATE);
//        Toast.makeText(getActivity(), addressPrefs.getString("userAddress","null"), Toast.LENGTH_SHORT).show();
        ((ManageAddressLists)getActivity()).changeActivity();
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

    public interface onManageAddressFragmentInteractionListener {
        // TODO: Update argument type and name
        void onManageAddressFragmentInteraction(Uri uri);
    }
}
