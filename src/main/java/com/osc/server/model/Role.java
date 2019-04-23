package com.osc.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * Created by Kerisnarendra on 23/04/2019.
 */

@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role extends BaseModel{
    private String roleName;
    private String roleDescription;
}
