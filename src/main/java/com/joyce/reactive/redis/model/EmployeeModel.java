package com.joyce.reactive.redis.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@Data
@Builder
public class EmployeeModel implements Serializable {
    private String id;
    private String name;
    private String department;
}
