package com.perficient.praxis.gildedrose.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @NotBlank(message = "Name is mandatory")
    public String name;

    @NotNull(message = "sellIn is mandatory")
    public Integer sellIn;

    @NotNull(message = "quality is mandatory")
    @Min(0)
    @Max(80)
    public Integer quality;

    public Type type;

    public Item() {
    }

    public Item(int id, String name, int sellIn, int quality, Type type) {
        this.id = id;
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public enum Type {
        AGED,
        NORMAL,
        LEGENDARY,
        TICKETS
    }

    @Override
    public String toString() {
        return this.id+ ", " +this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
