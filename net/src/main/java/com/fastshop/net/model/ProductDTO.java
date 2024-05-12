package com.fastshop.net.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;

    private String category;

    private double price;

    private Integer number;

    private String imageName;
}
