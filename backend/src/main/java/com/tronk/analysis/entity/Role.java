package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "app_role")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends AbstractEntity<UUID> {
    String name;

    @ManyToMany(mappedBy = "roles")
    Collection<User> users = new HashSet<>();
}

