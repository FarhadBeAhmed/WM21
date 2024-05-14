package co.wm21.https.interfaces;

import com.google.gson.JsonArray;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.DrawerCatModel;

public interface OnDrawerCatListView {
    void onDrawerCatListDataLoad(List<DrawerCatModel> drawerCatModels);

    void onDrawerCatListStartLoading();

    void onDrawerCatListStopLoading();

    void onDrawerCatListShowMessage(String errmsg);
}
