package tric.tricproject.Model;

import javax.persistence.*;

@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(name = "name")
    private String categoryName;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oppositeCategory", referencedColumnName = "categoryId")
    private Category oppositeCategory;


    public Category(long categoryId, String categoryName, Category oppositeCategory) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.oppositeCategory = oppositeCategory;
    }

    public Category(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category() {
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Category getOppositeCategory() {
        return oppositeCategory;
    }

    public void setOppositeCategory(Category oppositeCategory) {
        this.oppositeCategory = oppositeCategory;
    }
}
