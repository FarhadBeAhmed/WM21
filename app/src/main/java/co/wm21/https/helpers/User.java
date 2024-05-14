package co.wm21.https.helpers;

import android.content.Context;

import java.util.Date;

import co.wm21.https.ProjectApp;

public class User {
    String username;
    Date sessionExpiryDate;
    boolean remember;
    String password;
    String memberType;
    String mobile;
    String treeId;
    String userId;
    private SessionHandler session;

    public User() { this(ProjectApp.getContext()); }

    public User(String username, Date sessionExpiryDate, boolean remember, String password, String MemberType,String mobile,String treeId,String userId) {
        setAll(username, sessionExpiryDate, remember, password, MemberType,mobile,treeId,userId);
    }

    public User(SessionHandler session) {
        this.session = session;
        User user = session.getUserDetails();
        setAll(user.getUsername(), user.getSessionExpiryDate(), user.getRemember(), user.getPassword(), user.getMemberType(),user.getMobile(),user.getTreeId(),user.getUserId());
    }

    public User(Context applicationContext) {
        this(new SessionHandler(applicationContext));
    }

    public SessionHandler getSession() {
        return session;
    }

    public User setAll(String username, Date sessionExpiryDate, boolean remember, String password, String MemberType,String mobile,String treeId,String userId) {
        return setUsername(username).setSessionExpiryDate(sessionExpiryDate).setRemember(remember).setPassword(password).setMemberType(MemberType).setMobile(mobile).setTreeId(treeId).setUserId(userId);
    }

    public User setUsername(String username) { this.username = username; return this; }
    public User setSessionExpiryDate(Date sessionExpiryDate) { this.sessionExpiryDate = sessionExpiryDate; return this; }
    public User setRemember(boolean remember) { this.remember = remember; return this; }
    public User setPassword(String password) { this.password = password; return this; }
    public User setMemberType(String memberType) { this.memberType = memberType; return this; }
    public User setMobile(String mobile) {this.mobile = mobile;return this;}
    public User setTreeId(String treeId) {this.treeId = treeId;return this;}
    public User setUserId(String userId) {this.userId = userId;return this;}

    public Object[] getAll() {
        return new Object[] { getUsername(), getSessionExpiryDate(), getRemember(), getPassword(), getMemberType() };
    }

    public String getUsername() { return username; }
    public Date getSessionExpiryDate() { return sessionExpiryDate; }
    public boolean getRemember() { return remember; }
    public String getPassword() { return password; }
    public String getMemberType() { return memberType; }
    public String getMobile() {return mobile;}
    public String getTreeId() {return treeId;}
    public String getUserId() {return userId;}


}
