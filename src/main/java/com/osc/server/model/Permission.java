package com.osc.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Kerisnarendra on 24/04/2019.
 */

@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Permission extends BaseModel{
    @ManyToMany(mappedBy = "permissions")
    private Collection<Role> roles;

    @Column(nullable=false)
    private String permissionName;
    private String permissionDescription;
}
