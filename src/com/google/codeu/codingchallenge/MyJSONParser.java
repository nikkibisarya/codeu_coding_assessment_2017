// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;

final class MyJSONParser implements JSONParser
{
	private int valIndex = 0;
	private MyJSON parseHelper(String in) throws IOException
	{
      //check to see if string exists
      if (in.length() == 0)
      {
        	throw new IOException("Invalid string");
      }
      
		MyJSON store = new MyJSON();

		valIndex = condenseWhiteSpace(in, valIndex);
      // Check it is a bracket.
   	if(in.charAt(valIndex) != '{')
   	{
   		throw new IOException("Next one is " + in.charAt(valIndex) + " not { at index " + valIndex);
   	}
      
    	valIndex += 1;
      
	   
		while (true) 
		{
		   valIndex = condenseWhiteSpace(in, valIndex);
         //if there is a closing bracket, end of parse
		   if (in.charAt(valIndex) == '}')
		   {
		   	break;
		   }
             
		   int stringEnd = parseString(in, valIndex);
         // Increment by 1 to skip the " character.
         String key = in.substring(valIndex + 1, stringEnd);
		   	
         // The next character after the ending " could be whitespace. 
		   valIndex = condenseWhiteSpace( in, stringEnd + 1);
		   	
		   // check for colon.
		   if(in.charAt(valIndex) != ':')
		   {
		   	throw new IOException("There is no colon after the key");
		   }
            
         // There could be whitespace after the colon.
		   valIndex = condenseWhiteSpace( in, valIndex + 1 );

		   if (in.charAt(valIndex) == '"')
		   {
		   	int valEnd = parseString(in, valIndex);
		   	String value = in.substring(valIndex + 1, valEnd);
               
		   	store.setString(key, value);
		   	valIndex = condenseWhiteSpace( in, valEnd + 1);
               
		   	if (in.charAt(valIndex) != ',' && in.charAt(valIndex) != '}')
            {
		   	 	throw new IOException("Missing comma or closing bracket");
            }
            // If not this we know that it has to be a '}' character. 
            // However, to cut down on code that is already taken care of later.
            if (in.charAt(valIndex) == ',')
            {
               valIndex += 1;
            }
		   }
		   else if (in.charAt(valIndex) == '{')
		   {
            //recursively call this function in case there are multiple dictionaries
            MyJSON value = parseHelper(in);
		   	store.setObject(key, value);
            //There could be excess whitespace after the opening bracket--look for comma or closing bracket
		   	valIndex = condenseWhiteSpace( in, valIndex + 1);
            
                        
            if (in.charAt(valIndex) != ',' && in.charAt(valIndex) != '}')
            {
		   	 	throw new IOException("Missing comma or closing bracket");
            }
            // If not this, we know that it has to be a '}' character. 
            // However, to cut down on code that is already taken care of later.
            if (in.charAt(valIndex) == ',')
            {
               valIndex += 1;
            }
		   }
		   else
		   {
		   	throw new IOException("Empty");
		   }
         //make sure the dictionary is properly closed
		   valIndex = condenseWhiteSpace(in, valIndex);
		   if (in.charAt(valIndex) == '}')
		   {
		   	break;
		   }
	   }

   	return store;
	}

	@Override
	public JSON parse(String in) throws IOException 
	{
      //pass in current index and recurse using the helper function
		valIndex = 0;
	  	return parseHelper(in);  
	}

	private static int condenseWhiteSpace(String s, int index)
	{
	  	while(index < s.length() && s.charAt(index) == ' ')
	  	{
	  		index += 1;
	  	}
	  	//this is the start of the new string
	  	return index;
	}

	private static int parseString(String s, int index) throws IOException
	{
	   if(s.charAt(index) != '"')
	   {
	      throw new IOException("Missing quotation");
	   }
	         
	   index += 1;
	   while(index < s.length() && s.charAt(index) != '"')
	   {
		  	if(s.charAt(index) == '\\')
		  	{
              //ensure string length is valid, else throw an exception
	           if (index + 1 >= s.length())
	           {
	               throw new IOException("Exceeded string length");
	           }
               //check validity of escape character, otherwise throw an exception
		   	  if(s.charAt( index + 1 ) !=  't' || s.charAt( index + 1 ) != 'n' || 
	                s.charAt( index + 1 ) != '"' || s.charAt( index + 1 ) != '\\')
		   	  {
		   			throw new IOException("Not a valid escape character");
		   	  }
		   }
	   	index += 1;
	   }
	    //ensure that the string is properly closed
	   if (s.charAt(index) != '"')
	   {
	      throw new IOException("Missing closing quotation mark");
	   }
	   return index;
	}
}