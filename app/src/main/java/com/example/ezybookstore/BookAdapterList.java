package com.example.ezybookstore;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapterList extends ArrayAdapter {

    Context context;
    ArrayList<Book> books;
    String title;
    String desc;
    int price;
    int qty;
    String img;
    Singleton singleton = Singleton.getInstance();
    Fragment f ;

    public BookAdapterList(Context context,ArrayList<Book> books,Fragment f) {
        super(context,android.R.layout.simple_expandable_list_item_1, books);
        this.context = context;
        this.books = books;
        this.f = f.getActivity().getSupportFragmentManager().findFragmentById(R.id.fooFragment);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Book book = books.get(position);
        title = book.getName();
        desc = book.getDescription();
        price = book.getPrice();
        qty = book.qty;
        img = book.getImg();

        convertView = LayoutInflater.from(context).inflate(R.layout.order_list, parent, false);
        ReceiptViewHolder view = new ReceiptViewHolder(convertView);

        Picasso.get().load(img).into(view.imgValue);
        view.titleValue.setText(title);
        view.descValue.setText(desc);
        view.priceValue.setText("$"+price);
        view.qtyValue1.setText("x "+qty);
        view.qtyValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 1000; // Milliseconds

            @Override
            public void afterTextChanged(Editable editable) {
                String qtyString = editable.toString();
                int qtyInt = Integer.parseInt(qtyString);
                if(qtyString.length()>0){
                    book.qty = qtyInt;
                    singleton.subTotal = 0;
                    singleton.Taxes = 0;
                    singleton.Total = 0;
                    singleton.countSubTotal();
                    singleton.countTaxes();
                    singleton.countTotal();

                }
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                FragmentTransaction fragmentTransaction = f.getParentFragmentManager().beginTransaction();
                                fragmentTransaction.detach(f);
                                fragmentTransaction.attach(f);
                                fragmentTransaction.commit();
                            }
                        },
                        DELAY);
            }
        });

//        view.subTotalValue.setText("$"+singleton.getSubTotal());
//        view.TaxesValue.setText("$"+singleton.getTaxes());
//        view.TaxesValue.setText("$"+singleton.getTotal());
        convertView.setTag(position);
        return convertView;
    }


    static class ReceiptViewHolder {

        @BindView(R.id.txtQty)
        TextView qtyValue1;

        @BindView(R.id.imageView2)
        ImageView imgValue;

        @BindView(R.id.title)
        TextView titleValue;

        @BindView(R.id.desc)
        TextView descValue;

        @BindView(R.id.price_order)
        TextView priceValue;

        @BindView(R.id.qty)
        EditText qtyValue;


        ReceiptViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
