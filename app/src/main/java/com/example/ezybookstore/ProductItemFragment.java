package com.example.ezybookstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProductItemFragment extends Fragment {
    Book b ;
    ImageView img ;
    TextView title,p,d;
    Singleton singleton = Singleton.getInstance();
    ArrayList<Book> orderedBooks = singleton.getOrderedBooks();
    ArrayList<Book> books = singleton.getBooks();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.product_detail, container, false);
        img = root.findViewById(R.id.imageView);
        title = root.findViewById(R.id.title_detail);
        p = root.findViewById(R.id.price_detail);
        d = root.findViewById(R.id.desc_detail);

        Bundle bundle = this.getArguments();
        String item_name = bundle.getString("item_name");
        String priceS = bundle.getString("price");
        String descS = bundle.getString("desc");
        String imgS = bundle.getString("img");
        Log.d("Test",item_name+priceS+descS);
        Picasso.get().load(imgS).into(img);
        title.setText(item_name);
        p.setText(priceS);
        d.setText(descS);
        Button next = root.findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(view);
                for(int i = 0 ;i<books.size();i++){
                    if(books.get(i).getName().equals(item_name)){
                        if(books.get(i).qty==0){
                            books.get(i).qty = 1;
                            orderedBooks.add(books.get((i)));
                        }else{
                            books.get(i).qty += 1;
                        }
                        break;
                    }
                }
                Fragment fragment = new CartFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fooFragment, fragment,"Test")
                        .addToBackStack("Test")
                        .commit();
            }


        });
        return root;
    }
    public void startService(View v){
        String input = "Service is running....";
        Intent service = new Intent(this.getContext(), MyService.class);
        service.putExtra("inputExtra",input);
        getActivity().startService(service);
    }

    public void stopService(View v){
        Intent serviceIntent = new Intent(this.getContext(),MyService.class);
        getActivity().stopService(serviceIntent);
    }

}