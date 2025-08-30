package com.example.expresssortingsystemdesign.adapter;

import static com.example.expresssortingsystemdesign.utils.Common.PushTopic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.example.expresssortingsystemdesign.R;
import com.example.expresssortingsystemdesign.bean.Send;
import com.example.expresssortingsystemdesign.dao.Express;
import com.example.expresssortingsystemdesign.dao.ExpressDao;
import com.example.expresssortingsystemdesign.databinding.RecordListviewItemBinding;
import com.example.expresssortingsystemdesign.utils.Common;
import com.example.expresssortingsystemdesign.utils.MToast;
import com.google.gson.Gson;

import java.util.List;

public class RecordListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Object> list;

    public RecordListViewAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            RecordListviewItemBinding binding = RecordListviewItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
            holder = new ViewHolder(binding);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        initView(holder, i);
        return view;

    }


    private void initView(ViewHolder holder, int i) {
        Express record = (Express) list.get(i);
        holder.binding.dateTimeText.setText(record.getCreateDateTime());
        holder.binding.locationText.setText(record.getLocation());
        holder.binding.packageText.setText(record.getPid());
        switch (record.getState()) {
            case "1":
                holder.binding.packageOutText.setText("未入库");
                holder.binding.packageImage.setImageResource(R.mipmap.in3);
                break;
            case "2":
                holder.binding.packageOutText.setText("已入库");
                holder.binding.packageImage.setImageResource(R.mipmap.in1);
                break;
            case "3":
                holder.binding.packageOutText.setText("已出库");
                holder.binding.packageImage.setImageResource(R.mipmap.in0);
                break;
        }
        holder.binding.deleteBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("请确认").setMessage("您即将删除该包裹，请确定是否删除!").setNegativeButton("确认", (dialogInterface, i13) -> {
                if (new ExpressDao(context).delete(String.valueOf(record.getEid())) == 1) {
                    MToast.mToast(context, "删除成功");
                    list.remove(i);
                    notifyDataSetChanged();
                } else {
                    MToast.mToast(context, "数据库发送错误");
                }
                builder.create().dismiss();
            }).setPositiveButton("取消", (dialogInterface, i1) -> {
                builder.create().dismiss();
            });
            builder.create().show();
        });

        //修改按钮
        holder.binding.updateBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final String[] items = new String[]{"未入库", "已入库", "已出库"};
            builder.setTitle("请确认").setSingleChoiceItems(items, Integer.parseInt(record.getState()) + 1, (dialogInterface, i12) -> {
                switch (i12 + 1) {
                    case 1:
                        holder.binding.packageOutText.setText("未入库");
                        holder.binding.packageImage.setImageResource(R.mipmap.in3);
                        record.setState("1");
                        break;
                    case 2:
                        holder.binding.packageOutText.setText("已入库");
                        holder.binding.packageImage.setImageResource(R.mipmap.in1);
                        record.setState("2");
                        break;
                    case 3:
                        holder.binding.packageOutText.setText("已出库");
                        holder.binding.packageImage.setImageResource(R.mipmap.in0);
                        record.setState("3");
                        break;
                }
                new ExpressDao(context).update(record, String.valueOf(record.getEid()));
                MToast.mToast(context, "修改成功");
                sendMessage(1, record.getCity(), record.getArea(), record.getPid(), record.getState());
                builder.create().dismiss();
            });
            builder.create().show();
        });
    }

    /**
     * @param message 需要发送的消息
     * @brief 再次封装MQTT发送
     */
    private void sendMessage(int cmd, String... message) {
        if (Common.mqttHelper != null && Common.mqttHelper.getConnected()) {
            Send send = new Send();
            switch (cmd) {
                case 1:
                    send.setCityid(Integer.parseInt(message[0]));
                    send.setArea(Integer.parseInt(message[1]));
                    send.setId(Integer.parseInt(message[2]));
                    send.setState(Integer.parseInt(message[3]));
                    break;

            }
            send.setCmd(cmd);
            String str = new Gson().toJson(send);
            new Thread(() -> Common.mqttHelper.publish(PushTopic, str, 1)).start();
        }
    }

    private class ViewHolder {
        private RecordListviewItemBinding binding;

        private ViewHolder(RecordListviewItemBinding binding) {
            this.binding = binding;
        }
    }
}
