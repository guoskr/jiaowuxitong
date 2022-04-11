package com.example.shujuku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_mianjiemian extends AppCompatActivity implements View.OnClickListener {
    public static int jm = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private DatabaseHelper mStudentDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;
    RecyclerView rvMain;
    SharedPreferences mSharedPreferences;
    private DrawerLayout mDrawerLayout;
    SearchView searchView;
    private Fragment[] mFragments = new Fragment[5];
    private BottomNavigationView mBottomNav;
    private int mPreFragmentFlag = 0;
    private ViewPager viewPager;
    private PagerAdapter adapter;//显示数组图片的View
    private List<View> viewPages = new ArrayList<>();
    //包裹点点的LinearLayout
    private ViewGroup group;
    private ImageView imageView;
    //定义一个ImageVIew数组，来存放生成的小园点
    private ImageView[] imageViews;
    View page1,page2,page3;
    ImageButton i1,i2,i3,i4;
    TextView t1,t2,t3,t4,t5,t6;
    ImageView v1,v2,v3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mianjiemian);
        Toolbar toolbar = findViewById(R.id.toolbar);
        LayoutInflater inflater = LayoutInflater.from(this);
        page1 = inflater.inflate(R.layout.playout, null);
        page2 = inflater.inflate(R.layout.playout2, null);
        page3 = inflater.inflate(R.layout.playout3, null);
        page1.setOnClickListener(this);
        page2.setOnClickListener(this);
        page3.setOnClickListener(this);
        toolbar.setTitle("教务管理系统");
        initView();
        initPageAdapter();
        initPointer();
        initEvent();
        v1=findViewById(R.id.imageView7);
        v1.setOnClickListener(this);
        v2=findViewById(R.id.imageView8);
        v2.setOnClickListener(this);
        v3=findViewById(R.id.imageView9);
        v3.setOnClickListener(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.xsxx:
                        ArrayList<String> number = new ArrayList<>();
                        ArrayList<String> name = new ArrayList<>();
                        ArrayList<String> gender = new ArrayList<>();
                        mSQLiteDatabase = mStudentDateBaseHelper.getWritableDatabase();
                        Cursor mCursor = mSQLiteDatabase.query("student", null, null, null, null, null, null);
                        int size = mCursor.getCount() < ReclyclerAdapter.maxSize ? mCursor.getCount() : ReclyclerAdapter.maxSize;
                        while (true) {
                            if (size-- == 0)
                                break;
                            mCursor.moveToNext();
                            number.add(mCursor.getString(mCursor.getColumnIndex("sno")));
                            name.add(mCursor.getString(mCursor.getColumnIndex("sname")));
                            gender.add(mCursor.getString(mCursor.getColumnIndex("ssex")));
                        }
                        mCursor.close();
                        rvMain.setAdapter(new ReclyclerAdapter(MainActivity_mianjiemian.this, number, name, gender));
                        break;
                    case R.id.xkxx:
                        ArrayList<String> number1 = new ArrayList<>();
                        ArrayList<String> name1 = new ArrayList<>();
                        ArrayList<String> gender1 = new ArrayList<>();
                        mSQLiteDatabase = mStudentDateBaseHelper.getWritableDatabase();
                        Cursor mCursor1 = mSQLiteDatabase.query("leave", null, null, null, null, null, null);
                        int size1 = mCursor1.getCount() < ReclyclerAdapter.maxSize ? mCursor1.getCount() : ReclyclerAdapter.maxSize;
                        while (true) {
                            if (size1-- == 0)
                                break;
                            mCursor1.moveToNext();
                            number1.add(mCursor1.getString(mCursor1.getColumnIndex("cause")));
                            name1.add(mCursor1.getString(mCursor1.getColumnIndex("timestart")));
                            gender1.add(mCursor1.getString(mCursor1.getColumnIndex("timefinal")));
                        }
                        mCursor1.close();
                        rvMain.setAdapter(new ReclyclerAdapterqj(MainActivity_mianjiemian.this, number1, name1, gender1));

                }


                return false;
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.xsxx, R.id.xkxx, R.id.qjxx)
//                .setDrawerLayout(drawer)
//                .build();
//
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
        mStudentDateBaseHelper = new DatabaseHelper(this, "user_db", null, 8);
        mSQLiteDatabase = mStudentDateBaseHelper.getReadableDatabase();
        mSharedPreferences = this.getSharedPreferences("student", MODE_PRIVATE);
        initView();
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchAction();
                return false;
            }
        });
        //initView();
//        initFragment();
//        selectFragment();
    }

    private void searchAction() {
        final String[] arrayGender = new String[]{"按学号查找", "按姓名查找", "按专业查找"};
        new AlertDialog.Builder(MainActivity_mianjiemian.this)
                .setTitle("搜索类型")
                .setItems(arrayGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity_mianjiemian.this, search.class);
                        switch (which) {
                            case 0:
                                intent.putExtra("search_type", search.TYPE_SEARCH_NUMBER);
                                break;
                            case 1:
                                intent.putExtra("search_type", search.TYPE_SEARCH_NAME);
                                break;
                            case 2:
                                intent.putExtra("search_type", search.TYPE_SEARCH_sdept);
                                break;
                        }
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }

//    private void refreshRecyclerView() {
//        if (jm == 1) {
//            ArrayList<String> number = new ArrayList<>();
//            ArrayList<String> name = new ArrayList<>();
//            ArrayList<String> gender = new ArrayList<>();
//            mSQLiteDatabase = mStudentDateBaseHelper.getWritableDatabase();
//            Cursor mCursor = mSQLiteDatabase.query("student", null, null, null, null, null, null);
//            int size = mCursor.getCount() < ReclyclerAdapter.maxSize ? mCursor.getCount() : ReclyclerAdapter.maxSize;
//            while (true) {
//                if (size-- == 0)
//                    break;
//                mCursor.moveToNext();
//                number.add(mCursor.getString(mCursor.getColumnIndex("sno")));
//                name.add(mCursor.getString(mCursor.getColumnIndex("sname")));
//                gender.add(mCursor.getString(mCursor.getColumnIndex("ssex")));
//            }
//            mCursor.close();
//            rvMain.setAdapter(new ReclyclerAdapter(MainActivity_mianjiemian.this, number, name, gender));
//        }
//        if (jm == 2) {
//            ArrayList<String> number = new ArrayList<>();
//            ArrayList<String> name = new ArrayList<>();
//            ArrayList<String> gender = new ArrayList<>();
//            mSQLiteDatabase = mStudentDateBaseHelper.getWritableDatabase();
//            Cursor mCursor = mSQLiteDatabase.query("course", null, null, null, null, null, null);
//            int size = mCursor.getCount() < ReclyclerAdapter.maxSize ? mCursor.getCount() : ReclyclerAdapter.maxSize;
//            while (true) {
//                if (size-- == 0)
//                    break;
//                mCursor.moveToNext();
//                number.add(mCursor.getString(mCursor.getColumnIndex("sno")));
//                name.add(mCursor.getString(mCursor.getColumnIndex("sname")));
//                gender.add(mCursor.getString(mCursor.getColumnIndex("cname")));
//            }
//            mCursor.close();
//            rvMain.setAdapter(new ReclyclerAdapter(MainActivity_mianjiemian.this, number, name, gender));
//        }
//    }

//    private void initView() {

//        rvMain = (RecyclerView) findViewById(R.id.recycler_View);
//        rvMain.setLayoutManager(new LinearLayoutManager(MainActivity_mianjiemian.this));

//    }

    protected void onStart() {
        super.onStart();
//        refreshRecyclerView();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu.main_activity_mianjiemian, menu);
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
////        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.playout:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.addCategory(Intent.CATEGORY_BROWSABLE);
                intent1.setData(Uri.parse("https://www.hbu.edu.cn/"));
                startActivity(intent1);
                finish();
                break;
            case R.id.playout2:
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_VIEW);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.setData(Uri.parse("https://zhjw.hbu.cn/login"));
                startActivity(intent2);
                finish();
                break;
            case R.id.playout3:
                Intent intent3 = new Intent();
                intent3.setAction(Intent.ACTION_VIEW);
                intent3.addCategory(Intent.CATEGORY_BROWSABLE);
                intent3.setData(Uri.parse("http://jwc.hbu.edu.cn/"));
                startActivity(intent3);
                finish();
                break;
            case R.id.nav_search:
                searchAction();
                break;
            case R.id.imageView7:
                Intent intent4=new Intent(MainActivity_mianjiemian.this,xsxx.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.imageView8:
                Intent intent5=new Intent(MainActivity_mianjiemian.this,qjxx.class);
                startActivity(intent5);
                finish();
                break;
            case R.id.imageView9:
                Intent intent6=new Intent(MainActivity_mianjiemian.this,xkxx.class);
                startActivity(intent6);
                finish();
                break;
        }
    }
//    private void initView() {
//        mBottomNav = findViewById(R.id.nav_view);
//
//    }
    private void initFragment() {
//        mFragments[0] = new xsxx();
////        mFragments[1] = new WeChatFragment();
////        mFragments[2] = new ProjectFragment();
//        initLoadFragment(R.id.nav_host_fragment, 0, mFragments);
    }
    private void initEvent() {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new MainActivity_mianjiemian.GuidePageChangeListener());
    }
    //初始化ViewPager
    private void initPageAdapter() {
        /**
         * 对于这几个想要动态载入的page页面，使用LayoutInflater.inflate()来找到其布局文件，并实例化为View对象
         */
        //添加到集合中
        viewPages.add(page1);
        viewPages.add(page2);
        viewPages.add(page3);
        adapter = new PagerAdapter() {
            //获取当前界面个数
            @Override
            public int getCount() {
                return viewPages.size();
            }
            //判断是否由对象生成页面
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewPages.get(position));
            }
            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = viewPages.get(position);
                container.addView(view);
                return view;
            }
        };
    }
    //绑定控件
    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //将状态栏颜色设置为与toolbar一致
            getWindow().setStatusBarColor(getResources().getColor(R.color.normal_blue));
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        group = (ViewGroup) findViewById(R.id.viewGroup);
    }
    //初始化下面的小圆点的方法
    private void initPointer() {
        //有多少个界面就new多长的数组
        imageViews = new ImageView[viewPages.size()];
        for (int i = 0; i < imageViews.length; i++) {
            imageView = new ImageView(this);
            //设置控件的宽高
            imageView.setLayoutParams(new ViewGroup.LayoutParams(25, 25));
            //设置控件的padding属性
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;
            //初始化第一个page页面的图片的原点为选中状态
            if (i == 0) {
                //表示当前图片
                imageViews[i].setBackgroundResource(R.drawable.ic_launcher_background);
                /**
                 * 在java代码中动态生成ImageView的时候
                 * 要设置其BackgroundResource属性才有效
                 * 设置ImageResource属性无效
                 */
            } else {
                imageViews[i].setBackgroundResource(R.drawable.ic_launcher_foreground);
            }
            group.addView(imageViews[i]);
        }
    }
    public class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        //页面滑动完成后执行
        @Override
        public void onPageSelected(int position) {
            //判断当前是在那个page，就把对应下标的ImageView原点设置为选中状态的图片
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(R.drawable.ic_launcher_background);
                if (position != i) {
                    imageViews[i].setBackgroundResource(R.drawable.ic_launcher_foreground);
                }
            }
        }
        //监听页面的状态，0--静止 1--滑动  2--滑动完成
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}