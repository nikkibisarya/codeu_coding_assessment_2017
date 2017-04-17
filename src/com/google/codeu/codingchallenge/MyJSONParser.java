package com.google.codeu.codingchallenge;

import java.io.IOException;

final class MyJSONParser implements JSONParser {

  @Override
  public JSON parse(String in) throws IOException {
  
   String str = in;
   if (str == null) {
         return 0;
     }

   int length = str.length( );
   char temp;
   int count;
   Map<String, String> strDict = new HashMap<String, String>();
   Map<String, JSON> objDict = new HashMap<String, JSON>();

   for(int i = 0; i < length; i + count)
   {
      count = 0;
      temp = str.charAt( i );
      String strValue = " ";
      int nextCount;
      char nxtTemp;
      
      while(temp != '{')
      {
         count++;
         if(temp != ' ')
         {
            throw exception;
         }
      }
         nxtCount = i + count + 1;
         nxtTemp = str.charAt( nxtCount );
         while(nxtTemp != '"')
         {
            nxtCount++;
            nxtTemp = str.charAt( i + nxtCount + 1 );
            if(nxtTemp != ' ' | nxtTemp != '"')
            {
               throw exception;
            }
            else
            {
               while(++nxtCount != '"')
               {
                  strValue += str.charAt(nxtCount);           
               }
               nxtTemp = str.charAt( i + count + 1 );
               if(nxtTemp != ":" | nxtTemp != " ")
               {
                  throw exception;
               }
               else
               {
                  while(++nxtCount != ':')
                  {
                     
                  }
               }
              
            }
         }
         
      
      
   }
            //make sure the first character is a quote
            //collect string between first set of quotes
            //if quote is escaped by a backslash, ignore the quote but include it in the string
            //if there is a backslash and the letter after it is not a t, n, ", or \ then throw exception
            //if there is a backslash and those characters do follow, then include those characters in the string
            // but don't include the backslash
            //check to make sure that there is a colon
            //throw an exception if there is no colon
            //check to see if there is another open bracket
            //if so, associate that object wgith the key
            //recurse
            //if not, associate that string as the value with the key
         }
       }
             
   }    
  
   return new MyJSON();
  }
}
