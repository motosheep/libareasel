# areasel

介绍
地址选择库



20210930-------------
1增加数据源可以外部输入

    /**
     * @param source 数据来源:1本地 2传入
     */
    public void show(Activity activity, int type, List<AddressInfo> source) {
        AddressModel.getInstance().setRemoteData(source);
        Intent intent = new Intent(activity, LibSelAddressActivity.class);
        intent.putExtra(IntentCode.TYPE_DATA, type);
        intent.putExtra(IntentCode.TYPE_SOURCE, 2);
        activity.startActivity(intent);
    }

2更新numberpicker控件



20211002------------
1增加全屏城市选择页面