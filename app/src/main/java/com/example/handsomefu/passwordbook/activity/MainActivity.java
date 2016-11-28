package com.example.handsomefu.passwordbook.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.handsomefu.passwordbook.Constant;
import com.example.handsomefu.passwordbook.R;
import com.example.handsomefu.passwordbook.ToastUtils;
import com.example.handsomefu.passwordbook.bean.AccountItem;
import com.example.handsomefu.passwordbook.utils.SPUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HandsomeFu on 2016/10/25.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final int EDIT = 2;
    private static final int MAIN = 3;
    private ListAdapter listAdapter;
    private static final int ADD_ITEM = 1;
    private Button btAdd;
    private RecyclerView rvList;
    private DrawerLayout dl;
    private NavigationView nvRight;
    private NavigationView nvLeft;
    List<AccountItem> items = new ArrayList<AccountItem>();
    int deletePosition;
    int test = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        List<ArrayList<String>> listParent = new ArrayList<ArrayList<String>>();
        ArrayList<String> item1 = new ArrayList<String>();
        item1.add("00");
        item1.add("01");
        listParent.add(item1);
        ArrayList<String> item2 = new ArrayList<String>();
        item1.add("10");
        item1.add("11");
        listParent.add(item2);
        for (ArrayList<String> item : listParent) {
            for (String string : item) {
                Log.i("tag", string);
            }
        }


        super.onCreate(savedInstanceState);
        test = 1;
        initData();
        listAdapter = new ListAdapter();
        listAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra(Constant.ACCOUNT_ITEM, new Gson().toJson(items.get(position)));
                intent.putExtra(Constant.FROM, EDIT);
                intent.putExtra(Constant.POSITION, position);
                startActivityForResult(intent, EDIT);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                deletePosition = position;
                dialog();
            }
        });
        rvList.setAdapter(listAdapter);
        test = 3;
        nvRight.setNavigationItemSelectedListener(this);
        nvLeft.setNavigationItemSelectedListener(this);
        test = 4;
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除该条密码吗？");
        builder.setTitle("警告");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除该条记录
                delete();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void delete() {
        //删除该条记录
        items.remove(deletePosition);
        SPUtils.clear();
        Gson gson = new Gson();
        String item;
        for (int i = 0; i < items.size(); i++) {
            item = gson.toJson(items.get(i));
            SPUtils.putString((i + 1) + "", item);
        }
        SPUtils.putAmount(items.size());
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_change_pw:
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                intent.putExtra(Constant.FROM, MAIN);
                startActivity(intent);
                dl.closeDrawers();
                break;
        }
        return false;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        AccountItem ai;

        @Override
        public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(MainActivity.this).
                    inflate(R.layout.item_password, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.MyViewHolder holder, final int position) {
            ai = items.get(position);
            holder.tvAccount.setText(ai.getAccount());
            holder.tvPassword.setText(ai.getPassword());
            holder.tvType.setText(ai.getType());
            holder.tvDes.setText(ai.getDes());
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickLitener.onItemClick(holder.itemView, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickLitener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvAccount;
            TextView tvPassword;
            TextView tvType;
            TextView tvDes;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvAccount = (TextView) itemView.findViewById(R.id.tv_account);
                tvPassword = (TextView) itemView.findViewById(R.id.tv_password);
                tvType = (TextView) itemView.findViewById(R.id.tv_type);
                tvDes = (TextView) itemView.findViewById(R.id.tv_des);
            }
        }
    }

    private void initData() {
        items.clear();
        AccountItem ai;
        String item;
        int i = 1;
        test = 2;
        item = SPUtils.getString(1 + "");
        while (!TextUtils.isEmpty(item)) {
            Gson gson = new Gson();
            ai = gson.fromJson(item, AccountItem.class);
            items.add(ai);
            i++;
            item = SPUtils.getString(i + "");
        }
    }

    @Override
    public void initView() {
        btAdd = (Button) findViewById(R.id.bt_add);
        btAdd.setOnClickListener(this);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        dl = (DrawerLayout) findViewById(R.id.dl);
        nvRight = (NavigationView) findViewById(R.id.nv_right);
        nvRight.setItemIconTintList(null);//如果不加这句，则图标显示灰色
        nvLeft = (NavigationView) findViewById(R.id.nv_left);
        nvLeft.setItemIconTintList(null);//如果不加这句，则图标显示灰色
    }

    @Override
    public int getLayout() {
        return R.layout.ac_main;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add:
                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra(Constant.FROM, ADD_ITEM);
                startActivityForResult(intent, ADD_ITEM);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                //添加成功，在list中插入新条目，更新list
                Gson gson = new Gson();
                AccountItem accountItem = gson.fromJson(data.getStringExtra(Constant.ACCOUNT_ITEM),
                        AccountItem.class);
                initData();
                listAdapter.notifyDataSetChanged();
//                Snackbar.make(rvList, "您现在有" + SPUtils.getAmount() + "条数据",
//                        Snackbar.LENGTH_SHORT).show();
                break;
            case 2:
                //修改成功
                initData();
                listAdapter.notifyDataSetChanged();
                break;
        }
    }
}
