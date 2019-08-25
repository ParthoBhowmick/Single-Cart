package com.example.singlepagerivew.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.singlepagerivew.Activites.SingleProductPage;
import com.example.singlepagerivew.Adapter.DescriptionTabAdapter;
import com.example.singlepagerivew.Adapter.RecycleAdapter;
import com.example.singlepagerivew.Adapter.RecycleAdapterNoImg;
import com.example.singlepagerivew.Adapter.ViewPagerAdapter;
import com.example.singlepagerivew.Cart_CheckOut.Cart;
import com.example.singlepagerivew.JavaClases.WrappingViewPager;
import com.example.singlepagerivew.Models.Option;
import com.example.singlepagerivew.Models.Product_Pojo;
import com.example.singlepagerivew.Models.Gallery;
import com.example.singlepagerivew.Models.Products;
import com.example.singlepagerivew.Models.Sku;
import com.example.singlepagerivew.R;
import com.example.singlepagerivew.RetrofitPageHolder.SingleProductPlaceHolder;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingleProductFragment extends Fragment implements RecycleAdapter.OnOptionClickListener, RecycleAdapterNoImg.OnOptionClickListenerNoImg {

    //Initialization Part
    private TextView product_nameTv, product_priceTv, product_skuTv, stockAvailbilityTv, rating_point,  discount_percentTv, sell_priceTv;
    private ViewPager viewPager;

    private ImageView gotoCart;

    private ImageView plusImgV, minusImgV, rightNav, leftNav,sharing;
    private EditText product_quantEt;
    private String currencySymbol;
    private List<ImageView> recycleImgList = new ArrayList<>();
    private ArrayList<String> defVal = new ArrayList<>();
    private LinearLayout ll, llhidden, horzntlLayout;
    private HorizontalScrollView scrlView;
    private Boolean flag = true;
    private TabLayout tabLayout;
    private WrappingViewPager wrpViewPager;
    private String description;
    private Button cartBtn;

    //for model class
    private float price = 0;
    private int stocks = 0;
    private int id = 0;
    private String imgLink = "", cart_Sku = "", cart_variants = "", general = "";


    private HashMap<String, List<String>> variants = new HashMap<>();
    // I use linkedHashmap because i need to know the insertion order.
    private LinkedHashMap<String, Integer> featuredImages = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> imageSource = new LinkedHashMap<>();
    private OnSingleProductFragmentInteractionListener mListener;

    public SingleProductFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_single_product, container, false);

        //initialization part

        product_skuTv = view.findViewById(R.id.single_product_sku);

        rightNav = view.findViewById(R.id.right_nav);
        leftNav = view.findViewById(R.id.left_nav);

        gotoCart = view.findViewById(R.id.cartBtnView);

        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SingleProductPage)getActivity()).viewCart();
            }
        });

        String featuredImg = "";

        discount_percentTv = view.findViewById(R.id.discount_percent);
        sell_priceTv = view.findViewById(R.id.offer);

        product_nameTv = view.findViewById(R.id.detailed_product_name);
        viewPager = view.findViewById(R.id.pager);
        product_priceTv = view.findViewById(R.id.detailed_product_price);
        rating_point = view.findViewById(R.id.detailed_rating_point);
        stockAvailbilityTv = view.findViewById(R.id.stockAvail);

        sharing = view.findViewById(R.id.sharing);

        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SingleProductPage)getActivity()).openShare();
            }
        });


        horzntlLayout = view.findViewById(R.id.imageScrollContainer);
        scrlView = view.findViewById(R.id.scroller);

        cartBtn = view.findViewById(R.id.addtocart);

        tabLayout = view.findViewById(R.id.tablayout);
        wrpViewPager = view.findViewById(R.id.wrapViewPager);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        plusImgV = view.findViewById(R.id.increment);
        minusImgV = view.findViewById(R.id.decrement);
        product_quantEt = view.findViewById(R.id.product_quantity_et);

        plusImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = product_quantEt.getText().toString();
                int total = Integer.parseInt(val);
                total = total + 1;
                if (total > stocks && stocks > 0) {
                    total = stocks;
                    Toast.makeText(getActivity(), "You can't add more than " + stocks + " quantity", Toast.LENGTH_LONG).show();
                }
                if (stocks < 1) total = 0;
                product_quantEt.setText(total + "");
            }
        });

        minusImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = product_quantEt.getText().toString();
                int total = Integer.parseInt(val);
                if(stocks<1) total = 0;
                else {
                    if (total >= stocks) {
                        total = 1;
                    } else {
                        total = total - 1;
                        if (total < 1 && stocks > 0) total = 1;
                    }
                }
                product_quantEt.setText(total + "");
            }
        });



        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Pleas Wait..");
        dialog.setIndeterminate(true);
        dialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://accsectiondemo.site11.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SingleProductPlaceHolder singleProductPlaceHolder = retrofit.create(SingleProductPlaceHolder.class);
        Call<Product_Pojo> call = singleProductPlaceHolder.getPosts();
        call.enqueue(new Callback<Product_Pojo>() {
            @Override
            public void onResponse(Call<Product_Pojo> call, Response<Product_Pojo> response) {

                if (!response.isSuccessful()) {

                    return;
                } else {
                    Products products = response.body().getProducts();
                    id = products.getProductId();
                    sharing.setVisibility(View.VISIBLE);
                    product_nameTv.setText(products.getName());
                    currencySymbol = response.body().getCurrencySymbole();
                    description = products.getDescription();

                    String mainURL = products.getUrl();
                    List<Sku> skuList = products.getSku();


                    Fragment fragment = new ProductDescriptionFragment();
                    Bundle bundle = new Bundle();

                    bundle.putString("html", description);
                    fragment.setArguments(bundle);
                    DescriptionTabAdapter descTabAdapter = new DescriptionTabAdapter(getActivity().getSupportFragmentManager());
                    descTabAdapter.addFragment(fragment, "Description");
                    descTabAdapter.addFragment(new ProductSpecificationFragment(), "Specifications");
                    descTabAdapter.addFragment(new ProductReviewFragment(), "Reviews");
                    descTabAdapter.addFragment(new ProductReviewFragment(), "Similar Product");

                    wrpViewPager.setAdapter(descTabAdapter);
                    tabLayout.setupWithViewPager(wrpViewPager);

                    //push featured image one by one with insertion order (controlled by count) in linked hash map
                    int count = 0;

                    if (!products.getFileName().equals(null)) {
                        featuredImages.put(products.getFileName(), count);
                        imgLink = mainURL + products.getFileName();
                        count = count + 1;
                    }

                    List<Gallery> galleries = products.getGallery();

                    for (Gallery gallery : galleries) {
                        featuredImages.put(gallery.getName(), count);
                        count = count + 1;
                    }


                    // we need to create container to store different variants value.. This container need to be load
                    // dynamically. Also we need to track the insertion order so i use LinkedHashSet!!

                    int diff_field = skuList.get(0).getOptions().size();

                    List<LinkedHashSet<String>> listOfSet = new ArrayList<>();
                    List<String> var_field = new ArrayList<>();

                    for (int i = 0; i < diff_field; i++) {
                        listOfSet.add(new LinkedHashSet<String>());
                        var_field.add(skuList.get(0).getOptions().get(i).getOptionName());
                    }

                    int def_flag = 0;

                    for (Sku sku : skuList) {
                        List<Option> options = sku.getOptions();
                        int iterator = 0;

                        String key = "";
                        String imageLink = "none";

                        for (Option option : options) {
                            if (def_flag == 0) {
                                defVal.add(option.getValueName());
                                product_priceTv.setText(currencySymbol + sku.getRegularPrice());
                                cart_variants = cart_variants + option.getValueName() + " ; ";
                                product_skuTv.setText("SKU : " + sku.getSku());
                                cart_Sku = sku.getSku();
                                stocks = Integer.parseInt(sku.getStock());
                                price = Float.parseFloat(sku.getRegularPrice());
                                showStock(sku.getStock() + "");
                            }

                            listOfSet.get(iterator).add(option.getValueName());

                            key = key + option.getValueName();
                            if (option.getImageUrl() != null) {
                                imageLink = option.getImageUrl().toString();
                                if (imageLink.length() > 0) {
                                    imageSource.put(imageLink, iterator);
                                    general = imageLink;
                                }
                                if (imageLink.length() > 0 && !featuredImages.containsKey(imageLink)) {
                                    featuredImages.put(imageLink, count);
                                    count = count + 1;
                                }
                            }
                            iterator = iterator + 1;
                        }

                        def_flag++;

                        if (!imageLink.equals("none")) {
                            variants.put(key, Arrays.asList(sku.getRegularPrice(), sku.getSellPrice(), sku.getSku(), sku.getStock(), featuredImages.get(imageLink) + ""));
                        } else {
                            variants.put(key, Arrays.asList(sku.getRegularPrice(), sku.getSellPrice(), sku.getSku(), sku.getStock(), "-1"));
                        }

                    }


                    //load all featured image in a slider view.
                    viewPager.setAdapter(new ViewPagerAdapter(getActivity(), featuredImages.keySet().toArray(), mainURL, 0));

                    for (int imgController = 0; imgController < featuredImages.keySet().toArray().length; imgController++) {
                        final ImageView hrznlImg = new ImageView(getActivity());

                        hrznlImg.setPadding(10, 10, 10, 10);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(220, 180);
                        lp.setMargins(10, 10, 10, 10);

                        hrznlImg.setLayoutParams(lp);
                        Glide.with(getActivity()).load("" + mainURL + featuredImages.keySet().toArray()[imgController].toString())
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .centerCrop()
                                .into(hrznlImg);

                        recycleImgList.add(hrznlImg);
                        if (!flag)
                            hrznlImg.setBackgroundColor(Color.LTGRAY);

                        if (flag) {
                            hrznlImg.setBackgroundColor(Color.parseColor("#338AFF"));
                            flag = false;
                        }
                        horzntlLayout.addView(hrznlImg);

                        final int finalI = imgController;
                        hrznlImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (ImageView im : recycleImgList) {
                                    im.setBackgroundColor(Color.LTGRAY);
                                }
                                hrznlImg.setBackgroundColor(Color.parseColor("#338AFF"));
                                viewPager.setCurrentItem(finalI);
                            }
                        });
                    }

                    leftNav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tab = viewPager.getCurrentItem();
                            if (tab > 0) {
                                tab--;
                                viewPager.setCurrentItem(tab);
                            } else if (tab == 0) {
                                viewPager.setCurrentItem(tab);
                            }
                        }
                    });


                    rightNav.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            int tab = viewPager.getCurrentItem();
                            tab++;
                            viewPager.setCurrentItem(tab);

                        }

                    });

                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(final int position) {
                            for (ImageView imgView : recycleImgList) {
                                imgView.setBackgroundColor(Color.LTGRAY);
                            }
                            int ops = position;
                            scrlView.scrollTo((position - 2) * 230, 0);
                            recycleImgList.get(position).setBackgroundColor(Color.parseColor("#338AFF"));
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    if (listOfSet.size() > 0) {

                        ll = view.findViewById(R.id.variant);

                        for (int position = 0; position < listOfSet.size(); position++) {

                            LayoutInflater inflater = LayoutInflater.from(getActivity());
                            View inflatedLayout = inflater.inflate(R.layout.custom_variants_layout, ll, false);

                            TextView textView = inflatedLayout.findViewById(R.id.variant_Name);
                            textView.setText(var_field.get(position).toUpperCase() + " : ");

                            RecyclerView recyclerView = inflatedLayout.findViewById(R.id.variantRecycler);
                            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
                            layoutManager.setFlexWrap(FlexWrap.WRAP);
                            layoutManager.setFlexDirection(FlexDirection.ROW);
                            layoutManager.setAlignItems(AlignItems.STRETCH);
                            recyclerView.setLayoutManager(layoutManager);

                            if (imageSource.get(general) == position) {
                                callRecycleView(mainURL, position, listOfSet.get(position).toArray(), imageSource.keySet().toArray(), recyclerView);
                                ll.addView(inflatedLayout);
                            } else {
                                callRecycleViewNoImg(position, listOfSet.get(position).toArray(), recyclerView);
                                ll.addView(inflatedLayout);
                            }

                        }
                    }
                    llhidden = view.findViewById(R.id.llhidden);
                    llhidden.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Product_Pojo> call, Throwable t) {
                dialog.dismiss();
            }
        });


        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(product_quantEt.getText().toString()) > stocks) {
                    Toast.makeText(getActivity(), "You can't add more than " + stocks + " quantity", Toast.LENGTH_SHORT).show();
                } else if (stocks < 1) {
                    Toast.makeText(getActivity(), "Stock Out! Please Try Others", Toast.LENGTH_SHORT).show();
                } else if (product_quantEt.getText().toString().equals(null)) {
                    Toast.makeText(getActivity(), "Please Enter a Number", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Product is added to cart!", Toast.LENGTH_SHORT).show();
                    //((ProductDetailed)getActivity()).callFunc();
                    Cart cart = new Cart(id,product_nameTv.getText().toString(), product_skuTv.getText().toString(),imgLink,Integer.parseInt(product_quantEt.getText().toString()), price, stocks, cart_variants,true);
                    ((SingleProductPage)getActivity()).addCart(cart);
                }
            }
        });

        return view;
    }

    public void callRecycleViewNoImg(int position,Object[] items,RecyclerView recyclerView) {
        RecycleAdapterNoImg recycleAdapter = new RecycleAdapterNoImg(getActivity(), position,this);
        recycleAdapter.setItems(items);
        recyclerView.setAdapter(recycleAdapter);
    }


    public void callRecycleView(String mainURL, int position, Object[] items, Object[] links, RecyclerView recyclerView) {
        RecycleAdapter recycleAdapter = new RecycleAdapter(getActivity(), mainURL, position, this);
        recycleAdapter.setItems(items, links);
        recyclerView.setAdapter(recycleAdapter);
    }

    public String genKey(List<String> array) {
        String testKey = "";
        cart_variants = "";
        for (int i = 0; i < array.size(); i++) {
            testKey = testKey + array.get(i);
            cart_variants = cart_variants + array.get(i) + " ; ";
        }
        return testKey;
    }

    public void changeThing(String keyVal) {
        showStock(variants.get(keyVal).get(3));
        price = Float.parseFloat(variants.get(keyVal).get(0));
        cart_Sku = variants.get(keyVal).get(2);
        product_skuTv.setText("SKU : " + variants.get(keyVal).get(2));
        product_priceTv.setText(currencySymbol + price);
        stocks = Integer.parseInt(variants.get(keyVal).get(3));
        if (Integer.parseInt(variants.get(keyVal).get(4)) >= 0) {
            viewPager.setCurrentItem(Integer.parseInt(variants.get(keyVal).get(4)));
        }
    }

    public void showStock(String stock_count) {
        int stock = Integer.parseInt(stock_count);
        if (stock > 0) {
            stockAvailbilityTv.setTextColor(Color.parseColor("#006400"));
            stockAvailbilityTv.setText(stock + " products available");
            product_quantEt.setText("1");
        } else {
            stockAvailbilityTv.setTextColor(Color.parseColor("#FF0000"));
            stockAvailbilityTv.setText("Stock Out");
            product_quantEt.setText("0");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSingleProductFragmentFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSingleProductFragmentInteractionListener) {
            mListener = (OnSingleProductFragmentInteractionListener) context;
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


    @Override
    public void onOptionClick(String val, int postion) {
        defVal.set(postion, val);
        String findAll = genKey(defVal);
        changeThing(findAll);
    }

    @Override
    public void onOptionClickNoImg(String val, int postion) {
        defVal.set(postion, val);
        String findAll = genKey(defVal);
        changeThing(findAll);
    }

    public interface OnSingleProductFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSingleProductFragmentFragmentInteraction(Uri uri);
    }

}
