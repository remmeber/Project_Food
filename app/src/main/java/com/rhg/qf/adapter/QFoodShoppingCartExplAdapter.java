package com.rhg.qf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rhg.qf.R;
import com.rhg.qf.bean.ShoppingCartBean;
import com.rhg.qf.ui.UIAlertView;
import com.rhg.qf.utils.ImageUtils;
import com.rhg.qf.utils.ShoppingCartUtil;

import java.util.List;

/**
 * desc:购物车列表适配器
 * author：remember
 * time：2016/5/28 16:19
 * email：1013773046@qq.com
 */
public class QFoodShoppingCartExplAdapter extends BaseExpandableListAdapter {
    List<ShoppingCartBean> mData;
    Context context;
    private DataChangeListener onDataChangeListener;

    //TODO--------------------------购物车事件监听--------------------------------------------------
    View.OnClickListener ShortCartListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int clickPosition;
            switch (v.getId()) {
                case R.id.ivCheckGroup:
                    clickPosition = Integer.parseInt(String.valueOf(v.getTag()));
//                    isSelectAll = ShoppingCartUtil.selectGroup(mData,position);//TODO 如果有全选，则需要加上返回
                    ShoppingCartUtil.selectGroup(mData, clickPosition);
                    setDataChange();
                    notifyDataSetChanged();
                    break;
                case R.id.ivCheckGood:
                    String tag = String.valueOf(v.getTag());
                    if (tag.contains(",")) {
                        String s[] = tag.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        ShoppingCartUtil.selectOne(mData, groupPosition, childPosition);
                        setDataChange();
                        notifyDataSetChanged();
                    }
                    break;
                case R.id.imaShopForwardGroup:
                    clickPosition = (int) v.getTag();
                    break;
                case R.id.ivAdd:
                    String addTag = String.valueOf(v.getTag());
                    if (addTag.contains(",")) {
                        String s[] = addTag.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        String merchantId = ((ShoppingCartBean) getGroup(groupPosition)).getMerID();
                        ShoppingCartUtil.addOrReduceGoodsNum(true, (ShoppingCartBean.Goods) getChild(groupPosition, childPosition), merchantId,
                                (TextView) ((View) (v.getParent())).findViewById(R.id.etNum));
                        setDataChange();
                    }
                    break;
                case R.id.ivReduce:
                    String reduceTag = String.valueOf(v.getTag());
                    if (reduceTag.contains(",")) {
                        String s[] = reduceTag.split(",");
                        int groupPosition = Integer.parseInt(s[0]);
                        int childPosition = Integer.parseInt(s[1]);
                        String merchantId = ((ShoppingCartBean) getGroup(groupPosition)).getMerID();
                        ShoppingCartUtil.addOrReduceGoodsNum(false, (ShoppingCartBean.Goods) getChild(groupPosition, childPosition), merchantId,
                                (TextView) ((View) (v.getParent())).findViewById(R.id.etNum));
                        setDataChange();
                    }
                    break;
            }
        }


    };


    public QFoodShoppingCartExplAdapter(Context context) {
        this.context = context;
    }

    public void setmData(List<ShoppingCartBean> mData) {
        this.mData = mData;
        setDataChange();
    }

    @Override
    public int getGroupCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData == null ? 0 : mData.get(groupPosition).getGoods() == null
                ? 0 : mData.get(groupPosition).getGoods().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mData == null ? null : mData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData == null ? null : mData.get(groupPosition).getGoods() == null
                ? null : mData.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            groupViewHolder = new GroupViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_cart_group, parent, false);
            groupViewHolder.btGroupCheck = (ImageView) convertView.findViewById(R.id.ivCheckGroup);
            groupViewHolder.tvShopName = (TextView) convertView.findViewById(R.id.tvShopNameGroup);
            groupViewHolder.btForwardShop = (ImageView) convertView.findViewById(R.id.imaShopForwardGroup);
            convertView.setTag(groupViewHolder);
        } else
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        ShoppingCartUtil.checkItem(mData.get(groupPosition).isGroupSelected(), groupViewHolder.btGroupCheck);
        groupViewHolder.tvShopName.setText(mData.get(groupPosition).getMerchantName());
        groupViewHolder.btGroupCheck.setOnClickListener(ShortCartListener);
        groupViewHolder.btForwardShop.setOnClickListener(ShortCartListener);

        groupViewHolder.btGroupCheck.setTag(groupPosition);//TODO 设置点击的TAG，便于区分
        groupViewHolder.btForwardShop.setTag(groupPosition);//TODO 设置点击的TAG，便于区分
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        View itemView;
        if (convertView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_shop_cart_child, parent, false);
            childViewHolder = new ChildViewHolder(itemView);
            itemView.setTag(childViewHolder);
        } else {
            itemView = convertView;
            childViewHolder = (ChildViewHolder) itemView.getTag();
        }
        ShoppingCartBean.Goods goods = mData.get(groupPosition).getGoods().get(childPosition);

        boolean isChildSelected = goods.isChildSelected();
        String goodsPrice = goods.getPrice();
        String goodsNum = goods.getNumber();
        String goodsName = goods.getGoodsName();
        String goodsLogoUrl = goods.getGoodsLogoUrl();

        ImageUtils.showImage(goodsLogoUrl, childViewHolder.goodsLogo);
        childViewHolder.goodsLogo.setDrawingCacheEnabled(true);
        childViewHolder.tvGoodsName.setText(goodsName);
        childViewHolder.tvGoodsPrice.setText(goodsPrice);
        childViewHolder.goodsCount.setText(goodsNum);

        childViewHolder.btGoodsCheck.setTag(groupPosition + "," + childPosition);
        childViewHolder.btReduceNum.setTag(groupPosition + "," + childPosition);
        childViewHolder.btAddNum.setTag(groupPosition + "," + childPosition);
        childViewHolder.delete.setTag(groupPosition + "," + childPosition);

        ShoppingCartUtil.checkItem(isChildSelected, childViewHolder.btGoodsCheck);

        childViewHolder.btGoodsCheck.setOnClickListener(ShortCartListener);
        childViewHolder.btReduceNum.setOnClickListener(ShortCartListener);
        childViewHolder.btAddNum.setOnClickListener(ShortCartListener);
        childViewHolder.rlChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("RHG", "child is click");
            }
        });
        childViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDelDialog(groupPosition, childPosition);
            }
        });
        return itemView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 删除弹框
     *
     * @param groupPosition
     * @param childPosition
     */
    private void showDelDialog(final int groupPosition, final int childPosition) {
        final String productID = mData.get(groupPosition).getGoods().get(childPosition).getGoodsID();
        final UIAlertView delDialog = new UIAlertView(context, "温馨提示", "确认删除该商品吗?",
                "取消", "确定");
        delDialog.show();
        delDialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                                       @Override
                                       public void doLeft() {
                                           delDialog.dismiss();
                                       }

                                       @Override
                                       public void doRight() {
                                           delGoods(groupPosition, childPosition, ((ShoppingCartBean) getGroup(groupPosition)).getMerID(), productID);
                                           setDataChange();
                                           notifyDataSetChanged();
                                           delDialog.dismiss();
                                       }
                                   }
        );
    }

    /**
     * 删除商品
     *
     * @param groupPosition
     * @param childPosition
     */
    private void delGoods(int groupPosition, int childPosition, String merchantId, String foodId) {
        onDataChangeListener.removeData(merchantId, foodId);
        mData.get(groupPosition).getGoods().remove(childPosition);
        if (mData.get(groupPosition).getGoods().size() == 0) {
            mData.remove(groupPosition);
        }
        notifyDataSetChanged();
    }

    private void setDataChange() {
        String[] infos = ShoppingCartUtil.getShoppingCount(mData);
        if (onDataChangeListener != null)
            onDataChangeListener.onDataChange(infos[1]);
    }

    public void setDataChangeListener(DataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public interface DataChangeListener {
        void onDataChange(String CountMoney);

        void removeData(String merchantId, String foodId);
    }

    class GroupViewHolder {
        ImageView btGroupCheck;
        TextView tvShopName;
        ImageView btForwardShop;
    }

    class ChildViewHolder {
        RelativeLayout rlChild;
        /*商品选中*/
        ImageView btGoodsCheck;
        /*商品图片*/
        ImageView goodsLogo;
        /*商品的名字*/
        TextView tvGoodsName;
        /*商品的价格*/
        TextView tvGoodsPrice;
        /*减少商品*/
        ImageView btReduceNum;
        /*增加商品*/
        ImageView btAddNum;
        /*商品数量*/
        TextView goodsCount;
        /*删除*/
        LinearLayout delete;

        ChildViewHolder(View view) {
            rlChild = (RelativeLayout) view.findViewById(R.id.rl_child);
            btGoodsCheck = (ImageView) view.findViewById(R.id.ivCheckGood);
            goodsLogo = (ImageView) view.findViewById(R.id.ivGoodsLogo);
            tvGoodsName = (TextView) view.findViewById(R.id.tvItemChild);
            tvGoodsPrice = (TextView) view.findViewById(R.id.tvPriceNew);
            btReduceNum = (ImageView) view.findViewById(R.id.ivReduce);
            btAddNum = (ImageView) view.findViewById(R.id.ivAdd);
            goodsCount = (TextView) view.findViewById(R.id.etNum);
            delete = (LinearLayout) view.findViewById(R.id.lldelete);
            goodsCount.setFocusable(false);
        }
    }
}
