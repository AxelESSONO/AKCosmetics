package com.axel.akcosmetics.Fragment;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.axel.akcosmetics.Buyers.HomeActivity;
import com.axel.akcosmetics.Prevalent.Prevalent;
import com.axel.akcosmetics.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShippingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShippingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShippingFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private String totalAmount = "";





    private EditText nameEditText, phoneEditText, cityEditText, quarterEditText;
    private Button confirmOrderBtn;



    public ShippingFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShippingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShippingFragment newInstance(String param1, String param2)
    {
        ShippingFragment fragment = new ShippingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            Bundle bundle3 = getArguments();
            totalAmount = bundle3.getString("totalPrice");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_shipping, container, false);

        final String receivePrice = totalAmount;

        //Toast.makeText(getContext(), "J'affiche le prix total: " + receivePrice, Toast.LENGTH_SHORT).show();

        confirmOrderBtn = (Button) view.findViewById(R.id.shipping_confirm_final_order_btn);
        nameEditText = (EditText) view.findViewById(R.id.shipping_shipment_name);
        phoneEditText = (EditText) view.findViewById(R.id.shipping_shipment_phone_number);
        cityEditText = (EditText) view.findViewById(R.id.shipping_shipment_city);
        quarterEditText = (EditText) view.findViewById(R.id.shipping_shipment_quarter);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check();
                TabActivity tabs = new TabActivity();
                tabs.getTabHost().setCurrentTab(2);
            }
        });

               /** FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment ffrag = new PaymentFragment();
                fragmentTransaction.replace(R.id.container, ffrag);
                fragmentTransaction.commit(); **/

        // Inflate the layout for this fragment
        return view;
    }

    private void check()
    {


        if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(getContext(), "Veuillez saisir votre nom.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(getContext(), "Veuillez saisir votre numéro de téléphone.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(getContext(), "Veuillez saisir le nom de votre ville de résidence.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(quarterEditText.getText().toString()))
        {
            Toast.makeText(getContext(), "Veuillez saisir le nom de votre quartier.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            confirmOrder();
        }



    }

    private void confirmOrder()
    {

        // get date and time
        final String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM yyyy ");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat(" HH : mm : ss");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        String newChild = "Orders"+" "+saveCurrentDate+ " " + saveCurrentTime;

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnLineUser.getPhone());
        final HashMap<String, Object> ordersMap = new HashMap<>();

        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("City", cityEditText.getText().toString());
        ordersMap.put("quarter", quarterEditText.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "Non livré");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnLineUser.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getContext(), "Votre commande finale est passée avec succès", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        //finish();

                                    }
                                }
                            });
                }
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
