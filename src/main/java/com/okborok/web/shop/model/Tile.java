package com.okborok.web.shop.model;

import com.okborok.web.shop.enums.Categories;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created on 04.11.2017.
 */
@Getter
@Setter
@Entity
@Table(name = "TILE")
public class Tile {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 5)
    @Column(name = "TITLE")
    private String title;

    @Column(name = "CATEGORY")
    @Enumerated(value = EnumType.STRING)
    private Categories category;
}
