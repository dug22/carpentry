# Carpentry User Guide

## Table of Contents
1. [Overview](#overview)
2. [Getting Started](#getting-started)
4. [Columns](#columns)
   * [What are Columns?](#what-are-columns)
   * [Types of Columns](#types-of-columns)
   * [Column Type Default Null Values](#column-type-default-null-values)
   * [Creating a Column](#creating-a-column)
   * [Displaying Column Results](#displaying-column-results)
   * [Adding Data to a Column](#adding-data-to-a-column)
   * [Copying a Column](#copying-a-column)
   * [Applying Functions to a Column](#apply-functions-to-a-column)
   * [Filtering a Column](#filtering-a-column)
   * [Sorting a Column](#sorting-a-column)
   * [Other Common Column Methods](#other-common-column-methods)
   * [Column Conversions](#column-conversions)
   * [Column Transformations](#column-transformations)
5. [DataFrames](#dataframes)
   * [What is a DataFrame](#what-is-a-dataframe)
   * [Creating a DataFrame](#creating-a-dataframe)
   * [Displaying DataFrame Results](#displaying-dataframe-results)
   * [Loading Data From CSV Files](#import-data-from-csv-files)
   * [Saving a DataFrame As a CSV File](#export-data-to-csv-files)
   * [Dropping Data From a DataFrame](#dropping-data-from-a-dataframe)
   * [Sorting Data Within a DataFrame](#sorting-data-within-a-dataframe)
   * [Filling Null Values In a DataFrame](#filling-null-values-in-a-dataframe)
   * [Grouping and Aggregation](#grouping-and-aggregation)
   * [Filtering a DataFrame](#filtering-a-dataframe)
   * [Querying a DataFrame](#querying-a-dataframe)
   * [Joining DataFrames](#joining-dataframes)
6. [Visualizing Your Data](#visualizing-your-data)
   * [Pie Charts](#pie-charts)
   * [Doughnut Charts](#doughnut-charts)
   * [Bar Charts](#bar-charts)
   * [Line Charts](#line-charts)
   * [Map Visualizations](#map-visualizations) 


<div align= "justify">

## Overview
While Java is a popular language, you do not hear about Java being used for
Data Science related tasks. Carpentry simplifies data science tasks for Java developers with ease.
Just like a carpenter shapes and transforms wood into useful material, Carpentry is a Java DataFrame library
that lets you efficiently shape, transform, analyze, and visualize data simply.
Carpentry supports tasks like loading, exporting, cleaning, transforming, filtering, visualizing, and
summarizing data. This user guide will give you an in depth understanding of how Carpentry works.

## Getting Started

### Requirements
- Must be using Java 21+
- Must have an IDE to use (IntelliJ, VSCode, etc)

### Installation Process
You can use the dependency by copying this into your pom.xml file:

```xml
<dependency>
  <groupId>io.github.dug22</groupId>
  <artifactId>carpentry</artifactId>
  <version>LATEST</version>
</dependency>
```

## Columns

### What are Columns?
In a relational database, a column is an array/list of data values of a particular data type. Columns can contain many data types such as strings, numbers, dates, etc.
Each column usually contain names/headers, that pretty much describes the attributes or features it contains.

### Types of Columns
There are 11 column types Carpentry supports:

* BooleanColumn
* ByteColumn
* CharacterColumn
* DateColumn
* DateTimeColumn
* DoubleColumn
* FloatColumn
* IntegerColumn
* LongColumn
* ShortColumn
* StringColumn

### Column Type Default Null Values
There is a class called **Nulls**, which is used to look out for null values and assign default null values if nulls are detected. For instance if a null value was detected within a DoubleColumn, a value of Double.NaN will be assigned to that detected null value. Below represents a table
of what null value is assigned, based on their column type by default.

| Column Type | Assigned Null Value |
|---|---|
| Boolean | null |
| Byte | Byte.MIN_VALUE |
| Character| '*' |
| Double | NaN |
| Float | NaN |
| Integer | Integer.MIN_VALUE |
| LocaleDate | 1900-1-1 (formats can vary, depending on assigned formats)|
| LocaleDateTime | 1900-1-1 00:00:00 (formats can vary, depending on assigned formats)|
| Long | Long.MIN_VALUE |
| Short | Short.MIN_VALUE |
| String | "NA" |

To override the default null value for any column data type, all you have to do is type:

   ```
    static {
      Nulls.registerDefaultNull(Integer.class, -1);
      }
   ```

### Creating a Column
Creating a column is a pretty simple task. For example, if you want to create a column containing doubles, all
you have to simply do is type out the following code.

```
DoubleColumn doubleColumn = DoubleColumn.create("Numbers", new Double[]{1D,2D,3D,4D,5D,6D,7D,8D,9D,10D});
```

Congratulations, you successfully created your first column. This method of creating columns is global for every other
column type.

### Displaying Column Results
To print/display column results on your terminal you can simply use the following methods:

1. **show();** displays the entire column
2. **head();** displays the first five rows of a column.
3. **head(int count);** displays the first specified number of rows of a column.
4. **tail();** displays the last five rows of a column,
5. **tail(int count);** displays the last specified number of rows of a column.

```
doubleColumn.show();
```
```
┌─────────┐
│ Numbers │
├─────────┤
│ 1.0     │
│ 2.0     │
│ 3.0     │
│ 4.0     │
│ 5.0     │
│ 6.0     │
│ 7.0     │
│ 8.0     │
│ 9.0     │
│ 10.0    │
└─────────┘
```

### Adding Data to a Column
To add new value(s) to a given column, you can utilize the following methods:

1. **append(T value);** - adds a single value to a column.

   ```
   doubleColumn.append(11d);
   ```

   ```
    ┌─────────┐
    │ Numbers │
    ├─────────┤
    │ 1.0     │
    │ 2.0     │
    │ 3.0     │
    │ 4.0     │
    │ 5.0     │
    │ 6.0     │
    │ 7.0     │
    │ 8.0     │
    │ 9.0     │
    │ 10.0    │
    │ 11.0    │
    └─────────┘
   ```
2. **appendAll(T[] values);** - adds multiple values to a column.

   ```
   doubleColumn.appendAll(new Double[]{12d, 13d, 14d});
   ```

   ```
    ┌─────────┐
    │ Numbers │
    ├─────────┤
    │ 1.0     │
    │ 2.0     │
    │ 3.0     │
    │ 4.0     │
    │ 5.0     │
    │ 6.0     │
    │ 7.0     │
    │ 8.0     │
    │ 9.0     │
    │ 10.0    │
    │ 11.0    │
    │ 12.0    │
    │ 13.0    │
    │ 14.0    │
    └─────────┘
    ```
3. **appendNull();** - adds the default assigned null value to a column (default null values, depend on their column's data type). For example, NaN are the default null values for Double/Float columns.

   ```
   doubleColumn.appendNull();
   ```  

   ```
    ┌─────────┐
    │ Numbers │
    ├─────────┤
    │ 1.0     │
    │ 2.0     │
    │ 3.0     │
    │ 4.0     │
    │ 5.0     │
    │ 6.0     │
    │ 7.0     │
    │ 8.0     │
    │ 9.0     │
    │ 10.0    │
    │ 11.0    │
    │ 12.0    │
    │ 13.0    │
    │ 14.0    │
    │ NaN     │
    └─────────┘
   ```
   * Remember it was mentioned earlier! To override the default null value for any column data type, all you have to do is type:
       ```
       static {
        Nulls.registerDefaultNull(Double.class, -1.0);
        }
       ```

### Copying a Column
There are two ways to go about copying an entire column. The  **copy();** method itself will copy an entire column, retaining the column's name and data.
The **emptyCopy()**; creates an empty copy of the column, only retaining the column's name, but not the column's data.

1. Copy Example:

   ```
   DoubleColumn doubleColumn = DoubleColumn.create("Numbers", new Double[]{1D,2D,3D,4D,5D,6D,7D,8D,9D,10D});
   DoubleColumn copyColumn = doubleColumn.copy();
   copyColumn.show();
   ```
   ```
    ┌─────────┐
    │ Numbers │
    ├─────────┤
    │ 1.0     │
    │ 2.0     │
    │ 3.0     │
    │ 4.0     │
    │ 5.0     │
    │ 6.0     │
    │ 7.0     │
    │ 8.0     │
    │ 9.0     │
    │ 10.0    │
    └─────────┘
   ```
2. Empty Copy Example:

   ```
    DoubleColumn doubleColumn = DoubleColumn.create("Numbers", new Double[]{1D,2D,3D,4D,5D,6D,7D,8D,9D,10D});
    DoubleColumn emptyCopyColumn = doubleColumn.emptyCopy();
    emptyCopyColumn.show();
   ```

   ```
   ┌─────────┐
   │ Numbers │
   ├─────────┤
   └─────────┘
   ```

### Applying Functions to a Column
Applying functions to your column allows you to transform your data. Let's say we wanted to take our current column's data and square its numbers.
We can do this by utilizing the **apply(ColumnFunction<? super T, ? extends T>);** method.

1. Squaring Example:

   ```
   DoubleColumn doubleColumn = DoubleColumn.create("Numbers", new Double[]{1D,2D,3D,4D,5D,6D,7D,8D,9D,10D});
   DoubleColumn squaredNumbersColumn = doubleColumn.apply(value -> Math.pow(value, 2));
   squaredNumbersColumn.show();
   ```
   ```
    ┌─────────┐
    │ Numbers │
    ├─────────┤
    │ 1.0     │
    │ 4.0     │
    │ 9.0     │
    │ 16.0    │
    │ 25.0    │
    │ 36.0    │
    │ 49.0    │
    │ 64.0    │
    │ 81.0    │
    │ 100.0   │
    └─────────┘
   ```

### Filtering a Column
Filtering data is an important concept in the world of data science. Filtering lets you filter out data that isn't necessary or important to us. Filtering a
column can be accomplished using the **filter(ColumnPredicate<? super T> condition);** method. Conditions of any sort can be passed within the column's filter method,
whether that'd be you're interested in values >= x, values that begin with the letter S, anything your heart desires.

1. Filtering Example:

   ```
   DoubleColumn doubleColumn = DoubleColumn.create("Numbers", new Double[]{1D,2D,3D,4D,5D,6D,7D,8D,9D,10D});
   DoubleColumn filterColumn = doubleColumn.filter(value -> value >= 5 && value <= 8); //Filter out values that aren't between 5-8.
   filterColumn.show();
   ```

   ```
   ┌─────────┐
   │ Numbers │
   ├─────────┤
   │ 5.0     │
   │ 6.0     │
   │ 7.0     │
   │ 8.0     │
   └─────────┘
   ```

### Sorting a Column
Sorting data within a column can be sorted in two ways. Data can be sorted in ascending order, or descending order.  Here is an example of how that can be
accomplished.

1. Sorting Example:

   ```
   DoubleColumn doubleColumn = DoubleColumn.create("Numbers", new Double[]{3d, 2d, 5d, 7d, 10d, 11d, 12d});
   System.out.println("Ascending Order");
   doubleColumn.sortAscending();
   doubleColumn.head();
   doubleColumn.sortDescending();
   System.out.println("Descending Order");
   doubleColumn.head();
   ```

   ```
    Ascending Order
    ┌─────────┐
    │ Numbers │
    ├─────────┤
    │ 2.0     │
    │ 3.0     │
    │ 5.0     │
    │ 7.0     │
    │ 10.0    │
    └─────────┘
    Descending Order
    ┌─────────┐
    │ Numbers │
    ├─────────┤
    │ 12.0    │
    │ 11.0    │
    │ 10.0    │
    │ 7.0     │
    │ 5.0     │
    └─────────┘
   ```

### Other Common Column Methods

There are some other common methods that columns share across the board. These common methods include the following:

1. **name();** - Returns the name of a column.
2. **setName(String name);** - Sets the name of a column.
3. **getColumnParser()** - Returns the column's default parser.
4. **setColumnParser(ColumnParser<T> columnParser)** - Defines a custom parser for a given column.
5. **unique();** - This method returns a set of data values of a column, removing duplicate elements.

### Column Conversions
Column conversions are a way to convert a column's data type to another column data type. For instance, we can convert our given DoubleColumn
to become a StringColumn. This would convert our numerical data to become textual data.

1. Conversion Example:
    ``` 
    DoubleColumn doubleColumn = DoubleColumn.create("Numbers", new Double[]{3d, 2d, 5d, 7d, 10d, 11d, 12d});
    StringColumn stringColumn = doubleColumn.asStringColumn();
    stringColumn.append("1");
    stringColumn.show();
    ```

   ```
   ┌─────────┐
   │ Numbers │
   ├─────────┤
   │ 3.0     │
   │ 2.0     │
   │ 5.0     │
   │ 7.0     │
   │ 10.0    │
   │ 11.0    │
   │ 12.0    │
   │ 1       │
   └─────────┘
   ```
Below is a provided conversion table, which shows you what data type each column supports being converted to.

| Column Type     | Supported Conversions                                                                                                                        |
  |-----------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| BooleanColumn   | CharacterColumn <br> DoubleColumn <br> IntegerColumn <br> StringColumn                                                                       |
| ByteColumn      | DoubleColumn <br> FloatColumn <br> IntegerColumn <br> LongColumn <br> ShortColumn <br> StringColumn                                          |
| CharacterColumn | StringColumn                                                                                                                                 |
| DateColumn      | StringColumn                                                                                                                                 |
| DateTimeColumn  | StringColumn                                                                                                                                 |
| DoubleColumn    | ByteColumn <br> FloatColumn <br> IntegerColumn <br> LongColumn <br> ShortColumn <br> StringColumn                                            |
| FloatColumn     | ByteColumn <br> DoubleColumn <br> IntegerColumn <br> LongColumn <br> ShortColumn <br> StringColumn                                           |
| IntegerColumn   | ByteColumn <br> DoubleColumn <br> FloatColumn <br> LongColumn <br> ShortColumn <br> StringColumn                                             |
| LongColumn      | ByteColumn <br> DoubleColumn <br> FloatColumn <br> IntegerColumn <br> ShortColumn <br> StringColumn                                          |
| ShortColumn     | ByteColumn <br> DoubleColumn <br> FloatColumn <br> IntegerColumn <br> LongColumn <br> StringColumn                                           |
| StringColumn    | BooleanColumn <br> DoubleColumn <br> IntegerColumn <br> FloatColumn <br> LongColumn <br> CharacterColumn <br> DateColumn <br> DateTimeColumn |

### Column Transformations
Column transformations are methods applied to one or more columns that produce a new column as a result. Think of transformations as applied functions to corresponding columns, transforming data.
Carpentry provides numerous built in transformative functions for various column types, handling common operation like:
- **Arithmetic operations:** Adding, Subtracting, Multiplying, Dividing in NumberColumns.
- **Date/Time operations:** Adding/subtracting time units, extracting components (second, minute, hour, day, month, year) from Date/DateTime columns.
- **String operations:** Converting case, extracting substrings, joining strings, etc.

Since this is a large topic to cover, please have a look on how column transformations work by visiting this [test repository](https://github.com/dug22/carpentry/tree/master/src/test/java/io/github/dug22/carpentry/column/transformation)  
which provides a handful of test examples.

## DataFrames

### What is a DataFrame?
A DataFrame is a two-dimensional data structure consisting rows and columns of potentially different types. You can think of it like a spreadsheet or SQL table.

### Creating a DataFrame
Creating a DataFrame is pretty simple.

```
DataFrame df = DataFrame.create();
```

This will create an empty DataFrame object. In order to provide a dataframe with column data we must simply pass column data within the create method.

```
DataFrame df = DataFrame.create(
  IntegerColumn.create("ID", new Integer[]{1, 2, 3, 4, 5}),
  StringColumn.create("Name", new String[]{"Jake", "John", "James", "Justin", "Jeremy"})
);
```

All columns must consist of the same length in data, meaning if "Column X" has five values, "Column Y" must also have five values, or else an Exception
will be thrown. All column names must also be unique. If a duplicate column header is detected, a suffix will be applied to the given column name with an "_" and a given letter that follows alphabetical order.

### Displaying DataFrame Results
To print/display a dataframe's results on your terminal you can simply use the following methods:

1. **show();** displays the entire dataframe
2. **head();** displays the first five rows of a dataframe.
3. **head(int count);** displays the first specified number of rows of a dataframe.
4. **tail();** displays the last five rows of a dataframe,
5. **tail(int count);** displays the last specified number of rows of a dataframe.

### Loading Data From CSV Files

With Carpentry you can easily load data from local or remote CSV files.

1. Loading Data From a Local CSV File Example:

    ```
    DataFrame dataFrame = DataFrame.read().csv(new File("/path/to/dataset.csv"));
    ```

2. Loading Data From a Remote CSV File Example:
    ```
    DataFrame  dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/titanic.csv");
    ```

3. Loading Data With Applied CsvReadingProperties Example:

   If you are not satisfied with the default properties, you can apply your own properties when loading data from a CSV file. You have the option to configure the following properties:

   * **setSource** - sets the data source from where you're reading (a CSV file from a local or remote location)
   * **hasHeaders** - set the expectation if column headers will be present or not. If set to false, default headers will be generated.
   * **setMaxColumnCharacterLength** - sets the max number of characters a data value can contain within a column. If the length exceeds this limit, a suffix of ... will be added at the end of the given data value.
   * **setDelimiter** - sets the delimiter used in the file. By default, it's a comma.
   * **setDateParser/setDateTimeParser** - allows you to specify date/datetime parsers when reading date/date time columns, making it easier to accept various datetime formats. You can direct these parsers to specific columns, by providing the column name.
   * **setDateFormatter/setDateTimeFormatter** - allows you to define the formats for date/date time columns. Instead of displaying results in YYYY-MM-DD format, you can format it to display results in MM/DD/YYYY instead. You can direct these formats to specific columns, by providing the column name.


       ```
           DataFrame dataFrame = DataFrame.read()
                    .csv(new CsvReadingProperties()
                            .setSource("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/employees.csv")
                            .setDateFormat("MM/dd/YYYY")
                            .build());
           dataFrame.head();
        ```

        ```
            ┌──────────────┬───────────────────┬─────────────────┬────────────┬─────────────┬──────────┐
            │ name         │ job_title         │ department      │ hire_date  │ salary      │ location │
            ├──────────────┼───────────────────┼─────────────────┼────────────┼─────────────┼──────────┤
            │ John Smith   │ Software Engineer │ Engineering     │ 05/12/2020 │ 85000       │ Seattle  │
            │ Emma Johnson │ Marketing Manager │ Marketing       │ 08/23/2019 │ -2147483648 │ New York │
            │ Liam Brown   │ Data Analyst      │ Analytics       │ 03/15/2021 │ 72000       │ Chicago  │
            │ Olivia Davis │ HR Specialist     │ Human Resources │ 01/01/1900 │ 65000       │ Austin   │
            │ Noah Wilson  │ Product Manager   │ Product         │ 01/09/2022 │ 95000       │ NA       │
            └──────────────┴───────────────────┴─────────────────┴────────────┴─────────────┴──────────┘
        ```

Carpentry does an excellent job automatically detecting what data type a column should be.

### Saving a DataFrame As a CSV File
Saving a dataframe that has been filtered or manipulated is very useful. To simply do this, all you have to do is the following:

```
DataFrame df = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/titanic.csv");
df = df.drop("Cabin");
df.write().toCsv(new File("titanic_cleaned.csv"));
```

This will save your manipulated dataframe as a CSV file. 

### Dropping Data From a DataFrame
Carpentry provides several method for dropping data from a DataFrame, whether that's an entire column, or rows with null values.

1. **Dropping Columns:** you can drop one or more columns from a dataframe based on either name, or index. Below are a few examples that reflect how this can be achieved.
     
    ```
    DataFrame dataframe = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");

    //Dropping one column by column name
    dataframe = dataframe.drop("sex"); //This will drop a single column called 'sex' from our dataframe

    //Dropping multiple columns by column name
    dataframe = dataframe.drop("sex", "year"); //This will drop two columns called 'sex' and 'year' from our dataframe

    //Dropping one column by index
    dataframe = dataframe.drop(7); //This will drop the column called 'year' because this column is the 8th column in our dataframe.

    //Dropping multiple columns by index
    dataframe = dataframe.drop(0, 5); //This will drop two columns called 'species' and 'year'. Since species will be the first column dropped, the indexes will change, making the column 'year' be in the sixth position.
    ```
2. **Dropping Rows With Null Values:** - you can drop rows that contain null rows.

    * Dropping Rows with the How function. How contains two drop features, How.ANY and How.ALL.
      * How.ANY drops the row if any value in that row is null. 
    
          ```
           DataFrame dataframe = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");
           dataframe = dataframe.dropNa(How.ANY);
          ```

      * How.ALL drops the row only if **ALL** values are null
      
          ```
           DataFrame dataframe = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");
           dataframe = dataframe.dropNa(How.ALL);
          ```
        
     * Dropping null rows based on specific column names is indeed possible. By specifying one or more column names, this approach operates similarly to the How.ANY method. However, in this case, a row will be removed if any of the specified columns contain null values.

          ```
           DataFrame dataframe = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");
           dataframe = dataframe.dropNa("year", "species");
          ```

### Sorting Data Within a DataFrame 
With Carpentry, you can sort data either in ascending or descending order. 

1. Sorting data either in ascending/descending order can be achieved utilizing the **sort(SortColumn[] sortColumns);** method
2. You do have the option to sort by multiple columns.

    ```
    DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/fake_employee_data.csv");
  
    //This will sort by Salary in ascending order
    dataFrame = dataFrame.sort(new SortColumn[]{
                  new SortColumn("Salary", SortColumn.Direction.ASCENDING)
  
    //This will sort by Salary in descending order.
    dataFrame = dataFrame.sort(new SortColumn[]{
                  new SortColumn("Salary", SortColumn.Direction.DESCENDING)
  
    //This will sort by Age in ascending order, then by Salary in descending order.
     dataFrame = dataFrame.sort(new SortColumn[]{
                  new SortColumn("Age", SortColumn.Direction.ASCENDING),
                  new SortColumn("Salary", SortColumn.Direction.DESCENDING)
          });
    ```

### Filling Null Values In a DataFrame
To replace null values with default values in a DataFrame, you can use the **fillNa(FillColumnValuePair[] fillColumnValuePairs);** method, which will
fills null values in specified columns with provided values.

  ```
  DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/palmer%20penguins.csv");
  dataFrame = dataFrame.fillNa(new FillColumnValuePair[]{
                  new FillColumnValuePair("bill_length_mm", -1.0), //Null values will be replaced with -1
                  new FillColumnValuePair("bill_depth_mm", -1.0),  //Null values will be replaced with -1
                  new FillColumnValuePair("flipper_length_mm", -1),  //Null values will be replaced with -1
                  new FillColumnValuePair("body_mass_g", -1),  //Null values will be replaced with -1
                  new FillColumnValuePair("sex", "Not specified")  //Null values will be replaced with Not specified
          });
  dataframe.head();
  ```

  ```
  ┌─────────┬───────────┬────────────────┬───────────────┬───────────────────┬─────────────┬───────────────┬──────┐
  │ species │ island    │ bill_length_mm │ bill_depth_mm │ flipper_length_mm │ body_mass_g │ sex           │ year │
  ├─────────┼───────────┼────────────────┼───────────────┼───────────────────┼─────────────┼───────────────┼──────┤
  │ Adelie  │ Torgersen │ 39.1           │ 18.7          │ 181               │ 3750        │ male          │ 2007 │
  │ Adelie  │ Torgersen │ 39.5           │ 17.4          │ 186               │ 3800        │ female        │ 2007 │
  │ Adelie  │ Torgersen │ 40.3           │ 18.0          │ 195               │ 3250        │ female        │ 2007 │
  │ Adelie  │ Torgersen │ -1.0           │ -1.0          │ -1                │ -1          │ Not specified │ 2007 │
  │ Adelie  │ Torgersen │ 36.7           │ 19.3          │ 193               │ 3450        │ female        │ 2007 │
  └─────────┴───────────┴────────────────┴───────────────┴───────────────────┴─────────────┴───────────────┴──────┘
 ```
### Grouping and Aggregation
Grouping refers to the process of organizing data within a DataFrame into groups based on one or more criteria, , typically the unique values in one or more columns.
Aggregation refers to the process of applying a function to a dataset to produce a single, summarized value. When working with large datasets it makes sense to
group data and aggregate it to make analysis easier. Carpentry simplifies this process for you. 

**Aggregation Functions Supported**
| Aggregation Function | Description |
|---|---|
| COUNT | Compute count of column values|
| MAX | Compute max of column values |
| MEAN | Compute mean of column values |
| MIN | Compute min of column values |
| STD | Returns the sample standard devivation of a column |
| STD_POPULATION | Returns the population standard deviation of a column|
| SUM | Compute sum of column values |
| TRUE_COUNT | Compute count of column values that are true (used only for Boolean Columns) |
| FALSE_COUNT | Compute count of column values that are false (used only for Boolean Columns) |

**Grouping and Aggregating Example**
  * In this example, we’ll load the vehicle-mini dataset, group the data by year, and calculate the total selling price for each year.

      ```
      DataFrame vehicleDataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
      vehicleDataFrame = vehicleDataFrame.groupBy("Year").aggregate(new AggregationEntry("Selling_Price", AggregationType.SUM)); 
      vehicleDataFrame.head();
      ```
      ```
      ┌──────┬───────────────────┐
      │ Year │ Selling_Price_sum │
      ├──────┼───────────────────┤
      │ 2006 │ 100000.0          │
      │ 2007 │ 335000.0          │
      │ 2009 │ 1811000.0         │
      │ 2010 │ 850000.0          │
      │ 2011 │ 1640000.0         │
      └──────┴───────────────────┘
      ```
  * You can also group data by multiple columns and include as many aggregation operations as you need. In this example, we’ll group by both transmission and year, then compute the sum of selling price, and kilometers driven for each transmission-year combination.

      ```
       DataFrame vehicleDataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
       vehicleDataFrame = vehicleDataFrame.groupBy("Transmission", "Year").aggregate(new AggregationEntry("Selling_Price", AggregationType.SUM), new AggregationEntry("Kms_Driven", AggregationType.SUM));
       vehicleDataFrame.show();
      ```

      ```
      ┌──────────────┬──────┬───────────────────┬────────────────┐
      │ Transmission │ Year │ Selling_Price_sum │ Kms_Driven_sum │
      ├──────────────┼──────┼───────────────────┼────────────────┤
      │ Automatic    │ 2009 │ 1250000.0         │ 78000.0        │
      │ Automatic    │ 2010 │ 850000.0          │ 119000.0       │
      │ Automatic    │ 2011 │ 1640000.0         │ 306300.0       │
      │ Automatic    │ 2012 │ 1375000.0         │ 33800.0        │
      │ Automatic    │ 2013 │ 4710000.0         │ 184800.0       │
      │ Automatic    │ 2014 │ 1964999.0         │ 28000.0        │
      │ Automatic    │ 2016 │ 900000.0          │ 50000.0        │
      │ Automatic    │ 2018 │ 2625000.0         │ 29500.0        │
      │ Manual       │ 2006 │ 100000.0          │ 80000.0        │
      │ Manual       │ 2007 │ 335000.0          │ 245000.0       │
      │ Manual       │ 2009 │ 561000.0          │ 199000.0       │
      │ Manual       │ 2012 │ 1660000.0         │ 260000.0       │
      │ Manual       │ 2013 │ 390000.0          │ 33000.0        │
      │ Manual       │ 2014 │ 3534999.0         │ 619000.0       │
      │ Manual       │ 2015 │ 2995000.0         │ 164000.0       │
      │ Manual       │ 2016 │ 1215000.0         │ 71000.0        │
      │ Manual       │ 2017 │ 1340000.0         │ 142500.0       │
      │ Manual       │ 2018 │ 2916000.0         │ 96700.0        │
      │ Manual       │ 2019 │ 3095000.0         │ 30000.0        │
      └──────────────┴──────┴───────────────────┴────────────────┘
      ```
      
### Filtering a DataFrame
Filtering a DataFrame enables you to select specific rows based on conditions applied to one or more columns. You can use either the filter or where 
method to perform filtering. When referencing a column for filtering, use the syntax dataFrame.column("ColumnName") to access a set of predicate methods 
for defining filter conditions. For this example, we'll use the vehicles-mini dataset to filter vehicles manufactured between 2010 and 2015. Here's how you can do it:

1. Filter Example 1:
   
     ```
        DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
        dataFrame = dataFrame.filter(dataFrame.column("Year").between(2010, 2015));
        dataFrame.head();
     ```
  
      ```
        ┌──────────────────────────┬──────┬───────────────┬────────────┬───────────┬─────────────┬──────────────┬──────────────┐
        │ Car_Name                 │ Year │ Selling_Price │ Kms_Driven │ Fuel_Type │ Seller_Type │ Transmission │ Owner        │
        ├──────────────────────────┼──────┼───────────────┼────────────┼───────────┼─────────────┼──────────────┼──────────────┤
        │ Hyundai Verna 1.6 SX     │ 2012 │ 600000        │ 100000     │ Diesel    │ Individual  │ Manual       │ First Owner  │
        │ Honda Amaze VX i-DTEC    │ 2014 │ 450000        │ 141000     │ Diesel    │ Individual  │ Manual       │ Second Owner │
        │ Tata Indigo Grand Petrol │ 2014 │ 240000        │ 60000      │ Petrol    │ Individual  │ Manual       │ Second Owner │
        │ Hyundai Creta 1.6 VTVT S │ 2015 │ 850000        │ 25000      │ Petrol    │ Individual  │ Manual       │ First Owner  │
        │ Chevrolet Sail 1.2 Base  │ 2015 │ 260000        │ 35000      │ Petrol    │ Individual  │ Manual       │ First Owner  │
        └──────────────────────────┴──────┴───────────────┴────────────┴───────────┴─────────────┴──────────────┴──────────────┘
      ```

You can chain multiple predicates to refine your DataFrame filter. Below is an example using the vehicles-mini dataset to filter vehicles manufactured between 2010 and 2015 that also use diesel as their fuel type.

2. Filter Example #2:
   
      ```
       DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
       dataFrame = dataFrame.filter(
                    and(
                            dataFrame.column("Year").between(2010, 2015),
                            dataFrame.column("Fuel_Type").objEq("Diesel"))
            );
       dataFrame.head();
      ```

      OR

      ```
       DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
       dataFrame = dataFrame.filter(dataFrame.column("Year").between(2010, 2015).and(dataFrame.column("Fuel_Type").objEq("Diesel")));
       dataFrame.head();
      ```
      
      ```
      ┌──────────────────────────────────────┬──────┬───────────────┬────────────┬───────────┬─────────────┬──────────────┬──────────────┐
      │ Car_Name                             │ Year │ Selling_Price │ Kms_Driven │ Fuel_Type │ Seller_Type │ Transmission │ Owner        │
      ├──────────────────────────────────────┼──────┼───────────────┼────────────┼───────────┼─────────────┼──────────────┼──────────────┤
      │ Hyundai Verna 1.6 SX                 │ 2012 │ 600000        │ 100000     │ Diesel    │ Individual  │ Manual       │ First Owner  │
      │ Honda Amaze VX i-DTEC                │ 2014 │ 450000        │ 141000     │ Diesel    │ Individual  │ Manual       │ Second Owner │
      │ Chevrolet Enjoy TCDi LTZ 7 Seater    │ 2013 │ 390000        │ 33000      │ Diesel    │ Individual  │ Manual       │ Second Owner │
      │ Jaguar XF 2.2 Litre Luxury           │ 2014 │ 1964999       │ 28000      │ Diesel    │ Dealer      │ Automatic    │ First Owner  │
      │ Mercedes-Benz New C-Class 220 CDI AT │ 2013 │ 1425000       │ 59000      │ Diesel    │ Dealer      │ Automatic    │ First Owner  │
      └──────────────────────────────────────┴──────┴───────────────┴────────────┴───────────┴─────────────┴──────────────┴──────────────┘
      ```

Practice Problem: We have a customer who is interested in buying a vehicle that was manufactured between 2012-2015, has a selling price of less than $250,000, mileage < 70,000,  and can be either first or second owner. Apply a filter to find vehicles
that meets this customer's needs.

3. Filter Practice Problem Answer:

  * First we need to read the given dataset

      ```
      DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
      ```

  * Second we need to apply the appropriate filters

      ```
      dataFrame = dataFrame.filter(
              and( //And is required because it must meet all these conditions to meet our customer's needs
                        dataFrame.column("Year").between(2012, 2015), // Check for vehicles manufactured between 2012-2015
                        dataFrame.column("Selling_Price").lt(250_000), //Check for selling prices less than $250,000 
                        dataFrame.column("Kms_Driven").lt(70_000), // Check if it has a mileage of less than 70_000 Kilometers driven
                        or( //Apply an or operation because the owner could be first or second
                                dataFrame.column("Owner").startsWith("First"),
                                dataFrame.column("Owner").startsWith("Second")
                        )

                ));
      ```
  
  * Third we need to display our dataframe's results

      ```
      dataFrame.show();
      ```

  * Final Result - We have two vehicles that the customer might be interested in.

      ```
      ┌──────────────────────────┬──────┬───────────────┬────────────┬───────────┬─────────────┬──────────────┬──────────────┐
      │ Car_Name                 │ Year │ Selling_Price │ Kms_Driven │ Fuel_Type │ Seller_Type │ Transmission │ Owner        │
      ├──────────────────────────┼──────┼───────────────┼────────────┼───────────┼─────────────┼──────────────┼──────────────┤
      │ Tata Indigo Grand Petrol │ 2014 │ 240000        │ 60000      │ Petrol    │ Individual  │ Manual       │ Second Owner │
      │ Maruti Alto LXi          │ 2012 │ 160000        │ 60000      │ Petrol    │ Individual  │ Manual       │ Second Owner │
      └──────────────────────────┴──────┴───────────────┴────────────┴───────────┴─────────────┴──────────────┴──────────────┘
     ```


### Querying a DataFrame
Carpentry's query system, provides users a convienent way to filter rows in a DataFrame using string expressions. There are a few rules that you have to follow
when querying a dataframe

1. Column names and target values must be quoted with `` (backtick symbols)
2. Available value comparison operations:
    * =, !=, <, <=, >, >=, ~= (regex)
3. Available logical operatios:
    * AND, OR

For this example, we'll use the vehicles-mini dataset to query vehicles that were manufactured between 2012 and 2015.

1. Query Example #1:

   ```
   DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
   dataFrame = dataFrame.query("`Year` >= `2012` AND `Year` <= `2015`");
   dataFrame.head();
   ```

   ```
    ┌──────────────────────────┬──────┬───────────────┬────────────┬───────────┬─────────────┬──────────────┬──────────────┐
    │ Car_Name                 │ Year │ Selling_Price │ Kms_Driven │ Fuel_Type │ Seller_Type │ Transmission │ Owner        │
    ├──────────────────────────┼──────┼───────────────┼────────────┼───────────┼─────────────┼──────────────┼──────────────┤
    │ Hyundai Verna 1.6 SX     │ 2012 │ 600000        │ 100000     │ Diesel    │ Individual  │ Manual       │ First Owner  │
    │ Honda Amaze VX i-DTEC    │ 2014 │ 450000        │ 141000     │ Diesel    │ Individual  │ Manual       │ Second Owner │
    │ Tata Indigo Grand Petrol │ 2014 │ 240000        │ 60000      │ Petrol    │ Individual  │ Manual       │ Second Owner │
    │ Hyundai Creta 1.6 VTVT S │ 2015 │ 850000        │ 25000      │ Petrol    │ Individual  │ Manual       │ First Owner  │
    │ Chevrolet Sail 1.2 Base  │ 2015 │ 260000        │ 35000      │ Petrol    │ Individual  │ Manual       │ First Owner  │
    └──────────────────────────┴──────┴───────────────┴────────────┴───────────┴─────────────┴──────────────┴──────────────┘
   ```

 In the next example, we will query for vehicles that are diesel only, and either first owner or second owner. Note, parenthesis must be used since this requires
 a compound logical expression.

 2. Query Example #2:

    ```
    DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
    dataFrame = dataFrame.query("(`Fuel_Type` = `Diesel`)  AND (`Owner` = `First Owner` OR `Owner` = `Second Owner`)");
    dataFrame.head();
    ```
    ```
    ┌───────────────────────────────────┬──────┬───────────────┬────────────┬───────────┬─────────────┬──────────────┬──────────────┐
    │ Car_Name                          │ Year │ Selling_Price │ Kms_Driven │ Fuel_Type │ Seller_Type │ Transmission │ Owner        │
    ├───────────────────────────────────┼──────┼───────────────┼────────────┼───────────┼─────────────┼──────────────┼──────────────┤
    │ Hyundai Verna 1.6 SX              │ 2012 │ 600000        │ 100000     │ Diesel    │ Individual  │ Manual       │ First Owner  │
    │ Honda Amaze VX i-DTEC             │ 2014 │ 450000        │ 141000     │ Diesel    │ Individual  │ Manual       │ Second Owner │
    │ Hyundai Venue SX Opt Diesel       │ 2019 │ 1195000       │ 5000       │ Diesel    │ Dealer      │ Manual       │ First Owner  │
    │ Chevrolet Enjoy TCDi LTZ 7 Seater │ 2013 │ 390000        │ 33000      │ Diesel    │ Individual  │ Manual       │ Second Owner │
    │ Jaguar XF 2.2 Litre Luxury        │ 2014 │ 1964999       │ 28000      │ Diesel    │ Dealer      │ Automatic    │ First Owner  │
    └───────────────────────────────────┴──────┴───────────────┴────────────┴───────────┴─────────────┴──────────────┴──────────────┘
    ```
  Practice Problem: We have a customer who is interested in buying a vehicle whose selling price is less than $250,000, mileage that is less than 70,000, and a car names that starts with the letter C or H (Hint regex matching).

3. Query Pract Problem Answer:

  * First we need to read the given dataset

    ```
    DataFrame dataFrame = DataFrame.read().csv("https://raw.githubusercontent.com/dug22/datasets/refs/heads/main/vehicles-mini.csv");
    ```
  
  * Second we need to apply the appropriate query expression

    ```
    dataFrame = dataFrame.query("`Selling_Price` < `250000` AND `Kms_Driven` < `70000` AND `Car_Name` ~= `^[chCH]`"); //The last bit of the query expression looks for car names that start with the letter C or H.
    ```

  * Third we need to display our dataframe's results

    ```
    dataFrame.show();
    ```

  * Final Result - We have one vehicle that the customer might be interested in.

    ```
    ┌────────────────────────┬──────┬───────────────┬────────────┬───────────┬─────────────┬──────────────┬──────────────────────┐
    │ Car_Name               │ Year │ Selling_Price │ Kms_Driven │ Fuel_Type │ Seller_Type │ Transmission │ Owner                │
    ├────────────────────────┼──────┼───────────────┼────────────┼───────────┼─────────────┼──────────────┼──────────────────────┤
    │ Hyundai i10 Magna 1.1L │ 2014 │ 229999        │ 60000      │ Petrol    │ Individual  │ Manual       │ Fourth & Above Owner │
    └────────────────────────┴──────┴───────────────┴────────────┴───────────┴─────────────┴──────────────┴──────────────────────┘
    ```

### Joining DataFrames
Joining is a new dataframe function that was introduced in Carpentry v1.0.6. Joining allows you to join two dataframes together. This is very useful if you're working with
two dataframes that have common traits. There are four types of joins in Carpentry:

* Inner
* Outer
* Left
* Right

Before diving into the types of joins we can perform, we need to create two dataframes. 

**DataFrame One**

  ```
  DataFrame dataFrameOne = DataFrame.create(
                IntegerColumn.create("ID", new Integer[]{1, 2, 3,}),
                StringColumn.create("Name", new String[]{"Bob", "John", "Andrew"})
        );
  ```

**DataFrame Two**

  ```
  DataFrame dataFrameTwo = DataFrame.create(
                IntegerColumn.create("ID", new Integer[]{1, 2, 4}),
                IntegerColumn.create("Age", new Integer[]{25, 30, 22})
        );
  ```

**Inner Join**

An Inner join will return a DataFrame based on common values in specied columns. Rows that do not have a match in both DataFrames are excluded.
To perform an inner with DataFrame 1 and DataFrame 2, we can do the following:

  ```
  DataFrame innerJoin = dataFrameOne.join(dataFrameTwo, JoinType.INNER, "ID", "ID"); 
  ```

  ```
  ┌────┬──────┬─────┐
  │ ID │ Name │ Age │
  ├────┼──────┼─────┤
  │ 1  │ Bob  │ 25  │
  │ 2  │ John │ 30  │
  └────┴──────┴─────┘
  ```

**Outer Join**

An Outer join will return a DataFrame where all rows from both DataFrames are includeed, and missing values are filled with null values.
To perform an outer join with DataFrame 1 and DataFrame 2, we can do the following:

  ```
  DataFrame outerJoin = dataFrameOne.join(dataFrameTwo, JoinType.OUTER, "ID", "ID");
  ```

  ```
  ┌────┬────────┬──────┐
  │ ID │ Name   │ Age  │
  ├────┼────────┼──────┤
  │ 1  │ Bob    │ 25   │
  │ 2  │ John   │ 30   │
  │ 3  │ Andrew │      │
  │ 4  │        │ 22   │
  └────┴────────┴──────┘
 ```
  
**Left Join**

A left join merges two DataFrames based on specified key columns. It keeps every row from the left DataFrame and includes only matching rows from the right DataFrame.
For non-matching rows from the right null values will be applied. To perform a left join with DataFrame 1 and DataFrame 2, we can do the following:

  ```
  DataFrame leftJoin = dataFrameOne.join(dataFrameTwo, JoinType.LEFT, "ID", "ID", "_left", "_right"); //Suffixes must be applied.
  ```

  ```
  ┌────┬───────────┬───────────┐
  │ ID │ Name_left │ Age_right │
  ├────┼───────────┼───────────┤
  │ 1  │ Bob       │ 25        │
  │ 2  │ John      │ 30        │
  │ 3  │ Andrew    │           │
  └────┴───────────┴───────────┘
  ```

**Right Join**

A right keeps all rows from the right dataframe and only the matching rows from the dataframe. Unmatched rows from the left DataFrame will be applied with a null value.
To perform a right join with DataFrame 1 and DataFrame 2 we can do the following:

  ```
  DataFrame rightJoin = dataFrameOne.join(dataFrameTwo, JoinType.RIGHT, "ID", "ID");
  ```

  ```
  ┌────┬──────┬─────┐
  │ ID │ Name │ Age │
  ├────┼──────┼─────┤
  │ 1  │ Bob  │ 25  │
  │ 2  │ John │ 30  │
  │ 4  │      │ 22  │
  └────┴──────┴─────┘
  ```

## Visualizing Your Data

</div>
