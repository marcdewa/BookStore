package com.example.ezybookstore;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CartFragment extends Fragment {
    Singleton singleton = Singleton.getInstance();

    @BindView(R.id.listView)
    ListView myListView;

    BookAdapterList bookAdapter;
    Unbinder unbinder;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.cart_fragment, container, false);


        TextView subTotal = root.findViewById(R.id.txtSubTotal);
        TextView Taxes = root.findViewById(R.id.txtTaxes);
        TextView Total = root.findViewById(R.id.textTotalPrice);

        unbinder = ButterKnife.bind(this, root);
        bookAdapter = new BookAdapterList(this.getContext(),singleton.getOrderedBooks(),this);
        myListView.setAdapter(bookAdapter);

        singleton.countSubTotal();
        singleton.countTaxes();
        singleton.countTotal();

        Log.d("subTotal",""+singleton.subTotal);

        subTotal.setText("$"+singleton.subTotal);
        Taxes.setText("$"+singleton.Taxes);
        Total.setText("$"+singleton.Total);
        Button next = root.findViewById(R.id.button2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(singleton.isOrderEmpty()){
                    Toast.makeText(getActivity(),"Your cart is empty",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(),"Your order will be we process",Toast.LENGTH_LONG).show();
                }
                singleton.clearOrderedBooks();
                Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fooFragment);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    public void refresh(View root){
        TextView subTotal = root.findViewById(R.id.txtSubTotal);
        TextView Taxes = root.findViewById(R.id.txtTaxes);
        TextView Total = root.findViewById(R.id.textTotalPrice);

        subTotal.setText("$"+singleton.subTotal);
        Taxes.setText("$"+singleton.Taxes);
        Total.setText("$"+singleton.Total);
    }


}
