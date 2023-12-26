package adapters

import ItemModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ItemModelAdapter(private var itemList: List<ItemModel>) :
    RecyclerView.Adapter<ItemModelAdapter.ViewHolder>() {

    fun updateData(newItemList: List<ItemModel>) {
        itemList = newItemList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        // Bind data to the ViewHolder's views
        holder.sNo.text = "${item.sNo}"
        holder.item.text = "${item.item}"
        holder.current.text = "${item.currentLocation}"
        holder.status.text = "${item.status}"
        holder.new.text = "${item.newLocation}"
        holder.unit.text = "${item.unit}"
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sNo: TextView = itemView.findViewById(R.id.sNoField)
        val item: TextView = itemView.findViewById(R.id.itemField)
        val current: TextView = itemView.findViewById(R.id.currentField)
        val status: TextView = itemView.findViewById(R.id.statusField)
        val new: TextView = itemView.findViewById(R.id.newField)
        val unit: TextView = itemView.findViewById(R.id.unitField)
    }
}
