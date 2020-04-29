package com.bookrecommend.demo.util;

import java.util.*;

/**
 * Created by  on 2016/12/8.ShiYan
 * 一.计算所有物品对的偏差
 * 二.利用偏差进行预测
 */
public class SlopeOne {
    Map<Integer, Map<Integer, Integer>> frequency = null;
    Map<Integer, Map<Integer, Double>> deviation = null;
    Map<Integer, Map<Integer, Integer>> user_rating = null;

    public SlopeOne(Map<Integer, Map<Integer, Integer>> user_rating) {
        frequency = new HashMap<Integer, Map<Integer, Integer>>();
        deviation = new HashMap<Integer, Map<Integer, Double>>();
        this.user_rating = user_rating;
    }

    /**
     * 所有有item间的评分偏差
     */
    public void computeDeviation() {
        for (Map.Entry<Integer, Map<Integer, Integer>> ratingsEntry : user_rating.entrySet()) {
            for (Map.Entry<Integer, Integer> ratingEntry : ratingsEntry.getValue().entrySet()) {
                int item = ratingEntry.getKey();
                int rating = ratingEntry.getValue();
                Map<Integer, Integer> itemFrequency = null;
                if (!frequency.containsKey(item)) {
                    itemFrequency = new HashMap<Integer, Integer>();
                    frequency.put(item, itemFrequency);
                } else {
                    itemFrequency = frequency.get(item);
                }

                Map<Integer, Double> itemDeviation = null;
                if (!deviation.containsKey(item)) {
                    itemDeviation = new HashMap<Integer, Double>();
                    deviation.put(item, itemDeviation);
                } else {
                    itemDeviation = deviation.get(item);
                }

                for (Map.Entry<Integer, Integer> ratingEntry2 : ratingsEntry.getValue().entrySet()) {
                    int item2 = ratingEntry2.getKey();
                    int rating2 = ratingEntry2.getValue();
                    if (item != item2) {
                        //两个项目的用户数
                        itemFrequency.put(item2, itemFrequency.containsKey(item2) ? itemFrequency.get(item2) + 1 : 1);
                        //两个项目的评分偏差，累加
                        itemDeviation.put(item2, itemDeviation.containsKey(item2) ? itemDeviation.get(item2) + (rating - rating2) : rating - rating2);
                    }
                }
            }
        }

        for (Map.Entry<Integer, Map<Integer, Double>> itemsDeviation : deviation.entrySet()) {
            int item = itemsDeviation.getKey();
            Map<Integer, Double> itemDev = itemsDeviation.getValue();
            Map<Integer, Integer> itemFre = frequency.get(item);
            for (int itemName : itemDev.keySet()) {
                itemDev.put(itemName, itemDev.get(itemName) / itemFre.get(itemName));
            }
        }
    }

    /**
     * 评分预测
     *
     * @param userRating 目标用户的评分
     * @param k          返回前k个
     * @return
     */
    public List<Map.Entry<Integer, Double>> predictRating(Map<Integer, Integer> userRating, int k) {
        Map<Integer, Double> recommendations = new HashMap<Integer, Double>();
        Map<Integer, Integer> frequencies = new HashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> userEntry : userRating.entrySet()) {
            int userItem = userEntry.getKey();
            double rating = userEntry.getValue();
            for (Map.Entry<Integer, Map<Integer, Double>> deviationEntry : deviation.entrySet()) {
                int item = deviationEntry.getKey();
                Map<Integer, Double> itemDeviation = deviationEntry.getValue();
                Map<Integer, Integer> itemFrequency = frequency.get(item);
                if (!userRating.containsKey(item) && itemDeviation.containsKey(userItem)) {
                    int fre = itemFrequency.get(userItem);
                    if (!recommendations.containsKey(item))
                        recommendations.put(item, 0.0);
                    if (!frequencies.containsKey(item))
                        frequencies.put(item, 0);
                    //分子部分
                    recommendations.put(item, recommendations.get(item) + (itemDeviation.get(userItem) + rating) * fre);
                    //分母部分
                    frequencies.put(item, frequencies.get(item) + fre);
                }
            }
        }
        for (Map.Entry<Integer, Double> recoEntry : recommendations.entrySet()) {
            int key = recoEntry.getKey();
            double value = recoEntry.getValue() / frequencies.get(key);
            recommendations.put(key, value);
        }
        //排序，这里还可以使用优先队列返回top_k
        List<Map.Entry<Integer, Double>> list_map = new ArrayList<Map.Entry<Integer, Double>>(recommendations.entrySet());
        Collections.sort(list_map, new Comparator<Map.Entry<Integer, Double>>() {
                    @Override
                    public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                        if (o2.getValue() > o1.getValue())
                            return 1;
                        else if (o2.getValue() < o1.getValue())
                            return -1;
                        else
                            return 0;
                    }
                }
        );
        List<Map.Entry<Integer, Double>> top_k = new ArrayList<Map.Entry<Integer, Double>>();
        if (list_map.size() < k) k = list_map.size();
        for (int i = 0; i < k; i++) {
            top_k.add(list_map.get(i));
        }
        return top_k;
    }

//    public static void main(String[] args){
//        Map<Integer,Map<Integer,Integer>> userRatings=new HashMap<Integer, Map<Integer, Integer>>();
//        Map<Integer,Integer> xiMingRating=new HashMap<Integer, Integer>();
//        xiMingRating.put(1,4);
//        xiMingRating.put(2,3);
//        xiMingRating.put(3,4);
//        Map<Integer,Integer> xiHaiRating=new HashMap<Integer, Integer>();
//        xiHaiRating.put(1,5);
//        xiHaiRating.put(2,2);
//        Map<Integer,Integer> liMeiRating=new HashMap<Integer, Integer>();
//        liMeiRating.put(2,3);
//        liMeiRating.put(3,4);
//        Map<Integer,Integer> liLeiRating=new HashMap<Integer, Integer>();
//        liLeiRating.put(1,5);
//        liLeiRating.put(3,3);
//
//        userRatings.put(1,xiMingRating);
//        userRatings.put(2,xiHaiRating);
//        userRatings.put(3,liMeiRating);
//        userRatings.put(4,liLeiRating);
//
//        SlopeOne slopOne=new SlopeOne(userRatings);
//        slopOne.computeDeviation();
//        List<Map.Entry<Integer,Double>> top_k=slopOne.predictRating(userRatings.get(4),5);
//        for(Map.Entry<Integer,Double> item:top_k){
//            System.out.println(item.getKey()+"   "+item.getValue());
//        }
//    }
}