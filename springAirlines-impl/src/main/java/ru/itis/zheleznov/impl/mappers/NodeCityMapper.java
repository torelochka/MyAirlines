package ru.itis.zheleznov.impl.mappers;

import org.neo4j.driver.types.Node;
import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.impl.models.City;

public class NodeCityMapper {

    public static CityDto map(Node node) {
        return CityDto.builder()
                .id(node.get("postgre_id").asLong())
                .name(node.get("name").asString())
                .build();
    }
}
