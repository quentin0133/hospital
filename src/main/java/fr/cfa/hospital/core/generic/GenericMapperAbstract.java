package fr.cfa.hospital.core.generic;

/**
 * Abstract base class providing mapping contracts between domain entities and data transfer objects.
 * Intended to be extended by custom mapper implementations or used alongside annotation processors such as MapStruct.
 *
 * @param <E> the entity type
 * @param <D> the response DTO type
 * @param <P> the payload / request DTO type
 */
public abstract class GenericMapperAbstract<E, D, P> {

    /**
     * Converts a domain entity to its corresponding response DTO representation.
     *
     * @param entity the entity to map
     * @return the mapped response DTO
     */
    public abstract D toDto(E entity);

    /**
     * Converts an incoming payload DTO to its corresponding domain entity representation.
     *
     * @param dto the payload DTO to map
     * @return the mapped entity instance
     */
    public abstract E toEntity(P dto);
}