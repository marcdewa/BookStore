package com.example.ezybookstore;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Singleton singleton = Singleton.getInstance();
    ArrayList<Book> books = singleton.getBooks();
    ArrayList<Book> allBooks = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.product_list, container, false);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi jsonApi = retrofit.create(JsonApi.class);

        Call<JsonObject> call = jsonApi.getObject();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("Test",response.body().getAsJsonArray("products").toString());
                JsonArray arr = response.body().getAsJsonArray("products");
                Gson gson = new Gson();
                if(books.size()==0){
                    for(int i =0;i<arr.size();i++){
                        JsonObject j = (JsonObject) arr.get(i);
                        Book b = gson.fromJson(j, Book.class);
                        Log.d("test",b.getCategory());
                        books.add(b);
                        allBooks.add(b);
                    }
                }
                Tile(root,inflater);
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TestFail",t.getMessage());
            }

        });

        Button business = root.findViewById(R.id.bBusiness);
        Button cook = root.findViewById(R.id.bCook);
        Button mystery = root.findViewById(R.id.bMystery);
        Button scifi = root.findViewById(R.id.bScifi);
        Button clear = root.findViewById(R.id.bClear);

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Book> books1 = new ArrayList<>();
                int i = 0 ;
                do{
                    if(allBooks.get(i).getCategory().equals("business")){
                        books1.add(allBooks.get(i));
                    }
                    i++;
                }while(i<allBooks.size());
                books = books1;
                refresh();
            }
        });
        cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Book> books1 = new ArrayList<>();
                int i = 0 ;
                do{
                    if(allBooks.get(i).getCategory().equals("cookbooks")){
                        books1.add(allBooks.get(i));
                    }
                    i++;
                }while(i<allBooks.size());
                books = books1;
                refresh();
            }
        });
        mystery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Book> books1 = new ArrayList<>();
                int i = 0 ;
                do{
                    if(allBooks.get(i).getCategory().equals("mystery")){
                        books1.add(allBooks.get(i));
                    }
                    i++;
                }while(i<allBooks.size());
                books = books1;
                refresh();
            }
        });
        scifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Book> books1 = new ArrayList<>();
                int i = 0 ;
                do{
                    if(allBooks.get(i).getCategory().equals("scifi")){
                        books1.add(allBooks.get(i));
                    }
                    i++;
                }while(i<allBooks.size());
                books = books1;
                refresh();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Book> books1 = new ArrayList<>();
                books = allBooks;
                refresh();
            }
        });

        return root;
    }

    public void refresh(){
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fooFragment);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }

    public void Tile(View root, LayoutInflater inflater){
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new BookAdapter(books);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Book b = books.get(position);
                String itemName = b.getName();
                String Price = "$"+b.getPrice();
                String Desc  = b.getDescription();

                Bundle bundle = new Bundle();
                bundle.putString("item_name",itemName);
                bundle.putString("price",Price);
                bundle.putString("desc",Desc);
                bundle.putString("img",b.getImg());

                Fragment fragment = new ProductItemFragment();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fooFragment, fragment,"Test")
                        .addToBackStack("Test")
                        .commit();
            }


        });

    }
}
