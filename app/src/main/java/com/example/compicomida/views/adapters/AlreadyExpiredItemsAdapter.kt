package com.example.compicomida.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.compicomida.CompiComidaApp
import com.example.compicomida.R
import com.example.compicomida.model.localDb.entities.PantryItem
import com.example.compicomida.views.adapters.diff.PantryDiffCallback
import net.nicbell.materiallists.ListItem
import java.time.format.DateTimeFormatter

class AlreadyExpiredItemsAdapter(
    private var pantryItemList: List<PantryItem>,
) : RecyclerView.Adapter<AlreadyExpiredItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutItem = R.layout.recycler_already_expire_list
        val view =
            LayoutInflater.from(viewGroup.context).inflate(layoutItem, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(pantryItemList[position])

    override fun getItemCount(): Int = pantryItemList.size

    fun updateList(expireList: List<PantryItem>) {
        val diffCallback = PantryDiffCallback(pantryItemList, expireList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        pantryItemList = expireList
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val pantryElement = view.findViewById<ListItem>(R.id.alreadyExpireItemElement)

        fun bind(pantryItem: PantryItem) {

            val unit = pantryItem.unit ?: ""
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val expireOn = "Venció el ${pantryItem.expirationDate.format(formatter)}"

            pantryElement.headline.text = itemView.context.getString(
                R.string.expire_items_headline_text,
                pantryItem.pantryName,
                CompiComidaApp.appModule.parseQuantity(pantryItem.quantity),
                unit
            )
            pantryElement.supportText.text = expireOn


        }

    }


}