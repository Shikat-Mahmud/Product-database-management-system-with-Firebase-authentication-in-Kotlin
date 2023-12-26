import com.opencsv.bean.CsvBindByName

data class ItemModel(
    @CsvBindByName(column = "SNo") var sNo: String? = null,
    @CsvBindByName(column = "Item") var item: String? = null,
    @CsvBindByName(column = "Current Location") var currentLocation: String? = null,
    @CsvBindByName(column = "New Location") var newLocation: String? = null,
    @CsvBindByName(column = "Status") var status: String? = null,
    @CsvBindByName(column = "Unit") var unit: String? = null
)
