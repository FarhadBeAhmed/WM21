package co.wm21.https.presenter;

import java.util.List;

import co.wm21.https.FHelper.networks.Models.AddToCartModel;
import co.wm21.https.FHelper.networks.Models.BlogsModel;
import co.wm21.https.interfaces.OnAddToCartRequestComplete;
import co.wm21.https.interfaces.OnAddToCartView;
import co.wm21.https.interfaces.OnBlogListRequestComplete;
import co.wm21.https.interfaces.OnBlogListView;
import co.wm21.https.serviceapis.InvokeAddToCartApi;
import co.wm21.https.serviceapis.InvokeBlogListApi;

public class AddToCartPresenter {
    OnAddToCartView addToCartView;

    public AddToCartPresenter(OnAddToCartView addToCartView) {
        this.addToCartView = addToCartView;
    }

    public void AddToCartDataLoad(String pId,String userId,String color,String size,int qty) {
        addToCartView.onAddToCartStartLoading();
        new InvokeAddToCartApi(pId,userId,color,size,qty, new OnAddToCartRequestComplete() {
            @Override
            public void onAddToCartRequestComplete(Object obj) {
                addToCartView.onAddToCartStopLoading();
                addToCartView.onAddToCartDataLoad((AddToCartModel) obj);
            }

            @Override
            public void onAddToCartRequestError(String errMsg) {
                addToCartView.onAddToCartStopLoading();
                addToCartView.onAddToCartShowMessage(errMsg);
            }
        });
    }
}
