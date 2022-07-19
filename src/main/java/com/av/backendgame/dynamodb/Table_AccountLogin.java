package com.av.backendgame.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.av.backendgame.api.controller.service.loginscreen.MailSender;
import org.apache.juli.logging.Log;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
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
    private static final String INFO_CODE = "VerifyCode";
    private static final String INFO_CODE_TIME = "VerifyTime";
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
    public void updateVerifyCode(String mail, int code, long time){
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(HASHKEY, mail, RANGEKEY, LoginType.MAIL.getValue())
                .withUpdateExpression("SET " + MAP_INFO + "." + INFO_CODE + " = :code, " + MAP_INFO + "." + INFO_CODE_TIME + " = :codeTime")
                .withValueMap(new ValueMap()
                        .withNumber(":code", code)
                        .withNumber(":codeTime", time))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        table.updateItem(updateItemSpec);
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

    public Object sendMail(String mail, int code, String ip) {
        Item currentAccount = this.getItem(mail, LoginType.MAIL.getValue());
        if(currentAccount!= null) {
            long verifyTime = ((BigDecimal)currentAccount.getMap(MAP_INFO).get(INFO_CODE_TIME)).longValue();
            long currentTime = System.currentTimeMillis();
            long deltaTime = (currentTime - verifyTime)/1000;
            if(deltaTime> 180){

                this.updateVerifyCode(mail, code, System.currentTimeMillis());
                return true;
            }
            return false;
        }
        long userId = System.currentTimeMillis();
        Map<String, Object> info = new HashMap<>();
        info.put(INFO_CODE, code);
        info.put(INFO_CODE_TIME, userId);
        this.insertItem(mail, LoginType.MAIL.getValue(), userId , info);

        String nameShow = mail.split("@")[0] + (int)Math.floor(Math.random()*(9999-1000+1)+1000);
        Table_UserData.getInstance().regWithMail(nameShow, userId, ip);
        return userId;
    }

    public Object loginWithMail(String mail, int code) {
        Item itemAcc = this.getItem(mail, LoginType.MAIL.getValue());
        if(itemAcc == null) return false;
        Item itemUser = Table_UserData.getInstance().getItem(((BigDecimal)itemAcc.getNumber(ATTRIBUTE_USER_ID)).longValue());
        if(itemUser==null) return false;
        int originCode= ((BigDecimal)itemAcc.getMap(MAP_INFO).get(INFO_CODE)).intValue();
        return code== originCode;
    }
}
