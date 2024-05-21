package com.example.demo;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rep implements Serializable {
    private String subject;
    private String name;
    private String second_name;
    private int exp;
    private int inn;

    @Override
    public String toString() {
        return "Car{" +
                "subject='" + subject + '\'' +
                ", name='" + name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", exp=" + exp +
                ", inn='" + inn + '\'' +
                '}';
    }
}