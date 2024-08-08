package vn.hoidanit.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
    public static Specification<Product> nameLike(String name){
        return (root, query, criteriaBuilder)
        -> criteriaBuilder.like(root.get(Product_.NAME), "%"+name+"%");
    }
    public static Specification<Product> minPrice(double minPrice){
        return (root, query, criteriaBuilder)
        -> criteriaBuilder.greaterThanOrEqualTo(root.get(Product_.PRICE), minPrice);
    }

    public static Specification<Product> maxPrice(double maxPrice){
        return (root, query, criteriaBuilder)
        -> criteriaBuilder.lessThanOrEqualTo(root.get(Product_.PRICE), maxPrice);
    }

    public static Specification<Product> findFactory(String factory){
        return (root, query, criteriaBuilder)
        -> criteriaBuilder.equal(root.get(Product_.FACTORY), factory);
    }

    public static Specification<Product> findMultiFactory (List<String> factory){
        return (root, query, criteriaBuilder)
        -> root.get(Product_.FACTORY).in(factory);
    }

    public static Specification<Product> rangePrice (double minPrice, double maxPrice){
        return (root, query, criteriaBuilder)
        -> criteriaBuilder.between(root.get(Product_.PRICE), minPrice, maxPrice);
    }

    public static Specification<Product> findMultiTargets (List<String> target){
        return (root, query, criteriaBuilder)
        -> root.get(Product_.TARGET).in(target);
    }
}
