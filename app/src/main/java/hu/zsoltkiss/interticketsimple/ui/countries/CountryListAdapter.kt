package hu.zsoltkiss.interticketsimple.ui.countries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.zsoltkiss.interticketsimple.R
import hu.zsoltkiss.interticketsimple.data.model.Country

class CountryListAdapter(): RecyclerView.Adapter<CountryItemViewHolder>() {

    private val countries = mutableListOf<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false)
        return CountryItemViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        holder.countryNameTextView.text = countries[position].name
        holder.capitalTextView.text = countries[position].capital
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    fun updateItems(newItems: List<Country>) {
        countries.clear()
        countries.addAll(newItems)
        notifyDataSetChanged()
    }
}


class CountryItemViewHolder(v: View): RecyclerView.ViewHolder(v) {
    val countryNameTextView: TextView = v.findViewById(R.id.tvCountryName)
    val capitalTextView: TextView = v.findViewById(R.id.tvCapital)
}