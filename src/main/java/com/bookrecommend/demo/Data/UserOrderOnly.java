package com.bookrecommend.demo.Data;

import com.bookrecommend.demo.util.Utils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserOrderOnly {
    private Integer id;

    private Date createTime;

    private Date paymentTime;

    private Date deliverTime;

    private float totalMoney;

    private Integer totalNumber;

    private Boolean paymentStatus;

    private Boolean deliverStatus;

    private Boolean receiveStatus;

    private List<ShopingOrderOnly> shopingOrders;


    public String getCreateTime() {
        return Utils.Date2String(createTime, false);
    }

    public String getPaymentTime() {
        return Utils.Date2String(paymentTime, false);
    }

    public String getDeliverTime() {
        return Utils.Date2String(deliverTime, false);
    }

    public UserOrderOnly(Integer id, Date createTime, Date paymentTime, Date deliverTime, Boolean paymentStatus, Boolean deliverStatus, Boolean receiveStatus) {
        this.id = id;
        this.createTime = createTime;
        this.paymentTime = paymentTime;
        this.deliverTime = deliverTime;
        this.paymentStatus = paymentStatus;
        this.deliverStatus = deliverStatus;
        this.receiveStatus = receiveStatus;
    }

    public float getTotalMoney() {
        BigDecimal bigDecimal = new BigDecimal(totalMoney);
        return bigDecimal.setScale(2, BigDecimal.ROUND_UP).floatValue();
    }
}
