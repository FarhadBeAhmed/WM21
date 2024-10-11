package co.wm21.https.FHelper.networks.Models.balanceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceData {
    @SerializedName("netBalance")
    @Expose
    private String netBalance;
    @SerializedName("withdraw")
    @Expose
    private String withdraw;
    @SerializedName("direct")
    @Expose
    private String direct;
    @SerializedName("match")
    @Expose
    private String match;
    @SerializedName("bpi")
    @Expose
    private String bpi;
    @SerializedName("bdp")
    @Expose
    private String bdp;
    @SerializedName("bar")
    @Expose
    private String bar;
    @SerializedName("exr")
    @Expose
    private String exr;
    @SerializedName("inc")
    @Expose
    private String inc;
    @SerializedName("flexi")
    @Expose
    private String flexi;
    @SerializedName("cen_trx")
    @Expose
    private String cenTrx;
    @SerializedName("past")
    @Expose
    private String past;
    @SerializedName("total_income")
    @Expose
    private String totalIncome;
    @SerializedName("total_expense")
    @Expose
    private String totalExpense;
    @SerializedName("top_earner_incentive")
    @Expose
    private String topEarnerIncentive;
    @SerializedName("shop_refer_commission")
    @Expose
    private String shopReferCommission;

    public String getNetBalance() {
        return netBalance;
    }

    public void setNetBalance(String netBalance) {
        this.netBalance = netBalance;
    }

    public String getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(String withdraw) {
        this.withdraw = withdraw;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getBpi() {
        return bpi;
    }

    public void setBpi(String bpi) {
        this.bpi = bpi;
    }

    public String getBdp() {
        return bdp;
    }

    public void setBdp(String bdp) {
        this.bdp = bdp;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getExr() {
        return exr;
    }

    public void setExr(String exr) {
        this.exr = exr;
    }

    public String getInc() {
        return inc;
    }

    public void setInc(String inc) {
        this.inc = inc;
    }

    public String getFlexi() {
        return flexi;
    }

    public void setFlexi(String flexi) {
        this.flexi = flexi;
    }

    public String getCenTrx() {
        return cenTrx;
    }

    public void setCenTrx(String cenTrx) {
        this.cenTrx = cenTrx;
    }

    public String getPast() {
        return past;
    }

    public void setPast(String past) {
        this.past = past;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(String totalExpense) {
        this.totalExpense = totalExpense;
    }

    public String getTopEarnerIncentive() {
        return topEarnerIncentive;
    }

    public void setTopEarnerIncentive(String topEarnerIncentive) {
        this.topEarnerIncentive = topEarnerIncentive;
    }

    public String getShopReferCommission() {
        return shopReferCommission;
    }

    public void setShopReferCommission(String shopReferCommission) {
        this.shopReferCommission = shopReferCommission;
    }
}
