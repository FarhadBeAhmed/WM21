package co.wm21.https.interfaces;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.ShopTypeModel;
import co.wm21.https.FHelper.networks.Models.ShopTypeModelHead;

public interface OnShopTypeView {
    void onShopTypeDataLoad(ShopTypeModel shopTypeModel);

    void onShopTypeStartLoading();

    void onShopTypeStopLoading();

    void onShopTypeShowMessage(String errmsg);
}
