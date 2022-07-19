package com.av.backendgame.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.HashMap;
import java.util.Map;

public class Table_UserData {

    //////////////////////////////////////////
    private static final String TABLENAME = "UserData";
    private static final String HASHKEY = "UserId";
    private static final String ATTRIBUTE_POINT = "Point";
    private static final String ATTRIBUTE_IP = "IP";
    private static final String MAP_INFO = "Information";
    private static final String INFO_LOGINTYPE = "LoginType";
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
        if(this.getItem(userId)!=null) return false;

        Map<String, Object> info = new HashMap<>();
        info.put(INFO_LOGINTYPE, LoginType.USERNAME.getValue());
        info.put(INFO_NAME, nameShow);
        info.put(INFO_AVT_ID, 0);
        this.insertItem(userId, START_POINT, ip, info);
        return true;
    }

}
