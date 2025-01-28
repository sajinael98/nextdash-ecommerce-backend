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

    @Column(columnDefinition = "INT", name = "create_r")
    private boolean create;

    @Column(columnDefinition = "INT", name = "read_r")
    private boolean read;

    @Column(columnDefinition = "INT", name = "update_r")
    private boolean update;

    @Column(columnDefinition = "INT", name = "delete_r")
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
