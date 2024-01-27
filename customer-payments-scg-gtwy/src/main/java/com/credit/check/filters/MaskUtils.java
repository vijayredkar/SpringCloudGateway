package com.credit.check.filters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class MaskUtils 
{
	private static List<String> lines = new ArrayList<String>();
	private static final String MASK_CHARS = "****";
	private static boolean configLoaded = false;
	private static boolean readConfigOnEveryRequest = true;
	
	private static String currentDir = System.getProperty("user.dir");
	private static Path path = Path.of(currentDir + "//" + "sensitive-data.properties");
	private static String sensitiveFieldType = "sensitive-fields";
	
	private static String tokensToProcess;
	private static String[] tokens;	
	
	
	
	private static void configRefreshPolicy() 
	{
		if(!configLoaded || readConfigOnEveryRequest)
		{
			readConfig();
		}
	}
	
	
	public static void readConfig() 
	{	
		try 
		{	
			lines = Files.readAllLines(path);

			for(String line : lines)
			{
			  if(line.contains(sensitiveFieldType))
			  {
				  tokensToProcess = line.substring(line.indexOf("=") +1);
				  tokens =  tokensToProcess.split(",");
				  break;
			  }
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		configLoaded = true;
	}
	
	public static void mask(Entry<String, JsonNode> f) 
	{	
		configRefreshPolicy();
		
		for(String element : tokens)
		  {
			  if(element.equals(f.getKey()))
			  {
				  System.out.println("-- Detected sensitive field. Masking : "+element);
				  f.setValue(TextNode.valueOf(MASK_CHARS));
				  break;
			  } 
		  }
		 }	
	
	/*
	public static void maskLeadingChars(Entry<String, JsonNode> f, int numCharsToMask) //typical use case for CC# 
	{
		
	}
	
	public static void maskTrailingChars(Entry<String, JsonNode> f, int numCharsToMask) //typical usecase for CC# 
	{
		
	}	
	*/
}