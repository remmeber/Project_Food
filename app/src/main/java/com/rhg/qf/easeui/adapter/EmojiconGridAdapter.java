package com.rhg.qf.easeui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rhg.qf.R;
import com.rhg.qf.easeui.domain.EaseEmojicon;
import com.rhg.qf.easeui.utils.EaseSmileUtils;

import java.util.List;

public class EmojiconGridAdapter extends ArrayAdapter<EaseEmojicon> {

    private EaseEmojicon.Type emojiconType;


    public EmojiconGridAdapter(Context context, int textViewResourceId, List<EaseEmojicon> objects, EaseEmojicon.Type emojiconType) {
        super(context, textViewResourceId, objects);
        this.emojiconType = emojiconType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            if (emojiconType == EaseEmojicon.Type.BIG_EXPRESSION) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_big_expression, parent, false);
            } else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.ease_row_expression, parent, false);
            }
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_expression);
        TextView textView = (TextView) convertView.findViewById(R.id.tv_name);
        EaseEmojicon emojicon = getItem(position);
        if (textView != null && emojicon.getName() != null) {
            textView.setText(emojicon.getName());
        }
        if (EaseSmileUtils.DELETE_KEY.equals(emojicon.getEmojiText())) {
//            ImageUtils.showImage("drawable://"+R.drawable.ease_delete_expression,imageView);
            imageView.setImageResource(R.drawable.ease_delete_expression);
        } else {
            if (emojicon.getIcon() != 0) {
                imageView.setImageResource(emojicon.getIcon());
            } else if (emojicon.getIconPath() != null) {
//                ImageUtils.showImage(emojicon.getIconPath(),imageView);
                Glide.with(getContext()).load(emojicon.getIconPath()).placeholder(R.drawable.ease_default_expression).into(imageView);
            }
        }


        return convertView;
    }

}
