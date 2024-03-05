package com.FireFacilAuto.domain.entity.lawfields.clause;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.hibernate.query.sqm.ComparisonOperator;

import java.io.IOException;

public class ClauseDeserializer<T> extends StdDeserializer<Clause<T>> {

    public ClauseDeserializer() {
        super(Clause.class); // Corrected usage
    }

    @Override
    public Clause<T> deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);

        ClauseTypes clauseTypes = ClauseTypes.valueOf(node.get("clauseTypes").asText());
        PossibleClauses clauseField = clauseTypes.getClauseByName(node.get("clauseField").asText());
        ComparisonOperator comparisonOperator = ComparisonOperator.valueOf(node.get("comparisonOperator").asText());
        ClauseValueWrapper<T> value = deserializationContext.readValue(node.get("value").traverse(), ClauseValueWrapper.class);
        int priority = node.get("priority").asInt();

        return new Clause<>(clauseField, clauseTypes, comparisonOperator, value, priority);
    }
}
