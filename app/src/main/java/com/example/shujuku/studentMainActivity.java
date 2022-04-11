package com.example.shujuku;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class studentMainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mLinearLayout;
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
    ImageView v1,v2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        LayoutInflater inflater = LayoutInflater.from(this);
        page1 = inflater.inflate(R.layout.playout, null);
        page2 = inflater.inflate(R.layout.playout2, null);
        page3 = inflater.inflate(R.layout.playout3, null);
        page1.setOnClickListener(this);
        page2.setOnClickListener(this);
        page3.setOnClickListener(this);
        initView();
        initPageAdapter();
        initPointer();
        initEvent();
        i1=findViewById(R.id.imageButton9);
        i1.setOnClickListener(this);
        i2=findViewById(R.id.imageButton10);
        i2.setOnClickListener(this);
        i3=findViewById(R.id.imageButton12);
        i3.setOnClickListener(this);
        i4=findViewById(R.id.imageButton7);
        i4.setOnClickListener(this);
        v1=findViewById(R.id.imageView4);
        v1.setOnClickListener(this);
        v2=findViewById(R.id.imageView5);
        v2.setOnClickListener(this);
        t1=findViewById(R.id.textView9);
        t1.setOnClickListener(this);
        t2=findViewById(R.id.textView11);
        t2.setOnClickListener(this);
        t3=findViewById(R.id.textView12);
        t3.setOnClickListener(this);
        t4=findViewById(R.id.textView13);
        t4.setOnClickListener(this);
        t5=findViewById(R.id.textView15);
        t5.setOnClickListener(this);
        t6=findViewById(R.id.textView16);
        t6.setOnClickListener(this);
    }

    //为控件绑定事件,绑定适配器
    private void initEvent() {
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new GuidePageChangeListener());
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
           case R.id.imageView4:
               Intent intent4 = new Intent();
               intent4.setAction(Intent.ACTION_VIEW);
               intent4.addCategory(Intent.CATEGORY_BROWSABLE);
               intent4.setData(Uri.parse("https://www.hbu.edu.cn/"));
               startActivity(intent4);
               finish();
               break;
           case R.id.imageView5:
               Intent intent5 = new Intent();
               intent5.setAction(Intent.ACTION_VIEW);
               intent5.addCategory(Intent.CATEGORY_BROWSABLE);
               intent5.setData(Uri.parse("http://dsxxzhuanti.hbu.cn/"));
               startActivity(intent5);
               finish();
               break;
           case R.id.textView15:
               Intent intent6 = new Intent();
               intent6.setAction(Intent.ACTION_VIEW);
               intent6.addCategory(Intent.CATEGORY_BROWSABLE);
               intent6.setData(Uri.parse("https://www.hbu.edu.cn/"));
               startActivity(intent6);
               finish();
               break;
           case R.id.textView16:
               Intent intent7 = new Intent();
               intent7.setAction(Intent.ACTION_VIEW);
               intent7.addCategory(Intent.CATEGORY_BROWSABLE);
               intent7.setData(Uri.parse("http://dsxxzhuanti.hbu.cn/"));
               startActivity(intent7);
               finish();
               break;
           case R.id.imageButton9:
               Intent intent8 = new Intent(this,qingjia.class);
               startActivity(intent8);
               finish();
               break;
           case R.id.imageButton10:
               Intent intent9 = new Intent(this,baoxiu.class);
               startActivity(intent9);
               finish();
               break;
           case R.id.imageButton7:
               Intent intent10 = new Intent(this,studentxk.class);
               startActivity(intent10);
               finish();
               break;

       }
    }

    //ViewPager的onPageChangeListener监听事件，当ViewPager的page页发生变化的时候调用
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
