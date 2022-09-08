package com.jpa.secondlevelcache.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;


@NamedQueries({
        @NamedQuery(name = "Country.findTotalCount", query = "Select count(c) from Country c",
                hints = @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String countryName;

    @org.hibernate.annotations.Cache(
            usage = CacheConcurrencyStrategy.READ_WRITE
    )
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "country",
            cascade = ALL,
            orphanRemoval = true
    )
    private Set<City> cities;
}
