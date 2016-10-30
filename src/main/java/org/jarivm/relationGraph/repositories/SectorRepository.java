package org.jarivm.relationGraph.repositories;

import org.jarivm.relationGraph.domains.Client;
import org.jarivm.relationGraph.domains.Project;
import org.jarivm.relationGraph.domains.Sector;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jari Van Melckebeke
 * @since 02.10.16
 */
@Repository
public interface SectorRepository extends GraphRepository<Sector> {
    @Query("MATCH (m:Sector) WHERE m.name={name} return m limit 1")
    LinkedHashMap<String, Object> findByName(@Param("name") String name);

    Collection<Project> findByNameContaining(@Param("0") String name);

    @Query("MATCH (m:Sector)-[IS_SECTOR_FOR]->(a:Client) RETURN m.name as sector, collect(a.name) as clients")
    List<Map<String, Sector>> graph(@Param("l") int l);

    @Query("MATCH (n:Sector) return keys(n) limit 1")
    String[] findProperties();

    @Query("MATCH (m:Sector) WHERE ANY(prop in keys(m) where prop={propertyKey} and m[prop] contains {propertyValue}) RETURN m;")
    Iterable<Sector> findByProperty(@Param("propertyKey") String propertyName, @Param("propertyValue") String propertyValue);

}
