# Android-groceries

A simple groceries ToDo App for storing your shopping list. This Android app contains a solid example of working experience in all software aspects from the internal persistence layer to list rendering with RecyclerViews.

The whole process implements CRUD operations over a `Grocery` Item.


## Storing data in Android

Android Systems have different ways of persisting data, many of them are hardware-based, these one are named `ExternalStorage`, the counterpart the `InternalStorage` work directly with private data on the device memory.

A pretty good and direct way of storing simple data is by using the `SharePreferences` Class, which can store and persist key-value paired type data.

The most used (and recommended) way of storing data is through databases. Android natively supports `SQLite3`, but a good option can be Google's Firebase.

## Structure

This app has a more complex package-based structure, here's a quick explanation.

1. Activities: List of Android Activities that conforms the application.
2. Data: This one contains the `DatabaseHandler` class, internally inherits the `SQLiteOpenHelper` that describes the CRUD functionality in the database perspective.
3. Model: a POJO Class that represents the Model of the resource (OOP - MVC reference).
4. UI: RecyclerViewAdapter class that contains the logic between the Widget and the CRUD methods.
5. Utils: this contains a-like Utility class with variables with for reusability purposes.

## Credits

 - [David E Lares](https://twitter.com/davidlares3)

## License

 - [MIT](https://opensource.org/licenses/MIT)
