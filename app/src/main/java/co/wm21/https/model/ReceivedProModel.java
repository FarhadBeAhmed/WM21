package co.wm21.https.model;

public class ReceivedProModel {

    String serial,date,pin;
    int count,point,used;

    public ReceivedProModel(String serial, String date, int used, int point, String pin, int count) {
        this.serial = serial;
        this.used = used;
        this.date = date;
        this.point = point;
        this.pin = pin;
        this.count = count;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
