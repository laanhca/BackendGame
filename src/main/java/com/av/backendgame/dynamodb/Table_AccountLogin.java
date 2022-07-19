package com.av.backendgame.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class Table_AccountLogin {


    //////////////////////////////////////////////////////////////////
    private static final String TABLENAME = "AccountLogin";
    private static final String HASHKEY = "Credentials";
    private static final String RANGEKEY = "LoginType";
    private static final String ATTRIBUTE_USER_ID = "UserId";
    private static final String MAP_INFO = "Information";
    private static final String INFO_PASS = "Password";
    private static final String INFO_DEVICE = "DeviceModel";



    private static Table_AccountLogin instance;
    private Table table;

    private Table_AccountLogin(){
        table = BaseDynamoDB.getInstance().dynamoDB.getTable(TABLENAME);
    }
    public static final Table_AccountLogin getInstance(){
        return instance= instance == null? new Table_AccountLogin(): instance;
    }
/////////////////////////////////////////////////////////////////////////
    public Item getItem(String credentials, int loginType){
        return table.getItem( new GetItemSpec().withPrimaryKey(HASHKEY, credentials, RANGEKEY, loginType));
    }
    public void insertItem(String credentials, int loginTpe, long userId, Map<String, Object> info){
        try {
            table.putItem(new Item().withPrimaryKey(HASHKEY, credentials, RANGEKEY, loginTpe)
                    .withNumber(ATTRIBUTE_USER_ID, userId)
                    .withMap(MAP_INFO, info));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateItem(){

    }
///////////////////////////////////////////////////////////////////////////
    public Object loginWithUserName(String credentials, String password) {
        Item account = table.getItem( new GetItemSpec().withPrimaryKey(HASHKEY, credentials, RANGEKEY, LoginType.USERNAME.getValue()));
        return account.getMap(MAP_INFO).get(INFO_PASS).equals(password);
    }

    public Object regWithUserName(String credentials, String password, String ip) {

        if(this.getItem(credentials,LoginType.USERNAME.getValue())!= null) return false;
        Map<String, Object> info = new HashMap<>();
        info.put(INFO_PASS, password);
        long userId = System.currentTimeMillis();
        this.insertItem(credentials, LoginType.USERNAME.getValue(), userId , info);

        return Table_UserData.getInstance().regWithUserName(credentials, userId, ip);

    }

    public Object sendMail(String mail, int code) {
        if(this.getItem(mail, LoginType.MAIL.getValue())!= null) return null;
        long userid = System.currentTimeMillis();
        Map<String, Object> info = new HashMap<>();
        long userId = System.currentTimeMillis();
        this.insertItem(mail, LoginType.MAIL.getValue(), userId , info);
        return userid;
    }
}
