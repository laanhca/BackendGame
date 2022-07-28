package com.av.backendgame.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.av.backendgame.Security.RSASecurity;

import java.util.HashMap;
import java.util.Map;

public class Table_UserData {

    //////////////////////////////////////////
    private static final String TABLENAME = "UserData";
    private static final String HASHKEY = "UserId";
    private static final String ATTRIBUTE_POINT = "Point";
    private static final String ATTRIBUTE_IP = "IP";
    private static final String MAP_INFO = "Information";
    private static final String INFO_LOGIN_TYPE = "LoginType";
    private static final String INFO_AVT_ID = "AvatarId";
    private static final String INFO_NAME = "NameShow";
    private static final int START_POINT = 10;
    private Table table;
    private static Table_UserData instance;
    private Table_UserData(){
        table = BaseDynamoDB.getInstance().dynamoDB.getTable(TABLENAME);
    }
    public static Table_UserData getInstance(){
        return instance = instance == null? new Table_UserData(): instance;
    }

    ////////////////////////////////////////////////
    public Item getItem(long userId){
        return table.getItem( new GetItemSpec().withPrimaryKey(HASHKEY, userId));
    }
    public void insertItem(long userId, int point, String ip, Map<String, Object> info){
        try {
            table.putItem(new Item().withPrimaryKey(HASHKEY, userId)
                    .withNumber(ATTRIBUTE_POINT, point)
                    .withString(ATTRIBUTE_IP, ip)
                    .withMap(MAP_INFO, info));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateItem(){

    }
    /////////////////////////////////////////////////

    public Object regWithUserName(String nameShow, long userId, String ip) {
        if(this.getItem(userId)!=null) return null;

        Map<String, Object> info = new HashMap<>();
        info.put(INFO_LOGIN_TYPE, LoginType.USERNAME.getValue());
        info.put(INFO_NAME, nameShow);
        info.put(INFO_AVT_ID, 0);
        this.insertItem(userId, START_POINT, ip, info);
        Map<String, Object> result = new HashMap<>();
        result.put(HASHKEY, userId);
        return result;
    }
    public Object regWithMail(String nameShow, long userId, String ip) {
        if(this.getItem(userId)!=null) return null;

        Map<String, Object> info = new HashMap<>();
        info.put(INFO_LOGIN_TYPE, LoginType.MAIL.getValue());
        info.put(INFO_NAME, nameShow);
        info.put(INFO_AVT_ID, 0);
        this.insertItem(userId, START_POINT, ip, info);
        Map<String, Object> result = new HashMap<>();
        result.put(HASHKEY, userId);
        return result;
    }

    public Object changeAvtId(long userId, int avatarId) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(HASHKEY, userId)
                .withUpdateExpression("SET "+ MAP_INFO + "." + INFO_AVT_ID + "= :avtId")
                .withValueMap(new ValueMap()
                        .withNumber(":avtId", avatarId))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        table.updateItem(updateItemSpec);
        Map<String, Object> result = new HashMap<>();
        result.put(INFO_AVT_ID, avatarId);
        return result;
    }

    public Object changeNameShow(long userId, String newName) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(HASHKEY, userId)
                .withUpdateExpression("SET "+ MAP_INFO + "." + INFO_NAME + "= :name")
                .withValueMap(new ValueMap()
                        .withString(":name", newName))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        table.updateItem(updateItemSpec);
        Map<String, Object> result = new HashMap<>();
        result.put(INFO_NAME, newName);
        return result;
    }
    public Object getNameShow(long userId){
        Item item= getItem(userId);
        if(item == null) return false;
        String name = item.getMap(MAP_INFO).get(INFO_NAME).toString();
        Map<String, Object> result = new HashMap<>();
        result.put(INFO_NAME, name);
        return result;
    }
    public Object getPoint(long userId){
        Item item= getItem(userId);
        if(item == null) return false;
        int point = item.getNumber(ATTRIBUTE_POINT).intValue();
        Map<String, Object> result = new HashMap<>();
        result.put(ATTRIBUTE_POINT, point);
        return result;
    }

    public Object updatePoint(long userId, int point) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(HASHKEY, userId)
                .withUpdateExpression("SET "+ ATTRIBUTE_POINT + "= :point")
                .withValueMap(new ValueMap()
                        .withNumber(":point", point))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        table.updateItem(updateItemSpec);
        Map<String, Object> result = new HashMap<>();
        result.put(ATTRIBUTE_POINT, point);
        return result;
    }
    public long getUserId(String token){
        Map<String, String> data = RSASecurity.getInstance().Decryption(token);
        if(data== null) return 0;
        long userId = Long.parseLong(data.get("UserId"));
        long timeOut = Long.parseLong(data.get("TimeOut"));
        long currentTime = System.currentTimeMillis();
        long deltaTime = (currentTime - timeOut)/1000;
        if(deltaTime > 86400){
            return 0;
        }
        return userId;
    }

}
