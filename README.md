# 4sale! - Android Client

---
Final Group Project

KeepCoding Startup Engineering Master Boot Camp

---

###Project structure:

* #####Modules:
  - **`App`**:  
  Main module of the App. All specific app content is allocated in it
  - **`Custom Views`**:  
  Module that contains some generic custom view: `CustomTextView`, `CustomEditText`, `CustomButton`.  
  All of them have an attribute `fontName` to be set in the *xml layout file* with the font name   (file name) to be used by the custom view.
  - **`General Utils`**:  
  All general util methods that could be used in every project:
    - `DateManager.java`: All `Date` formatting methods.  
    - `FormattingManager.java`: Formatting methods for `String`.
    - `KeyboardManager.java`: Method to hide keyboard.
    - `SharedPreferencesGeneralManager.java`: Methods save/get/delete SharedPreferences items (String, int, float, boolean). This class should be used in the `SharedPreferencesManager.java` (in App/src/main/java/*package*/Utils)
    
  - **`Network`**:  
  All Classes and Interfaces to complete a general API request:
    - `APIAsyncTask.java`: Generic asyncTask for API requests ('GET/POST').
    - `NetworkUtils`: Methods to build url params for 'GET' requests, body params for 'POST', URL from a String, parse json into objects.    
    - `callbacks`:
      - `OnNetworkActivityCallback.java`: Interface to notify that network activity has started/finished, for example to show/hide a "preloader".
      - `OnResponseReceived.java`: Interface to return json String received in `APIAsyncTask.java`.
      - `OnDataParsedCallback.java`: Interface to send json parsed data (received by `onResponseReceived.java`) to any fragment/activity.
  

---

###Module `Network`:
In contains all code to send a general API request and receive the data.

####`NetworkPreloaderActivity.java`:
Activity to extend when you want your custom Activity to show a "preloader" (`loading_alert.xml`) for network activity.  
**When an Activity extends `NetworkPreloaderActivity`, you should include the `loading_alert.xml` into the activity layout** as follows:

```
<include layout="@layout/loading_alert"/>
```

To set a custom appearance for the "preloader", just change the `loading_alert.xml` and the contents of this two `NetworkPreloaderActivity` methods (you could also override them in your Custom Activity to set a different "preloader" ocassionally).

```
public void showPreloader(String message) {
    Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_continuously);
    rotation.setFillAfter(true);
    if (mLoadingIcon != null) {
        mLoadingIcon.startAnimation(rotation);
    }
    if (mLoadingText != null) {
        mLoadingText.setText(message);
    }
    if (mLoadingGroup != null) {
        mLoadingGroup.setVisibility(View.VISIBLE);
    }
}

public void hidePreloader() {
    if (mLoadingIcon != null) {
        mLoadingIcon.clearAnimation();
    }
    if (mLoadingGroup != null) {
        mLoadingGroup.setVisibility(View.GONE);
    }
}
```


####`APIAsyncTask.java`:
Generic asyncTask for API requests ('GET/POST'). 

This is the constructor:

```
public APIAsyncTask(String urlString, ApiRequestType apiRequestType, HashMap<String, String> headers, HashMap<String, Object> urlDataParams, HashMap<String, Object> bodyDataParams,
                    OnResponseReceivedCallback onResponseReceivedCallback, OnDataParsedCallback onDataParsedCallback, Class<?> modelClass) {
    mUrlString = urlString;
    mApiRequestType = apiRequestType;
    mHeaders = headers;
    mUrlDataParams = urlDataParams;
    mBodyDataParams = bodyDataParams;
    mOnResponseReceivedCallbackWeakReference = new WeakReference<>(onResponseReceivedCallback);
    mOnDataParsedCallbackWeakReference = new WeakReference<>(onDataParsedCallback);
    mModelClass = modelClass;
}
```

* **`urlString`** (Sring): web service url.  
* **`apiRequestType`** (ApiRequestType): set the API Request type (enum).
* **`headers`** (HashMap): all header for the API request.
* **`urlDataParams`** (HashMap): all data to send in the url for the API request.
* **`bodyDataParams`** (HashMap): all data to send in the body for the API request.
* **`onResponseReceivedCallback`** (OnResponseReceivedCallback): where to send back the received JSON String. In our case, the **APIManager** object.
* **`onDataParsedCallback`** (OnDataParsedCallback): where the received parsed objects should be sent. Generally is the **Fragment/Activity** where we are going to use them to be shown or whatever.
* **`modelClass`** (Class<?>): Class of model that should be used to parse the received data.
 
Here is an example about how to use it for sending a request, in the APIManager.

```
public void allProducts(OnDataParsedCallback<Product> onDataParsedCallback){
    APIAsyncTask allProductsAsyncTask = new APIAsyncTask(ALL_PRODUCTS_URL, 
    											           false, 
    											           null, 
    											           null, 
    											           this, 
    											           onDataParsedCallback, 
    											           Product.class);
    allProductsAsyncTask.execute();
}
``` 

####`NetworkUtils.java`: 
Methods to build url params for 'GET' requests, body params for 'POST', URL from a String, parse json into objects.

* **`urlFromString`**:  
Method to convert an `String` into an `URL`.
* **`getUrlDataString`**:  
Method that formats an input `HashMap` with all the needed "pairs" to build a params URL `String` (usually for a 'GET, DELETE' request).
* **`getBodyDataString`**:  
Method that formats an input `HashMap` with all the needed "pairs" to build a body params `String` (usually for a 'POST, PUT' request).
* **`parseObjects`**:  
Method that receives a responseCode (int), a JSON String and a Model Class to parse a JSON received data into objects/one object of class especified by the input model class.  
Then, it sends via `OnDataParsedCallback` all parsed objects/one object to a callback (fragment/activity), received also as an input.

Note that the Model Class must implement a constructor that receives a `JSONObject` as input.  
An example of the constructor:

```
public Product(JSONObject json) throws JSONException, ParseException {
    mId = json.optString(ID_KEY);
    mName = json.optString(NAME_KEY);
    mDetail = json.optString(DESCRIPTION_KEY);
    mPublished = DateManager.dateFromString(json.optString(PUBLISHED_DATE_KEY), DATE_FORMAT);
    mIsSelling = json.optBoolean(SELLING_KEY);
    mPrice = json.optString(PRICE_KEY);
    mSeller = new User((JSONObject) json.opt(SELLER_KEY));
    mCategory = new Category((JSONObject) json.opt(CATEGORY_KEY));

    mImages = new ArrayList<>();
    JSONArray objectsArray = json.optJSONArray(IMAGES_KEY);
    for (int i = 0; i < objectsArray.length(); i++) {
        mImages.add(objectsArray.getString(i));
    }
}
```

####`Interfaces`:

* **`OnNetworkActivityCallback.java`**:  
Interface to notify that network activity has started/finished, for example to show/hide a "preloader". It is used in the `NetworkPreloaderActivity`.

* **`OnResponseReceived.java`**:  
Interface to return json String received in `APIAsyncTask.java` to the custom `APIManager`:

```
public class APIManager implements OnResponseReceivedCallback{

	// Custom API request...

    @Override
    public void onResponseReceived(int responseCode, String response, Class<?> modelClass, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference) {
        NetworkUtils.parseObjects(responseCode, response, modelClass, onDataParsedCallbackWeakReference);
    }
}
```

* **`OnDataParsedCallback.java`**:  
Interface to send **generic** object/one object parsed from received json data (received by `onResponseReceived.java`) to the fragment/activity that is going to use them.

```
public class ProductsFragment extends Fragment implements OnDataParsedCallback<Product> {
```

---

###Module `General Utils`:

####`DateManager.java`:  
Methods for `Date` formatting.

####`FormattingManager.java`:
Method for general formatting (`Strings`...).

####`KeyboardManager.java`:  
Method to hide keyboard automatically.

####`SharedPreferencesGeneralManager.java`:  
Methods to **save/get/delete items in SharedPreferences**.  
Methods to **convert a Custom Object into JSON String** to be a able to save/get it to SharedPreferences (Gson).

---

###Module `Custom Views`:

Module that includes Custom Views in which we can set the `fontName` directly in the **layout xml file**.
We have 3 classes in it:

* CustomFontButton
* CustomFontEditText
* CustomFontTextView

Here is an example of using them:

```
<com.farfromsober.customviews.CustomFontTextView
    xmlns:ffs="http://schemas.android.com/apk/res-auto"
    android:id="@+id/loading_text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="15dp"
    android:gravity="center"
    ffs:fontName="Raleway-Light.ttf"
    android:textColor="@color/loading_alert_text"
    android:textSize="17sp"
    tools:text="@string/loading_mock_text"/>
```
Custom font files should be added in **`app/src/main/assets/`**.

---

###Module `App`:

####Packages and Folders structure:

* `src/main`:
  - `assets`: Custom font files
  - `java/com.farfromsober.ffs`:
    - `activities`: Custom Activity classes.
    - `adapters`: Adapters for tables, grids...
    - `callbacks`: Interfaces (one file for each one).
    - `fragments`: Custom Fragment classes.
    - `model`: Model classes.
    - `network`: All specific classes for this project that have to do with network communication. `APIManager.java` is placed there.
    - `utils`: All specific utils for this project. `SharedPreferencesManager.java` is placed there.
  - `res`:
    - `color`: `xml` files for color selectors.
    - `drawable`: image resources, and image selectors.
    - `layout`: `xml` layout files.
    - `menu`: `xml` menu files.
    - `values`: default resources files.
    - `values-en`: string resources file for English language.
    - `values-v21`: resources files for versions above 21.
    - `values-w820dp`: resources files for devices with screen width greated than 820pd.
    
    
    

####`MainActivity.java`:

Activity for main navigation (with Drawer).  
[Android Developers Navigation Drawer](http://developer.android.com/intl/es/training/implementing-navigation/nav-drawer.html)  
[Navigation Drawer manual 1 (sgoliver)](http://www.sgoliver.net/blog/interfaz-de-usuario-en-android-navigation-drawer/)  
[Navigation Drawer manual 2 (hermosaprogramacion)](http://www.hermosaprogramacion.com/2014/11/android-navigation-drawer-tutorial/)

* Menu options get loaded from `strings.xml` (ESP/ENG versions), so there no need to change code for adding/removing/editing them. 

```
<string-array name="DrawerMenuTitles">
    <item>Productos</item>
    <item>Mapa</item>
    <item>Notificaciones</item>
    <item>Perfil</item>
</string-array>

<string-array name="DrawerMenuIconsOff">
    <item>drawer_products_item_icon_off</item>
    <item>drawer_map_item_icon_off</item>
    <item>drawer_notifications_item_icon_off</item>
    <item>drawer_profile_item_icon_off</item>
</string-array>

<string-array name="DrawerMenuIconsOn">
    <item>drawer_products_item_icon_on</item>
    <item>drawer_map_item_icon_on</item>
    <item>drawer_notifications_item_icon_on</item>
    <item>drawer_profile_item_icon_on</item>
</string-array>
```
 
* A "preloader layout" (`loading_alert.xml`) has been included into `activity_main.xml` to be shown/hide within network activity.
* Change `getFragmentToNavigateTo(int position)` to change fragment to be loaded when menu button is clicked.


####`APIManager.java`:

Class to manage all API requests. The class implements `OnResponseReceivedCallback` to receive the JSON String from the `APIAsyncTask`, and then uses `NetworkUtils.parseObjects()` method to parse the `String` into custom objects set by `Class<?> modelClass` attribute.

Here is an example with two custom API requests:

```
public class APIManager implements OnResponseReceivedCallback{
    private static final String ALL_PRODUCTS_URL = "http://beta.json-generator.com/api/json/get/NyJpZWxQl";
    private static final String ALL_USERS_URL = "http://beta.json-generator.com/api/json/get/NJsNmZgQe";

    public void allProducts(OnDataParsedCallback<Product> onDataParsedCallback){
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(ALL_PRODUCTS_URL, 
        										              false, 
        										              null, 
        										              null, 
        										              this, 
        										              onDataParsedCallback, 
        										              Product.class);
        allProductsAsyncTask.execute();
    }

    public void allUsers(OnDataParsedCallback<User> onDataParsedCallback){
        APIAsyncTask allUsersAsyncTask = new APIAsyncTask(ALL_USERS_URL, 
        										           false, 
        										           null, 
        										           null, 
        										           this, 
        										           onDataParsedCallback, 
        										           User.class);
        allUsersAsyncTask.execute();
    }

    @Override
    public void onResponseReceived(int responseCode, String response, Class<?> modelClass, 
    							    WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference) {
        NetworkUtils.parseObjects(responseCode, response, modelClass, onDataParsedCallbackWeakReference);
    }
}
```

####`SharedPreferencesManager.java`:
Custom Shared Preferences Manager that uses `SharedPreferencesGeneralManager`methods to save/get/delete data into Shared Preferences.

Here is an example of how to use it:

```
public class SharedPreferencesManager {


    //region Keys
    private static final String PREF_LOGIN_USER = "com.farfromsober.generalutils.SharedPreferencesGeneralManager.PREF_LOGIN_USER";
    //endregion


    //region Save Preferences
    public static void savePrefLoginUser(Context context, String value) {
        SharedPreferencesGeneralManager.savePreferece(context, PREF_LOGIN_USER, value);
    }
    //endregion


    //region Save Preferences
    public static String getPrefLoginUser(Context context) {
        return SharedPreferencesGeneralManager.getPreferenceString(context, PREF_LOGIN_USER);
    }
    //endregion


    // region Remove Preferences
    public static void removePrefLoginUser(Context context) {
        SharedPreferencesGeneralManager.removePreference(context, PREF_LOGIN_USER);
    }
    //endregion
}
```


####Model Classes:

All model classes include 2 really important methods:

* Constructor receiving a `JSONObject`:  
All the model objects that should be parsed from a JSON received data must implement this constructor, if we want to used The `Network` module code prepared for this.
* `toHashMap()` method:  
All model objects that needs to be sent to server via API request ('GET'/'POST'), should implement this method, to be able to use `Network` module code prepared for this.

Here is an example:

```
public class Category {

	// Keys for parsing JSON data received
    private static final String INDEX_KEY = "index";
    private static final String NAME_KEY = "name";

    private String mName;
    private double mIndex;

    public Category(String name, double index) {
        super();
        mName = name;
        mIndex = index;
    }

    public Category(JSONObject json) {
        mName = json.optString(NAME_KEY);
        mIndex = json.optDouble(INDEX_KEY);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getIndex() {
        return mIndex;
    }

    public void setIndex(double index) {
        mIndex = index;
    }

    @Override
    public String toString() {
        return "Category{" +
                "mName='" + mName + '\'' +
                ", mIndex=" + mIndex +
                '}';
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(NAME_KEY, getName());
        hashMap.put(INDEX_KEY, getIndex());

        return hashMap;
    }
}
```





