package com.av.backendgame.dynamodb;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table_Leaderboard {
    private static final String TABLENAME = "Leaderboard";
    private static final String HASHKEY = "STT";
    private static final String ATTRIBUTE_NAMESHOW = "NameShow";
    private static final String ATTRIBUTE_POINT = "Point";

    private Table table;
    private static Table_Leaderboard instance;
    private Table_Leaderboard(){
        table = BaseDynamoDB.getInstance().dynamoDB.getTable(TABLENAME);
    }

    public static Table_Leaderboard getInstance(){
        return instance = instance == null? new Table_Leaderboard(): instance;
    }
    //////////////////////////////////////////////////////////////

    public Item getItem(int stt){
        return table.getItem( new GetItemSpec().withPrimaryKey(HASHKEY, stt));
    }

    public void insertItem(int stt, String name, int point){
        try {
            table.putItem(new Item().withPrimaryKey(HASHKEY, stt)
                            .withString(ATTRIBUTE_NAMESHOW, name)
                            .withNumber(ATTRIBUTE_POINT, point));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateItem(int stt, String nameShow, int point){
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(HASHKEY, stt)
                .withUpdateExpression("SET "+ ATTRIBUTE_NAMESHOW + "= :nameShow," + ATTRIBUTE_POINT + "= :point")
                .withValueMap(new ValueMap()
                        .withString(":nameShow", nameShow)
                        .withNumber(":point", point))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        table.updateItem(updateItemSpec);

    }
    ///////////////////////////////////////////////////////////////////////////////

    public void updateLeaderboard(String token, int point){
        long userId = Table_UserData.getInstance().getUserId(token);
        if(userId ==0) return;
        Map<String, Object> nameShow = (Map<String, Object>) Table_UserData.getInstance().getNameShow(userId);
        String name = nameShow.get(ATTRIBUTE_NAMESHOW).toString();

        if(checkIfUserIdInTopAndUpdate(name, point)) return;


        int itemNumber = getTotalItems() -1;
        if(itemNumber < 99){
            insertItem(itemNumber, name, point);
        }else{
            int lowestPoint = getLowestPoint();
            if(point < lowestPoint) return;
            updateItem(getSttOfLowestUser(lowestPoint),name, point);

        }

    }

    private boolean checkIfUserIdInTopAndUpdate(String name, int point) {
        for (int i = 0; i < 100; i++) {
            Item item = getItem(i);
            if(item == null) return false;
            if(name.equals(item.getString(ATTRIBUTE_NAMESHOW))) {
                updateItem(i, name, point );
                return true;
            }
        }
        return false;
    }

    public int getLowestPoint(){
        int[] points = new int[100];

        for (int i = 0; i < 100; i++) {
            Item item = getItem(i);
            points[i] = item.getNumber(ATTRIBUTE_POINT).intValue();
        }

        int n = points.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {

            for (int j = 1; j < (n - i); j++) {

                if (points[j - 1] > points[j]) {
                    temp = points[j - 1];
                    points[j - 1] = points[j];
                    points[j] = temp;

                }

            }
        }
        return points[0];

    }
    public int getSttOfLowestUser(int lowestPoint){
        for (int i = 0; i < 100; i++) {
            Item item = getItem(i);
            if(lowestPoint == item.getNumber(ATTRIBUTE_POINT).intValue()){
                return i;
            }
        }
        return 0;
    }
    public int getTotalItems(){
        for (int i = 0; i < 100; i++) {
            Item item = getItem(i);
            if(item == null) return i+1;

        }
        return 100;
    }
    public List<Map<String,String>> getLeaderboard(){
        ArrayList<Map<String,String>> listLeader = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Item item = getItem(i);
            if(item == null) return listLeader;
            Map<String, String> listLb = new HashMap<>();
            listLb.put(ATTRIBUTE_NAMESHOW, item.getString(ATTRIBUTE_NAMESHOW));
            listLb.put(ATTRIBUTE_POINT, item.getNumber(ATTRIBUTE_POINT).toString());
            listLeader.add(listLb);
        }
        return listLeader;
    }
}
