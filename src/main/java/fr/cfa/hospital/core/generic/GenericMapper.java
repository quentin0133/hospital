package fr.cfa.hospital.core.generic;

/**
 * Generic contract for mapping between persistence entities and data transfer objects.
 *
 * @param <E> the entity type
 * @param <D> the response DTO type
 * @param <P> the payload / request DTO type
 */
public interface GenericMapper<E, D, P> {

    /**
     * Converts a domain entity to its corresponding response DTO representation.
     *
     * @param entity the entity to map
     * @return the mapped response DTO
     */
    D toDto(E entity);

    /**
     * Converts an incoming payload DTO to its corresponding domain entity representation.
     *
     * @param dto the payload DTO to map
     * @return the mapped entity instance
     */
    E toEntity(P dto);
}