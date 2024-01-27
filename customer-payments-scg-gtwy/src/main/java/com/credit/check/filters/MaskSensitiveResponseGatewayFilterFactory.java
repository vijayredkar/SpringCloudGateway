package com.credit.check.filters;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class MaskSensitiveResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<Config> {

    final Logger logger = LoggerFactory.getLogger(MaskSensitiveResponseGatewayFilterFactory.class);
    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFilterFactory;

    public MaskSensitiveResponseGatewayFilterFactory(ModifyResponseBodyGatewayFilterFactory modifyResponseBodyFilterFactory) {
        super(Config.class);
        this.modifyResponseBodyFilterFactory = modifyResponseBodyFilterFactory;
    }
    
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("fields", "replacement");
    }


    @Override
    public GatewayFilter apply(Config config) {
        
        return modifyResponseBodyFilterFactory
          .apply(c -> c.setRewriteFunction(JsonNode.class, JsonNode.class, new MaskFields(config)));
    }
}