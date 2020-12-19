package com.joyce.reactive.redis.demo1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EmployeeModel implements Serializable {
    private static final long serialVersionUID = 1603714798906422731L;
    private String id;
    private String name;
    private String department;
}