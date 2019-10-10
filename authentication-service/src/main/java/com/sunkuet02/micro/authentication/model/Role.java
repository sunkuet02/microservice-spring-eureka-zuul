package com.sunkuet02.micro.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_name",
                        columnNames = {"name"}
                )
        }
)
public class Role extends BaseEntity implements GrantedAuthority {

    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @Override
    public String getAuthority() {
        return name;
    }
}
