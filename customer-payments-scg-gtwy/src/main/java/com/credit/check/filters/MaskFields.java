package com.credit.check.filters;

import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import reactor.core.publisher.Mono;
    
    public class MaskFields implements RewriteFunction<JsonNode,JsonNode> {
        private final Pattern fields;
        private final String replacement;
                
        public MaskFields(Config config) {
            this.fields = Pattern.compile(config.getFields());
            this.replacement = config.getReplacement();
        }

        @Override
        public Publisher<JsonNode> apply(ServerWebExchange t, JsonNode u) {
        	
            return Mono.just(scrubRecursively(u));
        }

        private JsonNode scrubRecursively(JsonNode u) 
        {
            if ( !u.isContainerNode()) {
                return u;
            }
            
            if ( u.isObject()) {
                ObjectNode node = (ObjectNode)u;
                node.fields().forEachRemaining((f) -> 
                	{
                		handleSensitive(f);				      //custom field list to mask - helps externalize
                		/*
                		 //-- when list of fields is limited and can be supplied in the app yml itself
                		if ( fields.matcher(f.getKey()).matches() && f.getValue().isTextual()) 
                		{
                			f.setValue(TextNode.valueOf(replacement)); //fields specified in config		
                		}
                		else 
	                    {
	                        f.setValue(scrubRecursively(f.getValue()));
	                    }
	                    */
                	});
            }
            else if ( u.isArray()) {
                ArrayNode array = (ArrayNode)u;
                for ( int i = 0 ; i < array.size() ; i++ ) {
                    array.set(i, scrubRecursively(array.get(i)));
                }
            }
            
            return u;
        }

		private void handleSensitive(Entry<String, JsonNode> f) 
		{	
			MaskUtils.mask(f);
		}		
    }