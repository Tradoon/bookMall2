package com.tradoon.bookMall.api.Enum;

/**
 * author:tradoon
 * desciption: 出版社类型
 * date:2022/ / /
 */
public enum PublishingHouseType {
    PEOPEL_PUBLISH_HOUSE(1,"人民出版社"),
    MUSIC_PUBLISH_HOUSE(2,"音乐出版社"),
    ART_PUBLISH_HOUSE(3,"美术出版社"),
    EDU_PUBLISH_HOUSE(4,"教育出版社"),
    TEC_PUBLISH_HOUSE(5,"科技出版社"),
    AREA_PUBLISH_HOUSE(6,"地方出版社");

    private int type;
    private String typeName;
    PublishingHouseType(int type,String typeName){
        this.type=type;
        this.typeName=typeName;
    }
    public String getName(){
        return this.typeName;
    }

    public int getType(){
        return this.type;
    }

    // 根据type 获得name
    public  static String getTypeNameByType(int type){
        for(PublishingHouseType typeItem:PublishingHouseType.values()){
            if(typeItem.getType()==type)
                return typeItem.getName();
        }
        return null;
    }


}
