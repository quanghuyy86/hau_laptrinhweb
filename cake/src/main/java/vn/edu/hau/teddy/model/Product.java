package vn.edu.hau.cake.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_products")
public class Product extends BaseEntity{
    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Column(name = "price", precision = 13, nullable = false)
    private BigDecimal price;

    @Column(name = "price_sale", precision = 13, nullable = false)
    private BigDecimal priceSale;

    @Column(name = "short_description", length = 3000, nullable = false)
    private String shortDes;

    @Lob
    @Column(name = "detail_description", nullable = false, columnDefinition = "LONGTEXT")
    private String detail;

    @Column(name = "avatar", nullable = true)
    private String avatar;

    @Column(name = "is_hot", nullable = true)
    private Boolean isHot = false;

    @Column(name = "seo", length = 1000, nullable = true)
    private String seo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id") //khóa ngoại chỉ đến khóa chính
    private Categories categories;

    public void addProductImages(ProductImages _productImages) {
        _productImages.setProduct(this);
        productImages.add(_productImages);
    }

    public Set<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(Set<ProductImages> productImages) {
        this.productImages = productImages;
    }

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "product")
    private Set<ProductImages> productImages = new HashSet<ProductImages>();
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "product")
    private Set<SaleOrderProducts> saleOrderProducts = new HashSet<SaleOrderProducts>();


    public Set<SaleOrderProducts> getSaleOrderProducts() {
        return saleOrderProducts;
    }

    public void setSaleOrderProducts(Set<SaleOrderProducts> saleOrderProducts) {
        this.saleOrderProducts = saleOrderProducts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal priceSale) {
        this.priceSale = priceSale;
    }

    public String getShortDes() {
        return shortDes;
    }

    public void setShortDes(String shortDes) {
        this.shortDes = shortDes;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }
    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }
}
