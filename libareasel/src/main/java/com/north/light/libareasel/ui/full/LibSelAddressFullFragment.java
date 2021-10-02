package com.north.light.libareasel.ui.full;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.north.light.libareasel.AddressMain;
import com.north.light.libareasel.R;
import com.north.light.libareasel.adapter.AddressFullAdapter;
import com.north.light.libareasel.bean.AddressDetailInfo;
import com.north.light.libareasel.bean.AddressInfo;
import com.north.light.libareasel.bean.AddressSelResult;
import com.north.light.libareasel.bean.local.AddressLocalFullItemInfo;
import com.north.light.libareasel.constant.AddressConstant;
import com.north.light.libareasel.model.AddressModel;
import com.north.light.libareasel.utils.AddressChineseUtils;
import com.north.light.libareasel.widget.AddressSlideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * FileName: LibSelFullFragment
 * Author: lizhengting
 * Date: 2021/10/1 19:23
 * Description:全屏页面地址选择fragment
 * 主要显示信息为--市的信息，搜索匹配省市区
 */
public class LibSelAddressFullFragment extends Fragment {
    public static final String CODE_REQUEST = "CODE_REQUEST";
    //数据来源:1本地 2远程
    private int mType = 1;
    /**
     * 根布局
     */
    private View mRootView;
    //数据源
    private List<AddressInfo> mOrgData = new ArrayList();
    //所有省市区的数据列表信息
    private List<AddressLocalFullItemInfo> mAllInfoList = new ArrayList();
    //显示数据集合
    private List<AddressLocalFullItemInfo> mInfoList = new ArrayList<>();
    //推荐城市名字集合
    private List<String> recCityList = new ArrayList<>();
    //map--市-省
    private HashMap<String, AddressDetailInfo> cityMap = new HashMap<>();
    //map--区-市
    private HashMap<String, AddressDetailInfo> districtMap = new HashMap<>();
    /**
     * 显示数据adapter
     */
    private AddressFullAdapter mFullAdapter;
    private RecyclerView mFullRecy;

    /**
     * 输入框
     */
    private EditText mInputET;

    /**
     * 提示框
     */
    private AddressSlideBar mSlide;
    private TextView mSlideTips;


    /**
     * 显示模式：0默认模式
     */
    public static LibSelAddressFullFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt(CODE_REQUEST, type);
        LibSelAddressFullFragment instance = new LibSelAddressFullFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_lib_sel_full_address, container, false);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(CODE_REQUEST, -1);
            if (mType == -1) {
                return;
            }
            initView();
            initEvent();
        }
    }

    private void initView() {
        mSlide = mRootView.findViewById(R.id.fragment_lib_sel_picker_address_full_slidebar);
        mSlideTips = mRootView.findViewById(R.id.fragment_lib_sel_picker_address_full_slidebar_tips);
        mInputET = mRootView.findViewById(R.id.fragment_lib_sel_picker_address_full_input);
        mFullRecy = mRootView.findViewById(R.id.fragment_lib_sel_picker_address_full_content);
        mFullRecy.setLayoutManager(new LinearLayoutManager(requireActivity(),
                LinearLayoutManager.VERTICAL, false));
        mFullAdapter = new AddressFullAdapter(requireActivity());
        mFullRecy.setAdapter(mFullAdapter);
        initData();
        mFullAdapter.setData(mInfoList);
    }

    private void initEvent() {
        mSlide.setOnTouchLetterListener(new AddressSlideBar.onTouchLetterListener() {
            @Override
            public void onTouchLetterChange(String letter, boolean touch) {
                mSlideTips.setVisibility((touch) ? View.VISIBLE : View.GONE);
                mSlideTips.setText(letter);
                //滑动到recyclerview指定位置
                mFullAdapter.scrollToTips(letter,mFullRecy);
            }
        });
        mInputET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    //空数据
                    mFullAdapter.setData(mInfoList);
                } else {
                    //显示搜索结果
                    String input = s.toString();
                    SearchInfo(input);
                }
            }
        });
        mFullAdapter.setOnClickListener(new AddressFullAdapter.OnClickListener() {
            @Override
            public void Select(int sourceType, AddressDetailInfo info) {
                setCallbackData(sourceType,info);
            }
        });
    }

    /**
     * 设置返回数据
     */
    private void setCallbackData(int sourceType,AddressDetailInfo info) {
        if (sourceType == 1) {
            //省份
            AddressSelResult result = new AddressSelResult();
            result.setProvince(info.getName());
            result.setProvinceId(info.getId());
            AddressMain.getInstance().onSelData(result);
        } else if (sourceType == 2) {
            //市
            AddressSelResult result = new AddressSelResult();
            result.setCity(info.getName());
            result.setCityId(info.getId());
            //查询上级省份
            AddressDetailInfo provinceInfo = cityMap.get(info.getName());
            if (provinceInfo != null) {
                result.setProvinceId(provinceInfo.getId());
                result.setProvince(provinceInfo.getName());
            }
            AddressMain.getInstance().onSelData(result);
        } else if (sourceType == 3) {
            //区
            AddressSelResult result = new AddressSelResult();
            result.setDistrict(info.getName());
            result.setDistrictId(info.getId());
            //查询上级市
            AddressDetailInfo cityInfo = districtMap.get(info.getName());
            if (cityInfo != null) {
                result.setCityId(cityInfo.getId());
                result.setCityId(cityInfo.getName());
            }
            if (cityInfo != null) {
                //查询上级省份
                AddressDetailInfo provinceInfo = cityMap.get(cityInfo.getName());
                if (provinceInfo != null) {
                    result.setProvinceId(provinceInfo.getId());
                    result.setProvince(provinceInfo.getName());
                }
            }
            AddressMain.getInstance().onSelData(result);
        }
        requireActivity().finish();
    }

    /**
     * 搜索数据
     */
    private void SearchInfo(String input) {
        List<AddressLocalFullItemInfo> result = new ArrayList<>();
        for (AddressLocalFullItemInfo allData : mAllInfoList) {
            if (allData.getAddress().contains(input)) {
                //过滤
                result.add(allData);
            }
        }
        mFullAdapter.setData(result);
    }


    //数据处理-----------------------------------------------------------------------------------

    /**
     * 初始化数据
     */
    private void initData() {
        //初始化数据
        recCityList.add("北京");
        recCityList.add("上海");
        recCityList.add("深圳");
        recCityList.add("广州");
        List<AddressInfo> result = new ArrayList();
        if (mType == 1) {
            result = AddressModel.getInstance().getAddressData(requireActivity(), AddressConstant.DATA_XML_NAME);
        } else if (mType == 2) {
            result = AddressModel.getInstance().getAddressRemote();
        }
        mOrgData.clear();
        mOrgData.addAll(result);
        for (AddressInfo addressInfo : result) {
            //省
            addAllAddressInfo(addressInfo.getId(), addressInfo.getName(), 1);
            for (String city : addressInfo.getCityMap().keySet()) {
                List<AddressDetailInfo> cityList = addressInfo.getCityMap().get(city);
                //市
                if (cityList != null && cityList.size() != 0) {
                    for (AddressDetailInfo cityDetail : cityList) {
                        addAllAddressInfo(cityDetail.getId(), cityDetail.getName(), 2);
                        //显示数据设置
                        addShowAddressInfo(cityDetail.getId(), cityDetail.getName(), 2);
                        addCityToProvinceMap(cityDetail.getName(), addressInfo.getId(), addressInfo.getName());
                        //区
                        List<AddressDetailInfo> districtList = addressInfo.getDistrictMap().get(cityDetail.getName());
                        if (districtList != null && districtList.size() != 0) {
                            for (AddressDetailInfo districtDetail : districtList) {
                                addAllAddressInfo(districtDetail.getId(), districtDetail.getName(), 3);
                                addDistrictToCityMap(districtDetail.getName(), cityDetail.getId(), cityDetail.getName());
                            }
                        }
                    }
                }
            }
        }
        //整合数据
        sortData();
    }

    private void addCityToProvinceMap(String cityName, String id, String name) {
        AddressDetailInfo info = new AddressDetailInfo();
        info.setId(id);
        info.setName(name);
        cityMap.put(cityName, info);
    }

    private void addDistrictToCityMap(String districtName, String id, String name) {
        AddressDetailInfo info = new AddressDetailInfo();
        info.setId(id);
        info.setName(name);
        districtMap.put(districtName, info);
    }

    /**
     * 整合数据
     */
    private void sortData() {
        Collections.sort(mInfoList, new Comparator<AddressLocalFullItemInfo>() {
            @Override
            public int compare(AddressLocalFullItemInfo o1, AddressLocalFullItemInfo o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        //为显示数据添加字符标题
        List<AddressLocalFullItemInfo> mCacheList = new ArrayList<>();
        for (int i = 0; i < mInfoList.size() - 1; i++) {
            AddressLocalFullItemInfo info = mInfoList.get(i);
            mCacheList.add(mCacheList.size(), info);
            String titleStr = info.getTitle();
            if (i == 0) {
                AddressLocalFullItemInfo titleInfo = new AddressLocalFullItemInfo();
                titleInfo.setType(3);
                titleInfo.setTitle(titleStr);
                mCacheList.add(0, titleInfo);
            } else {
                AddressLocalFullItemInfo oldInfo = mInfoList.get(i - 1);
                String oldTitleStr = oldInfo.getTitle();
                if (!oldTitleStr.equals(titleStr)) {
                    AddressLocalFullItemInfo titleInfo = new AddressLocalFullItemInfo();
                    titleInfo.setType(3);
                    titleInfo.setTitle(titleStr);
                    mCacheList.add(mCacheList.size() - 1, titleInfo);
                }
            }
        }
        mInfoList.clear();
        mInfoList.addAll(mCacheList);
        //添加推荐数据
        List<AddressDetailInfo> recList = new ArrayList<>();
        for (int i = 0; i < mInfoList.size(); i++) {
            String name = mInfoList.get(i).getAddress();
            for (String recName : recCityList) {
                if (name.contains(recName)) {
                    AddressDetailInfo info = new AddressDetailInfo();
                    info.setId(mInfoList.get(i).getAddressId());
                    info.setName(name);
                    recList.add(info);
                }
            }
        }
        AddressLocalFullItemInfo recInfo = new AddressLocalFullItemInfo();
        recInfo.setType(1);
        recInfo.setSourceType(2);
        recInfo.setRecommendList(recList);
        mInfoList.add(0, recInfo);
    }

    /**
     * 添加全部数据列表的单个数据
     */
    private void addAllAddressInfo(String id, String name, int sourceType) {
        AddressLocalFullItemInfo allAddress = new AddressLocalFullItemInfo();
        allAddress.setType(2);
        allAddress.setSourceType(sourceType);
        AddressDetailInfo info = new AddressDetailInfo();
        info.setId(id);
        info.setName(name);
        allAddress.setAddressDetail(info);
        mAllInfoList.add(allAddress);
    }

    /**
     * 添加显示的数据集合
     */
    private void addShowAddressInfo(String id, String name, int sourceType) {
        AddressLocalFullItemInfo showAddress = new AddressLocalFullItemInfo();
        showAddress.setType(2);
        showAddress.setSourceType(sourceType);
        AddressDetailInfo info = new AddressDetailInfo();
        info.setId(id);
        info.setName(name);
        showAddress.setAddressDetail(info);
        showAddress.setTitle(AddressChineseUtils.getFirstLetter(name));
        mInfoList.add(showAddress);
    }

}
