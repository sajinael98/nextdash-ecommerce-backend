package com.saji.dashboard_backend.modules.user_managment.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Permission {
    @Column(nullable = false)
    private String resource;

    @Column(name = "create_resource", columnDefinition = "INT")
    private boolean create;

    @Column(name = "read_resource", columnDefinition = "INT")
    private boolean read;

    @Column(name = "update_resource", columnDefinition = "INT")
    private boolean update;

    @Column(name = "delete_resource", columnDefinition = "INT")
    private boolean delete;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Permission))
            return false;
        Permission permission = (Permission) o;
        return resource.equalsIgnoreCase(permission.resource);
    }


    @Override
    public int hashCode() {
        return Objects.hash(resource);
    }

    public boolean isValid() {
        return create || read || update || delete;
    }
}
