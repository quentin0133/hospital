package fr.cfa.hospital.core.generic;

public interface GenericMapper<E, D, P> {
    D toDto(E entity);
    E toEntity(P dto);
}