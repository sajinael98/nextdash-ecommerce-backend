package com.saji.dashboard_backend.shared.entites;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class SubEntity extends AuditableEmbeddable implements Serializable {
}