package co.wm21.https.helpers;

import static co.wm21.https.BuildConfig.VERSION_NAME;

import android.net.Uri;

/**
 * Making a interface instead of making static final class because a interface always static, final,
 * public.<br/>
 * For Example
 * <pre>
 * public interface Link {
 *     {@link String} MAKE = "make";
 *     {@link String} REMOVE = "remove";
 *     {@link String} ACTION = "action";
 * }
 * </pre>
 * <p>
 * In static final class
 * <pre>
 * public static final class Link {
 *     public static final {@link String} MAKE = "make";
 *     public static final {@link String} REMOVE = "remove";
 *     public static final {@link String} ACTION = "action";
 * }
 * </pre>
 */
interface ConstantValues {
    String web_url = "https://wm21.net/";
    String URL = "https://www.wm21.co/";
    String KEY_EMPTY = "";
    String APP_VER = VERSION_NAME;
    String ID = "id";
    String DATE = "date";
    String TIME = "time";
    String USERNAME = "username";
    String PASSWORD = "password";
    String NOTICE = "notice";
    String GRID_LAYOUT = "grid";
    String MENU_LAYOUT = "menu";
    String RESULT = "result";
    String LIMIT = "limit";

    interface Login {
        String STATUS = "error";
        String MESSAGE = "msg";
        String TYPE = "type";
    }

    interface Category {
        String CATEGORY_ID = "cat_id";
        String SUB_CATEGORY_ID = "scat_id";
        String BRAND_ID = "brand_id";
        String ORDERING = "ordering ";

        String CATEGORY_NAME = "cat";
        String SUB_CATEGORY_NAME = "scat";
        String SUB_CATEGORY_IMAGE = "scat_img";
        String CATEGORY_IMAGE = "cat_img";
        String BRAND_NAME = "brand";
    }


    interface Balance {
        interface Affiliate {
            String MY_PROMOTIONAL_EARNING = "my_promotional_earning";
            String OWN_PRIMARY_EARNING = "own_primary_earning";
            String OWN_ADVANCE_EARNING = "own_advance_earning";
            String TEAM_PRIMARY_EARNING = "team_primary_earning";
            String TEAM_ADVANCE_EARNING = "team_advance_earning";
            String TEAM_EXTRA_EARNING = "team_extra_earning";
            String TOTAL_AFFILIATE_EARNING = "total_affiliate_earning";
            String TOTAL_MOBILE_RECHARGE = "total_mobile_recharge";
            String TOTAL_WITHDRAWAL_BALANCE = "total_withdrawal_balance";
            String CURRENT_WALLET_BALANCE = "current_wallet_balance";
        }

        interface MLM {
            String MY_PROMOTIONAL_EARNING = "my_promotional_earning";
            String OWN_PRIMARY_EARNING = "own_primary_earning";
            String OWN_ADVANCE_EARNING = "own_advance_earning";
            String TEAM_PRIMARY_EARNING = "team_primary_earning";
            String TEAM_ADVANCE_EARNING = "team_advance_earning";
            String TEAM_EXTRA_EARNING = "team_extra_earning";
            String TOTAL_AFFILIATE_EARNING = "total_affiliate_earning";
            String TOTAL_MOBILE_RECHARGE = "total_mobile_recharge";
            String TOTAL_WITHDRAWAL_BALANCE = "total_withdrawal_balance";
            String CURRENT_WALLET_BALANCE = "current_wallet_balance";
        }
    }

    interface Profile {

            String NAME = "name";
            String IMAGE = "img";
            String DATE = "date";
            String RATING = "rating";
            String REVIEW = "review";
            String MOBILE = "mobile";
            String EMAIL = "email";
            String ID_CARD_TYPE = "id_card_type";
            String ID_CARD_INFO = "id_card_info";
            String Country = "country";


        interface MLM {

        }
    }

    interface Slide {
        String IMAGE = "img1";
        String INFO = "info";
    }

    interface Product {
        String NAME = "name";
        String OFFER_DATE = "offer_date";
        String UPLOAD_BY = "upload_by";
        String IMAGE = "img";
        String PREVIOUS_PRICE = "sprice";
        String PRICE = "price";
        String DISCOUNT = "discount";
        String PRODUCT_ID = "serial";
        String LIMIT = "limit";
        String PARCEL = "parcel";
        String VALUE = "value";
        String POINT = "point";
    }

    interface PopularProduct {
        String NAME = "name";
        String IMAGE = "img";
        String PREVIOUS_PRICE = "sprice";
        String PRICE = "price";
        String DISCOUNT = "discount";
        String PRODUCT_ID = "serial";
        String PARCEL = "parcel";
        String UPLOAD_BY = "upload_by";
        String OFFERED_DATE = "offer_date";
        String POINT = "point";
    }
}