package com.shopping.integral.dao.model;

import com.shopping.base.domain.IdEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor  //空的构造函数
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "integral_accessory")
public class IntegralAccessory extends IdEntity {

    /**
     * name : 图片名称
     */
    private String name;

    /**
     * path : 图片路径
     */
    private String path;

    /**
     * size : 图片大小
     */
    private float size;

    /**
     * width : 图片宽度
     */
    private int width;

    /**
     * height : 图片高度
     */
    private int height;

    /**
     * ext : 图片类型
     */
    private String ext;

    /**
     * info : 图片描述
     */
    private String info;

    @ManyToMany(mappedBy = "goodsPhotos")
    private List<IntegralGoods> integralGoodsList = new ArrayList();
}
