package org.jarivm.relationGraph.objects.repositories;

import org.jarivm.relationGraph.objects.domains.Sector;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Jari Van Melckebeke
 * @since 23.09.16
 */
public interface SectorRepository extends GraphRepository<Sector> {
}
