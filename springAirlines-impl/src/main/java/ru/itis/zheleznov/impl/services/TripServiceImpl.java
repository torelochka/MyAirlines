package ru.itis.zheleznov.impl.services;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.zheleznov.api.dto.CityDto;
import ru.itis.zheleznov.api.dto.TripDto;
import ru.itis.zheleznov.api.services.TripService;
import ru.itis.zheleznov.impl.mappers.NodeCityMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService {

    private static final String CYPHER_DIRECT_TRIP = "match t = (atyrau:City {name: '?from?'})-[*1]->(london:City {name:'?to?'})" +
            " with nodes(t) as cities, " +
            "reduce(totalCost = 0, p IN relationships(t) | totalCost + p.cost) AS cost, " +
            "reduce(totalTime = 0, ti IN relationships(t) | totalTime + ti.time) AS time " +
            "return cities, cost, time order by time limit 1";

    private static final String CYPHER_FASTEST_TRIP = "match t = (atyrau:City {name: '?from?'})-[*1..4]->(london:City {name:'?to?'})" +
            " with nodes(t) as cities, " +
            "reduce(totalCost = 0, p IN relationships(t) | totalCost + p.cost) AS cost, " +
            "reduce(totalTime = 0, ti IN relationships(t) | totalTime + ti.time) AS time " +
            "return cities, cost, time order by time limit 1";

    private static final String CYPHER_CHEAPEST_TRIP = "match t = (atyrau:City {name: '?from?'})-[*1..4]->(london:City {name:'?to?'})" +
            " with nodes(t) as cities, " +
            "reduce(totalCost = 0, p IN relationships(t) | totalCost + p.cost) AS cost, " +
            "reduce(totalTime = 0, ti IN relationships(t) | totalTime + ti.time) AS time " +
            "return cities, cost, time order by cost limit 1";

    private static final String CYPHER_COUNT = "match (n:City) return count(n) as countCities";

    private final Driver driver;

    @Autowired
    public TripServiceImpl(Driver driver) {
        this.driver = driver;
    }

    @Override
    public TripDto cheapestTrip(CityDto from, CityDto to) {
        try (Session session = driver.session()) {
            Record record = session.run(insertValues(CYPHER_CHEAPEST_TRIP, from, to)).single();

            Integer time = record.get("time").asInt();
            Integer cost = record.get("cost").asInt();

            List<Object> nodeCities = record.get("cities").asList();

            List<CityDto> cities = nodeCities
                    .stream().map(city -> NodeCityMapper.map((Node) city))
                    .collect(Collectors.toList());

            return TripDto.builder()
                    .cities(cities)
                    .cost(cost)
                    .time(time)
                    .build();

        }
    }

    @Override
    public TripDto fastestTrip(CityDto from, CityDto to) {
        try (Session session = driver.session()) {
            Record record = session.run(insertValues(CYPHER_FASTEST_TRIP, from, to)).single();

            Integer time = record.get("time").asInt();
            Integer cost = record.get("cost").asInt();

            List<Object> nodeCities = record.get("cities").asList();

            List<CityDto> cities = nodeCities
                    .stream().map(city -> NodeCityMapper.map((Node) city))
                    .collect(Collectors.toList());

            return TripDto.builder()
                    .cities(cities)
                    .cost(cost)
                    .time(time)
                    .build();

        }
    }

    @Override
    public TripDto directTrip(CityDto from, CityDto to) {
        try (Session session = driver.session()) {
            Record record = session.run(insertValues(CYPHER_DIRECT_TRIP, from, to)).single();

            Integer time = record.get("time").asInt();
            Integer cost = record.get("cost").asInt();

            List<Object> nodeCities = record.get("cities").asList();

            List<CityDto> cities = nodeCities
                    .stream().map(city -> NodeCityMapper.map((Node) city))
                    .collect(Collectors.toList());

            return TripDto.builder()
                    .cities(cities)
                    .cost(cost)
                    .time(time)
                    .build();

        }
    }

    private String insertValues(String cypherScript, CityDto from, CityDto to) {
        return cypherScript.replace("?from?", from.getName()).replace("?to?", to.getName());
    }
}
