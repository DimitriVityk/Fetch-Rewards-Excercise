# Fetch-Rewards-Excercise

ListItem
------------
A class to represent list items. There are three fields:  
**id:** an **int** that represents the jSON item's id.  
**listId:** an **int** that represents the JSON item's listID.  
**name:** a **String** that represents the jSON item's name.  

Getter methods are implemented for each field.  
  
The class implements Comparable<ListItem> so that it can be sorted later. 
The compareTo method is overriden to compare the names of items.  
Since each item's name begins with the word "Item" the only factor that decides the order is the integer value after "Item".
So, I split both strings at the space between "Item" and the number i.e. "123". This gives me an array of size two for each ListItem where the element at index 1 is a string representing an integer value.  
I make each String into an Integer object using the Integer.valueOf() methods. (actually I use both Integer.valueOf() and Integer.parseInt(), but they both work in this scenario, so I wont change it)   
finally I use the Integer.compareTo() method to compare the two Integer objects and I return the results.

Section
------------
A class to represent each section in the main recyclerview. There are two fields.  
**sectionHeader:** A **String** that represents the group number of the ListItems in itemList.   
**itemList:** A **List\<ListItem\>** that represents all of the JSON items that share the same listId. In particular, the listId found in sectionHeader.  

The class has getter methods for both fields.

The class has a basic toString method for debugging purposes.  

There is no compareTo method because the ordering of sections is handled by the TreeMap used in the ListDownloader class.

Main Activity
--------------
MainActivity only has two methods. onCreate and loadItemMap.  
activity_main.xml only has two Views. The main recyclerView that presents the list of sections and a bottomNavigationView that allows the user to skip to certain sections of the recyclerView.  

**onCreate:**  
- The most important field of MainActivity is listOfSections (List\<Section\>). It is the list that holds the Sections that will be displayed in the recyclerView  
- The onCreate method instantiates the mainRecyclerView, mainRecyclerAdapter, and layoutManager a LinearLayoutManager.  
- It passes listOfSections to the mainRecyclerAdapter.  
- It sets the mainRecyclerView's adapter to mainRecyclerAdapter. It sets the mainRecyclerView's layoutManager to the LinearLayoutManager discussed earlier.
- It adds item decorations to the recyclerView. One that separated the sections with a line and one that Separates each section with 20dp of vertical space.   

- Finally, the onCreate method instantiates a ListDownloader Object named listDownloader. ListDownloader is a class that implements the Runnable interface and is tasked will accessing and processing the JSON data at https://fetch-hiring.s3.amazonaws.com/hiring.json more on that later.    
- A new thread is then initialized and it is passed the variable listDownloader. The thread is then started which executes the Runabble.

**loadItemMap:**
- loadItemMap is called from the within the process() method of the ListDownloader class.  
- It has one parameter. treeMap, which is a map where the keys are Integers representing the listId of a group of items with the same listId. The values are a lists of ListItems (List\<ListItem\>)  
- For each entry in the Map, or rather for each listId, the value, a List, is assigned to a temporary List variabled named groupList. groupList is then sorted using Collections.sort(grouplist).  
- A temporary Section object is instantiated named newSection. The section constructor is passed two parameters. The current key of treeMap in String form, and the sorted groupList.
- newSection is then added to the ListOfSections List described in the onCreate method.
- After all of the map entries have been iterated through, MainRecyclerAdapter.notifyDataSetChanged() is called to update the mainRecyclerView.
- Finally, an onNavigationItemSelectedListener is created for the bottomNavigationView. When the user presses a button on the bottomNavigationView it will automatically scroll the recyclerView to the top of the selected section using the layoutManager.scrollToPositionWithOffset() method;

ListDownloader
--------------
ListDownloader is a class that implements the Runabble interface. In this application it parses JSON and stores values in a TreeMap. A TreeMap was chosen because it sorts keys according to their natural order. In this case it will sort listIds in ascending order.  
- ListDownloader has three fields.
- **baseURL (String):** String containing the target URL "https://fetch-hiring.s3.amazonaws.com/hiring.json"  
- **TAG (String):** Simply a tag for Log statements.  
- **mainActivity (MainActivity):** The only parameter in the constructor, used to call runOnUiThread() later. 

- The class also has two methods, run(), part of the Runnable interface, which is executed automatically, and process(String result) which is a helper method that is called within run().

- The run() method uses the Java.net and Android.net packages to create an HTTP connection using the URL provided and setting the request method to "GET". If the response code is 200, HTTP_OK, then the JSON is read using an InputStreamReader and a BufferedReader. The results are stored in a StringBuilder object named result.   
- When there is no more data left, the StringBuilder is passed to the process() method.
- In the process method a TreeMap<Integer, List\<ListItem\>\> is created.
- The JSON is then parsed using the org.JSON package. First a JSON array is created and then it is iterated through and all of it's elements, JSONObjects, are accessed. If the name of the object is Null or an empty string, the for loop skips to the next JSONObject. Otherwise it stores the Object's id and listId fields and adds them as parameters to a temporary ListItem Object. If an Object with the same listId has already been added to the list, then that listId's corresponding List is accessed, and the temporary ListItem is added to it. Else, A new ListItem list is created, the ListItem is added to it, and a new entry is added to the treeMap with the ListItem's listId being the Key, and the new List being the value.
- Finally, mainActivity.runOnUiThread() is called because we are trying to update our UI from a non-UI thread, and mainActivity.loadItemMap() is passed the treeMap as a parameter (mainActivity.loadItemMap(treemap);)

Misc.
-------

- bottom_rounded_corner.xml and top_rounded_corner.xml were added to the drawable folder to achieve rounded edges on various Views in the app.
- VerticalSpaceDecoration is a class created to add space between recyclerViews.
- There are two recyclerViews. mainRecyclerView displays the Sections and gets its layout from section_row.xml. section_row.xml has a child recyclerView under the Header called sectionRecyclerView. sectionRecyclerView displays the individual items, and it gets its layout from item_row.xml. 


