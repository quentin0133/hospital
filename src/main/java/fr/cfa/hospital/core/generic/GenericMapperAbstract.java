package fr.cfa.hospital.core.generic;

public abstract class GenericMapperAbstract<E, D, P> {
    public abstract D toDto(E entity);
    public abstract E toEntity(P dto);
}